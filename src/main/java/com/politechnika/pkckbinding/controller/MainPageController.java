package com.politechnika.pkckbinding.controller;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import java.time.LocalDateTime;
import java.util.GregorianCalendar;
import java.util.Map;

import com.politechnika.pkckbinding.dto.CreationDto;
import com.politechnika.pkckbinding.dto.flightschedule.Launch;
import com.politechnika.pkckbinding.service.ExistingIdsService;
import com.politechnika.pkckbinding.tool.XmlConverter;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@AllArgsConstructor
public class MainPageController {

    private ExistingIdsService existingIdsService;
    private XmlConverter xmlConverter;

    @GetMapping("/")
    public String mainPage() {
        return "index";
    }

    @ModelAttribute("localDateTime")
    public XMLGregorianCalendar getCurrentDateTime() throws DatatypeConfigurationException {
        GregorianCalendar gregorianCalendar = new GregorianCalendar();
        DatatypeFactory datatypeFactory = DatatypeFactory.newInstance();
        return datatypeFactory.newXMLGregorianCalendar(gregorianCalendar);
    }

    @ModelAttribute("creationDto")
    public CreationDto getCreationDto() {
        return existingIdsService.getCreationDto();
    }

    @GetMapping("/launches")
    public String launches(Model launch) {
        launch.addAttribute("launch", new Launch());
        return "launches";
    }

    @PostMapping("/launches")
    public void saveLaunch(@ModelAttribute Launch launch) {
        int size = existingIdsService.getLaunchIds().size();
        String launchId = "lan_" + (size + 1);
        launch.setLaunchId(launchId);
        xmlConverter.getFlightSchedule().getLaunches().getLaunch().add(launch);
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
