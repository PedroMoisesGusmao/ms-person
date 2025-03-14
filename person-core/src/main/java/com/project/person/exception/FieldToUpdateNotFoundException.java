package com.project.person.exception;

public class FieldToUpdateNotFoundException extends BadRequestException {
    public FieldToUpdateNotFoundException(String field) {
        super(String.format("Field %s cannot be updated (wasn't found, or can't be changed)", field));
    }
}
