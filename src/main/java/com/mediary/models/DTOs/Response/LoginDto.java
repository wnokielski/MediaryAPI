package com.mediary.Models.DTOs.Response;

import lombok.Data;

@Data
public class LoginDto {
    com.mediary.Models.DTOs.JwtResponse token;
    UserDataDto userData;
}
