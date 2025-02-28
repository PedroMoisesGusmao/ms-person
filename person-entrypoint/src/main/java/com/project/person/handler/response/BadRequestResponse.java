package com.project.person.handler.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;
import org.springframework.http.HttpStatus;

@Getter
@ToString
@AllArgsConstructor
public class BadRequestResponse {
    private String message;
    private final HttpStatus status = HttpStatus.BAD_REQUEST;
}
