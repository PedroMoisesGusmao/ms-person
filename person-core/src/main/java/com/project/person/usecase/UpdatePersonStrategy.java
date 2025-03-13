package com.project.person.usecase;

public interface UpdatePersonStrategy {
    void updatePerson(String id, Object body);

    boolean isValid(String field);
}
