package com.mediary.Models.DTOs.Response;

import lombok.Data;

@Data
public class LoginDto {
    String token;
    UserDataDto userData;
}
