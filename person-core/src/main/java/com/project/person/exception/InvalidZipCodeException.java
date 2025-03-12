package com.project.person.exception;

public class InvalidZipCodeException extends BadRequestException{
    public InvalidZipCodeException() {
        super("Zip code not found");
    }
}
