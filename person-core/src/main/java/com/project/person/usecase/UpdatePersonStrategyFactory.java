package com.project.person.usecase;

import com.project.person.exception.FieldToUpdateNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class UpdatePersonStrategyFactory {
    private final List<UpdatePersonStrategy> strategies;

    public UpdatePersonStrategy findFieldsToUpdate(final String field) {
        return strategies.stream()
                .filter(strategy -> strategy.isValid(field))
                .findFirst()
                .orElseThrow(() -> new FieldToUpdateNotFoundException(field));
    }
}
