package com.mediary.Models.DTOs;

import java.math.BigDecimal;
import java.sql.Date;

import lombok.Data;

@Data
public class UserDto {
    private Integer id;
    private String email;
    private String fullName;
    private String gender;
    private Date dateOfBirth;
    private BigDecimal weight;
    private BigDecimal height;
}
