package com.project.usecase.strategy;

import com.project.person.domain.Person;
import com.project.person.ports.output.SavePersonOutputPort;
import com.project.person.usecase.strategy.UpdateNameStrategy;
import org.jeasy.random.EasyRandom;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension .class)
public class UpdateNameStrategyTest {
    @InjectMocks
    private UpdateNameStrategy updateNameStrategy;
    @Mock
    private SavePersonOutputPort savePerson;

    EasyRandom easyRandom;

    @BeforeEach
    void setUp() {
        easyRandom = new EasyRandom();
    }

    @Test
    void should_update_name_then_return_person() {
        Person person = easyRandom.nextObject(Person.class);
        String name = easyRandom.nextObject(String.class);

        when(savePerson.save(any(Person.class))).thenReturn(null);

        updateNameStrategy.updatePerson(person, name);

        assertEquals(person.getName(), name);
        verify(savePerson).save(person);
    }

    @Test
    void should_return_false_for_invalid_field() {
        verifyNoInteractions(savePerson);
        assertFalse(updateNameStrategy.isValid("invalid-field"));
    }
}
