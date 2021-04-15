package com.mediary.Services.Interfaces;

import java.util.List;

import com.mediary.Models.Dtos.Request.AddTestResultDto;
import com.mediary.Models.Dtos.Response.GetTestResultDto;
import com.mediary.Models.Entities.TestResultEntity;

public interface ITestResultService {

    void addTestResult(AddTestResultDto testResultDto, Integer userId) throws Exception;

    List<GetTestResultDto> getTestResultsByUser(Integer userId);

    List<GetTestResultDto> testResultsToDtos(List<TestResultEntity> testResultEntities);

    GetTestResultDto testResultToDto(TestResultEntity testResultEntity);
}
