package com.project.person.usecase.strategy;

import com.project.person.domain.Person;
import com.project.person.exception.UnderAgePersonException;
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
public class UpdateBirthDateStrategy implements UpdatePersonStrategy {
    private final SavePersonOutputPort savePerson;
    @Value("${project.person.minimum-age}")
    private int minimumAge;
    @Override
    public void updatePerson(final Person person, final Object body) {
        log.info("[UpdatePersonBirthDateStrategy][Start] Update person birth date: {}", person);
        LocalDate birthDate = (LocalDate) body;

        validateAge(birthDate);

        person.setBirthDate(birthDate);

        savePerson.save(person);
        log.info("[UpdatePersonBirthDateStrategy][End] Update person birth date: {}", person);
    }

    @Override
    public boolean isValid(String field) {
        return Objects.equals(field, "birth_date");
    }

    private void validateAge(LocalDate birthDate) {
        Optional.ofNullable(birthDate)
                .map(date -> Period.between(date, LocalDate.now()).getYears())
                .filter(years -> years >= minimumAge)
                .orElseThrow(UnderAgePersonException::new);
    }

}
