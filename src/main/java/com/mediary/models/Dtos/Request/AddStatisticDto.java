package com.mediary.Models.DTOs.Request;

import java.sql.Timestamp;

import lombok.Data;

@Data
public class AddStatisticDto {
    private String value;
    private Timestamp date;
    private Integer statisticTypeId;
}
