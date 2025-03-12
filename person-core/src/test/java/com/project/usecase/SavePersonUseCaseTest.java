package com.project.usecase;

import com.project.person.ports.output.FetchAddressByZipCodeOutputPort;
import com.project.person.ports.output.FetchLastRegisteredPersonOutputPort;
import com.project.person.ports.output.FetchPersonByCpfOutputPort;
import com.project.person.usecase.SavePersonUseCase;
import org.jeasy.random.EasyRandom;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class SavePersonUseCaseTest {
    @InjectMocks
    private SavePersonUseCase useCase;
    @Mock
    private FetchPersonByCpfOutputPort fetchPersonByCpf;
    @Mock
    private FetchAddressByZipCodeOutputPort fetchAddressByZipCode;
    @Mock
    private FetchLastRegisteredPersonOutputPort fetchLastRegisteredPerson;
    private EasyRandom easyRandom;

    @BeforeEach
    void setUp() {
        easyRandom = new EasyRandom();
    }

    @Test
    void should_save_person_then_return_saved_person() {
        when()
    }
}
