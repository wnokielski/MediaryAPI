package com.mediary.Models.DTOs.Request;

import java.sql.Date;

import lombok.Data;

@Data
public class UserUpdateDto {
    private String email;
    private String fullName;
    private String gender;
    private Date dateofbirth;
}
