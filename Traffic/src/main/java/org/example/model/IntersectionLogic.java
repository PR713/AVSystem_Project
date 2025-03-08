package org.example.model;

import org.example.enums.RoadDirection;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class IntersectionLogic {
    private final TrafficLight trafficLight;
    private final Map<RoadDirection, List<Vehicle>> waitingVehicles;
    private final List<Vehicle> vehiclesThatLeft;


    public IntersectionLogic() {
        this.trafficLight = new TrafficLight();
        this.waitingVehicles = new HashMap<>();

        for (RoadDirection direction : RoadDirection.values()) {
            waitingVehicles.put(direction, new ArrayList<>());
        }

        this.vehiclesThatLeft = new ArrayList<>();
    }


    public void addVehicle(Vehicle vehicle) {
        waitingVehicles.get(vehicle.getStartDirection()).add(vehicle);
    }


    public void step() {

    }


    //public theMostCrowded
    //TODO np pojedyncze drogi priorytetowane, albo pary a potem proporcje może :)
}
