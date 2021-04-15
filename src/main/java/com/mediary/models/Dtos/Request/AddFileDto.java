package com.mediary.Models.Dtos.Request;

import org.springframework.web.multipart.MultipartFile;

import lombok.Data;

@Data
public class AddFileDto {
    private MultipartFile File;
}
