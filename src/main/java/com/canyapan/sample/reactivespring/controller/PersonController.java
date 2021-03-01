package com.canyapan.sample.reactivespring.controller;

import com.canyapan.sample.reactivespring.entity.Person;
import com.canyapan.sample.reactivespring.service.PersonService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/api/v1/persons")
@RequiredArgsConstructor
public class PersonController {
    private final PersonService personService;

    @GetMapping("/")
    public Flux<Person> getAll() {
        return personService.findAll();
    }
}
