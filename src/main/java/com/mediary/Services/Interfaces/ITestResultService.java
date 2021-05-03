package com.mediary.Services.Interfaces;

import java.util.List;

import com.mediary.Models.DTOs.Request.AddTestResultDto;
import com.mediary.Models.DTOs.Response.GetTestResultDto;
import com.mediary.Models.Entities.TestResultEntity;
import com.mediary.Models.Entities.UserEntity;
import com.mediary.Services.Exceptions.BlobStorageException;
import com.mediary.Services.Exceptions.EntityNotFoundException;
import com.mediary.Services.Exceptions.IncorrectFieldException;

import org.springframework.web.multipart.MultipartFile;

public interface ITestResultService {

    void addTestResultByAuthHeader(AddTestResultDto testResult, MultipartFile[] files, String authHeader)
            throws EntityNotFoundException, IncorrectFieldException, BlobStorageException;

    void addTestResult(AddTestResultDto testResult, MultipartFile[] files, UserEntity user)
            throws EntityNotFoundException, IncorrectFieldException, BlobStorageException;

    AddTestResultDto getJson(String testResult);

    List<GetTestResultDto> getTestResultsByAuthHeader(String authHeader) throws EntityNotFoundException;

    List<GetTestResultDto> getTestResultsByUser(UserEntity user);

    List<GetTestResultDto> testResultsToDtos(List<TestResultEntity> testResultEntities);

    GetTestResultDto testResultToDto(TestResultEntity testResultEntity);
}
