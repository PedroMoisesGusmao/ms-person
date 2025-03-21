package com.project.usecase;

import com.project.person.domain.Person;
import com.project.person.ports.output.FetchPersonOutputPort;
import com.project.person.usecase.UpdatePersonStrategyFactory;
import com.project.person.usecase.UpdatePersonUseCase;
import com.project.person.usecase.strategy.UpdateEmailStrategy;
import com.project.person.usecase.strategy.UpdateNameStrategy;
import org.jeasy.random.EasyRandom;
import org.jeasy.random.EasyRandomParameters;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UpdatePersonUseCaseTest {
    @InjectMocks
    private UpdatePersonUseCase useCase;
    @Mock
    private UpdatePersonStrategyFactory factory;
    @Mock
    private UpdateNameStrategy updateNameStrategy;
    @Mock
    private UpdateEmailStrategy updateEmailStrategy;
    @Mock
    private FetchPersonOutputPort fetchPerson;
    EasyRandom easyRandom;

    @BeforeEach
    void setUp() {
        EasyRandomParameters parameters = new EasyRandomParameters()
                .randomize(field -> field.getName().equals("birthDate"),
                        () -> LocalDate.now().minusYears(16));
        easyRandom = new EasyRandom(parameters);
    }

    @Test
    void should_update_person_then_return_updated_person() {
        Person person = easyRandom.nextObject(Person.class);
        Map<String, Object> request = Map.of("name", person);

        when(fetchPerson.fetch(any(Integer.class))).thenReturn(person);
        when(factory.findFieldsToUpdate("name")).thenReturn(updateNameStrategy);

        doNothing().when(updateNameStrategy).updatePerson(any(Person.class), any(Object.class));
        Person updatedPerson = useCase.update(1, request);

        assertEquals(person, updatedPerson);
        verify(updateNameStrategy).updatePerson(any(Person.class), any(Object.class));
    }

    @Test
    void should_update_when_multiples_fields_then_return_updated_person() {
        Person person = easyRandom.nextObject(Person.class);
        Map<String, Object> request = Map.of("name", person.getName(), "email", person.getEmail());

        when(fetchPerson.fetch(any(Integer.class))).thenReturn(person);
        when(factory.findFieldsToUpdate("name")).thenReturn(updateNameStrategy);
        when(factory.findFieldsToUpdate("email")).thenReturn(updateEmailStrategy);

        Person updatedPerson = useCase.update(1, request);

        assertEquals(person, updatedPerson);
        verify(updateNameStrategy).updatePerson(any(Person.class), any(Object.class));
    }
}