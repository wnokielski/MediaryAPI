package com.mediary.Models.DTOs.Request;

import java.math.BigDecimal;
import java.sql.Date;

import lombok.Data;

@Data
public class UserUpdateDto {
    private String fullName;
    private String gender;
    private Date dateOfBirth;
    private BigDecimal weight;
    private BigDecimal height;
}
