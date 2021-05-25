package com.mediary.Models.DTOs.Request;

import lombok.Data;

@Data
public class AddTestItemDto {
    private String name;
    private String value;
    private String unit;
}
