package com.mediary.Models.DTOs.Request;

import java.sql.Timestamp;

import lombok.Data;

@Data
public class AddScheduleItemDto {
    private String title;
    private Timestamp date;
    private String place;
    private String address;
    private String note;
    private Integer scheduleItemTypeId;
}
