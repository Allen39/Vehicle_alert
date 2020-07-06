package com.egen.service;

import com.egen.entity.Alert;

import java.text.ParseException;
import java.util.List;

public interface AlertService {

    List<Alert> findAlertsByPriority(String priority);

    List<Alert> findAlertsByVin(String vin);

    Alert create(Alert alert);

    List<Alert> getAlertsInLastTwoHour(int hours) throws ParseException;
}
