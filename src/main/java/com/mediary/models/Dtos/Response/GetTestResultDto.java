package com.mediary.Models.DTOs.Response;

import java.sql.Date;
import java.util.Collection;

import lombok.Data;

@Data
public class GetTestResultDto {

    private Integer id;
    private String title;
    private String note;
    private Date dateOfTheTest;
    private GetTestTypeDto testType;
    private Integer userId;
    private Collection<GetFileDto> files;
    private Collection<GetTestResultItemDto> testResultItems;
}
