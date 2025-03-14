package com.project.person.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@AllArgsConstructor
public class Address {

    private String zipCode;

    private String thoroughfare;

    private String complement;

    private String neighborhood;

    private String state;
}
