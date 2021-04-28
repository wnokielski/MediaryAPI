package com.mediary.models.DTOs.Response;

import lombok.Data;

import java.math.BigDecimal;
import java.sql.Date;

@Data
public class UserDataDto {
    private String fullName;
    private String gender;
    private Date dateOfBirth;
    private BigDecimal weight;
}
