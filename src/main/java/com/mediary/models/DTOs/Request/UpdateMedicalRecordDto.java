package com.mediary.Models.DTOs.Request;

import com.mediary.Models.DTOs.Response.GetFileDto;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
public class UpdateMedicalRecordDto {
    private Integer id;
    private String title;
    private String location;
    private String category;
    private String note;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private Date dateOfTheTest;
    private List<GetFileDto> files = new ArrayList<>();
    private List<UpdateTestItemDto> testItems = new ArrayList<>();
    private List<AddTestItemDto> newTestItems = new ArrayList<>();
}
