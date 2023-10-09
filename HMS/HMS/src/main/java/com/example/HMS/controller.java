package com.example.HMS;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class controller {


    @GetMapping("/")
    public String index() {
        return "Greetings from Spring Boot!";
    }
}
