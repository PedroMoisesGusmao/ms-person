package com.project.person.usecase;

import com.project.person.domain.Person;
import com.project.person.ports.input.SavePersonInputPort;
import com.project.person.ports.output.SavePersonOutputPort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class SavePersonUseCase implements SavePersonInputPort {

    private final SavePersonOutputPort savePerson;

    public SavePersonUseCase(SavePersonOutputPort savePerson) {
        this.savePerson = savePerson;
    }

    @Override
    public Person save(Person person) {
        log.info("[PERSON - SAVE PERSON][Start] Save person: cpfCnpj: {}, person: {}",
                person.getCpfCnpj(),
                person);

        Person savedPerson = savePerson.save(person);

        log.info("[PERSON - SAVE PERSON][End] Save Person: id: {}", savedPerson.getId());

        return savedPerson;
    }
}
