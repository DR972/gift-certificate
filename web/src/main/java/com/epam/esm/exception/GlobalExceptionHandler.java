package com.epam.esm.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.lang.NonNull;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.*;
import java.util.stream.Collectors;

import static org.springframework.http.HttpStatus.*;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @Override
    @NonNull
    protected ResponseEntity<Object> handleHttpMessageNotReadable(@NonNull HttpMessageNotReadableException ex, @NonNull HttpHeaders headers,
                                                                  @NonNull HttpStatus status, @NonNull WebRequest request) {
        saveLog(Exception.class, ex, request.getParameterMap());
        return ResponseEntity.status(BAD_REQUEST).body(new ApiError(ExceptionCode.MESSAGE_NOT_READABLE_EXCEPTION,
                "The request is not being read"));
    }

    @Override
    @NonNull
    protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(@NonNull HttpRequestMethodNotSupportedException ex,
                                                                         @NonNull HttpHeaders headers, @NonNull HttpStatus status,
                                                                         @NonNull WebRequest request) {
        saveLog(Exception.class, ex, request.getParameterMap());
        return ResponseEntity.status(METHOD_NOT_ALLOWED).body(new ApiError(ExceptionCode.METHOD_NOT_ALLOWED_EXCEPTION,
                ex.getLocalizedMessage()));
    }

    @Override
    @NonNull
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, @NonNull HttpHeaders headers,
                                                                  @NonNull HttpStatus status, @NonNull WebRequest request) {
        saveLog(Exception.class, ex, request.getParameterMap());
        return ResponseEntity.status(BAD_REQUEST).body(new ApiError(ExceptionCode.ARGUMENT_NOT_VALID,
                ex.getBindingResult().getFieldErrors().stream()
                        .map(fieldError -> fieldError.getDefaultMessage() + "; ")
                        .collect(Collectors.joining())));
    }

    @Override
    @NonNull
    protected ResponseEntity<Object> handleNoHandlerFoundException(@NonNull NoHandlerFoundException ex, @NonNull HttpHeaders headers,
                                                                   @NonNull HttpStatus status, @NonNull WebRequest request) {
        saveLog(NullPointerException.class, ex, request.getParameterMap());
        return ResponseEntity.status(NOT_FOUND).body(new ApiError(ExceptionCode.NOT_FOUND_EXCEPTION,
                ex.getLocalizedMessage()));
    }


    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    protected ResponseEntity<Object> handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException ex, WebRequest request) {
        saveLog(MethodArgumentTypeMismatchException.class, ex, request.getParameterMap());
        return ResponseEntity.status(NOT_FOUND).body(new ApiError(ExceptionCode.NOT_FOUND_EXCEPTION,
                "The request with the '" + ex.getParameter().getParameterName() + "' parameter cannot be executed"));
    }

    @ExceptionHandler(DataAccessException.class)
    protected ResponseEntity<Object> handleDaoException(DataAccessException ex, WebRequest request) {
        saveLog(DataAccessException.class, ex, request.getParameterMap());
        return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiError(ExceptionCode.DATABASE_ERROR, "Database error"));
    }

    @ExceptionHandler(DuplicateEntityException.class)
    protected ResponseEntity<Object> handleDuplicateEntityException(DuplicateEntityException ex, WebRequest request) {
        saveLog(DuplicateEntityException.class, ex, request.getParameterMap());
        return ResponseEntity.status(BAD_REQUEST).body(new ApiError(ExceptionCode.DUPLICATE_ENTITY_EXCEPTION, ex.getLocalizedMessage()));
    }

    @ExceptionHandler(NoSuchEntityException.class)
    protected ResponseEntity<Object> handleNoSuchEntityException(NoSuchEntityException ex, WebRequest request) {
        saveLog(NoSuchEntityException.class, ex, request.getParameterMap());
        return ResponseEntity.status(BAD_REQUEST).body(new ApiError(ExceptionCode.NO_SUCH_ENTITY_EXCEPTION, ex.getLocalizedMessage()));
    }

    @ExceptionHandler(NullPointerException.class)
    protected ResponseEntity<Object> handleNullPointerException(NullPointerException ex, WebRequest request) {
        saveLog(NullPointerException.class, ex, request.getParameterMap());
        return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiError(ExceptionCode.INTERNAL_SERVER_ERROR_EXCEPTION, "Internal server error"));
    }

    @ExceptionHandler(Exception.class)
    protected ResponseEntity<Object> handleAllExceptions(Exception ex, WebRequest request) {
        saveLog(Exception.class, ex, request.getParameterMap());
        return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiError(ExceptionCode.INTERNAL_SERVER_ERROR_EXCEPTION, "Internal server error"));
    }

    private void saveLog(Class<?> clazz, Exception ex, Map<String, String[]> requestParameterMap) {
        StringBuilder message = new StringBuilder(clazz.getName());
        for (StackTraceElement ste : ex.getStackTrace()) {
            if (ste.toString().startsWith("com.epam.esm")) {
                message.append("\n").append(ste).append(" method - ").append(ste.getMethodName());
            }
        }
        message.append("\n").append(ex.getLocalizedMessage()).append("\n").append(requestParameterMap);
        LOGGER.error(message.toString());
    }
}