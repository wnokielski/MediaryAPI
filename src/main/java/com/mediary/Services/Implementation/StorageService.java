package com.mediary.Services.Implementation;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.azure.storage.blob.BlobContainerClient;
import com.azure.storage.blob.models.BlobItem;
import com.mediary.Services.Interfaces.IStorageService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class StorageService implements IStorageService {

    @Autowired
    BlobContainerClient containerClient;

    @Override
    public boolean deleteBlob(String blobName, String containerName) {
        try {
            var blobClient = containerClient.getBlobClient(blobName);
            blobClient.delete();
            return true;
        } catch (Exception e) {
            return false;
        }

    }

    @Override
    public byte[] downloadBlob(String blobName, String containerName) {
        var blobClient = containerClient.getBlobClient(blobName);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        blobClient.download(outputStream);
        var blob = outputStream.toByteArray();
        return blob;
    }

    @Override
    public List<String> listBlobs(String containerName) {
        var items = new ArrayList<String>();

        for (BlobItem blobItem : containerClient.listBlobs()) {
            items.add(blobItem.getName());
        }
        return items;
    }

    @Override
    public String uploadBlob(MultipartFile file, String blobName, String containerName) {
        var blobClient = containerClient.getBlobClient(blobName);
        if (blobClient.exists()) {
            return null;
        } else {
            try {
                blobClient.upload(file.getInputStream(), file.getSize());
            } catch (IOException e) {
                return null;
            }
        }
        var fileUrl = blobClient.getBlobUrl();
        return fileUrl;
    }

    @Override
    public String generateBlobName(String fileName, String userId) {
        String blobName = userId + "_" + LocalDateTime.now() + "_" + fileName;
        return blobName;
    }
}
