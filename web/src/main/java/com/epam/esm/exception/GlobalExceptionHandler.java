package com.epam.esm.exception;

import com.epam.esm.service.exception.DuplicateEntityException;
import com.epam.esm.service.exception.NoSuchEntityException;
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

import java.util.Map;
import java.util.stream.Collectors;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.METHOD_NOT_ALLOWED;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

/**
 * The class {@code GlobalExceptionHandler} presents entity which will be returned from controller in case generating exceptions.
 *
 * @author Dzmitry Rozmysl
 * @version 1.0
 */
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
    protected ResponseEntity<Object> handleMethodArgumentNotValid(@NonNull MethodArgumentNotValidException ex, @NonNull HttpHeaders headers,
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

    /**
     * The {@code handleHttpMethodArgumentTypeMismatchException} method returns a response if MethodArgumentTypeMismatchException is generated.
     *
     * @param ex      MethodArgumentTypeMismatchException exception
     * @param request WebRequest request
     * @return ResponseEntity<Object> object
     */
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    protected ResponseEntity<Object> handleHttpMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException ex, WebRequest request) {
        saveLog(MethodArgumentTypeMismatchException.class, ex, request.getParameterMap());
        return ResponseEntity.status(NOT_FOUND).body(new ApiError(ExceptionCode.NOT_FOUND_EXCEPTION,
                "The request with the '" + ex.getParameter().getParameterName() + "' parameter cannot be executed"));
    }

    /**
     * The {@code DataAccessException} method returns a response if DataAccessException is generated.
     *
     * @param ex      DataAccessException exception
     * @param request WebRequest request
     * @return ResponseEntity<Object> object
     */
    @ExceptionHandler(DataAccessException.class)
    protected ResponseEntity<Object> handleHttpDaoException(DataAccessException ex, WebRequest request) {
        saveLog(DataAccessException.class, ex, request.getParameterMap());
        return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiError(ExceptionCode.DATABASE_ERROR, "Database error"));
    }

    /**
     * The {@code handleHttpDuplicateEntityException} method returns a response if DuplicateEntityException is generated.
     *
     * @param ex      DuplicateEntityException exception
     * @param request WebRequest request
     * @return ResponseEntity<Object> object
     */
    @ExceptionHandler(DuplicateEntityException.class)
    protected ResponseEntity<Object> handleHttpDuplicateEntityException(DuplicateEntityException ex, WebRequest request) {
        saveLog(DuplicateEntityException.class, ex, request.getParameterMap());
        return ResponseEntity.status(BAD_REQUEST).body(new ApiError(ExceptionCode.DUPLICATE_ENTITY_EXCEPTION, ex.getLocalizedMessage()));
    }

    /**
     * The {@code handleHttpNoSuchEntityException} method returns a response if NoSuchEntityException is generated.
     *
     * @param ex      NoSuchEntityException exception
     * @param request WebRequest request
     * @return ResponseEntity<Object> object
     */
    @ExceptionHandler(NoSuchEntityException.class)
    protected ResponseEntity<Object> handleHttpNoSuchEntityException(NoSuchEntityException ex, WebRequest request) {
        saveLog(NoSuchEntityException.class, ex, request.getParameterMap());
        return ResponseEntity.status(BAD_REQUEST).body(new ApiError(ExceptionCode.NO_SUCH_ENTITY_EXCEPTION, ex.getLocalizedMessage()));
    }

    /**
     * The {@code handleHttpNullPointerException} method returns a response if NullPointerException is generated.
     *
     * @param ex      NullPointerException exception
     * @param request WebRequest request
     * @return ResponseEntity<Object> object
     */
    @ExceptionHandler(NullPointerException.class)
    protected ResponseEntity<Object> handleHttpNullPointerException(NullPointerException ex, WebRequest request) {
        saveLog(NullPointerException.class, ex, request.getParameterMap());
        return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiError(ExceptionCode.INTERNAL_SERVER_ERROR_EXCEPTION, "Internal server error"));
    }

    /**
     * The {@code handleHttpException} method returns a response if Exception is generated.
     *
     * @param ex      Exception exception
     * @param request WebRequest request
     * @return ResponseEntity<Object> object
     */
    @ExceptionHandler(Exception.class)
    protected ResponseEntity<Object> handleHttpException(Exception ex, WebRequest request) {
        saveLog(Exception.class, ex, request.getParameterMap());
        return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiError(ExceptionCode.INTERNAL_SERVER_ERROR_EXCEPTION, "Internal server error"));
    }

    /**
     * The {@code saveLog} method saves a log entry in case of an error.
     *
     * @param clazz               Class<?> clazz
     * @param ex                  Exception exception
     * @param requestParameterMap Map<String, String[]> requestParameterMap
     */
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