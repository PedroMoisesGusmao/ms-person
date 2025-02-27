package com.project.person.database.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.util.List;

@Getter
@Setter
@Document("person")
public class PersonEntity {
    @MongoId
    private String id;

    @Field("name")
    private String name;

    @Field("age")
    private String age;

    @Field("cpf")
    private String cpf;

//    @Field("addresses")
//    private List<AddressEntity> address;

    @Field("birth_date")
    private String birthDate;
}
