package com.mediary.Models.DTOs.Response;

import lombok.Data;

@Data
public class GetFileDto {
    private Integer id;
    private String originalName;
    private String url;
}
