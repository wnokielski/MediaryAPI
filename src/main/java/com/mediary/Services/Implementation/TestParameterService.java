package com.mediary.Services.Implementation;

import java.util.ArrayList;
import java.util.Collection;

import com.mediary.Models.DTOs.Response.TestParameterDto;
import com.mediary.Models.Entities.TestParameterEntity;
import com.mediary.Services.Interfaces.ITestParameterService;

import org.springframework.stereotype.Service;

@Service
public class TestParameterService implements ITestParameterService {

    @Override
    public Collection<TestParameterDto> testParametersToDtos(Collection<TestParameterEntity> testParameters) {
        Collection<TestParameterDto> testParameterDtos = new ArrayList<TestParameterDto>();
        for (TestParameterEntity testParameterEntity : testParameters) {
            var testParameterDto = testParameterToDto(testParameterEntity);
            testParameterDtos.add(testParameterDto);
        }
        return testParameterDtos;
    }

    @Override
    public TestParameterDto testParameterToDto(TestParameterEntity testParameter) {
        TestParameterDto testParameterDto = new TestParameterDto();
        testParameterDto.setName(testParameter.getName());
        testParameterDto.setUnit(testParameter.getUnit());
        return testParameterDto;
    }

}
