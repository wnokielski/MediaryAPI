package com.mediary.Services.Interfaces;

import java.util.List;

import com.mediary.Models.DTOs.Response.GetTestTypeDto;
import com.mediary.Models.Entities.TestTypeEntity;

public interface ITestTypeService {

    List<GetTestTypeDto> getAllTestTypes();

    List<GetTestTypeDto> testTypesToDtos(List<TestTypeEntity> testTypeEntities);

    GetTestTypeDto testTypeWithParametersToDto(TestTypeEntity testTypeEntity);
}
