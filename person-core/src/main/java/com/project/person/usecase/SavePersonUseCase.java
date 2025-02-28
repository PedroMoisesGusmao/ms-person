package com.project.person.usecase;

import com.project.person.domain.Person;
import com.project.person.ports.input.CheckPersonIsRegisteredInputPort;
import com.project.person.ports.input.SavePersonInputPort;
import com.project.person.ports.output.SavePersonOutputPort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class SavePersonUseCase implements SavePersonInputPort {

    private final SavePersonOutputPort savePerson;
    private final CheckPersonIsRegisteredInputPort checkPersonIsRegistered;
    @Override
    public Person save(Person person) {
        log.info("[SavePersonUseCase][Start] Save person: cpf: {}, person: {}",
                person.getCpf(),
                person);

        checkPersonIsRegistered.check(person.getCpf());
        Person savedPerson = savePerson.save(person);

        log.info("[SavePersonUseCase][End] Saved Person: id: {}", savedPerson.getId());

        return savedPerson;
    }
}
