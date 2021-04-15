package com.mediary.Models.Dtos.Response;

import lombok.Data;

@Data
public class GetFileDto {
    private Integer id;
    private String originalName;
    private String url;
}
