package com.vertexfinance.loan.response;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class ApiResponse<T> {

    private boolean success;

    private String message;

    private LocalDateTime timestamp;

    private T data;

}
