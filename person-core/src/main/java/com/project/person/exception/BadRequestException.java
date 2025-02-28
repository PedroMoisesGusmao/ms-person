package com.project.person.exception;

import org.springframework.http.HttpStatus;

public class BadRequestException extends RuntimeException {
    private static final HttpStatus status = HttpStatus.BAD_REQUEST;

    public BadRequestException(String message) {
        super(message);
    }
}
