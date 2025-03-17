package com.project.person.usecase;

import com.project.person.domain.Person;
import com.project.person.ports.input.UpdatePersonInputPort;
import com.project.person.ports.output.FetchPersonOutputPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class UpdatePersonUseCase implements UpdatePersonInputPort {
    private final FetchPersonOutputPort fetchPerson;
    private final UpdatePersonStrategyFactory factory;
    @Override
    public Person update(final String id, final Map<String, Object> request) {
        Person person = fetchPerson.fetch(id);
        request.forEach(
                (key, value) -> factory.findFieldsToUpdate(key).updatePerson(person, value));
        return person;
    }
}
