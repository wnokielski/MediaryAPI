package com.mediary.Models.DTOs.Request;

import com.mediary.Models.DTOs.Response.GetFileDto;
import lombok.Data;

import java.sql.Date;
import java.util.Collection;

@Data
public class UpdateMedicalRecordDto {
    private String title;
    private String location;
    private String category;
    private String note;
    private Date dateOfTheTest;
    private Collection<GetFileDto> files;
    private Collection<UpdateTestItemDto> testItems;
    private Collection<AddTestItemDto> newTestItems;
}
