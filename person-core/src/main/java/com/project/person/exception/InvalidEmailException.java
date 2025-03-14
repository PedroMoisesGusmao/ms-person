package com.project.person.exception;

public class InvalidEmailException extends BadRequestException{
    public InvalidEmailException() {
        super("The passed email is invalid");
    }
}
