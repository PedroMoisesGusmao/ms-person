package com.project.person.exception;

public class InvalidFieldToUpdateException extends BadRequestException {
    public InvalidFieldToUpdateException(String field) {
        super(String.format("Field %s cannot be updated (wasn't found, or can't be changed)", field));
    }
}
