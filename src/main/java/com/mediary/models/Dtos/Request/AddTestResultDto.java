package com.mediary.Models.Dtos.Request;

import java.sql.Date;
import java.util.Collection;

import lombok.Data;

@Data
public class AddTestResultDto {
    private String title;
    private String note;
    private Date dateofthetest;
    private Integer testTypeId;
    private Collection<AddTestResultItemDto> testResulItems;
}
