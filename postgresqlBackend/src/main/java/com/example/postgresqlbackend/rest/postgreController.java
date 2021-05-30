package com.example.postgresqlbackend.rest;

import com.example.postgresqlbackend.datalayer.postgreDataImpl;
import com.example.postgresqlbackend.dto.BookingDTO;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.net.URI;

@RestController
@RequestMapping("/booking")
public class postgreController {

    private postgreDataImpl postgreData = new postgreDataImpl();
    RestTemplate restTemplate = new RestTemplate();
    URI uri;
    @PostMapping("/createbooking")
    public BookingDTO createBooking(@RequestBody BookingDTO bookingDTO){
        return postgreData.createBooking(bookingDTO);
    }
}
