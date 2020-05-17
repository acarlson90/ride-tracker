package com.aaroncarlson.repository;

import com.aaroncarlson.model.Ride;
import com.aaroncarlson.util.RideRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class RideRepository  {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public Ride getRideById(final Long id) {
        return jdbcTemplate.queryForObject("SELECT * FROM ride WHERE ride_id = ?", new RideRowMapper(), id);
    }

    public List<Ride> getAllRides() {
        List<Ride> rides = jdbcTemplate.query("SELECT * FROM ride", new RideRowMapper());

        return rides;
    }

    public Ride createRide(final Ride ride) {
        Number key;

        /**
         * 1. Does NOT return ID/key of newly created Ride
         * 2. Have to define database schema in Java
         * 3. Uses anonymous inner class
         */

        // OPTION ONE: JDBCTemplate - straight database call
        jdbcTemplate.update("INSERT into ride (ride_name, ride_duration) values (?, ?)",
                ride.getName(), ride.getDuration());

        // OPTION TWO: SimpleJdbcTemplate - more like an ORM (NOTE: Order doesn't matter as long as it is before executeAndReturnKey())
        SimpleJdbcInsert insert = new SimpleJdbcInsert(jdbcTemplate);
        // Set Generated Key Name by Database Column name
        insert.setGeneratedKeyName("ride_id");
        // List of Database Columns
        List<String> columns = new ArrayList<>();
        columns.add("ride_name");
        columns.add("ride_duration");
        // Establish Database Table Name and Columns
        insert.setTableName("ride");
        insert.setColumnNames(columns);
        // Create a Map per row
        Map<String, Object> data = new HashMap<>();
        data.put("ride_name", ride.getName());
        data.put("ride_duration", ride.getDuration());

        key = insert.executeAndReturnKey(data);

        System.out.println("Key: " + key);

        // OPTION THREE
        // KeyHolder - is what stores the key that gets retrieved from the database while we do an update
        // GeneratedKeyHolder - since we have an SERIAL (auto incrementing) field
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                PreparedStatement preparedStatement = connection.prepareStatement("INSERT into ride (ride_name, ride_duration) values (?, ?)",
                        new String[] {"ride_id"});
                preparedStatement.setString(1, ride.getName());
                preparedStatement.setInt(2, ride.getDuration());
                return preparedStatement;
            }
        }, keyHolder);

        key = keyHolder.getKey();

        return getRideById(key.longValue());
    }

    public Ride editRide(final Ride ride) {
        jdbcTemplate.update("UPDATE ride SET ride_name = ?, ride_duration = ? WHERE ride_id = ?",
                ride.getName(), ride.getDuration(), ride.getId());

        return getRideById(ride.getId());
    }

    public void editRides(final List<Object[]> pairs) {
        // NOTE: Can use batchUpdate for INSERTing data as well
        jdbcTemplate.batchUpdate("UPDATE ride SET ride_name = ?, ride_duration = ? , ride_date = ?  WHERE ride_id = ? ", pairs);

    }

    public void deleteRide(final Long id) {

        /**
         * 1. ? must be in specific order
         * 2. NamedParameter is UNordered, can create NamedParameter instance at class level (instead of method level)
         * NOTE: NamedParameter can be used for all other methods above
         */

        // OPTION ONE
        Ride ride = getRideById(id);
        jdbcTemplate.update("DELETE FROM ride WHERE ride_id = ? ", ride.getId());

        // OPTION TWO - Named Parameters
        NamedParameterJdbcTemplate namedTemplate = new NamedParameterJdbcTemplate(jdbcTemplate);
        Map<String, Object> parameterMap = new HashMap<>();
        parameterMap.put("ride_id", id);

        namedTemplate.update("DELETE FROM ride WHERE ride_id = :ride_id", parameterMap);
    }

}
