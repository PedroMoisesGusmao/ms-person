package com.project.person.ports.output;

import com.project.person.domain.Person;

public interface SavePersonOutputPort {
    Person save(Person person);
}
