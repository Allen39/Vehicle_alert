package com.egen.service;

import com.egen.entity.Reading;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.util.List;

public interface ReadingService {

    List<Reading> getAllReadings();

    Reading getOneReading(String id);

    Reading insertReadings(Reading reading) throws JsonProcessingException;

}
