package com.mediary.Models.DTOs.Response;

import java.sql.Timestamp;

import lombok.Data;

@Data
public class GetStatisticDto {

    private Integer id;
    private String value;
    private Timestamp date;
    private String statisticTypeName;
}
