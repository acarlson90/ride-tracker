package com.aaroncarlson.util;

import com.aaroncarlson.model.Ride;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class RideRowMapper implements RowMapper<Ride> {

    @Override
    public Ride mapRow(ResultSet resultSet, int rowNumber) throws SQLException {
        Ride ride = new Ride();
        ride.setId(resultSet.getLong(("ride_id")));
        ride.setName(resultSet.getString("ride_name"));
        ride.setDuration(resultSet.getInt("ride_duration"));
        ride.setTimestamp(resultSet.getTimestamp("ride_date"));
        return ride;
    }

}
