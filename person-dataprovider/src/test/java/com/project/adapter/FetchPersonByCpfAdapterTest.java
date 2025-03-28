package com.project.adapter;

import com.project.person.adapter.mapper.PersonMapper;
import com.project.person.adapter.output.FetchPersonByCpfAdapter;
import com.project.person.database.entity.PersonEntity;
import com.project.person.database.repository.PersonRepository;
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

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class FetchPersonByCpfAdapterTest {
    @InjectMocks
    private FetchPersonByCpfAdapter fetchPersonByCpf;
    @Mock
    private PersonRepository repository;
    @Spy
    private PersonMapper mapper = Mappers.getMapper(PersonMapper.class);
    private EasyRandom easyRandom;

    @BeforeEach
    void setUp() {
        EasyRandomParameters easyRandomParameters = new EasyRandomParameters()
                .randomize(createFindEntityBirthDatePredicate(),
                        () -> LocalDate.now().minusYears(16).toString());
        easyRandom = new EasyRandom(easyRandomParameters);
    }

    @Test
    void should_fetch_then_return_person() {
        String cpf = easyRandom.nextObject(String.class);
        PersonEntity entity = easyRandom.nextObject(PersonEntity.class);

        when(repository.findByCpf(any(String.class))).thenReturn(Optional.of(entity));

        fetchPersonByCpf.fetchByCpf(cpf);

        verify(mapper).toDomain(entity);
    }

    private static Predicate<Field> createFindEntityBirthDatePredicate() {
        return field -> field.getName().equals("birthDate") && field.getType() == String.class;
    }
}
