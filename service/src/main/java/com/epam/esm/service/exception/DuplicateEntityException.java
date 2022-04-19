package com.epam.esm.service.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * The class {@code DuplicateEntityException} is generated in case entity already exists in database.
 *
 * @author Dzmitry Rozmysl
 * @version 1.0
 * @see RuntimeException
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class DuplicateEntityException extends RuntimeException {
    private String param;

    public DuplicateEntityException(String message, String param) {
        super(message);
        this.param = param;
    }
}
