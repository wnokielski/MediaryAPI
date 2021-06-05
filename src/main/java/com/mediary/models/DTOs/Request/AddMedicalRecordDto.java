package com.mediary.Models.DTOs.Request;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
public class AddMedicalRecordDto {
    private String title;
    private String location;
    private String category;
    private String note;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private Date dateOfTheTest;
    private List<AddTestItemDto> testItems = new ArrayList<>();
}

