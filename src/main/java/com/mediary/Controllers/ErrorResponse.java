package com.mediary.Controllers;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;

public class ErrorResponse {

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd hh:mm:ss")
    private LocalDateTime timestamp;
    @JsonFormat(shape = JsonFormat.Shape.NUMBER_INT)
    private int status;
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String message;

    public ErrorResponse(LocalDateTime timestamp, int status, String message)
    {
        this.timestamp=timestamp;
        this.status=status;
        this.message=message;
    }
}
