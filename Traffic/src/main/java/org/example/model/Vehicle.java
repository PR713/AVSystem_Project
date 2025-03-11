package org.example.model;

import org.example.enums.RoadDirection;

public class Vehicle {
    private final String id;
    private final RoadDirection startDirection;
    private final RoadDirection endDirection;
    private final int idNum;

    public Vehicle(String vehicleId, RoadDirection startDirection, RoadDirection endDirection) {
        this.id = vehicleId;
        this.startDirection = startDirection;
        this.endDirection = endDirection;
        this.idNum = Integer.parseInt(id.replaceAll("\\D+", ""));
    }

    public String getVehicleId() {
        return id;
    }

    public int getVehicleIdNumber() {
        return idNum;
    }

    public RoadDirection getStartDirection() {
        return startDirection;
    }

    public RoadDirection getEndDirection() {
        return endDirection;
    }


    @Override
    public String toString() {
        return "Vehicle: " + id +
                "\n, Start Direction: " + startDirection +
                "\n, End Direction: " + endDirection;
    }
}
