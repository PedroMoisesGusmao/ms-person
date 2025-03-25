package com.project.adapter;

import com.project.person.adapter.mapper.PersonMapper;
import com.project.person.adapter.output.FetchLastRegisteredPersonAdapter;
import com.project.person.database.entity.AddressEntity;
import com.project.person.database.entity.PersonEntity;
import com.project.person.database.repository.PersonRepository;
import com.project.person.domain.Address;
import com.project.person.domain.Person;
import org.jeasy.random.EasyRandom;
import org.jeasy.random.EasyRandomParameters;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.lang.reflect.Field;
import java.time.LocalDate;
import java.util.Optional;
import java.util.function.Predicate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class FetchLastRegisteredPersonAdapterTest {
    @InjectMocks
    private FetchLastRegisteredPersonAdapter fetchLastRegisteredPerson;
    @Mock
    private PersonRepository repository;
    @Spy
    private PersonMapper mapper = Mappers.getMapper(PersonMapper.class);

    private EasyRandom easyRandom;

    @BeforeEach
    void setUp() {
        EasyRandomParameters easyRandomParameters = new EasyRandomParameters()
                .randomize(field -> field.getName().equals("birthDate"),
                        () -> LocalDate.now().minusYears(16).toString());
        easyRandom = new EasyRandom(easyRandomParameters);
    }

    @Test
    void should_fetch_last_registered_person_then_return_person() {
        PersonEntity entity = easyRandom.nextObject(PersonEntity.class);
        Person person = toDomain(entity);

        when(repository.findTopByOrderByIdDesc()).thenReturn(Optional.of(entity));

        Optional<Person> returnedPersonOptional = fetchLastRegisteredPerson.fetchLastRegisteredPerson();
        Person returnedPerson = returnedPersonOptional.get();

        verify(mapper).toDomain(entity);
        assertEquals(person.getId(), returnedPerson.getId());
        assertEquals(person.getName(), returnedPerson.getName());
        assertEquals(person.getCpf(), returnedPerson.getCpf());
        assertEquals(person.getEmail(), returnedPerson.getEmail());
        assertEquals(person.getAddress().getZipCode(), returnedPerson.getAddress().getZipCode());
        assertEquals(person.getAddress().getThoroughfare(), returnedPerson.getAddress().getThoroughfare());
        assertEquals(person.getAddress().getComplement(), returnedPerson.getAddress().getComplement());
        assertEquals(person.getAddress().getNeighborhood(), returnedPerson.getAddress().getNeighborhood());
        assertEquals(person.getAddress().getState(), returnedPerson.getAddress().getState());
        assertEquals(person.getBirthDate(), returnedPerson.getBirthDate());
    }

    private static Person toDomain(PersonEntity entity) {
        return Person.builder()
                .id(entity.getId())
                .name(entity.getName())
                .cpf(entity.getCpf())
                .email(entity.getEmail())
                .address(toAddressDomain(entity.getAddress()))
                .birthDate(LocalDate.parse(entity.getBirthDate()))
                .build();
    }

    private static Address toAddressDomain(AddressEntity addressEntity) {
        return new Address(
                addressEntity.getZipCode(),
                addressEntity.getThoroughfare(),
                addressEntity.getComplement(),
                addressEntity.getNeighborhood(),
                addressEntity.getState()
        );
    }
}
