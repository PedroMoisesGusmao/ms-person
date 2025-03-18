package com.project.usecase;

import com.project.person.domain.Person;
import com.project.person.ports.output.FetchPersonOutputPort;
import com.project.person.ports.output.SavePersonOutputPort;
import com.project.person.usecase.UpdatePersonStrategyFactory;
import com.project.person.usecase.UpdatePersonUseCase;
import com.project.person.usecase.strategies.UpdatePersonNameStrategy;
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

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UpdatePersonUseCaseTest {
    @InjectMocks
    private UpdatePersonUseCase useCase;
    @Mock
    private UpdatePersonStrategyFactory factory;
    @Mock
    private UpdatePersonNameStrategy updatePersonNameStrategy;
    @Mock
    private FetchPersonOutputPort fetchPerson;
    @Mock
    private SavePersonOutputPort savePerson;

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
        Map<String, Object> request = Map.of("name", "Robertinho do grau");

        when(fetchPerson.fetch(anyString())).thenReturn(person);
        when(factory.findFieldsToUpdate("name")).thenReturn(updatePersonNameStrategy);
        when(savePerson.save(any())).thenReturn(null);

        Person updatedPerson = useCase.update("1", request);
    }
}