package com.project.usecase;

import com.project.person.exception.InvalidFieldToUpdateException;
import com.project.person.usecase.UpdatePersonStrategy;
import com.project.person.usecase.UpdatePersonStrategyFactory;
import com.project.person.usecase.strategies.UpdatePersonAddressByZipCodeStrategy;
import com.project.person.usecase.strategies.UpdatePersonBirthDateStrategy;
import com.project.person.usecase.strategies.UpdatePersonEmailStrategy;
import com.project.person.usecase.strategies.UpdatePersonNameStrategy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
public class UpdatePersonStrategyFactoryTest {
    private UpdatePersonStrategyFactory factory;

    @BeforeEach
    void setUp() {
        factory = new UpdatePersonStrategyFactory(List.of(
                new UpdatePersonAddressByZipCodeStrategy(null, null),
                new UpdatePersonBirthDateStrategy(null),
                new UpdatePersonEmailStrategy(null),
                new UpdatePersonNameStrategy(null)
        ));
    }

    @ParameterizedTest
    @MethodSource("strategies")
    void should_find_and_execute_strategy(
            String isValidValue, Class<? extends UpdatePersonStrategy> strategy) {
        UpdatePersonStrategy updatePersonStrategy = factory.findFieldsToUpdate(isValidValue);

        assertEquals(strategy, updatePersonStrategy.getClass());
    }

    @Test
    void should_throw_exception_when_invalid_field_is_passed() {
        InvalidFieldToUpdateException exception = assertThrows(InvalidFieldToUpdateException.class,
                () -> factory.findFieldsToUpdate("invalid_field"));

        String expectedMessage = "Field invalid_field cannot be updated (wasn't found, or can't be changed)";

        assertEquals(expectedMessage, exception.getMessage());
    }

    private static List<Arguments> strategies() {
        return List.of(
                Arguments.arguments("zip_code", UpdatePersonAddressByZipCodeStrategy.class),
                Arguments.arguments("birth_date", UpdatePersonBirthDateStrategy.class),
                Arguments.arguments("email", UpdatePersonEmailStrategy.class),
                Arguments.arguments("name", UpdatePersonNameStrategy.class)
        );
    }
}
