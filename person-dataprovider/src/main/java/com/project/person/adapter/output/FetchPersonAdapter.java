package com.project.person.adapter.output;

import com.project.person.adapter.mapper.PersonMapper;
import com.project.person.database.entity.PersonEntity;
import com.project.person.database.repository.PersonRepository;
import com.project.person.domain.Person;
import com.project.person.exception.PersonNotFoundException;
import com.project.person.ports.output.FetchPersonOutputPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class FetchPersonAdapter implements FetchPersonOutputPort {
    private final PersonRepository repository;
    private final PersonMapper mapper;
    @Override
    public Person fetch(int id) {
        PersonEntity entity = repository.findById(id)
                .orElseThrow(PersonNotFoundException::new);
        return mapper.toDomain(entity);
    }
}
