package com.canyapan.sample.reactivespring.controller;

import com.canyapan.sample.reactivespring.service.EchoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/v1/echo")
@RequiredArgsConstructor
public class MyNonBlockingEchoController {

    private final EchoService echoService;

    @GetMapping("/{message}")
    public Mono<String> getEcho(@PathVariable final String message) {
        return echoService.echo(message);
    }

}
