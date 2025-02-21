package com.project.person.adapter.input;

import com.project.person.ports.input.FetchPersonInputPort;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/person")
public class PersonController {
    public PersonController(FetchPersonInputPort fetchPersonInputPort) {
        this.fetchPersonInputPort = fetchPersonInputPort;
    }
    private final FetchPersonInputPort fetchPersonInputPort;
    @GetMapping
    public String fetch() {
        return fetchPersonInputPort.fetch();
    }
}
