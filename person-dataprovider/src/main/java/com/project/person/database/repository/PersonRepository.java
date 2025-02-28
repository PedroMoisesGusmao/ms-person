package com.project.person.database.repository;

import com.project.person.database.entity.PersonEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PersonRepository extends MongoRepository<PersonEntity, String> {
    Optional<PersonEntity> findByCpf(String cpf);
}
