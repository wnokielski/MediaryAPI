package com.mediary.Models.DTOs.Request;
import lombok.Data;

import java.sql.Timestamp;

@Data

public class ScheduleItemUpdateDto {
    private String title;
    private Timestamp date;
    private String place;
    private String address;
    private String note;
    private Integer scheduleItemTypeId;
}
