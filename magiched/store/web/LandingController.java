package com.magiched.store.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LandingController {

    @GetMapping("/")
    public String landing() {
        return "landing";
    }

    @GetMapping("/commissions")
    public String commissions() {
        return "coming-soon";
    }

    @GetMapping("/about")
    public String about() {
        return "coming-soon";
    }
}