package com.project.person.handler.response;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import org.springframework.http.HttpStatus;

@Getter
@Builder
@ToString
public class BadRequestResponse {
    private String message;
    private static final HttpStatus status = HttpStatus.BAD_REQUEST;
}
