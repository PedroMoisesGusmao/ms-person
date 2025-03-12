package com.project.person.adapter.output;

import com.project.person.adapter.mapper.PersonMapper;
import com.project.person.database.repository.PersonRepository;
import com.project.person.domain.Person;
import com.project.person.ports.output.FetchLastRegisteredPersonOutputPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class FetchLastRegisteredPersonAdapter implements FetchLastRegisteredPersonOutputPort {
    private final PersonRepository repository;
    private final PersonMapper mapper;

    @Override
    public Optional<Person> fetchLastRegisteredPerson() {
        return repository.findOneOrderByIdDesc()
                .map(mapper::toDomain);
    }
}
