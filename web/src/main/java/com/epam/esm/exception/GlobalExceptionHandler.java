package com.epam.esm.exception;

import com.epam.esm.service.exception.DuplicateEntityException;
import com.epam.esm.service.exception.NoSuchEntityException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * The class {@code GlobalExceptionHandler} presents entity which will be returned from controller in case generating exceptions.
 *
 * @author Dzmitry Rozmysl
 * @version 1.0
 */
@RestControllerAdvice
public class GlobalExceptionHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(GlobalExceptionHandler.class);
    /**
     * ResourceBundleMessageSource resourceBundleMessageSource.
     */
    private final ResourceBundleMessageSource resourceBundleMessageSource;

    /**
     * The constructor creates a GlobalExceptionHandler object
     *
     * @param resourceBundleMessageSource ResourceBundleMessageSource resourceBundleMessageSource
     */
    @Autowired
    public GlobalExceptionHandler(ResourceBundleMessageSource resourceBundleMessageSource) {
        this.resourceBundleMessageSource = resourceBundleMessageSource;
    }

    /**
     * The {@code handleHttpMessageNotReadableException} method returns a response if HttpMessageNotReadableException is generated.
     *
     * @param ex      HttpMessageNotReadableException exception
     * @param request WebRequest request
     * @return ApiError object
     */
    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    protected ApiError handleHttpMessageNotReadableException(HttpMessageNotReadableException ex, WebRequest request, Locale locale) {
        saveLog(HttpMessageNotReadableException.class, ex, request.getParameterMap());
        return new ApiError(ExceptionCode.MESSAGE_NOT_READABLE_EXCEPTION, resourceBundleMessageSource.getMessage("ex.notReadable", null, locale));
    }

    /**
     * The {@code handleHttpRequestMethodNotSupportedException} method returns a response if HttpRequestMethodNotSupportedException is generated.
     *
     * @param ex      HttpRequestMethodNotSupportedException exception
     * @param request WebRequest request
     * @return ApiError object
     */
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    protected ApiError handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException ex, WebRequest request, Locale locale) {
        saveLog(HttpRequestMethodNotSupportedException.class, ex, request.getParameterMap());
        return new ApiError(ExceptionCode.METHOD_NOT_ALLOWED_EXCEPTION, resourceBundleMessageSource.getMessage("ex.notSupported", null,
                locale) + ex.getMethod() + ")");
    }

    /**
     * The {@code handleHttpMethodArgumentNotValidException} method returns a response if MethodArgumentNotValidException is generated.
     *
     * @param ex      MethodArgumentNotValidException exception
     * @param request WebRequest request
     * @return ApiError object
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    protected ApiError handleHttpMethodArgumentNotValidException(MethodArgumentNotValidException ex, WebRequest request, Locale locale) {
        saveLog(MethodArgumentNotValidException.class, ex, request.getParameterMap());
        return new ApiError(ExceptionCode.ARGUMENT_NOT_VALID, ex.getBindingResult().getFieldErrors().stream()
                .map(fieldError -> resourceBundleMessageSource.getMessage(Objects.requireNonNull(fieldError.getDefaultMessage()), null, locale))
                .collect(Collectors.joining("; ")));
    }

    /**
     * The {@code handleHttpNoHandlerFoundException} method returns a response if NoHandlerFoundException is generated.
     *
     * @param ex      NoHandlerFoundException exception
     * @param request WebRequest request
     * @return ApiError object
     */
    @ExceptionHandler(NoHandlerFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    protected ApiError handleHttpNoHandlerFoundException(NoHandlerFoundException ex, WebRequest request, Locale locale) {
        saveLog(NoHandlerFoundException.class, ex, request.getParameterMap());
        return new ApiError(ExceptionCode.NOT_FOUND_URL_EXCEPTION, resourceBundleMessageSource.getMessage("ex.notURL", null,
                locale) + ex.getRequestURL() + ")");
    }

    /**
     * The {@code handleHttpMethodArgumentTypeMismatchException} method returns a response if MethodArgumentTypeMismatchException is generated.
     *
     * @param ex      MethodArgumentTypeMismatchException exception
     * @param request WebRequest request
     * @return ApiError object
     */
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    protected ApiError handleHttpMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException ex, WebRequest request, Locale locale) {
        saveLog(MethodArgumentTypeMismatchException.class, ex, request.getParameterMap());
        return new ApiError(ExceptionCode.NOT_FOUND_METHOD_ARGUMENT_EXCEPTION, resourceBundleMessageSource.getMessage("ex.argumentMismatch", null,
                locale) + ex.getParameter().getParameterName() + ")");
    }

    /**
     * The {@code handleHttpDataAccessException} method returns a response if DataAccessException is generated.
     *
     * @param ex      DataAccessException exception
     * @param request WebRequest request
     * @return ApiError object
     */
    @ExceptionHandler(DataAccessException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    protected ApiError handleHttpDataAccessException(DataAccessException ex, WebRequest request, Locale locale) {
        saveLog(DataAccessException.class, ex, request.getParameterMap());
        return new ApiError(ExceptionCode.DATABASE_ERROR, resourceBundleMessageSource.getMessage("ex.database", null, locale));
    }

    /**
     * The {@code handleHttpDuplicateEntityException} method returns a response if DuplicateEntityException is generated.
     *
     * @param ex      DuplicateEntityException exception
     * @param request WebRequest request
     * @return ApiError object
     */
    @ExceptionHandler(DuplicateEntityException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    protected ApiError handleHttpDuplicateEntityException(DuplicateEntityException ex, WebRequest request, Locale locale) {
        saveLog(DuplicateEntityException.class, ex, request.getParameterMap());
        return new ApiError(ExceptionCode.DUPLICATE_ENTITY_EXCEPTION, resourceBundleMessageSource.getMessage(ex.getLocalizedMessage(), null,
                locale) + ex.getParam());
    }

    /**
     * The {@code handleHttpNoSuchEntityException} method returns a response if NoSuchEntityException is generated.
     *
     * @param ex      NoSuchEntityException exception
     * @param request WebRequest request
     * @return ApiError object
     */
    @ExceptionHandler(NoSuchEntityException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    protected ApiError handleHttpNoSuchEntityException(NoSuchEntityException ex, WebRequest request, Locale locale) {
        saveLog(NoSuchEntityException.class, ex, request.getParameterMap());
        return new ApiError(ExceptionCode.NO_SUCH_ENTITY_EXCEPTION, resourceBundleMessageSource.getMessage(ex.getLocalizedMessage(), null,
                locale) + ex.getParam());
    }

    /**
     * The {@code handleHttpNullPointerException} method returns a response if NullPointerException is generated.
     *
     * @param ex      NullPointerException exception
     * @param request WebRequest request
     * @return ApiError object
     */
    @ExceptionHandler(NullPointerException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    protected ApiError handleHttpNullPointerException(NullPointerException ex, WebRequest request, Locale locale) {
        saveLog(NullPointerException.class, ex, request.getParameterMap());
        return new ApiError(ExceptionCode.INTERNAL_SERVER_ERROR_EXCEPTION, resourceBundleMessageSource.getMessage("ex.error", null, locale));
    }

    /**
     * The {@code handleHttpException} method returns a response if Exception is generated.
     *
     * @param ex      Exception exception
     * @param request WebRequest request
     * @return ApiError object
     */
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    protected ApiError handleHttpException(Exception ex, WebRequest request, Locale locale) {
        saveLog(Exception.class, ex, request.getParameterMap());
        return new ApiError(ExceptionCode.INTERNAL_SERVER_ERROR_EXCEPTION, resourceBundleMessageSource.getMessage("ex.error", null, locale));
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