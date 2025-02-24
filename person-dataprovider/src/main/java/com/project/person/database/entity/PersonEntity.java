package com.project.person.database.entity;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.util.List;

@Document("person")
public class PersonEntity {
    @MongoId
    private String id;

    @Field("name")
    private String name;

    @Field("age")
    private String age;

    @Field("cpf_cnpj")
    private String cpfCnpj;

    @Field("addresses")
    private List<AddressEntity> address;

    @Field("birth_date")
    private String birthDate;
}
