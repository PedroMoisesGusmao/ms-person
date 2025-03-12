package com.project.person.database.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Field;

@Getter
@Setter
public class AddressEntity {

    @Field("zip_code")
    private String zipCode;

    @Field("thoroughfare")
    private String thoroughfare;

    @Field("address_complement")
    private String addressComplement;

    @Field("neighborhood")
    private String neighborhood;

    @Field("state")
    private String state;
}
