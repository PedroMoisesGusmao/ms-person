package com.project.person.usecase.strategies;

import com.project.person.domain.Person;
import com.project.person.ports.output.FetchPersonOutputPort;
import com.project.person.ports.output.SavePersonOutputPort;
import com.project.person.usecase.UpdatePersonStrategy;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
public class UpdatePersonNameStrategy implements UpdatePersonStrategy {
    private final FetchPersonOutputPort fetchPerson;
    private final SavePersonOutputPort savePerson;
    @Override
    public void updatePerson(final String id, final Object body) {
        log.info("[UpdatePersonNameStrategy][Start] Update person name: {}", id);
        String name = (String) body;

        Person person = fetchPerson.fetch(id);
        person.setName(name);
        savePerson.save(person);

        log.info("[UpdatePersonNameStrategy][End] Person name updated: {}", person);
    }

    @Override
    public boolean isValid(String field) {
        return Objects.equals(field, "name");
    }
}
