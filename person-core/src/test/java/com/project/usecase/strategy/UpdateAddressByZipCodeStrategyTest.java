package com.project.usecase.strategy;

import com.project.person.domain.Person;
import com.project.person.exception.InvalidZipCodeException;
import com.project.person.ports.output.FetchAddressOutputPort;
import com.project.person.ports.output.SavePersonOutputPort;
import com.project.person.usecase.strategy.UpdateAddressByZipCodeStrategy;
import org.jeasy.random.EasyRandom;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UpdateAddressByZipCodeStrategyTest {
    @InjectMocks
    private UpdateAddressByZipCodeStrategy updateAddressByZipCodeStrategy;

    @Mock
    private SavePersonOutputPort savePerson;

    @Mock
    private FetchAddressOutputPort fetchAddress;

    EasyRandom easyRandom;

    @BeforeEach
    void setUp() {
        easyRandom = new EasyRandom();
    }

    @Test
    void should_update_address_then_return_person() {
        Person person = easyRandom.nextObject(Person.class);
        String zipCode = "12345-678";

        when(fetchAddress.fetchAddress(any(String.class))).thenReturn(person.getAddress());
        when(savePerson.save(any(Person.class))).thenReturn(null);

        updateAddressByZipCodeStrategy.updatePerson(person, zipCode);

        assertNotNull(person);
        assertNotNull(person.getAddress());
    }

    @Test
    void should_throw_error_when_invalid_zip_code_is_passed() {
        Person person = easyRandom.nextObject(Person.class);
        String zipCode = "12345";

        InvalidZipCodeException exception = assertThrows(InvalidZipCodeException.class, () -> {
            updateAddressByZipCodeStrategy.updatePerson(person, zipCode);
        });

        verifyNoInteractions(savePerson);
        assertEquals("Zip code not found", exception.getMessage());
    }

    @Test
    void should_return_false_when_invalid_field_is_passed() {
        verifyNoInteractions(savePerson);
        assertFalse(updateAddressByZipCodeStrategy.isValid("invalid_field"));
    }
}
