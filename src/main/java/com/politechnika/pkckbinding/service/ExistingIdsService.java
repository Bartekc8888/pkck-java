package com.politechnika.pkckbinding.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import com.politechnika.pkckbinding.dto.CreationDto;
import com.politechnika.pkckbinding.dto.customers.Customer;
import com.politechnika.pkckbinding.dto.flightschedule.FlightSchedule;
import com.politechnika.pkckbinding.dto.flightschedule.Launch;
import com.politechnika.pkckbinding.dto.flightschedule.Location;
import com.politechnika.pkckbinding.dto.flightschedule.Payload;
import com.politechnika.pkckbinding.dto.launchpads.Launchpad;
import com.politechnika.pkckbinding.dto.rockets.CurrencyType;
import com.politechnika.pkckbinding.dto.rockets.ForceUnitType;
import com.politechnika.pkckbinding.dto.rockets.MassUnitType;
import com.politechnika.pkckbinding.dto.rockets.Rocket;
import com.politechnika.pkckbinding.tool.XmlConverter;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class ExistingIdsService {
    private XmlConverter xmlConverter;

    public CreationDto getCreationDto() {
        return CreationDto.builder()
                          .currencyTypes(getCurrencies())
                          .massTypes(getMassTypes())
                          .forceUnitTypes(getThrustTypes())
                          .customerRef(getCustomerIds())
                          .launchpadRef(getLaunchpadIds())
                          .payloadRef(getPayloadIds())
                          .rocketRef(getRocketIds())
                          .launchRef(getLaunchIds())
                          .locationRef(getLocationIds())
                          .build();
    }

    private List<String> getThrustTypes() {
        ArrayList<ForceUnitType> currencyTypes = new ArrayList<>(Arrays.asList(ForceUnitType.values()));
        return currencyTypes.stream().map(Enum::toString).collect(Collectors.toList());
    }

    private List<String> getMassTypes() {
        ArrayList<MassUnitType> currencyTypes = new ArrayList<>(Arrays.asList(MassUnitType.values()));
        return currencyTypes.stream().map(Enum::toString).collect(Collectors.toList());
    }

    public List<String> getCurrencies() {
        ArrayList<CurrencyType> currencyTypes = new ArrayList<>(Arrays.asList(CurrencyType.values()));
        return currencyTypes.stream().map(Enum::toString).collect(Collectors.toList());
    }

    public List<String> getCustomerIds() {
        FlightSchedule flightSchedule = xmlConverter.getFlightSchedule();

        List<Customer> customers = flightSchedule.getCustomers().getCustomer();
        return customers.stream().map(Customer::getCustomerId).collect(Collectors.toList());
    }

    public List<String> getLaunchpadIds() {
        FlightSchedule flightSchedule = xmlConverter.getFlightSchedule();

        List<Launchpad> launchpads = flightSchedule.getLaunchpads().getLaunchpad();
        return launchpads.stream().map(Launchpad::getLaunchpadId).collect(Collectors.toList());
    }

    public List<String> getLaunchIds() {
        FlightSchedule flightSchedule = xmlConverter.getFlightSchedule();

        List<Launch> launch = flightSchedule.getLaunches().getLaunch();
        return launch.stream().map(Launch::getLaunchId).collect(Collectors.toList());
    }

    public List<String> getLocationIds() {
        FlightSchedule flightSchedule = xmlConverter.getFlightSchedule();

        List<Location> locations = flightSchedule.getLocations().getLocation();
        return locations.stream().map(Location::getLocationId).collect(Collectors.toList());
    }

    public List<String> getPayloadIds() {
        FlightSchedule flightSchedule = xmlConverter.getFlightSchedule();

        List<Payload> payloads = flightSchedule.getPayloads().getPayload();
        return payloads.stream().map(Payload::getPayloadId).collect(Collectors.toList());
    }

    public List<String> getRocketIds() {
        FlightSchedule flightSchedule = xmlConverter.getFlightSchedule();

        List<Rocket> rockets = flightSchedule.getRockets().getRocket();
        return rockets.stream().map(Rocket::getRocketId).collect(Collectors.toList());
    }

    public String generateId(String baseId, List<String> allIds) {
        Random generator = new Random();

        int size = allIds.size();
        String generatedId = baseId + (size + 1);
        while (allIds.contains(generatedId)) {
            generatedId = baseId + generator.nextInt();
        }

        return generatedId;
    }
}
