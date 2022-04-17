package com.epam.esm.exception;

import lombok.Data;

/**
 * Class {@code ApiError} represents objects that will be returned as a response when an error is generated.
 *
 * @author Dzmitry Rozmysl
 * @version 1.0
 */
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

