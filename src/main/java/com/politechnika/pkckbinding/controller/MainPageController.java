package com.politechnika.pkckbinding.controller;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import java.util.GregorianCalendar;
import java.util.Objects;

import com.politechnika.pkckbinding.dto.CreationDto;
import com.politechnika.pkckbinding.dto.customers.Customer;
import com.politechnika.pkckbinding.dto.flightschedule.Launch;
import com.politechnika.pkckbinding.dto.flightschedule.Location;
import com.politechnika.pkckbinding.dto.flightschedule.Payload;
import com.politechnika.pkckbinding.dto.launchpads.Launchpad;
import com.politechnika.pkckbinding.dto.rockets.Rocket;
import com.politechnika.pkckbinding.service.ExistingIdsService;
import com.politechnika.pkckbinding.tool.XmlConverter;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@AllArgsConstructor
public class MainPageController {

    private ExistingIdsService existingIdsService;
    private XmlConverter xmlConverter;

    @GetMapping("/")
    public String mainPage() {
        return "index";
    }


    @PostMapping("/savexml")
    public String saveXml() {
        xmlConverter.saveXmlFile(xmlConverter.getFlightSchedule());

        return "redirect:/";
    }

    @PostMapping("/loadxml")
    public String loadXml() {
        xmlConverter.setFlightSchedule(xmlConverter.loadXmlFile());

        return "redirect:/";
    }

    @RequestMapping(method = RequestMethod.POST, value = "/generateHtml", produces = MediaType.TEXT_HTML_VALUE)
    @ResponseBody
    public String generateHtml() {
        return xmlConverter.generateHtml();
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
        String launchId = existingIdsService.generateId("lan_", existingIdsService.getLaunchIds());
        launch.setLaunchId(launchId);
        xmlConverter.getFlightSchedule().getLaunches().getLaunch().add(launch);
    }

    @GetMapping("/launchpads")
    public String launchpads(Model launchpad) {
        launchpad.addAttribute("launchpad", new Launchpad());
        return "launchpads";
    }

    @PostMapping("/launchpads")
    public void saveLaunchpad(@ModelAttribute Launchpad launchpad) {
        String launchpadId = existingIdsService.generateId("lp_", existingIdsService.getLaunchpadIds());
        launchpad.setLaunchpadId(launchpadId);
        xmlConverter.getFlightSchedule().getLaunchpads().getLaunchpad().add(launchpad);
    }

    @GetMapping("/customers")
    public String customers(Model customer) {
        customer.addAttribute("customer", new Customer());
        return "customers";
    }

    @PostMapping("/customers")
    public void saveCustomer(@ModelAttribute Customer customer) {
        String launchId = existingIdsService.generateId("cst_", existingIdsService.getCustomerIds());
        customer.setCustomerId(launchId);
        xmlConverter.getFlightSchedule().getCustomers().getCustomer().add(customer);
    }

    @GetMapping("/rockets")
    public String rockets(Model rocket) {
        rocket.addAttribute("rocket", new Rocket());
        return "rockets";
    }

    @PostMapping("/rockets")
    public void saveRocket(@ModelAttribute Rocket rocket) {
        String rocketId = existingIdsService.generateId("roc_", existingIdsService.getRocketIds());
        rocket.setRocketId(rocketId);
        xmlConverter.getFlightSchedule().getRockets().getRocket().add(rocket);
    }

    @GetMapping("/payloads")
    public String payloads(Model payload) {
        payload.addAttribute("payload", new Payload());
        return "payloads";
    }

    @PostMapping("/payloads")
    public void savePayload(@ModelAttribute Payload payload) {
        String payloadId = existingIdsService.generateId("pal_", existingIdsService.getPayloadIds());
        payload.getPayloadType().setCapsule(Objects.equals(payload.getPayloadType().getCapsule(), "") ? null : payload.getPayloadType().getCapsule());
        payload.getPayloadType().setCargo(Objects.equals(payload.getPayloadType().getCargo(), "") ? null : payload.getPayloadType().getCargo());
        payload.getPayloadType().setSatellite(Objects.equals(payload.getPayloadType().getSatellite(), "") ? null : payload.getPayloadType().getSatellite());
        payload.setPayloadId(payloadId);
        xmlConverter.getFlightSchedule().getPayloads().getPayload().add(payload);
    }

    @GetMapping("/locations")
    public String locations(Model location) {
        location.addAttribute("location", new Location());
        return "locations";
    }

    @PostMapping("/locations")
    public void saveLocation(@ModelAttribute Location location) {
        String locationId = existingIdsService.generateId("loc_", existingIdsService.getLocationIds());
        location.setLocationId(locationId);
        xmlConverter.getFlightSchedule().getLocations().getLocation().add(location);
    }
}
