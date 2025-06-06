package com.project.person.ports.input;

import com.project.person.domain.Person;

import java.util.Map;

public interface UpdatePersonInputPort {
    Person update(int id, Map<String, Object> request);
}
