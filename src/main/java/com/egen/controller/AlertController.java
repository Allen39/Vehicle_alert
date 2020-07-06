package com.egen.controller;

import com.egen.entity.Alert;
import com.egen.service.AlertService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.List;

@RestController
@RequestMapping("/alerts")
@CrossOrigin
public class AlertController {

    private final AlertService Alertservice;

    @Autowired
    public AlertController(AlertService alertService) {
        this.Alertservice = alertService;
    }

    @GetMapping(path = "/vin/{vin}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public List<Alert> findAlerts(@PathVariable String vin) {
        return Alertservice.findAlertsByVin(vin);
    }

    @GetMapping(path = "/priority/{priority_type}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public List<Alert> findAlertsByPriority(@PathVariable String priority_type) {
        return Alertservice.findAlertsByPriority(priority_type);
    }

    @GetMapping(path="/pastAlerts/{hours}")
    public List<Alert> getAlertsInLastTwoHour(@PathVariable int hours) throws ParseException {
        return Alertservice.getAlertsInLastTwoHour(hours);
    }
}
