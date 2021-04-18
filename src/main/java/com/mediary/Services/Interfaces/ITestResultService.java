package com.mediary.Services.Interfaces;

import java.util.List;

import com.mediary.Models.DTOs.Request.AddTestResultDto;
import com.mediary.Models.DTOs.Response.GetTestResultDto;
import com.mediary.Models.Entities.TestResultEntity;
import com.mediary.Services.Exceptions.BlobStorageException;
import com.mediary.Services.Exceptions.EntityNotFoundException;
import com.mediary.Services.Exceptions.IncorrectFieldException;

import org.springframework.web.multipart.MultipartFile;

public interface ITestResultService {

    void addTestResult(AddTestResultDto testResult, MultipartFile[] files, Integer userId)
            throws EntityNotFoundException, IncorrectFieldException, BlobStorageException;

    AddTestResultDto getJson(String testResult);

    List<GetTestResultDto> getTestResultsByUser(Integer userId) throws EntityNotFoundException;

    List<GetTestResultDto> testResultsToDtos(List<TestResultEntity> testResultEntities);

    GetTestResultDto testResultToDto(TestResultEntity testResultEntity);
}
