package com.epam.esm.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ExceptionCode {
    BAD_REQUEST_EXCEPTION(400, 40001),
    MESSAGE_NOT_READABLE_EXCEPTION(400, 40002),
    ARGUMENT_NOT_VALID(400, 40003),
    DUPLICATE_ENTITY_EXCEPTION(400, 40004),
    NOT_FOUND_EXCEPTION(404, 40401),
    NO_SUCH_ENTITY_EXCEPTION(404, 40402),
    METHOD_NOT_ALLOWED_EXCEPTION(405, 40501),
    CONFLICT_EXCEPTION(409, 40901),
    INTERNAL_SERVER_ERROR_EXCEPTION(500,  50001),
    DATABASE_ERROR(500,  50002);

    private int status;
    private int errorCode;
}
