package com.example.demo.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ReservationsController {

    @GetMapping("/reservations")
    public String showReservationsPage() {
        return "reservations";
    }
}
