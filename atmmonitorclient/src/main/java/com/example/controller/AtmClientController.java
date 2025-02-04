package com.example.controller;

import org.springframework.boot.CommandLineRunner;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/clients")
public class AtmClientController {


    @GetMapping
    public String welcome() {
        return "welcome atm clients";
    }


}
