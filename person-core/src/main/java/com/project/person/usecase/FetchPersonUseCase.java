package com.project.person.usecase;

import com.project.person.ports.input.FetchPersonInputPort;
import lombok.Getter;
import org.springframework.stereotype.Service;

@Service
public class FetchPersonUseCase implements FetchPersonInputPort {

    @Override
    public String fetch() {
        new A().getName();
        return "PERSON";
    }

    @Getter
    private class A {
        private String name;
    }
}
