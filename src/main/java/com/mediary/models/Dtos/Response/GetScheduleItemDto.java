package com.mediary.Models.DTOs.Response;

import java.sql.Date;
import java.sql.Timestamp;

import lombok.Data;

@Data
public class GetScheduleItemDto {
    private Integer id;
    private String title;
    private Timestamp date;
    private String place;
    private String address;
    private String note;
    private GetScheduleItemTypeDto scheduleItemType;
}
