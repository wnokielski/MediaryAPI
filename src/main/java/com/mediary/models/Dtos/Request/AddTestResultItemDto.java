package com.mediary.Models.Dtos.Request;

import lombok.Data;

@Data
public class AddTestResultItemDto {
    private String name;
    private String value;
    private String unit;
}
