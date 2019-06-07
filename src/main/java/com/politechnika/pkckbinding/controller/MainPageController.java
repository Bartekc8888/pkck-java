package com.politechnika.pkckbinding.controller;

import com.politechnika.pkckbinding.dto.CreationDto;
import com.politechnika.pkckbinding.dto.flightschedule.Launch;
import com.politechnika.pkckbinding.service.ExistingIdsService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@AllArgsConstructor
public class MainPageController {

    private ExistingIdsService existingIdsService;

    @GetMapping("/")
    public String mainPage() {
        return "index";
    }

    @GetMapping("/launches")
    public String launches(Model launch) {
        CreationDto creationDto = existingIdsService.getCreationDto();
        launch.addAttribute("creationDto", creationDto);
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
