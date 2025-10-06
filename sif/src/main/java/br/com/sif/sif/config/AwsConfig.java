package br.com.sif.sif.config;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder; // 1. Importe este
import com.amazonaws.services.textract.AmazonTextract;
import com.amazonaws.services.textract.AmazonTextractClientBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AwsConfig {

    @Value("${aws.accessKeyId}")
    private String accessKey;

    @Value("${aws.secretKey}")
    private String secretKey;

    @Value("${aws.region}")
    private String region;

    @Bean
    public AmazonTextract amazonTextract() {
        AWSCredentials credentials = new BasicAWSCredentials(accessKey, secretKey);
        
        // 2. Criamos uma configuração de endpoint explícita
        AwsClientBuilder.EndpointConfiguration endpointConfiguration = 
                new AwsClientBuilder.EndpointConfiguration(
                        String.format("https://textract.%s.amazonaws.com", region),
                        region
                );

        return AmazonTextractClientBuilder.standard()
                .withCredentials(new AWSStaticCredentialsProvider(credentials))
                // 3. Usamos a configuração de endpoint em vez de apenas a região
                .withEndpointConfiguration(endpointConfiguration)
                .build();
    }
}