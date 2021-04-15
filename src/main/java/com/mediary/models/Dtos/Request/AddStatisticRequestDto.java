package com.mediary.Models.Dtos.Request;

import java.sql.Date;

import lombok.Data;

@Data
public class AddStatisticRequestDto {
    private String value;
    private Date date;
    private Integer statisticTypeId;
}
