package com.project.person.adapter.output;

import com.project.person.database.entity.PersonEntity;
import com.project.person.database.repository.PersonRepository;
import com.project.person.domain.Person;
import com.project.person.adapter.mapper.PersonMapper;
import com.project.person.ports.output.SavePersonOutputPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SavePersonAdapter implements SavePersonOutputPort {

    private final PersonRepository repository;

    private final PersonMapper mapper;
    @Override
    public Person save(Person person) {
        final PersonEntity entity = mapper.toEntity(person);
        repository.save(entity);
        return mapper.toDomain(entity);
    }
}