package com.canyapan.sample.reactivespring.controller;

import com.canyapan.sample.reactivespring.entity.Person;
import com.canyapan.sample.reactivespring.service.PersonService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;

import java.util.UUID;

import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.when;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ExtendWith(SpringExtension.class)
class PersonControllerIntegrationTest {

    @Autowired
    private WebTestClient webTestClient;

    @MockBean
    private PersonService mockPersonService;

    @Test
    void shouldReturnAllPersons() throws Exception {
        when(mockPersonService.findAll())
                .thenReturn(Flux.just(
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

        webTestClient.get()
                .uri("/api/v1/persons/") // Auto URL encoding happens here!!
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody()
                .jsonPath("$", hasSize(2)).isArray()
                .jsonPath("$.[0].id").value(is(not(emptyString())))
                .jsonPath("$.[0].firstName").value(is("John"))
                .jsonPath("$.[0].lastName").value(is("Doe"))
                .jsonPath("$.[1].id").value(is(not(emptyString())))
                .jsonPath("$.[1].firstName").value(is("Jane"))
                .jsonPath("$.[1].lastName").value(is("Doe"));
    }

}