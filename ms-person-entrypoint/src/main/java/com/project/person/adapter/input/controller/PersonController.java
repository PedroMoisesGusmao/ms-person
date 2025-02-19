package com.project.person.adapter.input.controller;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/person")
public class PersonController {
    @GetMapping
    public String getPerson(@PathVariable String num) {
        return num;
    }

    @PostMapping("{num}")
    public String savePerson(@PathVariable String num) {
        return num;
    }
}
