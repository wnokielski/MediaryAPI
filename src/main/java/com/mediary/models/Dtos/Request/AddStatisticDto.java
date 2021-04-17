package com.mediary.Models.DTOs.Request;

import java.sql.Date;

import lombok.Data;

@Data
public class AddStatisticDto {
    private String value;
    private Date date;
    private Integer statisticTypeId;
}
