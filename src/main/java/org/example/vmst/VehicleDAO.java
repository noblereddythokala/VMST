package org.example.vmst;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class VehicleDAO {

    // Add a new vehicle
    public void addVehicle(Vehicle vehicle) throws SQLException {
        String sql = "INSERT INTO Vehicles (vin, make, model, year, license_plate, owner_id, assigned_technician_id) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, vehicle.getVin());
            stmt.setString(2, vehicle.getMake());
            stmt.setString(3, vehicle.getModel());
            stmt.setInt(4, vehicle.getYear());
            stmt.setString(5, vehicle.getLicensePlate());
            stmt.setInt(6, vehicle.getOwnerId());
            if (vehicle.getAssignedTechnicianId() != null) {
                stmt.setInt(7, vehicle.getAssignedTechnicianId());
            } else {
                stmt.setNull(7, Types.INTEGER);
            }
            stmt.executeUpdate();
        }
    }

    // Fetch all vehicles
    public List<Vehicle> getAllVehicles() throws SQLException {
        List<Vehicle> vehicles = new ArrayList<>();
        String sql = "SELECT * FROM Vehicles";
        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Vehicle vehicle = new Vehicle();
                vehicle.setVin(rs.getString("vin"));
                vehicle.setMake(rs.getString("make"));
                vehicle.setModel(rs.getString("model"));
                vehicle.setYear(rs.getInt("year"));
                vehicle.setLicensePlate(rs.getString("license_plate"));
                vehicle.setOwnerId(rs.getInt("owner_id"));
                int techId = rs.getInt("assigned_technician_id");
                if (!rs.wasNull()) {
                    vehicle.setAssignedTechnicianId(techId);
                } else {
                    vehicle.setAssignedTechnicianId(null);
                }
                vehicles.add(vehicle);
            }
        }
        return vehicles;
    }

    // Fetch vehicles by owner
    public List<Vehicle> getVehiclesByOwner(int ownerId) throws SQLException {
        List<Vehicle> vehicles = new ArrayList<>();
        String sql = "SELECT * FROM Vehicles WHERE owner_id = ?";
        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, ownerId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Vehicle vehicle = new Vehicle();
                vehicle.setVin(rs.getString("vin"));
                vehicle.setMake(rs.getString("make"));
                vehicle.setModel(rs.getString("model"));
                vehicle.setYear(rs.getInt("year"));
                vehicle.setLicensePlate(rs.getString("license_plate"));
                vehicle.setOwnerId(rs.getInt("owner_id"));
                int techId = rs.getInt("assigned_technician_id");
                if (!rs.wasNull()) {
                    vehicle.setAssignedTechnicianId(techId);
                } else {
                    vehicle.setAssignedTechnicianId(null);
                }
                vehicles.add(vehicle);
            }
        }
        return vehicles;
    }

    // Assign technician to vehicle
    public void assignTechnician(String vin, int technicianId) throws SQLException {
        String sql = "UPDATE Vehicles SET assigned_technician_id = ? WHERE vin = ?";
        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, technicianId);
            stmt.setString(2, vin);
            stmt.executeUpdate();
        }
    }

    // Additional CRUD operations can be implemented similarly
}
