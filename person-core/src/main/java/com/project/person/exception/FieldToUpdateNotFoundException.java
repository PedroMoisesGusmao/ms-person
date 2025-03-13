package com.project.person.exception;

public class FieldToUpdateNotFoundException extends BadRequestException {
    public FieldToUpdateNotFoundException(String field) {
        super(String.format("Field %s not found", field));
    }
}
