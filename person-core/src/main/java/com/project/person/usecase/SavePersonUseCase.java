package com.project.person.usecase;

import com.project.person.domain.Address;
import com.project.person.domain.Person;
import com.project.person.exception.PersonIsAlreadyRegisteredException;
import com.project.person.exception.UnderAgePersonException;
import com.project.person.ports.input.SavePersonInputPort;
import com.project.person.ports.output.FetchAddressByZipCodeOutputPort;
import com.project.person.ports.output.FetchLastRegisteredPersonOutputPort;
import com.project.person.ports.output.FetchPersonByCpfOutputPort;
import com.project.person.ports.output.SavePersonOutputPort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;

@Slf4j
@Service
@RequiredArgsConstructor
public class SavePersonUseCase implements SavePersonInputPort {

    private final SavePersonOutputPort savePerson;
    private final FetchPersonByCpfOutputPort fetchPersonByCpf;
    private final FetchAddressByZipCodeOutputPort fetchAddressByZipCode;
    private final FetchLastRegisteredPersonOutputPort fetchLastRegisteredPerson;

    private final static int MINIMUM_AGE = 16;
    @Override
    public Person save(final Person person) {
        log.info("[SavePersonUseCase][Start] Save person: {}", person);

        validateAge(person.getBirthDate());
        checkIfPersonIsRegistered(person.getCpf());
        person.setId(definePersonId());
        Address address = fetchAddressByZipCode.fetchAddress(person.getAddress().getZipCode());
        person.setAddress(address);
        Person savedPerson = savePerson.save(person);

        log.info("[SavePersonUseCase][End] Saved Person: {}", savedPerson);

        return savedPerson;
    }

    private void validateAge(LocalDate birthDate) {
        Period period = Period.between(birthDate, LocalDate.now());

        if (period.getYears() < MINIMUM_AGE) {
            throw new UnderAgePersonException();
        }
    }

    private void checkIfPersonIsRegistered(String cpf) {
        if (fetchPersonByCpf.fetchByCpf(cpf).isPresent()) {
            throw new PersonIsAlreadyRegisteredException();
        }
    }

    private int definePersonId() {
        return fetchLastRegisteredPerson.fetchLastRegisteredPerson()
                .orElse(Person.builder().id(0).build())
                .getId() + 1;
    }
}
