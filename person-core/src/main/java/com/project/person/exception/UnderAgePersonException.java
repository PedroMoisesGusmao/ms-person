package com.project.person.exception;

public class UnderAgePersonException extends BadRequestException {
    public UnderAgePersonException() {
        super("Person is under the minimum age");
    }
}
