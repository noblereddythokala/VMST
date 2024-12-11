package org.example.vmst;
import java.sql.Connection;
import java.sql.SQLException;

public class Databasetest {
    public static void main(String[] args) {
        try (Connection conn = Database.getConnection()) {
            if (conn != null) {
                System.out.println("Connection to the database was successful!");
            } else {
                System.out.println("Failed to make connection!");
            }
        } catch (SQLException e) {
            System.err.println("Error while connecting to the database: " + e.getMessage());
        }
    }
}
