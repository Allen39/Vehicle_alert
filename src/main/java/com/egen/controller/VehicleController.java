package com.egen.controller;

import com.egen.entity.Vehicle;
import com.egen.service.VehicleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/vehicles")
@CrossOrigin(origins = "http://mocker.egen.academy")
public class VehicleController {

    private final VehicleService service;

    @Autowired
    public VehicleController(VehicleService service) {
        this.service = service;
    }

    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public List<Vehicle> getAllVehicles() {
        return service.getAllVehicles();
    }

    @GetMapping(path = "/{vin}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public Vehicle getOneVehicle(@PathVariable String vin) {
        return service.getOneVehicle(vin);
    }


    @PutMapping
    public List<Vehicle> upsert(@RequestBody List<Vehicle> vList) {
//        System.out.println("Receiving vehicles list with size:- "+vList.size());
        return (List<Vehicle>) service.upsert(vList);
    }

}
