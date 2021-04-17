package com.mediary.Services.Interfaces;

import java.util.List;

import com.mediary.Models.DTOs.Request.AddTestResultDto;
import com.mediary.Models.DTOs.Response.GetTestResultDto;
import com.mediary.Models.Entities.TestResultEntity;

import org.springframework.web.multipart.MultipartFile;

public interface ITestResultService {

    void addTestResult(AddTestResultDto testResult, MultipartFile[] files, Integer userId) throws Exception;

    AddTestResultDto getJson(String testResult);

    List<GetTestResultDto> getTestResultsByUser(Integer userId);

    List<GetTestResultDto> testResultsToDtos(List<TestResultEntity> testResultEntities);

    GetTestResultDto testResultToDto(TestResultEntity testResultEntity);
}
