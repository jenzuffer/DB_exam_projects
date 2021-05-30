package com.example.postgresqlbackend.controler;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.net.URI;

@RestController
@RequestMapping("/booking")
public class postgreController {

    RestTemplate restTemplate = new RestTemplate();
    URI uri;
    @PostMapping("/createBooking")
    public
}
