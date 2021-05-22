package com.mediary.Models.DTOs.Response;

import lombok.Data;

@Data
public class GetTestItemDto {
    private Integer id;
    private String name;
    private String value;
    private String unit;
}
