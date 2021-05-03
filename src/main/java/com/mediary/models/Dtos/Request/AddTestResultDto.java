package com.mediary.Models.DTOs.Request;

import java.sql.Date;
import java.util.Collection;

import lombok.Data;

@Data
public class AddTestResultDto {
    private String title;
    private String location;
    private String note;
    private Date dateOfTheTest;
    private Integer testTypeId;
    private Collection<AddTestResultItemDto> testResulItems;
}
