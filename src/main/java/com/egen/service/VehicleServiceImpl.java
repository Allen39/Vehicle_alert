package com.egen.service;

import com.egen.exception.VehicleNotFoundException;
import com.egen.repository.VehicleRepository;
import com.egen.entity.Vehicle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class VehicleServiceImpl implements VehicleService {

    private final VehicleRepository repository;

    @Autowired
    public VehicleServiceImpl(VehicleRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Vehicle> getAllVehicles() {
        return (List<Vehicle>) repository.findAll();
    }

    @Override
    public Vehicle getOneVehicle(String vin) {
        Optional<Vehicle> existing = repository.findByVin(vin);
        return existing.orElseThrow(() -> new VehicleNotFoundException("Vehicle with VIN:- "+vin+" not found"));
    }

    @Override
    public List<Vehicle> upsert(List<Vehicle> vList) {
        return (List<Vehicle>) repository.saveAll(vList);
    }

//    @Override
//    public Vehicle create(Vehicle v) {
//        return null;
//    }


//
//    @Override
//    public Vehicle delete(String vin) {
//        return null;
//    }

}
