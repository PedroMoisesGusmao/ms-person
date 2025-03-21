package com.project.usecase;

import com.project.person.exception.InvalidFieldToUpdateException;
import com.project.person.usecase.UpdatePersonStrategy;
import com.project.person.usecase.UpdatePersonStrategyFactory;
import com.project.person.usecase.strategy.UpdateAddressByZipCodeStrategy;
import com.project.person.usecase.strategy.UpdateBirthDateStrategy;
import com.project.person.usecase.strategy.UpdateEmailStrategy;
import com.project.person.usecase.strategy.UpdateNameStrategy;
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
                new UpdateAddressByZipCodeStrategy(null, null),
                new UpdateBirthDateStrategy(null),
                new UpdateEmailStrategy(null),
                new UpdateNameStrategy(null)
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
                Arguments.arguments("zip_code", UpdateAddressByZipCodeStrategy.class),
                Arguments.arguments("birth_date", UpdateBirthDateStrategy.class),
                Arguments.arguments("email", UpdateEmailStrategy.class),
                Arguments.arguments("name", UpdateNameStrategy.class)
        );
    }
}
