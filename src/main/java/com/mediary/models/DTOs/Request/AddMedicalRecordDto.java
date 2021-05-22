package com.mediary.Models.DTOs.Request;

import java.sql.Date;
import java.util.Collection;

import lombok.Data;

@Data
public class AddMedicalRecordDto {
    private String title;
    private String location;
    private String note;
    private Date dateOfTheTest;
    private Integer testTypeId;
    private Collection<AddTestItemDto> testItems;
}
