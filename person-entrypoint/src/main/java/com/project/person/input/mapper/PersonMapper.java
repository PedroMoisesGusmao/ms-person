package com.project.person.input.mapper;

import com.project.person.domain.Person;
import com.project.person.input.domain.request.PersonRequest;
import com.project.person.input.domain.response.PersonResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PersonMapper {
    Person toPerson(final PersonRequest request);
    PersonResponse toPersonResponse(final Person person);
}
