package com.mediary.Models.DTOs.Response;

import java.util.Collection;

import lombok.Data;

@Data
public class GetTestTypeDto {
    private Integer id;
    private String name;
    private Collection<TestParameterDto> parameters;
}
