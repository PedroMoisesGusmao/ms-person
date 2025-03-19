package com.project.person.usecase;

import com.project.person.domain.Person;
import com.project.person.ports.input.FetchPersonInputPort;
import com.project.person.ports.output.FetchPersonOutputPort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class FetchPersonUseCase implements FetchPersonInputPort {
    private final FetchPersonOutputPort fetchPerson;
    @Override
    public Person fetch(final int id) {
        log.info("[FetchPersonUseCase][Start] - Fetching person with id: {}", id);
        Person person = fetchPerson.fetch(id);
        log.info("[FetchPersonUseCase][End] - Person fetched: {}", person);
        return person;
    }
}
