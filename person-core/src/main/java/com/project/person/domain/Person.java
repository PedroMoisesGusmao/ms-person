package com.project.person.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Person {
    private String id;

    private String name;

    private String age;

    private String cpfCnpj;

    private List<Address> address;

    private String birthDate;
}
