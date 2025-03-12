package com.project.person.ports.output;

import com.project.person.domain.Person;

import java.util.Optional;

public interface FetchLastRegisteredPersonOutputPort {
    Optional<Person> fetchLastRegisteredPerson();
}
