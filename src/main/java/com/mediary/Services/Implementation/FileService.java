package com.mediary.Services.Implementation;

import java.util.ArrayList;
import java.util.Collection;

import com.mediary.Models.Dtos.Response.GetFileDto;
import com.mediary.Models.Entities.FileEntity;
import com.mediary.Models.Entities.TestResultEntity;
import com.mediary.Repositories.FileRepository;
import com.mediary.Repositories.TestResultRepository;
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

    @Value("${azure.storage.containerName")
    private String containerName;

    @Autowired
    FileRepository fileRepository;

    @Autowired
    TestResultRepository testResultRepository;

    @Autowired
    IStorageService storageService;

    public boolean uploadFile(MultipartFile file, Integer userId, TestResultEntity testResult) {
        boolean success = false;
        String fileName = file.getName();
        String blobName = storageService.generateBlobName(fileName, userId.toString());
        String url = storageService.uploadBlob(file, blobName, containerName);
        if (url != null) {
            success = true;
        }
        FileEntity fileEntity = new FileEntity();
        fileEntity.setUuid(blobName);
        fileEntity.setOriginalname(fileName);
        fileEntity.setUrl(url);
        fileEntity.setTestresultByTestresultid(testResult);
        fileRepository.save(fileEntity);
        fileRepository.flush();

        return success;
    }

    public boolean deleteFile(Integer fileId) {
        FileEntity file = fileRepository.findById(fileId);
        if (file == null) {
            log.warn("file with specified Id doesn't exist");
            return false;
        } else {
            var success = storageService.deleteBlob(file.getUuid(), containerName);
            if (success) {
                fileRepository.delete(file);
                fileRepository.flush();
                return true;
            } else {
                log.warn("Specified blob wasn't deleted");
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
        fileDto.setOriginalName(file.getOriginalname());
        fileDto.setUrl(file.getUrl());
        return fileDto;
    }

}
