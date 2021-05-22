package com.mediary.Services.Interfaces;

import java.util.List;

import com.mediary.Models.DTOs.Request.AddMedicalRecordDto;
import com.mediary.Models.DTOs.Response.GetMedicalRecordDto;
import com.mediary.Models.Entities.MedicalRecordEntity;
import com.mediary.Models.Entities.UserEntity;
import com.mediary.Services.Exceptions.BlobStorageException;
import com.mediary.Services.Exceptions.EntityNotFoundException;
import com.mediary.Services.Exceptions.IncorrectFieldException;

import org.springframework.web.multipart.MultipartFile;

public interface IMedicalRecordService {

    void addMedicalRecordByAuthHeader(AddMedicalRecordDto medicalRecord, MultipartFile[] files, String authHeader)
            throws EntityNotFoundException, IncorrectFieldException, BlobStorageException;

    void addMedicalRecord(AddMedicalRecordDto medicalRecord, MultipartFile[] files, UserEntity user)
            throws EntityNotFoundException, IncorrectFieldException, BlobStorageException;

    AddMedicalRecordDto getJson(String medicalRecord);

    List<GetMedicalRecordDto> getMedicalRecordsByAuthHeader(String authHeader) throws EntityNotFoundException;

    List<GetMedicalRecordDto> getMedicalRecordsByUser(UserEntity user);

    List<GetMedicalRecordDto> medicalRecordsToDtos(List<MedicalRecordEntity> medicalRecordEntities);

    GetMedicalRecordDto medicalRecordToDto(MedicalRecordEntity medicalRecordEntity);
}
