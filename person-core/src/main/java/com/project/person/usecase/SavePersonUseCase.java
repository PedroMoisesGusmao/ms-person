package com.project.person.usecase;

import com.project.person.domain.Address;
import com.project.person.domain.Person;
import com.project.person.exception.PersonIsAlreadyRegisteredException;
import com.project.person.exception.UnderAgePersonException;
import com.project.person.ports.input.SavePersonInputPort;
import com.project.person.ports.output.FetchAddressOutputPort;
import com.project.person.ports.output.FetchLastRegisteredPersonOutputPort;
import com.project.person.ports.output.FetchPersonByCpfOutputPort;
import com.project.person.ports.output.SavePersonOutputPort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class SavePersonUseCase implements SavePersonInputPort {

    private final SavePersonOutputPort savePerson;
    private final FetchPersonByCpfOutputPort fetchPersonByCpf;
    private final FetchAddressOutputPort fetchAddressByZipCode;
    private final FetchLastRegisteredPersonOutputPort fetchLastRegisteredPerson;

    @Value("${project.person.minimum-age}")
    private int minimumAge;

    @Override
    public Person save(final Person person) {
        log.info("[SavePersonUseCase][Start] Save person: {}", person);

        validateAge(person.getBirthDate());
        checkIfPersonIsRegistered(person.getCpf());

        Address address = fetchAddressByZipCode.fetchAddress(person.getAddress().getZipCode());
        address.setZipCode(formatAddress(address.getZipCode()));
        person.setAddress(address);
        person.setId(definePersonId());
        savePerson.save(person);

        log.info("[SavePersonUseCase][End] Saved Person: {}", person);

        return person;
    }

    private void validateAge(LocalDate birthDate) {
        Optional.ofNullable(birthDate)
                .map(date -> Period.between(birthDate, LocalDate.now()).getYears())
                .filter(years -> years >= minimumAge)
                .orElseThrow(UnderAgePersonException::new);
    }

    private void checkIfPersonIsRegistered(String cpf) {
        fetchPersonByCpf.fetchByCpf(cpf)
                .orElseThrow(PersonIsAlreadyRegisteredException::new);
    }

    private static String formatAddress(final String zipCode) {
        return zipCode.replace("-", "");
    }

    private int definePersonId() {
        return fetchLastRegisteredPerson.fetchLastRegisteredPerson()
                .orElse(Person.builder().id(0).build())
                .getId() + 1;
    }
}
