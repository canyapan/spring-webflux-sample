package com.canyapan.sample.reactivespring.service;

import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class EchoService {

    public Mono<String> echo(String message) {
        return Mono.just(message);
    }
}
