package com.mediary.Services.Interfaces;

import java.util.Collection;
import java.util.List;

import com.mediary.Models.DTOs.Request.AddTestItemDto;
import com.mediary.Models.DTOs.Response.GetTestItemDto;
import com.mediary.Models.Entities.MedicalRecordEntity;
import com.mediary.Models.Entities.TestItemEntity;
import com.mediary.Services.Exceptions.EntityNotFoundException;
import com.mediary.Services.Exceptions.IncorrectFieldException;

public interface ITestItemService {

    void addTestItems(Collection<AddTestItemDto> testItemDtos, MedicalRecordEntity medicalRecord)
            throws IncorrectFieldException;

    void addTestItem(AddTestItemDto testItemDto, MedicalRecordEntity medicalRecord)
            throws IncorrectFieldException;

    AddTestItemDto getJson(String testItem);

    List<GetTestItemDto> getAllByMedicalRecordId(Integer medicalRecordId) throws EntityNotFoundException;

    Collection<GetTestItemDto> testItemsToDtos(Collection<TestItemEntity> testItems);

    GetTestItemDto testItemToDto(TestItemEntity testItem);

}
