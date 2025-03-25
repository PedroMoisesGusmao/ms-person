package com.project.adapter;

import com.project.person.adapter.mapper.PersonMapper;
import com.project.person.adapter.output.SavePersonAdapter;
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
import java.util.function.Predicate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class SavePersonAdapterTest {
    @InjectMocks
    private SavePersonAdapter useCase;
    @Mock
    private PersonRepository repository;
    @Spy
    private PersonMapper mapper = Mappers.getMapper(PersonMapper.class);

    private EasyRandom easyRandom;

    @BeforeEach
    void setUp() {
        EasyRandomParameters easyRandomParameters = new EasyRandomParameters()
                .randomize(createFindDomainBirthDatePredicate(),
                        () -> LocalDate.now().minusYears(16));
        easyRandom = new EasyRandom(easyRandomParameters);
    }

    @Test
    void should_save_person_then_return_person() {
        Person person = easyRandom.nextObject(Person.class);

        when(repository.save(any(PersonEntity.class))).thenReturn(null);

        Person savedPerson = useCase.save(person);

        assertNotNull(savedPerson);
        verify(mapper).toEntity(any(Person.class));
        verify(mapper).toDomain(any(PersonEntity.class));
        assertEquals(person.getId(), savedPerson.getId());
        assertEquals(person.getName(), savedPerson.getName());
        assertEquals(person.getCpf(), savedPerson.getCpf());
        assertEquals(person.getEmail(), savedPerson.getEmail());
        assertEquals(person.getAddress().getZipCode(), savedPerson.getAddress().getZipCode());
        assertEquals(person.getAddress().getThoroughfare(), savedPerson.getAddress().getThoroughfare());
        assertEquals(person.getAddress().getComplement(), savedPerson.getAddress().getComplement());
        assertEquals(person.getAddress().getNeighborhood(), savedPerson.getAddress().getNeighborhood());
        assertEquals(person.getAddress().getState(), savedPerson.getAddress().getState());
        assertEquals(person.getBirthDate(), savedPerson.getBirthDate());
    }

    private static Predicate<Field> createFindDomainBirthDatePredicate() {
        return field -> field.getName().equals("birthDate") && field.getType() == LocalDate.class;
    }
}
