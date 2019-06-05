package com.politechnika.pkckbinding.controller;

import com.politechnika.pkckbinding.dto.flightschedule.Launch;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainPageController {

    @GetMapping("/")
    public String mainPage() {
        return "index";
    }

    @GetMapping("/launches")
    public String launches(Model launch) {
        launch.addAttribute("launch", new Launch());
        return "launches";
    }

    @GetMapping("/launchpads")
    public String launchpads() {
        return "launchpads";
    }

    @GetMapping("/customers")
    public String customers() {
        return "customers";
    }

    @GetMapping("/rockets")
    public String rockets() {
        return "rockets";
    }

    @GetMapping("/payloads")
    public String payloads() {
        return "payloads";
    }

    @GetMapping("/locations")
    public String locations() {
        return "locations";
    }
}
