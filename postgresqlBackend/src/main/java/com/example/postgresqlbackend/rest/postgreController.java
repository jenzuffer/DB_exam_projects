package com.example.postgresqlbackend.rest;

import com.example.postgresqlbackend.datalayer.postgreDataImpl;
import com.example.postgresqlbackend.dto.BookingDTO;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.net.URI;

@RestController
@RequestMapping("/booking")
public class postgreController {

    private postgreDataImpl postgreData = new postgreDataImpl();
    RestTemplate restTemplate = new RestTemplate();
    URI uri;


    @PostMapping("/createbooking/")
    @ResponseBody
    public boolean createBooking(@RequestBody BookingDTO bookingDTO){
        return postgreData.createBooking(bookingDTO);
    }
}
