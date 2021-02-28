package com.canyapan.sample.reactivespring.repository;

import com.canyapan.sample.reactivespring.entity.Person;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface PersonRepo extends ReactiveMongoRepository<Person, String> {
    Flux<Person> findByFirstNameAndLastName(String firstName, String lastName);
}
