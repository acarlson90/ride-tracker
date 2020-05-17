package com.aaroncarlson.controller;

import com.aaroncarlson.model.Ride;
import com.aaroncarlson.service.RideService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1/rides")
public class RideController {

    @Autowired
    private RideService rideService;

    @GetMapping
    public List<Ride> getAllRides() {
        return rideService.getAllRides();
    }

    @GetMapping("{id}")
    public Ride getRideById(@PathVariable(value = "id") final Long id) {
        return rideService.getRideById(id);
    }

    @PostMapping
    public @ResponseBody Ride createRide(@Valid @RequestBody final Ride ride) {
        return rideService.createRide(ride);
    }

    @PutMapping
    public Ride editRide(@RequestBody final Ride ride) {
        return rideService.editRide(ride);
    }

    @GetMapping("/batch")
    public Object batchUpdateRide() {
        rideService.batchRides();

        return null;
    }

    @DeleteMapping("{id}")
    public Object deleteRide(@PathVariable(value =  "id") final Long id) {
        rideService.deleteRide(id);

        return null;
    }

}
