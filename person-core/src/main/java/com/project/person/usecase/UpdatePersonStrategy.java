package com.project.person.usecase;

import com.project.person.domain.Person;

public interface UpdatePersonStrategy {
    void updatePerson(Person person, Object body);

    boolean isValid(String field);
}
