package com.canyapan.sample.reactivespring.service;

import com.canyapan.sample.reactivespring.entity.Person;
import com.canyapan.sample.reactivespring.exceptions.NotFoundException;
import com.canyapan.sample.reactivespring.repository.PersonRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class PersonService {

    private final PersonRepo personRepo;

    public Mono<Person> save(final Person person) {
        return personRepo.save(person);
    }

    public Mono<Person> delete(final String id) {
        return personRepo.findById(id)
                .switchIfEmpty(Mono.error(new NotFoundException("Person not found")))
                .doOnEach(p -> personRepo.deleteById(id));
    }

    public Flux<Person> findAll() {
        return personRepo.findAll();
    }

    public Mono<Person> findById(final String id) {
        return personRepo.findById(id)
                .switchIfEmpty(Mono.error(new NotFoundException("Person not found")));
    }

    public Flux<Person> findByName(final String firstName, final String lastName) {
        return personRepo.findByFirstNameAndLastName(firstName, lastName)
                .switchIfEmpty(Mono.error(new NotFoundException("Person not found")));
    }
}
