package com.mediary.Services.Interfaces;

import java.util.List;

import com.azure.storage.blob.BlobContainerClient;

import org.springframework.web.multipart.MultipartFile;

public interface IStorageService {

    BlobContainerClient getContainerClient(String containerName);

    boolean deleteBlob(String blobName, String containerName);

    byte[] downloadBlob(String blobName, String containerName);

    List<String> listBlobs(String containerName);

    String uploadBlob(MultipartFile file, String blobName, String containerName);

    String generateBlobName(String fileName, String userId);
}
