package com.project.adapter;

import com.project.person.adapter.mapper.PersonMapper;
import com.project.person.adapter.output.FetchPersonAdapter;
import com.project.person.database.entity.PersonEntity;
import com.project.person.database.repository.PersonRepository;
import com.project.person.exception.PersonNotFoundException;
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
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class FetchPersonAdapterTest {
    @InjectMocks
    private FetchPersonAdapter fetchPerson;

    @Mock
    private PersonRepository repository;

    @Spy
    private PersonMapper mapper = Mappers.getMapper(PersonMapper.class);

    private EasyRandom easyRandom;

    @BeforeEach
    void setUp() {
        EasyRandomParameters easyRandomParameters = new EasyRandomParameters()
                .randomize(createFindDomainBirthDatePredicate(),
                        () -> LocalDate.now().minusYears(16))
                .randomize(createFindEntityBirthDatePredicate(),
                        () -> LocalDate.now().minusYears(16).toString());
        easyRandom = new EasyRandom(easyRandomParameters);
    }

    @Test
    void should_fetch_person_then_return() {
        int entityId = easyRandom.nextInt();
        PersonEntity entity = easyRandom.nextObject(PersonEntity.class);

        when(repository.findById(entityId)).thenReturn(Optional.of(entity));

        fetchPerson.fetch(entityId);

        verify(mapper).toDomain(entity);
        verify(repository).findByCpf(entity.getCpf());
    }

    @Test
    void should_throw_exception_when_person_not_found() {
        int entityId = easyRandom.nextInt();

        when(repository.findById(entityId)).thenReturn(Optional.empty());

        PersonNotFoundException exception = assertThrows(PersonNotFoundException.class,
                () -> fetchPerson.fetch(entityId));

        assertEquals("Person not found", exception.getMessage());
    }

    private static Predicate<Field> createFindDomainBirthDatePredicate() {
        return field -> field.getName().equals("birthDate") && field.getType() == LocalDate.class;
    }

    private static Predicate<Field> createFindEntityBirthDatePredicate() {
        return field -> field.getName().equals("birthDate") && field.getType() == String.class;
    }
}
