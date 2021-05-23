package com.mediary.Services.Interfaces;

import java.util.Collection;

import com.mediary.Models.DTOs.Response.GetFileDto;
import com.mediary.Models.Entities.FileEntity;
import com.mediary.Models.Entities.TestResultEntity;
import com.mediary.Services.Exceptions.BlobStorageException;
import com.mediary.Services.Exceptions.EntityNotFoundException;

import org.springframework.web.multipart.MultipartFile;

public interface IFileService {

    public boolean uploadFile(MultipartFile file, Integer userId, TestResultEntity testResult)
            throws BlobStorageException;

    public boolean deleteFile(Integer fileId) throws BlobStorageException, EntityNotFoundException;

    public boolean checkUserPermission(Integer userId, FileEntity fileEntity);

    public Collection<GetFileDto> filesToDtos(Collection<FileEntity> files);

    public GetFileDto fileToDto(FileEntity file);


}
