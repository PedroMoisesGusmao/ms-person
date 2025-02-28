package com.project.person.exception;

public class PersonNotFoundException extends BadRequestException {
    public PersonNotFoundException() {
        super("Person not found");
    }
}
