package com.egen.service;

import com.egen.awsMessaging.SnsNotificationSender;
import com.egen.exception.AlertNotFoundException;
import com.egen.repository.AlertRepository;
import com.egen.entity.Alert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
public class AlertServiceImpl implements AlertService{

    private final AlertRepository AlertRepository;


    @Autowired
    public AlertServiceImpl(AlertRepository AlertRepository) {
        this.AlertRepository = AlertRepository;

    }

    @Override
    public List<Alert> findAlertsByPriority(String priority) {
        Iterable<Alert> existing = AlertRepository.findByPriority(priority);
        if(!existing.iterator().hasNext()) {
            throw new AlertNotFoundException("Alerts for Vehicles with Priority:- "+priority+" not found");
        }
        return (List<Alert>) existing;
    }

    @Override
    public List<Alert> findAlertsByVin(String vin) {
        Iterable<Alert> existing = AlertRepository.findByVin(vin);
        if(!existing.iterator().hasNext()) {
            throw new AlertNotFoundException("Alerts for Vehicle with VIN:- "+vin+" not found");
        }
        return (List<Alert>) existing;
    }


    @Override
    public List<Alert> getAlertsInLastTwoHour(int hours) throws ParseException {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        Date now = new Date(System.currentTimeMillis() - 3600 * hours * 1000);
        String time = sdf.format(now);

        //return AlertRepository.findAllWithCreationDateTimeBefore(new SimpleDateFormat("yyyy-MM-dd HH:mm").parse("2020-07-05 10:00"));
        return AlertRepository.getAlertsInLastTwoHour(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").parse(time));
    }

    @Override
    public Alert create(Alert alert) {
        return AlertRepository.save(alert);
    }

}
