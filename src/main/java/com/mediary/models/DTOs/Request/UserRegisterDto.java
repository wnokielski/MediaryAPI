package com.mediary.Models.DTOs.Request;

import java.sql.Date;

import lombok.Data;

@Data
public class UserRegisterDto {
    private String email;
    private String username;
    private String password;
    private String fullName;
    private String gender;
    private Date dateofbirth;
}
