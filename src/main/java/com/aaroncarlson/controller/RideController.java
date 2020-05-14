package com.aaroncarlson.controller;

import com.aaroncarlson.model.Ride;
import com.aaroncarlson.service.RideService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class RideController {

    @Autowired
    private RideService rideService;

    @GetMapping("/api/v1/rides")
    public @ResponseBody List<Ride> getAllRides() {
        return rideService.getAllRides();
    }

}
