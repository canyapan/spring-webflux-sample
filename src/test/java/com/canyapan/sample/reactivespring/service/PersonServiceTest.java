package com.canyapan.sample.reactivespring.service;

import com.canyapan.sample.reactivespring.entity.Person;
import com.canyapan.sample.reactivespring.exceptions.NotFoundException;
import com.canyapan.sample.reactivespring.repository.PersonRepo;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@SpringBootTest
@ExtendWith(SpringExtension.class)
class PersonServiceTest {

    @Autowired
    private PersonService personService;

    @MockBean
    private PersonRepo mockPersonRepo;

    @Test
    void shouldSavePerson() {
        final String expectedFirstName = "John";
        final String expectedLastName = "Doe";
        final String expectedId = UUID.randomUUID().toString();
        Person newPerson = Person.builder()
                .firstName(expectedFirstName)
                .lastName(expectedLastName)
                .build();

        when(mockPersonRepo.save(eq(newPerson)))
                .thenReturn(Mono.just(newPerson.toBuilder().id(expectedId).build()));

        newPerson = personService.save(newPerson).block();

        assertNotNull(newPerson, "should find a person");
        assertNotNull(newPerson.getId(), "should find a person with a new ID");
        assertEquals(expectedId, newPerson.getId(), "found person's id should match");
        assertEquals(expectedFirstName, newPerson.getFirstName(), "found person's first name should match");
        assertEquals(expectedLastName, newPerson.getLastName(), "found person'slast name should match");
    }

    @Test
    void shouldDeletePerson() {
        final String expectedFirstName = "John";
        final String expectedLastName = "Doe";
        final String expectedId = UUID.randomUUID().toString();
        Person newPerson = Person.builder()
                .id(expectedId)
                .firstName(expectedFirstName)
                .lastName(expectedLastName)
                .build();

        when(mockPersonRepo.findById(eq(expectedId)))
                .thenReturn(Mono.just(newPerson));

        when(mockPersonRepo.deleteById(eq(expectedId)))
                .thenReturn(Mono.empty());

        newPerson = personService.delete(expectedId).block();

        assertNotNull(newPerson, "should find a person");
        assertNotNull(newPerson.getId(), "should find a person with a new ID");
        assertEquals(expectedId, newPerson.getId(), "found person's id should match");
        assertEquals(expectedFirstName, newPerson.getFirstName(), "found person's first name should match");
        assertEquals(expectedLastName, newPerson.getLastName(), "found person'slast name should match");
    }

    @Test
    void shouldReturnNotFoundExceptionWhenDeletingPersonWithUnknownId() {
        final String unknownId = UUID.randomUUID().toString();

        when(mockPersonRepo.findById(eq(unknownId)))
                .thenReturn(Mono.empty());

        assertThrows(NotFoundException.class,
                () -> personService.delete(unknownId).block(), "should throw if id not found");
    }

    @Test
    void shouldGetAllPersons() {
        when(mockPersonRepo.findAll())
                .thenReturn(
                        Flux.just(
                                Person.builder()
                                        .id(UUID.randomUUID().toString())
                                        .firstName("John")
                                        .lastName("Doe")
                                        .build(),
                                Person.builder()
                                        .id(UUID.randomUUID().toString())
                                        .firstName("Jane")
                                        .lastName("Doe")
                                        .build()
                        ));


        final List<Person> personList = personService.findAll().collectList().block();

        assertNotNull(personList, "should get a list");
        assertEquals(2, personList.size(), "should include 2 person");
    }

    @Test
    void shouldGetPersonById() {
        final String expectedFirstName = "John";
        final String expectedLastName = "Doe";
        final String expectedId = UUID.randomUUID().toString();
        Person aPerson = Person.builder()
                .id(expectedId)
                .firstName(expectedFirstName)
                .lastName(expectedLastName)
                .build();

        when(mockPersonRepo.findById(eq(expectedId)))
                .thenReturn(Mono.just(aPerson));


        aPerson = personService.findById(expectedId).block();

        assertNotNull(aPerson, "should find a person");
        assertNotNull(aPerson.getId(), "should find a person with a new ID");
        assertEquals(expectedId, aPerson.getId(), "found person's id should match");
        assertEquals(expectedFirstName, aPerson.getFirstName(), "found person's first name should match");
        assertEquals(expectedLastName, aPerson.getLastName(), "found person'slast name should match");
    }

    @Test
    void shouldGetPersonByName() {
        final String expectedFirstName = "John";
        final String expectedLastName = "Doe";
        final String expectedId = UUID.randomUUID().toString();
        Person aPerson = Person.builder()
                .id(expectedId)
                .firstName(expectedFirstName)
                .lastName(expectedLastName)
                .build();

        when(mockPersonRepo.findByFirstNameAndLastName(eq(expectedFirstName), eq(expectedLastName)))
                .thenReturn(Flux.just(aPerson));


        aPerson = personService.findByName(expectedFirstName, expectedLastName).blockFirst();

        assertNotNull(aPerson, "should find a person");
        assertNotNull(aPerson.getId(), "should find a person with a new ID");
        assertEquals(expectedId, aPerson.getId(), "found person's id should match");
        assertEquals(expectedFirstName, aPerson.getFirstName(), "found person's first name should match");
        assertEquals(expectedLastName, aPerson.getLastName(), "found person'slast name should match");
    }

}