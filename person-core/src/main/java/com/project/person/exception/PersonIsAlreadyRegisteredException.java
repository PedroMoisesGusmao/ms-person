package com.project.person.exception;

public class PersonIsAlreadyRegisteredException extends BadRequestException {
    public PersonIsAlreadyRegisteredException() {
        super("Person is already registered");
    }
}
