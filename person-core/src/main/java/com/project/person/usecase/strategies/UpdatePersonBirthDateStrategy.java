package com.project.person.usecase.strategies;

import com.project.person.domain.Person;
import com.project.person.exception.UnderAgePersonException;
import com.project.person.ports.output.FetchPersonOutputPort;
import com.project.person.ports.output.SavePersonOutputPort;
import com.project.person.usecase.UpdatePersonStrategy;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.util.Objects;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class UpdatePersonBirthDateStrategy implements UpdatePersonStrategy {
    private final FetchPersonOutputPort fetchPerson;
    private final SavePersonOutputPort savePerson;
    @Value("${project.person.minimum-age}")
    private int minimumAge;
    @Override
    public void updatePerson(final String id, final Object body) {
        log.info("[UpdatePersonBirthDateStrategy][Start] Update person birth date: {}", id);
        LocalDate birthDate = (LocalDate) body;

        validateAge(birthDate);

        Person person = fetchPerson.fetch(id);
        person.setBirthDate(birthDate);

        savePerson.save(person);
        log.info("[UpdatePersonBirthDateStrategy][End] Update person birth date: {}", person);
    }

    @Override
    public boolean isValid(String field) {
        return Objects.equals(field, "birthDate");
    }

    private void validateAge(LocalDate birthDate) {
        Optional.ofNullable(birthDate)
                .map(date -> Period.between(date, LocalDate.now()).getYears())
                .filter(years -> years >= minimumAge)
                .orElseThrow(UnderAgePersonException::new);
    }

}
