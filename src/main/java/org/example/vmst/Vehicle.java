package org.example.vmst;

public class Vehicle {
    private String vin;
    private String make;
    private String model;
    private int year;
    private String licensePlate;
    private int ownerId;
    private Integer assignedTechnicianId; // Nullable

    // Constructors
    public Vehicle() {}

    public Vehicle(String vin, String make, String model, int year, String licensePlate, int ownerId, Integer assignedTechnicianId) {
        this.vin = vin;
        this.make = make;
        this.model = model;
        this.year = year;
        this.licensePlate = licensePlate;
        this.ownerId = ownerId;
        this.assignedTechnicianId = assignedTechnicianId;
    }

    // Getters
    public String getVin() {
        return vin;
    }

    public String getMake() {
        return make;
    }

    public String getModel() {
        return model;
    }

    public int getYear() {
        return year;
    }

    public String getLicensePlate() {
        return licensePlate;
    }

    public int getOwnerId() {
        return ownerId;
    }

    public Integer getAssignedTechnicianId() {
        return assignedTechnicianId;
    }

    // Setters
    public void setVin(String vin) {
        this.vin = vin;
    }

    public void setMake(String make) {
        this.make = make;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public void setLicensePlate(String licensePlate) {
        this.licensePlate = licensePlate;
    }

    public void setOwnerId(int ownerId) {
        this.ownerId = ownerId;
    }

    public void setAssignedTechnicianId(Integer assignedTechnicianId) {
        this.assignedTechnicianId = assignedTechnicianId;
    }
}
