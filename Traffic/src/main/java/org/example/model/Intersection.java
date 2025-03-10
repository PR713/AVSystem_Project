package org.example.model;

import org.example.enums.RoadDirection;
import org.example.trafficstrategy.TrafficLightStrategy;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Intersection {
    private final TrafficLights trafficLights;
    private final Map<RoadDirection, List<Vehicle>> waitingVehicles;
    private final Map<RoadDirection, Integer> numOfVehiclesPerDirection;
    private final List<Vehicle> vehiclesThatLeft;
    private final TrafficLightStrategy trafficLightStrategy;


    public Intersection(TrafficLightStrategy trafficLightStrategy) {
        this.trafficLights = new TrafficLights();
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

        for (List<Vehicle> list : waitingVehicles.values()) {
            if (!list.isEmpty()) {
                updateLights();
                break;
            }
        }

        RoadDirection directionWithGreenLight = trafficLights.getDirectionFixed();

        //ruch pojazdami
        //peewnie pobrać aktualnie zielone, może dodać w TrafficLight metodę getGreen lights
        //dla fixed zwraca 1 kierunek, dla most crowded też ? idk

    }

    private void updateLights() {
        new Thread(() -> {
            try {
                trafficLightStrategy.updateCurrentCycleStep(numOfVehiclesPerDirection, trafficLights);

                for (int i = 0; i < 2; i++ ){
                    trafficLightStrategy.makeMove(trafficLights);
                    Thread.sleep(1000);
                } //every cycle of trafficLights changes light in 2 steps

            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }).start();
    }

    //public theMostCrowded
}
