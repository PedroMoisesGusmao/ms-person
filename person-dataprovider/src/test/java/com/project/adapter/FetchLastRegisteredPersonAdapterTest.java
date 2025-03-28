package com.project.adapter;

import com.project.person.adapter.mapper.PersonMapper;
import com.project.person.adapter.output.FetchLastRegisteredPersonAdapter;
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
import java.time.LocalDate;
import java.util.Optional;
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

        when(repository.findTopByOrderByIdDesc()).thenReturn(Optional.of(entity));

        fetchLastRegisteredPerson.fetchLastRegisteredPerson();

        verify(mapper).toDomain(entity);
        verify(repository).findTopByOrderByIdDesc();
    }
}
