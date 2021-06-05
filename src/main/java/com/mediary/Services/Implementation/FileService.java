package com.mediary.Services.Implementation;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.mediary.Models.DTOs.Request.UpdateMedicalRecordDto;
import com.mediary.Models.DTOs.Response.GetFileDto;
import com.mediary.Models.DTOs.Response.GetMedicalRecordDto;
import com.mediary.Models.Entities.FileEntity;
import com.mediary.Models.Entities.MedicalRecordEntity;
import com.mediary.Models.Entities.UserEntity;
import com.mediary.Repositories.FileRepository;
import com.mediary.Repositories.MedicalRecordRepository;
import com.mediary.Services.Exceptions.BlobStorageException;
import com.mediary.Services.Exceptions.EntityNotFoundException;
import com.mediary.Services.Interfaces.IFileService;
import com.mediary.Services.Interfaces.IStorageService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class FileService implements IFileService {

    @Value("${azure.storage.containerName}")
    private String containerName;

    @Autowired
    FileRepository fileRepository;

    @Autowired
    MedicalRecordRepository medicalRecordRepository;

    @Autowired
    IStorageService storageService;

    public boolean uploadFile(MultipartFile file, Integer userId, MedicalRecordEntity medicalRecord)
            throws BlobStorageException {
        boolean success = false;
        String fileName = file.getOriginalFilename();
        String blobName = storageService.generateBlobName(fileName, userId.toString());
        String url = null;
        try {
            url = storageService.uploadBlob(file, blobName, containerName);
        } catch (Exception e) {
            log.warn("Storage service problem");
            throw new BlobStorageException("File upload problem occured");
        }

        if (url != null) {
            success = true;
        }
        FileEntity fileEntity = new FileEntity();
        fileEntity.setUuid(blobName);
        fileEntity.setOriginalName(fileName);
        fileEntity.setUrl(url);
        fileEntity.setMedicalRecordById(medicalRecord);
        fileRepository.save(fileEntity);
        fileRepository.flush();

        return success;
    }

    public boolean deleteFile(Integer fileId) throws BlobStorageException, EntityNotFoundException {
        FileEntity file = fileRepository.findById(fileId);
        if (file == null) {
            log.warn("file with specified Id doesn't exist");
            throw new EntityNotFoundException("file with specified Id doesn't exist");
        } else {
            boolean success = false;
            try {
                success = storageService.deleteBlob(file.getUuid(), containerName);
            } catch (Exception e) {
                throw new BlobStorageException("File deletion problem occured");
            }
            if (success) {
                fileRepository.delete(file);
                fileRepository.flush();
                return true;
            } else {
                log.warn("Specified blob weren't deleted");
                return false;
            }
        }
    }

    @Override
    public boolean checkUserPermission(Integer userId, FileEntity fileEntity) {
        String blobName = fileEntity.getUuid();
        int index = blobName.indexOf("_");
        String ownerId = blobName.substring(0, index);
        boolean isPermited = ownerId.equals(userId.toString());
        return isPermited;
    }

    @Override
    public Collection<GetFileDto> filesToDtos(Collection<FileEntity> files) {
        Collection<GetFileDto> fileDtos = new ArrayList<GetFileDto>();
        for (FileEntity fileEntity : files) {
            var fileDto = fileToDto(fileEntity);
            fileDtos.add(fileDto);
        }
        return fileDtos;
    }

    @Override
    public GetFileDto fileToDto(FileEntity file) {
        GetFileDto fileDto = new GetFileDto();
        fileDto.setId(file.getId());
        fileDto.setOriginalName(file.getOriginalName());
        fileDto.setUrl(file.getUrl());
        return fileDto;
    }

    @Override
    public List<FileEntity> getFilesByMedicalRecord(Integer medicalRecordID) {
        return fileRepository.findByMedicalRecordId(medicalRecordID);
    }

}
