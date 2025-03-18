package com.project.usecase;

import com.project.person.domain.Person;
import com.project.person.exception.PersonIsAlreadyRegisteredException;
import com.project.person.exception.UnderAgePersonException;
import com.project.person.ports.output.FetchAddressOutputPort;
import com.project.person.ports.output.FetchLastRegisteredPersonOutputPort;
import com.project.person.ports.output.FetchPersonByCpfOutputPort;
import com.project.person.ports.output.SavePersonOutputPort;
import com.project.person.usecase.SavePersonUseCase;
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
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class SavePersonUseCaseTest {
    @InjectMocks
    private SavePersonUseCase useCase;
    @Mock
    private SavePersonOutputPort savePersonOutputPort;
    @Mock
    private FetchPersonByCpfOutputPort fetchPersonByCpf;
    @Mock
    private FetchAddressOutputPort fetchAddressByZipCode;
    @Mock
    private FetchLastRegisteredPersonOutputPort fetchLastRegisteredPerson;
    private Person person;

    @BeforeEach
    void setUp() {
        EasyRandomParameters parameters = new EasyRandomParameters()
                .randomize(field -> field.getName().equals("birthDate"),
                        () -> LocalDate.now().minusYears(16));
        EasyRandom easyRandom = new EasyRandom(parameters);
        person = easyRandom.nextObject(Person.class);
        ReflectionTestUtils.setField(useCase, "minimumAge", 16);
    }

    @Test
    void should_save_person_then_return_saved_person() {
        when(fetchPersonByCpf.fetchByCpf(anyString())).thenReturn(Optional.empty());
        when(fetchAddressByZipCode.fetchAddress(anyString())).thenReturn(person.getAddress());
        when(fetchLastRegisteredPerson.fetchLastRegisteredPerson()).thenReturn(Optional.empty());
        when(savePersonOutputPort.save(any(Person.class))).thenReturn(person);

        Person savedPerson = useCase.save(person);

        assertEquals(savedPerson, person);
        assertEquals(1, savedPerson.getId());
        verify(savePersonOutputPort).save(any(Person.class));
    }

    @Test
    void should_throw_exception_when_under_age_person() {
        person.setBirthDate(LocalDate.now());

        assertThrows(UnderAgePersonException.class,
                () -> useCase.save(person));
    }

    @Test
    void should_throw_exception_when_person_is_already_registered() {
        when(fetchPersonByCpf.fetchByCpf(anyString())).thenReturn(Optional.of(person));

        assertThrows(PersonIsAlreadyRegisteredException.class,
                () -> useCase.save(person));
    }
}
