package com.example.postgresqlbackend.datalayer;

import com.example.postgresqlbackend.dto.BookingDTO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class postgreDataImpl {

    private static Connection connection;

    public postgreDataImpl() {
        try {
            Class.forName("org.postgresql.Driver");
            connection = DriverManager
                    .getConnection("jdbc:postgresql://localhost:5432/",
                    "postgres_user", "postgres_pass");
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }

    }

    public BookingDTO createBooking(BookingDTO bookingDTO) {
        
    }
}
