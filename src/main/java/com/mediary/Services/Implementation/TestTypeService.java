package com.mediary.Services.Implementation;

import java.util.ArrayList;
import java.util.List;

import com.mediary.Models.DTOs.Response.GetTestTypeDto;
import com.mediary.Models.DTOs.Response.TestTypeDto;
import com.mediary.Models.Entities.TestTypeEntity;
import com.mediary.Repositories.TestTypeRepository;
import com.mediary.Services.Interfaces.ITestTypeService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TestTypeService implements ITestTypeService {

    @Autowired
    TestTypeRepository testTypeRepository;

    @Autowired
    TestParameterService testParameterService;

    @Override
    public List<GetTestTypeDto> getAllTestTypes() {
        var testTypes = testTypeRepository.findAll();
        var testTypeDtos = testTypesToDtos(testTypes);
        return testTypeDtos;
    }

    @Override
    public List<GetTestTypeDto> testTypesToDtos(List<TestTypeEntity> testTypeEntities) {
        var testTypeDtos = new ArrayList<GetTestTypeDto>();

        for (TestTypeEntity testType : testTypeEntities) {
            var testTypeDto = testTypeWithParametersToDto(testType);
            testTypeDtos.add(testTypeDto);
        }
        return testTypeDtos;
    }

    @Override
    public TestTypeDto testTypeToDto(TestTypeEntity testTypeEntity) {
        var testTypeDto = new TestTypeDto();
        testTypeDto.setId(testTypeEntity.getId());
        testTypeDto.setName(testTypeEntity.getName());
        return testTypeDto;
    }

    @Override
    public GetTestTypeDto testTypeWithParametersToDto(TestTypeEntity testTypeEntity) {
        var testTypeDto = new GetTestTypeDto();
        testTypeDto.setId(testTypeEntity.getId());
        testTypeDto.setName(testTypeEntity.getName());
        var testParameters = testTypeEntity.getTestParametersById();
        testTypeDto.setParameters(testParameterService.testParametersToDtos(testParameters));
        return testTypeDto;
    }
}
