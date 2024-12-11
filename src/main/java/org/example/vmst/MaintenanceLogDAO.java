package org.example.vmst;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class MaintenanceLogDAO {

    // Log a new maintenance activity
    public void logMaintenance(MaintenanceLog log) throws SQLException {
        String sql = "INSERT INTO MaintenanceLogs (vehicle_vin, maintenance_date, description, technician_id) VALUES (?, ?, ?, ?)";
        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, log.getVehicleVin());
            stmt.setDate(2, Date.valueOf(log.getMaintenanceDate()));
            stmt.setString(3, log.getDescription());
            stmt.setInt(4, log.getTechnicianId());
            stmt.executeUpdate();
        }
    }

    // Fetch maintenance logs by vehicle VIN
    public List<MaintenanceLog> getLogsByVehicle(String vin) throws SQLException {
        List<MaintenanceLog> logs = new ArrayList<>();
        String sql = "SELECT * FROM MaintenanceLogs WHERE vehicle_vin = ?";
        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, vin);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                MaintenanceLog log = new MaintenanceLog();
                log.setId(rs.getInt("id"));
                log.setVehicleVin(rs.getString("vehicle_vin"));
                log.setMaintenanceDate(rs.getDate("maintenance_date").toLocalDate());
                log.setDescription(rs.getString("description"));
                log.setTechnicianId(rs.getInt("technician_id"));
                logs.add(log);
            }
        }
        return logs;
    }

    // Additional CRUD operations can be implemented similarly
}
