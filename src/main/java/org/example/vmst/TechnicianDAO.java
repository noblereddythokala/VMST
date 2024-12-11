package org.example.vmst;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TechnicianDAO {

    // Fetch all technicians
    public List<User> getAllTechnicians() throws SQLException {
        List<User> technicians = new ArrayList<>();
        String sql = "SELECT * FROM Users WHERE role = 'technician'";
        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                User technician = new User();
                technician.setId(rs.getInt("id"));
                technician.setUsername(rs.getString("username"));
                technician.setPassword(rs.getString("password"));
                technician.setRole(rs.getString("role"));
                technician.setName(rs.getString("name"));
                technician.setContactInfo(rs.getString("contact_info"));
                technicians.add(technician);
            }
        }
        return technicians;
    }

    // Assign a technician to a vehicle (Alternative method)
    public void assignTechnicianToVehicle(int technicianId, String vehicleVin) throws SQLException {
        String sql = "UPDATE Vehicles SET assigned_technician_id = ? WHERE vin = ?";
        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, technicianId);
            stmt.setString(2, vehicleVin);
            stmt.executeUpdate();
        }
    }

    // Additional technician-specific operations can be added here
}
