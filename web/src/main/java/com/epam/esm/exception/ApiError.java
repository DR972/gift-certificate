package com.epam.esm.exception;

import lombok.Data;

@Data
public class ApiError {
    private int status;
    private String errorMessage;
    private int errorCode;

    public ApiError(ExceptionCode exceptionCode, String errorMessage) {
        status = exceptionCode.getStatus();
        this.errorMessage = errorMessage;
        errorCode = exceptionCode.getErrorCode();
    }
}

