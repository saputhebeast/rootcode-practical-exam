package com.example.rootcodelabslpl.api.v1.lpl.dto;

import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
public class ErrorResponseDTO {
    private String message;
}
