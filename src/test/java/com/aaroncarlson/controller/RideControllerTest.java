package com.aaroncarlson.controller;

import com.aaroncarlson.model.Ride;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class RideControllerTest {

    private final String HOSTNAME = "http://localhost:";
    private final String API_VERSION = "/api/v1";
    private final String RIDE = "/rides";

    @LocalServerPort
    private int port;

    @Test
    public void testGetAllRides() throws Exception {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<List<Ride>> response = restTemplate.exchange(HOSTNAME + port + API_VERSION + RIDE,
                HttpMethod.GET, null, new ParameterizedTypeReference<List<Ride>>() {});

        List<Ride> rides = response.getBody();

        for (Ride ride : rides) {
            System.out.println(ride);
        }

        assertThat(response.getStatusCode(), equalTo(HttpStatus.OK));
    }

    @Test
    public void testGetRideById() throws Exception {
        RestTemplate restTemplate = new RestTemplate();

        Ride ride = restTemplate.getForObject(HOSTNAME + port + API_VERSION + RIDE + "/1", Ride.class);

        System.out.println(ride);

        assertNotNull(ride);
    }

    @Test
    public void testPutRide() throws Exception {
        RestTemplate restTemplate = new RestTemplate();
        Ride ride = new Ride();
        ride.setName("Range Rover");
        ride.setDuration(90);

        ride = restTemplate.postForObject(HOSTNAME + port + API_VERSION + RIDE, ride, Ride.class);

        System.out.println("[Ride] - " + ride);
    }

    @Test
    public void testEditRide() throws Exception {
        RestTemplate restTemplate = new RestTemplate();

        Ride ride = restTemplate.getForObject(HOSTNAME + port + API_VERSION + RIDE + "/1", Ride.class);

        ride.setName(ride.getName() + " - Update");
        ride.setDuration(ride.getDuration() + 1);

        restTemplate.put(HOSTNAME + port + API_VERSION + RIDE, ride);

        System.out.println("[Ride] - " + ride);
    }

    @Test
    public void testBatchUpdateRides() throws Exception {
        RestTemplate restTemplate = new RestTemplate();

        restTemplate.getForObject(HOSTNAME + port + API_VERSION + RIDE + "/batch", Object.class);
    }

    @Test
    public void testDeleteRide() throws Exception {
        RestTemplate restTemplate = new RestTemplate();

        restTemplate.delete(HOSTNAME + port + API_VERSION + RIDE + "/17");
    }

}
