package com.mediary.Services.Interfaces;

import java.util.Collection;
import java.util.List;

import com.mediary.Models.Dtos.Request.AddTestResultItemDto;
import com.mediary.Models.Dtos.Response.GetTestResultItemDto;
import com.mediary.Models.Entities.TestResultEntity;
import com.mediary.Models.Entities.TestResultItemEntity;

public interface ITestResultItemService {

    void addTestResultItems(Collection<AddTestResultItemDto> testResultItemDtos, TestResultEntity testResult);

    void addTestResultItem(AddTestResultItemDto testResultItemDto, TestResultEntity testResult);

    AddTestResultItemDto getJson(String testResultItem);

    List<GetTestResultItemDto> getAllByTestResultId(Integer testResultId);

    Collection<GetTestResultItemDto> testResultItemsToDtos(Collection<TestResultItemEntity> testResultItems);

    GetTestResultItemDto testResultItemToDto(TestResultItemEntity testResultItem);

}
