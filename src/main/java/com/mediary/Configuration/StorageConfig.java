package com.mediary.Configuration;

import com.azure.storage.blob.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

@Configuration
public class StorageConfig {

    @Autowired
    private Environment environment;

    @Bean
    public BlobServiceClient blobServiceClient() {
        return new BlobServiceClientBuilder()
                .connectionString(environment.getProperty("azure.storage.connectionString")).buildClient();
    }

    @Bean
    public BlobContainerClient blobClient() {
        return blobServiceClient().getBlobContainerClient(environment.getProperty("azure.storage.containerName"));
    }

}