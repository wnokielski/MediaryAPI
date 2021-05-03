package com.mediary.Services.Interfaces;

import java.util.List;

import com.mediary.Models.DTOs.Response.GetTestTypeDto;
import com.mediary.Models.DTOs.Response.TestTypeDto;
import com.mediary.Models.Entities.TestTypeEntity;

public interface ITestTypeService {

    List<GetTestTypeDto> getAllTestTypes();

    List<GetTestTypeDto> testTypesToDtos(List<TestTypeEntity> testTypeEntities);

    TestTypeDto testTypeToDto(TestTypeEntity testTypeEntity);

    GetTestTypeDto testTypeWithParametersToDto(TestTypeEntity testTypeEntity);
}
