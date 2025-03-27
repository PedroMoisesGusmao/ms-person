package com.project.adapter;

import com.project.person.adapter.mapper.PersonMapper;
import com.project.person.adapter.output.FetchPersonByCpfAdapter;
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
        Person expected = toDomain(entity);

        when(repository.findByCpf(any(String.class))).thenReturn(Optional.of(entity));

        Optional<Person> resultOptional = fetchPersonByCpf.fetchByCpf(cpf);
        Person result = resultOptional.get();

        verify(mapper).toDomain(entity);
        assertEquals(expected.getId(), result.getId());
        assertEquals(expected.getName(), result.getName());
        assertEquals(expected.getCpf(), result.getCpf());
        assertEquals(expected.getEmail(), result.getEmail());
        assertEquals(expected.getAddress().getZipCode(), result.getAddress().getZipCode());
        assertEquals(expected.getAddress().getThoroughfare(), result.getAddress().getThoroughfare());
        assertEquals(expected.getAddress().getComplement(), result.getAddress().getComplement());
        assertEquals(expected.getAddress().getNeighborhood(), result.getAddress().getNeighborhood());
        assertEquals(expected.getAddress().getState(), result.getAddress().getState());
        assertEquals(expected.getBirthDate(), result.getBirthDate());
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

    private static Predicate<Field> createFindEntityBirthDatePredicate() {
        return field -> field.getName().equals("birthDate") && field.getType() == String.class;
    }
}
