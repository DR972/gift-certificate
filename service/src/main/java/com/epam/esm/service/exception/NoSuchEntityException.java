package com.epam.esm.service.exception;

/**
 * The class {@code NoSuchEntityException} is generated in case entity doesn't found in database.
 *
 * @author Dzmitry Rozmysl
 * @version 1.0
 * @see RuntimeException
 */
public class NoSuchEntityException extends RuntimeException {
    public NoSuchEntityException(String message) {
        super(message);
    }
}
