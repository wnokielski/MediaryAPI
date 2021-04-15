package com.mediary.Services.Interfaces;

import java.util.Collection;
import java.util.List;

import com.mediary.Models.Dtos.Request.AddTestResultItemDto;
import com.mediary.Models.Dtos.Response.GetTestResultItemDto;
import com.mediary.Models.Entities.TestResultEntity;
import com.mediary.Models.Entities.TestResultItemEntity;

public interface ITestResultItemService {

    public void addTestResultItems(Collection<AddTestResultItemDto> testResultItemDtos, TestResultEntity testResult);

    public void addTestResultItem(AddTestResultItemDto testResultItemDto, TestResultEntity testResult);

    public List<GetTestResultItemDto> getAllByTestResultId(Integer testResultId);

    public Collection<GetTestResultItemDto> testResultItemsToDtos(Collection<TestResultItemEntity> testResultItems);

    public GetTestResultItemDto testResultItemToDto(TestResultItemEntity testResultItem);

}
