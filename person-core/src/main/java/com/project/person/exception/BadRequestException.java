package com.project.person.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class BadRequestException extends RuntimeException {
    private static final HttpStatus status = HttpStatus.BAD_REQUEST;

    public BadRequestException(String message) {
        super(message);
    }
}
