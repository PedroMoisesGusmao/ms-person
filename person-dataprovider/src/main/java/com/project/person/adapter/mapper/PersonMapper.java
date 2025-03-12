package com.project.person.adapter.mapper;

import com.project.person.database.entity.AddressEntity;
import com.project.person.database.entity.PersonEntity;
import com.project.person.domain.Address;
import com.project.person.domain.Person;
import com.project.person.domain.response.AddressResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface PersonMapper {
    @Mapping(target = "address", source = "address", qualifiedByName = "toAddressEntity")
    PersonEntity toEntity(final Person person);

    @Mapping(target = "address", source = "address", qualifiedByName = "toAddressFromAddressEntity")
    Person toDomain(final PersonEntity personEntity);

    @Named("toAddressEntity")
    AddressEntity toAddressEntity(final Address address);

    @Mapping(target = "zipCode", source = "addressResponse.cep")
    @Mapping(target = "thoroughfare", source = "addressResponse.logradouro")
    @Mapping(target = "complement", source = "addressResponse.complemento")
    @Mapping(target = "neighborhood", source = "addressResponse.bairro")
    @Mapping(target = "state", source = "addressResponse.estado")
    Address toAddressFromAddressResponse(final AddressResponse addressResponse);

    @Named("toAddressFromAddressEntity")
    Address toAddressFromAddressEntity(final AddressEntity addressEntity);
}
