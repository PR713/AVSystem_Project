package org.example.model;

import org.example.enums.RoadDirection;
import org.example.trafficstrategy.TrafficLightStrategy;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Intersection {
    private final TrafficLight trafficLight;
    private final Map<RoadDirection, List<Vehicle>> waitingVehicles;
    private final Map<RoadDirection, Integer> numOfVehiclesPerDirection;
    private final List<Vehicle> vehiclesThatLeft;
    private final TrafficLightStrategy trafficLightStrategy;


    public Intersection(TrafficLightStrategy trafficLightStrategy) {
        this.trafficLight = new TrafficLight();
        this.waitingVehicles = new HashMap<>();
        this.numOfVehiclesPerDirection = new HashMap<>();
        this.trafficLightStrategy = trafficLightStrategy;

        for (RoadDirection direction : RoadDirection.values()) {
            waitingVehicles.put(direction, new ArrayList<>());
            numOfVehiclesPerDirection.put(direction, 0);
        }

        this.vehiclesThatLeft = new ArrayList<>();
    }


    public void addVehicle(Vehicle vehicle) {
        waitingVehicles.get(vehicle.getStartDirection()).add(vehicle);
        numOfVehiclesPerDirection.put(vehicle.getStartDirection(), numOfVehiclesPerDirection.get(vehicle.getStartDirection()) + 1);
    }


    public void step() {
        //TODO invokes a managing method from given trafficLightStrategy
        boolean flag = false;

        for (List<Vehicle> list : waitingVehicles.values()) {
            if (!list.isEmpty()) {
                flag = true;
                break;
            }
        }

        if (flag) {
            trafficLightStrategy.getGreenLights(numOfVehiclesPerDirection);
        }
    }


    //public theMostCrowded
    //TODO np pojedyncze drogi priorytetowane, albo pary a potem proporcje może :)
}
