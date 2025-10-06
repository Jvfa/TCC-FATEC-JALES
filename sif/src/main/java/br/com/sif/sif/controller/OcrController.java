package br.com.sif.sif.controller;

import com.amazonaws.services.textract.AmazonTextract;
import com.amazonaws.services.textract.model.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/ocr")
public class OcrController {

    private final AmazonTextract amazonTextract;

    public OcrController(AmazonTextract amazonTextract) {
        this.amazonTextract = amazonTextract;
    }

    @PostMapping("/upload")
    public ResponseEntity<Map<String, String>> uploadParaOcr(@RequestParam("arquivo") MultipartFile arquivo) {
        try {
            ByteBuffer imageBytes = ByteBuffer.wrap(arquivo.getBytes());

            // Usamos 'AnalyzeDocument' com 'FORMS' para extrair pares de chave-valor (ex: "Nome:" -> "João")
            AnalyzeDocumentRequest request = new AnalyzeDocumentRequest()
                    .withDocument(new Document().withBytes(imageBytes))
                    .withFeatureTypes(FeatureType.FORMS);

            AnalyzeDocumentResult result = amazonTextract.analyzeDocument(request);

            Map<String, String> dadosExtraidos = parseKeyValuePairs(result.getBlocks());

            return ResponseEntity.ok(dadosExtraidos);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).build();
        }
    }

    // Método auxiliar para processar a resposta do Textract
    private Map<String, String> parseKeyValuePairs(List<Block> blocks) {
        Map<String, Block> blockMap = new HashMap<>();
        Map<String, Block> valueMap = new HashMap<>();
        Map<String, Block> keyMap = new HashMap<>();

        blocks.forEach(block -> {
            blockMap.put(block.getId(), block);
            if ("KEY_VALUE_SET".equals(block.getBlockType())) {
                if (block.getEntityTypes().contains("KEY")) {
                    keyMap.put(block.getId(), block);
                } else {
                    valueMap.put(block.getId(), block);
                }
            }
        });

        Map<String, String> extractedData = new HashMap<>();
        keyMap.forEach((keyId, keyBlock) -> {
            Block valueBlock = findValueBlock(keyBlock, valueMap);
            String key = getText(keyBlock, blockMap);
            String value = getText(valueBlock, blockMap);
            extractedData.put(key, value);
        });

        return extractedData;
    }

    private Block findValueBlock(Block keyBlock, Map<String, Block> valueMap) {
        for (Relationship relationship : keyBlock.getRelationships()) {
            if ("VALUE".equals(relationship.getType())) {
                for (String valueId : relationship.getIds()) {
                    return valueMap.get(valueId);
                }
            }
        }
        return null;
    }

    private String getText(Block block, Map<String, Block> blockMap) {
        if (block == null || block.getRelationships() == null) return "";
        StringBuilder text = new StringBuilder();
        for (Relationship relationship : block.getRelationships()) {
            if ("CHILD".equals(relationship.getType())) {
                for (String childId : relationship.getIds()) {
                    Block childBlock = blockMap.get(childId);
                    if ("WORD".equals(childBlock.getBlockType())) {
                        text.append(childBlock.getText()).append(" ");
                    }
                }
            }
        }
        return text.toString().trim().replace(":", "");
    }
}
