package org.example.model;

public class Vehicle {
    private final String vehicleId;
    private final String startDirection;
    private final String endDirection;

    public Vehicle(String vehicleId, String startDirection, String endDirection) {
        this.vehicleId = vehicleId;
        this.startDirection = startDirection;
        this.endDirection = endDirection;
    }

    public String getVehicleId() {
        return vehicleId;
    }

    public String getStartDirection() {
        return startDirection;
    }

    public String getEndDirection() {
        return endDirection;
    }

    @Override
    public String toString() {
        return "Vehicle: " + vehicleId +
                "\n, Start Direction: " + startDirection +
                "\n, End Direction: " + endDirection;
    }
}
