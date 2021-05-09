package com.example.treeleef1.exception;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class ExceptionMessage {
    private LocalDateTime timeOfException;
    private String message;
    private String details;
}
