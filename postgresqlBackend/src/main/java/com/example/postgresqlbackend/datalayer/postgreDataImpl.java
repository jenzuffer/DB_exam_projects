package com.example.postgresqlbackend.datalayer;

import com.example.postgresqlbackend.dto.BookingDTO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class postgreDataImpl {

    private static Connection connection;

    public postgreDataImpl() {
        try {
            Class.forName("org.postgresql.Driver");
            connection = DriverManager
                    .getConnection("jdbc:postgresql://localhost:5432/postgres?currentSchema=public",
                            "postgres_user", "postgres_pass");
            connection.setAutoCommit(true);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }

    }

    private static void closeDriver() {
        try {
            connection.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public boolean createBooking(BookingDTO bookingDTO) {

        String sql = "insert into public.booking_information(passport, bookintcount, departure, " +
                "destination, airportnames ) values(?, ?, ?, ?, ?)";
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, bookingDTO.getPassport());
            statement.setInt(2, bookingDTO.getBookingCount());
            statement.setString(3, bookingDTO.getAirportDeparture());
            statement.setString(4, bookingDTO.getAirportArrival());
            statement.setArray(5,  connection.createArrayOf("text", bookingDTO.getAirportNames().toArray()));
            System.out.println(statement.toString());
            statement.executeUpdate();
            statement.close();
            return true;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return false;
    }
}
