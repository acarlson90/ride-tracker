package com.aaroncarlson.repository;

import com.aaroncarlson.model.Ride;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class RideRepository {

    public List<Ride> findAll() {
        Ride teslaModelS = new Ride();
        teslaModelS.setName("Tesla Model S");
        teslaModelS.setDuration(120);

        List<Ride> rides = new ArrayList<>();
        rides.add(teslaModelS);
        return rides;
    }
}
