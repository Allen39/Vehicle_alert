package com.egen.service;

import com.egen.entity.Vehicle;

import java.util.List;

public interface VehicleService {

    List<Vehicle> getAllVehicles();

    Vehicle getOneVehicle(String vin);

    Iterable<Vehicle> upsert(List<Vehicle> vList);

    //Vehicle create(Vehicle v);

    //Vehicle delete(String vin);

}
