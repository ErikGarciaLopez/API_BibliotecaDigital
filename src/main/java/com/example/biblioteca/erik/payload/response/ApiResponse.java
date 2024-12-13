package com.example.biblioteca.erik.payload.response;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class ApiResponse {
    private String status;
    private String message;
    private Object data;
    private LocalDateTime timestamp;
}
