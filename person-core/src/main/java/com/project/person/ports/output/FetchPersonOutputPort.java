package com.project.person.ports.output;

import com.project.person.domain.Person;

public interface FetchPersonOutputPort {
    Person fetch(String id);
}
