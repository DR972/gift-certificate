package com.epam.esm.service.exception;

/**
 * The class {@code DuplicateEntityException} is generated in case entity already exists in database.
 *
 * @author Dzmitry Rozmysl
 * @version 1.0
 * @see RuntimeException
 */
public class DuplicateEntityException extends RuntimeException {
    public DuplicateEntityException(String message) {
        super(message);
    }
}
