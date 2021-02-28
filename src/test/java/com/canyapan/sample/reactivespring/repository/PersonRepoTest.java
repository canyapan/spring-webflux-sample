package com.canyapan.sample.reactivespring.repository;

import com.canyapan.sample.reactivespring.entity.Person;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ExtendWith(SpringExtension.class)
class PersonRepoTest {

    @Autowired
    private PersonRepo personRepo;

    @Test
    void shouldCrudWork() {
        final String expectedFirstName = "Can";
        final String expectedLastName = "Yapan";
        Person person1 = Person.builder()
                .firstName(expectedFirstName)
                .lastName(expectedLastName)
                .build();

        // INSERT
        person1 = personRepo.save(person1)
                .block();

        assertNotNull(person1, "should save a person");
        assertNotNull(person1.getId(), "should save a person with a new ID");
        assertEquals(expectedFirstName, person1.getFirstName(), "person's first name should match");
        assertEquals(expectedLastName, person1.getLastName(), "person's last name should match");

        final String id = person1.getId();

        // SELECT
        person1 = personRepo.findByFirstNameAndLastName(expectedFirstName, expectedLastName)
                .blockFirst();

        assertNotNull(person1, "should find a person");
        assertNotNull(person1.getId(), "should find a person with a new ID");
        assertEquals(id, person1.getId(), "found person's id should match");
        assertEquals(expectedFirstName, person1.getFirstName(), "found person's first name should match");
        assertEquals(expectedLastName, person1.getLastName(), "found person'slast name should match");

        // UPDATE
        final String newFirstName = "John";
        person1.setFirstName(newFirstName);
        person1 = personRepo.save(person1)
                .block();

        assertNotNull(person1, "should find a person");
        assertNotNull(person1.getId(), "should find a person with a new ID");
        assertEquals(id, person1.getId(), "found person's id should match");
        assertEquals(newFirstName, person1.getFirstName(), "Changed first name should match");
        assertEquals(expectedLastName, person1.getLastName(), "found person'slast name should match");

        // DELETE
        personRepo.deleteById(id).block();
        Optional<Person> person = personRepo.findById(id).blockOptional();

        assertFalse(person.isPresent(), "Person should have been deleted");
    }

}