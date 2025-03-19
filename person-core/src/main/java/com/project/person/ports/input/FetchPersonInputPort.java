package com.project.person.ports.input;

import com.project.person.domain.Person;

public interface FetchPersonInputPort {
    Person fetch(int id);
}
