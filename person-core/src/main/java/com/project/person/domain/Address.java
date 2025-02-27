package com.project.person.domain;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class Address {

    private String city;

    private String state;

    private String neighborhood;

    private String streetName;

    private String streetNumber;

    private String addressComplement;

    @NotNull(message = "The 'zip_code' field is required")
    @Size(min = 8, max = 8, message = "The 'zip_code' field must have 8 characters")
    private String zipCode;

}
