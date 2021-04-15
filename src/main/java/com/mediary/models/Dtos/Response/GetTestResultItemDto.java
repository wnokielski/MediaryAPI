package com.mediary.Models.Dtos.Response;

import lombok.Data;

@Data
public class GetTestResultItemDto {
    private Integer id;
    private String name;
    private String value;
    private String unit;
}
