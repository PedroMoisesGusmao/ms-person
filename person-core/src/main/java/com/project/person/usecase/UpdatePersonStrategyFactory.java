package com.project.person.usecase;

import com.project.person.exception.InvalidFieldToUpdateException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class UpdatePersonStrategyFactory {
    private final List<UpdatePersonStrategy> strategies;

    public UpdatePersonStrategy findFieldsToUpdate(final String field) {
        return strategies.stream()
                .filter(strategy -> strategy.isValid(field))
                .findFirst()
                .orElseThrow(() -> new InvalidFieldToUpdateException(field));
    }
}
