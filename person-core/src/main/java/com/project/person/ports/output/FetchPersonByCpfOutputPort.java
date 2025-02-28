package com.project.person.ports.output;

import com.project.person.domain.Person;

import java.util.Optional;

public interface FetchPersonByCpfOutputPort {
    Optional<Person> fetchByCpf(String cpf);
}
