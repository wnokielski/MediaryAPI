package com.mediary.Services.Interfaces;

import java.util.List;

import com.mediary.Models.DTOs.Request.AddMedicalRecordDto;
import com.mediary.Models.DTOs.Request.UpdateMedicalRecordDto;
import com.mediary.Models.DTOs.Request.UpdateTestItemDto;
import com.mediary.Models.DTOs.Response.GetMedicalRecordDto;
import com.mediary.Models.DTOs.UserDto;
import com.mediary.Models.Entities.MedicalRecordEntity;
import com.mediary.Models.Entities.UserEntity;
import com.mediary.Services.Exceptions.*;

import org.springframework.web.multipart.MultipartFile;

public interface IMedicalRecordService {

    GetMedicalRecordDto addMedicalRecordByAuthHeader(AddMedicalRecordDto medicalRecord, MultipartFile[] files, String authHeader)
            throws EntityNotFoundException, IncorrectFieldException, BlobStorageException, EnumConversionException;

    GetMedicalRecordDto addMedicalRecord(AddMedicalRecordDto medicalRecord, MultipartFile[] files, UserEntity user)
            throws EntityNotFoundException, IncorrectFieldException, BlobStorageException, EnumConversionException;

    AddMedicalRecordDto getJson(String medicalRecord);

    List<GetMedicalRecordDto> getMedicalRecordsByAuthHeader(String authHeader) throws EntityNotFoundException;

    List<GetMedicalRecordDto> getMedicalRecordsByUser(UserEntity user);

    List<GetMedicalRecordDto> medicalRecordsToDtos(List<MedicalRecordEntity> medicalRecordEntities);

    GetMedicalRecordDto medicalRecordToDto(MedicalRecordEntity medicalRecordEntity);

    int deleteMedicalRecord(UserDto user, Integer medicalRecordId) throws BlobStorageException, EntityNotFoundException;

    List<GetMedicalRecordDto> getMedicalRecordsSorted(List<GetMedicalRecordDto> medicalRecords, String sortType);

    List<GetMedicalRecordDto> sortByToday(List<GetMedicalRecordDto> medicalRecords);

    List<GetMedicalRecordDto> sortByLastWeek(List<GetMedicalRecordDto> medicalRecords);

    List<GetMedicalRecordDto> sortByLastMonth(List<GetMedicalRecordDto> medicalRecords);

    List<GetMedicalRecordDto> sortByLastYear(List<GetMedicalRecordDto> medicalRecords);

    List<GetMedicalRecordDto> sortByPast(List<GetMedicalRecordDto> medicalRecords);

    GetMedicalRecordDto updateMedicalRecordById(UpdateMedicalRecordDto medicalRecord, String authHeader, Integer medicalRecordId, MultipartFile[] files)
            throws EntityNotFoundException, EntityDoesNotBelongToUser, IncorrectFieldException, EnumConversionException, BlobStorageException;

    void updateTestItemById(UpdateTestItemDto medicalRecordItem, String authHeader, Integer medicalRecordItemId, MedicalRecordEntity medicalRecordEntity) throws EntityNotFoundException, IncorrectFieldException, EntityDoesNotBelongToUser;

    List<GetMedicalRecordDto> getScheduleItemByAuthHeaderAndDate(String authHeader, String dateFrom, String dateTo) throws EntityNotFoundException;
}
