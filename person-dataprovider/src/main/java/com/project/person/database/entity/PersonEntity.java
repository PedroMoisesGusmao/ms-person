package com.project.person.database.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.MongoId;

@Getter
@Setter
@Document("person")
public class PersonEntity {
    @MongoId
    private int id;

    @Field("name")
    private String name;

    @Field("age")
    private String age;

    @Field("cpf")
    private String cpf;

    @Field("address")
    private AddressEntity address;

    @Field("birth_date")
    private String birthDate;
}
