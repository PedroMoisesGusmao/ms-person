package com.project.person.input.controller.mapper;

import com.project.person.domain.Address;
import com.project.person.domain.Person;
import com.project.person.input.domain.request.PersonRequest;
import com.project.person.input.domain.response.AddressResponse;
import com.project.person.input.domain.response.PersonResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface PersonRequestMapper {
    @Mapping(target = "address.zipCode", source = "zipCode")
    Person toPerson(final PersonRequest request);
    PersonResponse toPersonResponse(final Person person);
}
