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
import java.util.stream.Collectors;

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

            // CORREÇÃO: Voltamos a usar 'FORMS' que é compatível com a chamada síncrona.
            AnalyzeDocumentRequest request = new AnalyzeDocumentRequest()
                    .withDocument(new Document().withBytes(imageBytes))
                    .withFeatureTypes(FeatureType.FORMS);

            AnalyzeDocumentResult result = amazonTextract.analyzeDocument(request);

            // Usamos a lógica original para interpretar os pares de chave-valor
            Map<String, String> dadosExtraidos = parseKeyValuePairs(result.getBlocks());

            return ResponseEntity.ok(dadosExtraidos);
        } catch (Exception e) {
            e.printStackTrace(); // O erro detalhado aparecerá aqui no console do backend
            return ResponseEntity.status(500).build();
        }
    }

    // Método auxiliar para processar a resposta do Textract
    private Map<String, String> parseKeyValuePairs(List<Block> blocks) {
        Map<String, Block> blockMap = blocks.stream().collect(Collectors.toMap(Block::getId, block -> block));
        Map<String, Block> keyMap = new HashMap<>();
        Map<String, Block> valueMap = new HashMap<>();

        blocks.forEach(block -> {
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
            if (valueBlock != null) {
                String key = getText(keyBlock, blockMap);
                String value = getText(valueBlock, blockMap);
                if (!key.isEmpty() && !value.isEmpty()) {
                    extractedData.put(key, value);
                }
            }
        });

        return extractedData;
    }

    private Block findValueBlock(Block keyBlock, Map<String, Block> valueMap) {
        if (keyBlock.getRelationships() == null) return null;
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
                    if (childBlock != null && "WORD".equals(childBlock.getBlockType())) {
                        text.append(childBlock.getText()).append(" ");
                    }
                }
            }
        }
        // Remove o ':' do final das chaves e espaços extras
        return text.toString().trim().replaceAll(":$", "").trim();
    }
}
