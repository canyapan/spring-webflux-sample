package com.canyapan.sample.reactivespring.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class EchoServiceTest {

    @Autowired
    private EchoService echoService;

    @Test
    void shouldEchoMessage() {
        final String message = "brown fox jumped over the lazy dog";
        assertEquals(message, echoService.echo(message).block(), "should return exactly the same message");
    }

}