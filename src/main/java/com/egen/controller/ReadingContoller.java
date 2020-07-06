package com.egen.controller;

import com.egen.entity.Reading;
import com.egen.service.ReadingService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/readings")
@CrossOrigin(origins = "http://mocker.egen.academy")
public class ReadingContoller {

    private final ReadingService service;

    @Autowired
    public ReadingContoller(ReadingService service) {
        this.service = service;
    }

    @RequestMapping(method = RequestMethod.GET,
    produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public List<Reading> getAllReadings() {
        return service.getAllReadings();
    }

    @RequestMapping(method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public Reading insertReadings(@RequestBody Reading reading) throws JsonProcessingException {
        System.out.println("Creating Reading record");
        return service.insertReadings(reading);
    }
}
