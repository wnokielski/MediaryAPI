package com.mediary.Services.Interfaces;

import java.util.List;

import com.mediary.Models.DTOs.Request.AddTestResultDto;
import com.mediary.Models.DTOs.Response.GetTestResultDto;
import com.mediary.Models.DTOs.UserDto;
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

    int deleteTestResult(UserDto user, Integer testResultId) throws BlobStorageException, EntityNotFoundException;

    List<GetTestResultDto> getTestResultsSorted(List<GetTestResultDto> testResults, String sortType);

    List<GetTestResultDto> sortByToday(List<GetTestResultDto> testResults);

    List<GetTestResultDto> sortByLastWeek(List<GetTestResultDto> testResults);

    List<GetTestResultDto> sortByLastMonth(List<GetTestResultDto> testResults);

    List<GetTestResultDto> sortByLastYear(List<GetTestResultDto> testResults);

    List<GetTestResultDto> sortByPast(List<GetTestResultDto> testResults);
}
