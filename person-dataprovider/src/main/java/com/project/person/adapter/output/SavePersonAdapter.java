package com.project.person.adapter.output;

import com.project.person.adapter.mapper.PersonEntityMapper;
import com.project.person.database.entity.PersonEntity;
import com.project.person.database.repository.PersonRepository;
import com.project.person.domain.Person;
import com.project.person.ports.output.SavePersonOutputPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SavePersonAdapter implements SavePersonOutputPort {
    private final PersonRepository repository;
    private final PersonEntityMapper mapper;
    @Override
    public Person save(Person person) {
        final PersonEntity entity = mapper.toEntity(person);
        final PersonEntity personSaved = repository.save(entity);
        return mapper.toDomain(personSaved);
    }
}
