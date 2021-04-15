package com.mediary.Models.Dtos.Response;

import java.sql.Date;

import lombok.Data;

@Data
public class GetScheduleItemDto {
    private Integer id;
    private String title;
    private Date date;
    private String place;
    private String address;
    private String note;
    private GetScheduleItemTypeDto scheduleItemType;
}
