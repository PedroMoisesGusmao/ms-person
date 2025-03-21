package com.project.usecase.strategy;

import com.project.person.domain.Person;
import com.project.person.exception.InvalidEmailException;
import com.project.person.ports.output.SavePersonOutputPort;
import com.project.person.usecase.strategy.UpdateEmailStrategy;
import org.jeasy.random.EasyRandom;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UpdateEmailStrategyTest {
    @InjectMocks
    private UpdateEmailStrategy updateEmailStrategy;
    @Mock
    private SavePersonOutputPort savePerson;

    EasyRandom easyRandom;

    @BeforeEach
    void setUp() {
        easyRandom = new EasyRandom();
    }

    @Test
    void should_update_email_then_return_person() {
        Person person = easyRandom.nextObject(Person.class);
        String email = "example@example.com";

        when(savePerson.save(any(Person.class))).thenReturn(null);

        updateEmailStrategy.updatePerson(person, email);

        assertEquals(person.getEmail(), email);
    }

    @Test
    void should_throw_exception_when_invalid_email_is_passed() {
        Person person = easyRandom.nextObject(Person.class);
        String email = "example";

        InvalidEmailException exception = assertThrows(InvalidEmailException.class, () -> {
            updateEmailStrategy.updatePerson(person, email);
        });

        verifyNoInteractions(savePerson);
        assertEquals("The passed email is invalid", exception.getMessage());
    }

    @Test
    void should_return_false_when_invalid_field_is_passed() {
        verifyNoInteractions(savePerson);
        assertFalse(updateEmailStrategy.isValid("invalid_field"));
    }
}
