package com.mediary.Models.DTOs.Request;

import lombok.Data;

@Data
public class UpdateTestResultItemDto {
    private Integer id;
    private String name;
    private String value;
    private String unit;
}
