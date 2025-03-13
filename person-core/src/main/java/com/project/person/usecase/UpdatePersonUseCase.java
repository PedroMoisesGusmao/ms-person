package com.project.person.usecase;

import com.project.person.ports.input.UpdatePersonInputPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class UpdatePersonUseCase implements UpdatePersonInputPort {
    private final UpdatePersonStrategyFactory factory;
    @Override
    public void update(final String id, final Map<String, Object> request) {
        request.keySet().stream()
                .map(factory::findFieldsToUpdate)
                .forEach((strategy) ->
                        request.values().forEach(
                            (value) -> strategy.updatePerson(id, value)
                ));
    }
}
