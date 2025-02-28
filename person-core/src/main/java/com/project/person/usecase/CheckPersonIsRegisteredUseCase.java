package com.project.person.usecase;

import com.project.person.domain.Person;
import com.project.person.exception.PersonIsAlreadyRegisteredException;
import com.project.person.ports.input.CheckPersonIsRegisteredInputPort;
import com.project.person.ports.output.FetchPersonByCpfOutputPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CheckPersonIsRegisteredUseCase implements CheckPersonIsRegisteredInputPort {
    private final FetchPersonByCpfOutputPort fetchPerson;
    @Override
    public void check(String cpf) {
        Optional<Person> person = fetchPerson.fetchByCpf(cpf);
        if (person.isPresent()) {
            throw new PersonIsAlreadyRegisteredException();
        }
    }
}
