package com.mediary.Models.Dtos.Request;

import java.sql.Date;

import lombok.Data;

@Data
public class AddScheduleItemDto {
    private String title;
    private Date date;
    private String place;
    private String address;
    private String note;
    private Integer scheduleItemTypeId;
}
