package com.canyapan.sample.reactivespring.controller;

import com.canyapan.sample.reactivespring.service.EchoService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import reactor.core.publisher.Mono;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class MyNonBlockingEchoControllerTest {

    @Autowired
    private MyNonBlockingEchoController controller;

    @MockBean
    private EchoService echoService;

    @Test
    void shouldReturnSameMessage() {
        final String message = "brown fox jumped over the lazy dog";

        when(echoService.echo(eq(message)))
                .thenReturn(Mono.just(message));

        final Mono<String> response = controller.getEcho(message);
        assertEquals(message, response.block(), "response should match with the input");
    }
}