package org.example.ristoranteprogetto.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PublicController {

    @GetMapping("/")
    public String home() {
        return "Backend Spring Boot is running!";
    }

    @GetMapping("/health")
    public String healthCheck() {
        return "OK";
    }
}
