package com.epam.esm.service.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * The class {@code NoSuchEntityException} is generated in case entity doesn't found in database.
 *
 * @author Dzmitry Rozmysl
 * @version 1.0
 * @see RuntimeException
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class NoSuchEntityException extends RuntimeException {
    private String param;

    public NoSuchEntityException(String message, String param) {
        super(message);
        this.param = param;
    }
}
