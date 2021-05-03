package com.mediary.Services.Interfaces;

import java.util.Collection;

import com.mediary.Models.DTOs.Response.TestParameterDto;
import com.mediary.Models.Entities.TestParameterEntity;

public interface ITestParameterService {
    public Collection<TestParameterDto> testParametersToDtos(Collection<TestParameterEntity> testParameters);

    public TestParameterDto testParameterToDto(TestParameterEntity testParameter);
}
