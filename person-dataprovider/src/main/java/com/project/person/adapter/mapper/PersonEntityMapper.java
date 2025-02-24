package com.project.person.adapter.mapper;

import com.project.person.database.entity.PersonEntity;
import com.project.person.domain.Person;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PersonEntityMapper {
    PersonEntity toEntity(final Person person);

    Person toDomain(final PersonEntity personEntity);
}
