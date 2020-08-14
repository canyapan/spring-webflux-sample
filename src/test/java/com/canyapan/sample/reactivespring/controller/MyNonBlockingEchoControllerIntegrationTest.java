package com.canyapan.sample.reactivespring.controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class MyNonBlockingEchoControllerIntegrationTest {

    @Autowired
    private WebTestClient webTestClient;

    @Test
    void shouldReturnSameMessage() {
        final String message = "brown fox jumped over the lazy dog";

        webTestClient.get()
                .uri("/api/v1/echo/{message}", message)
                .exchange()
                .expectStatus().isOk()
                .expectBody(String.class).isEqualTo(message);
    }
}