package com.person.usecase;

import com.person.ports.output.FetchPersonOutputPort;
import org.springframework.stereotype.Service;

@Service
public class FetchPersonUseCase implements FetchPersonOutputPort {
    @Override
    public String fetch() {
        return "VOLTEI COMO PERSON";
    }
}
