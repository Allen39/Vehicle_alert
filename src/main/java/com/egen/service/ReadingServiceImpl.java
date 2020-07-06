package com.egen.service;

import com.egen.awsMessaging.SnsNotificationSender;
import com.egen.exception.ReadingNotFoundException;
import com.egen.repository.AlertRepository;
import com.egen.repository.ReadingsRepository;
import com.egen.repository.VehicleRepository;
import com.egen.entity.Alert;
import com.egen.entity.Reading;
import com.egen.entity.Vehicle;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class ReadingServiceImpl implements ReadingService {

    private final ReadingsRepository readingsRepository;

    private final VehicleRepository vehicleRepository;

    private final AlertRepository alertRepository;

    private SnsNotificationSender snsNotificationSender;

    private  ObjectMapper objectMapper;


    @Autowired
    public ReadingServiceImpl(ObjectMapper objectMapper,SnsNotificationSender snsNotificationSender,ReadingsRepository readingsRepository, VehicleRepository vehicleRepository, AlertRepository alertRepository) {
        this.readingsRepository = readingsRepository;
        this.vehicleRepository = vehicleRepository;
        this.alertRepository = alertRepository;
        this.snsNotificationSender = snsNotificationSender;
        this.objectMapper = objectMapper;
    }

    @Override
    public List<Reading> getAllReadings() {
        return (List<Reading>) readingsRepository.findAll();
    }

    @Override
    public Reading getOneReading(String id) {
        Optional<Reading> existing = readingsRepository.findById(id);
        if(!existing.isPresent()) {
            throw new ReadingNotFoundException("Reading with id:- "+id+" Not Found");
        }
        return existing.get();
    }

    @Override
    public Reading insertReadings(Reading reading) throws JsonProcessingException {
        Optional<Vehicle> existing = vehicleRepository.findByVin(reading.getVin());
        Date date = new Date();
        SimpleDateFormat df = new SimpleDateFormat("YYYY-MM-DD hh:mm:ss.s");
        try {
            date = df.parse(reading.getTimestamp());
        }
        catch (ParseException e) {
            System.out.println(e.getMessage());
        }

        if(existing.isPresent()) {
            if(reading.getFuelVolume() < 0.1 * existing.get().getMaxFuelVolume()) {
                Alert alert = new Alert();
                alert.setVin(reading.getVin());
                alert.setPriority("MEDIUM");
                alert.setDescription("Low Fuel");
                alert.setTimestamp(date);
                alert.setLatitude(reading.getLatitude());
                alert.setLongitude(reading.getLongitude());
                alertRepository.save(alert);
                System.out.println("Alert for Vehicle with VIN:- "+reading.getVin()+" with priority MEDIUM");
            }
            if(reading.getEngineRpm() > existing.get().getRedlineRpm()) {
                Alert alert = new Alert();
                alert.setVin(reading.getVin());
                alert.setPriority("HIGH");
                alert.setDescription("High Engine Rpm");
                alert.setTimestamp(date);
                alert.setLatitude(reading.getLatitude());
                alert.setLongitude(reading.getLongitude());

                //send email
                //String message = objectMapper.writeValueAsString(alert);
                String message = "High alert for vehicel with VIN " + alert.getVin() + " and the cause of alert is " +alert.getDescription();
                snsNotificationSender.send("HIGH Alert", message);

                alertRepository.save(alert);
                System.out.println("Alert for Vehicle with VIN:- "+reading.getVin()+" with priority HIGH");
            }
            if(reading.getTires().getFrontLeft() < 32 || reading.getTires().getFrontLeft() > 36 || reading.getTires().getFrontRight() > 36 || reading.getTires().getFrontRight() < 32 || reading.getTires().getRearLeft() > 36 || reading.getTires().getRearLeft() < 32 || reading.getTires().getRearRight() > 36 || reading.getTires().getRearRight() < 32) {
                System.out.print("Tires low");
                Alert alert = new Alert();
                alert.setVin(reading.getVin());
                alert.setPriority("LOW");
                alert.setDescription("High/Low Tire Pressure");
                alert.setTimestamp(date);
                alert.setLatitude(reading.getLatitude());
                alert.setLongitude(reading.getLongitude());
                alertRepository.save(alert);
                System.out.println("Alert for Vehicle with VIN:- "+reading.getVin()+" with priority LOW");
            }
            if(reading.isEngineCoolantLow() || reading.isCheckEngineLightOn()) {
                System.out.print("Coolant problem");
                Alert alert = new Alert();
                alert.setVin(reading.getVin());
                alert.setPriority("LOW");
                if (reading.isEngineCoolantLow())
                {
                alert.setDescription("Engine Coolant Low");
                }else
                {
                    alert.setDescription("Engine Light On");
                }
                alert.setTimestamp(date);
                alert.setLatitude(reading.getLatitude());
                alert.setLongitude(reading.getLongitude());
                alertRepository.save(alert);
                System.out.println("Alert for Vehicle with VIN:- "+reading.getVin()+" with priority LOW");
            }
        }
        return readingsRepository.save(reading);
    }

}
