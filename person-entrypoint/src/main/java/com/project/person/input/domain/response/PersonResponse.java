package com.project.person.input.domain.response;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.project.person.domain.Address;

import java.util.List;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class PersonResponse {
    private String id;

    private String name;

    private String age;

    private String cpfCnpj;

    private List<Address> address;

    private String birthDate;
}
