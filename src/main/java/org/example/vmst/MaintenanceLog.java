package org.example.vmst;

import java.time.LocalDate;

public class MaintenanceLog {
    private int id;
    private String vehicleVin;
    private LocalDate maintenanceDate;
    private String description;
    private int technicianId;

    // Constructors
    public MaintenanceLog() {}

    public MaintenanceLog(int id, String vehicleVin, LocalDate maintenanceDate, String description, int technicianId) {
        this.id = id;
        this.vehicleVin = vehicleVin;
        this.maintenanceDate = maintenanceDate;
        this.description = description;
        this.technicianId = technicianId;
    }

    // Getters
    public int getId() {
        return id;
    }

    public String getVehicleVin() {
        return vehicleVin;
    }

    public LocalDate getMaintenanceDate() {
        return maintenanceDate;
    }

    public String getDescription() {
        return description;
    }

    public int getTechnicianId() {
        return technicianId;
    }

    // Setters
    public void setId(int id) {
        this.id = id;
    }

    public void setVehicleVin(String vehicleVin) {
        this.vehicleVin = vehicleVin;
    }

    public void setMaintenanceDate(LocalDate maintenanceDate) {
        this.maintenanceDate = maintenanceDate;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setTechnicianId(int technicianId) {
        this.technicianId = technicianId;
    }
}
