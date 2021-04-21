package com.mediary.Models.DTOs.Request;

import lombok.Data;

@Data
public class UserRegisterDto {
    private String email;
    private String password;
    private String fullName;
}
