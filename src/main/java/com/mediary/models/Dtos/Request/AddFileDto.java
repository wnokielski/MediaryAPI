package com.mediary.Models.DTOs.Request;

import org.springframework.web.multipart.MultipartFile;

import lombok.Data;

@Data
public class AddFileDto {
    private MultipartFile file;
}
