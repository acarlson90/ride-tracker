package com.aaroncarlson.service;

import com.aaroncarlson.model.Ride;
import com.aaroncarlson.repository.RideRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class RideService {

    @Autowired
    private RideRepository rideRepository;

    public Ride getRideById(final Long id) {
        return rideRepository.getRideById(id);
    }

    public List<Ride> getAllRides() {
        return rideRepository.getAllRides();
    }

    public Ride createRide(final Ride ride) {
        return rideRepository.createRide(ride);
    }

    public Ride editRide(final Ride ride) {
        return rideRepository.editRide(ride);
    }

    public void batchRides() {
        List<Ride> rides = getAllRides();

        // Create name value pair
        List<Object[]> pairs = new ArrayList<>();

        for (Ride ride: rides) {
            // Order DOES matter
            Object[] updatedRide = {
                    ride.getName(), ride.getDuration(), new Timestamp(System.currentTimeMillis()), ride.getId()
            };
            pairs.add(updatedRide);
        }

        rideRepository.editRides(pairs);
    }

    public void deleteRide(final Long id) {
        rideRepository.deleteRide(id);
    }

}
