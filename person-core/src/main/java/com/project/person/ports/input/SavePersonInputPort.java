package com.project.person.ports.input;

import com.project.person.domain.Person;

public interface SavePersonInputPort {
    Person save(Person person);
}
