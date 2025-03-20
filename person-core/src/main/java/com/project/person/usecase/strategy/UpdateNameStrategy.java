package com.project.person.usecase.strategy;

import com.project.person.domain.Person;
import com.project.person.ports.output.SavePersonOutputPort;
import com.project.person.usecase.UpdatePersonStrategy;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
public class UpdateNameStrategy implements UpdatePersonStrategy {
    private final SavePersonOutputPort savePerson;
    @Override
    public void updatePerson(final Person person, final Object body) {
        log.info("[UpdatePersonNameStrategy][Start] Update person name: {}", person);
        String name = (String) body;

        person.setName(name);

        savePerson.save(person);

        log.info("[UpdatePersonNameStrategy][End] Person name updated: {}", person);
    }

    @Override
    public boolean isValid(String field) {
        return Objects.equals(field, "name");
    }
}
