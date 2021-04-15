package com.mediary.Services.Interfaces;

import com.mediary.Models.Entities.FileEntity;
import com.mediary.Models.Entities.TestResultEntity;

import org.springframework.web.multipart.MultipartFile;

public interface IFileService {

    public boolean uploadFile(MultipartFile file, Integer userId, TestResultEntity testResult);

    public boolean deleteFile(Integer fileId);

    public boolean checkUserPermission(Integer userId, FileEntity fileEntity);

}
