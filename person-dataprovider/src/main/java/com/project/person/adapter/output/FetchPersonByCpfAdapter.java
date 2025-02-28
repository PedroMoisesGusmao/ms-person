package com.project.person.adapter.output;

import com.project.person.adapter.mapper.PersonEntityMapper;
import com.project.person.database.entity.PersonEntity;
import com.project.person.database.repository.PersonRepository;
import com.project.person.domain.Person;
import com.project.person.ports.output.FetchPersonByCpfOutputPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class FetchPersonByCpfAdapter implements FetchPersonByCpfOutputPort {
    private final PersonRepository repository;
    private final PersonEntityMapper mapper;
    @Override
    public Optional<Person> fetchByCpf(String cpf) {
        Optional<PersonEntity> optionalPerson = repository.findByCpf(cpf);
        return optionalPerson.map(mapper::toDomain);
    }
}
