package com.project.person.input.controller;

import com.project.person.domain.Person;
import com.project.person.input.controller.contract.PersonContract;
import com.project.person.input.controller.mapper.PersonRequestMapper;
import com.project.person.input.domain.request.PersonRequest;
import com.project.person.input.domain.response.PersonResponse;
import com.project.person.ports.input.FetchPersonInputPort;
import com.project.person.ports.input.SavePersonInputPort;
import com.project.person.ports.input.UpdatePersonInputPort;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/person")
public class PersonController implements PersonContract {
    private final FetchPersonInputPort fetchPerson;
    private final SavePersonInputPort savePerson;
    private final UpdatePersonInputPort updatePerson;
    private final PersonRequestMapper mapper;
    @GetMapping
    public ResponseEntity<PersonResponse> fetch(@RequestHeader final int id) {
        Person person = fetchPerson.fetch(id);
        return ResponseEntity.status(OK).body(mapper.toPersonResponse(person));
    }
    @PostMapping
    public ResponseEntity<PersonResponse> save(@RequestBody @Valid final PersonRequest request) {
        Person person = savePerson.save(mapper.toPerson(request));
        return ResponseEntity.status(CREATED).body(mapper.toPersonResponse(person));
    }

    @PatchMapping
    public ResponseEntity<PersonResponse> update(@RequestHeader int id, @RequestBody Map<String, Object> request) {
        Person person = updatePerson.update(id, request);
        return ResponseEntity.status(OK).body(mapper.toPersonResponse(person));
    }

}
