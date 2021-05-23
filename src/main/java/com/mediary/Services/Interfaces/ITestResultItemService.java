package com.mediary.Services.Interfaces;

import java.util.Collection;
import java.util.List;

import com.mediary.Models.DTOs.Request.AddTestResultItemDto;
import com.mediary.Models.DTOs.Response.GetTestResultItemDto;
import com.mediary.Models.Entities.TestResultEntity;
import com.mediary.Models.Entities.TestResultItemEntity;
import com.mediary.Services.Exceptions.EntityNotFoundException;
import com.mediary.Services.Exceptions.IncorrectFieldException;

public interface ITestResultItemService {

    void addTestResultItems(Collection<AddTestResultItemDto> testResultItemDtos, TestResultEntity testResult)
            throws IncorrectFieldException;

    void addTestResultItem(AddTestResultItemDto testResultItemDto, TestResultEntity testResult)
            throws IncorrectFieldException;

    AddTestResultItemDto getJson(String testResultItem);

    List<GetTestResultItemDto> getAllByTestResultId(Integer testResultId) throws EntityNotFoundException;

    Collection<GetTestResultItemDto> testResultItemsToDtos(Collection<TestResultItemEntity> testResultItems);

    GetTestResultItemDto testResultItemToDto(TestResultItemEntity testResultItem);

    void deleteTestResultItem(Integer id);

}
