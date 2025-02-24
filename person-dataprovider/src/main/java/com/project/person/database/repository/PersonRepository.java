package com.project.person.database.repository;

import com.project.person.database.entity.PersonEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonRepository extends MongoRepository<PersonEntity, String> {
}
