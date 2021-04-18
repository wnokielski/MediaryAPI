package com.mediary.Services.Implementation;

import java.util.ArrayList;
import java.util.List;

import com.mediary.Models.DTOs.Response.GetTestTypeDto;
import com.mediary.Models.Entities.TestTypeEntity;
import com.mediary.Repositories.TestTypeRepository;
import com.mediary.Services.Interfaces.ITestTypeService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TestTypeService implements ITestTypeService {

    @Autowired
    TestTypeRepository testTypeRepository;

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
            var testTypeDto = testTypeToDto(testType);
            testTypeDtos.add(testTypeDto);
        }
        return testTypeDtos;
    }

    @Override
    public GetTestTypeDto testTypeToDto(TestTypeEntity testTypeEntity) {
        var testTypeDto = new GetTestTypeDto();
        testTypeDto.setId(testTypeEntity.getId());
        testTypeDto.setName(testTypeEntity.getName());
        return testTypeDto;
    }
}
