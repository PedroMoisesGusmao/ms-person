package com.project.usecase;

import com.project.person.domain.Person;
import com.project.person.ports.output.FetchPersonOutputPort;
import com.project.person.usecase.FetchPersonUseCase;
import org.jeasy.random.EasyRandom;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class FetchPersonUseCaseTest {
    @InjectMocks
    private FetchPersonUseCase useCase;

    @Mock
    private FetchPersonOutputPort fetchPersonOutputPort;

    private EasyRandom easyRandom;
    @BeforeEach
    void setUp() {
        easyRandom = new EasyRandom();
    }

    @Test
    void should_fetch_person() {
        final Person person = easyRandom.nextObject(Person.class);
        when(fetchPersonOutputPort.fetch(anyString())).thenReturn(person);

        final Person fetchedPerson = useCase.fetch(easyRandom.nextObject(String.class));

        assertEquals(person, fetchedPerson);
    }
}
