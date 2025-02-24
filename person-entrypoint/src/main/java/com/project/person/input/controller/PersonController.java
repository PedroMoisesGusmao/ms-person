package com.project.person.input.controller;

import com.project.person.domain.Person;
import com.project.person.input.domain.request.PersonRequest;
import com.project.person.input.domain.response.PersonResponse;
import com.project.person.input.mapper.PersonMapper;
import com.project.person.ports.input.SavePersonInputPort;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/person")
public class PersonController {
    private final SavePersonInputPort savePerson;
    private final PersonMapper mapper;
    @PostMapping
    public ResponseEntity<PersonResponse> save(final PersonRequest request) {
        Person person = savePerson.save(mapper.toPerson(request));
        return ResponseEntity.status(CREATED).body(mapper.toPersonResponse(person));
    }

}
