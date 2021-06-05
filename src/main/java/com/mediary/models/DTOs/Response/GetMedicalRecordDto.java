package com.mediary.Models.DTOs.Response;

import lombok.Data;

import java.sql.Timestamp;
import java.util.Collection;

@Data
public class GetMedicalRecordDto {

    private Integer id;
    private String title;
    private String location;
    private String category;
    private String note;
    private Timestamp dateOfTheTest;
    private Integer userId;
    private Collection<GetFileDto> files;
    private Collection<GetTestItemDto> testItems;
}
