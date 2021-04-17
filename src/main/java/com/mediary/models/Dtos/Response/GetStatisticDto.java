package com.mediary.Models.DTOs.Response;

import java.sql.Date;

import lombok.Data;

@Data
public class GetStatisticDto {

    private Integer id;
    private String value;
    private Date date;
    private String statisticTypeName;
}
