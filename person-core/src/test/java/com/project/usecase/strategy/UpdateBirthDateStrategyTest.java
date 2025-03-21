package com.project.usecase.strategy;

import com.project.person.domain.Person;
import com.project.person.exception.UnderAgePersonException;
import com.project.person.ports.output.SavePersonOutputPort;
import com.project.person.usecase.strategy.UpdateBirthDateStrategy;
import org.jeasy.random.EasyRandom;
import org.jeasy.random.EasyRandomParameters;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UpdateBirthDateStrategyTest {
    @InjectMocks
    private UpdateBirthDateStrategy updateBirthDateStrategy;

    @Mock
    private SavePersonOutputPort savePerson;

    private static final int MINIMUM_AGE = 16;

    EasyRandom easyRandom;

    @BeforeEach
    void setUp() {
        ReflectionTestUtils.setField(updateBirthDateStrategy, "minimumAge", MINIMUM_AGE);
        EasyRandomParameters easyRandomParameters = new EasyRandomParameters()
                .randomize(field -> field.getName().equals("birthDate"),
                        () -> LocalDate.now().minusYears(MINIMUM_AGE));
        easyRandom = new EasyRandom(easyRandomParameters);
    }

    @Test
    void should_update_birth_date_then_return_person() {
        Person person = easyRandom.nextObject(Person.class);
        LocalDate birthDate = LocalDate.now().minusYears(20);

        when(savePerson.save(any(Person.class))).thenReturn(null);
        updateBirthDateStrategy.updatePerson(person, birthDate);

        verify(savePerson).save(person);
        assertEquals(birthDate, person.getBirthDate());
    }

    @Test
    void should_throw_exception_when_birth_date_is_invalid() {
        Person person = easyRandom.nextObject(Person.class);
        LocalDate birthDate = LocalDate.now().minusYears(10);

        UnderAgePersonException exception = assertThrows(UnderAgePersonException.class, () -> {
            updateBirthDateStrategy.updatePerson(person, birthDate);
        });

        verifyNoInteractions(savePerson);
        assertEquals("Person is under the minimum age", exception.getMessage());
    }

    @Test
    void should_return_false_for_invalid_field() {
        verifyNoInteractions(savePerson);
        assertFalse(updateBirthDateStrategy.isValid("invalid-field"));
    }
}
