package org.example.model;

import org.example.enums.RoadDirection;
import org.example.trafficstrategy.TrafficLightStrategy;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;
import java.util.stream.Collectors;

public class Intersection {
    private final TrafficLights trafficLights;
    private final Map<RoadDirection, List<Vehicle>> waitingVehicles;
    private final Map<RoadDirection, Integer> numOfVehiclesPerDirection;
    private final List<Vehicle> vehiclesThatLeftInStep;
    private final TrafficLightStrategy trafficLightStrategy;
    private final ScheduledExecutorService scheduler;


    public Intersection(TrafficLightStrategy trafficLightStrategy) {
        this.trafficLights = new TrafficLights();
        this.waitingVehicles = new HashMap<>();
        this.numOfVehiclesPerDirection = new HashMap<>();
        this.trafficLightStrategy = trafficLightStrategy;
        this.scheduler = Executors.newScheduledThreadPool(1);

        for (RoadDirection direction : RoadDirection.values()) {
            waitingVehicles.put(direction, new ArrayList<>());
            numOfVehiclesPerDirection.put(direction, 0);
        }

        this.vehiclesThatLeftInStep = new ArrayList<>();
    }


    public void addVehicle(Vehicle vehicle) {
        waitingVehicles.get(vehicle.getStartDirection()).add(vehicle);
        numOfVehiclesPerDirection.put(vehicle.getStartDirection(), numOfVehiclesPerDirection.get(vehicle.getStartDirection()) + 1);
    }


    public void step(CountDownLatch latch) {
        //TODO invokes a managing method from given trafficLightStrategy
        boolean flagAreVehicles = false;
        for (List<Vehicle> list : waitingVehicles.values()) {
            if (!list.isEmpty()) {
                updateLights();
                flagAreVehicles = true;
                break;
            }
        }

        if (!flagAreVehicles) {
            latch.countDown();
            return;
        }

        trafficLightStrategy.updateCurrentCycleStep(numOfVehiclesPerDirection, trafficLights);
        RoadDirection directionWithGreenLight = trafficLights.getDirectionFixed();

        scheduler.schedule(() -> {
            try {
                List<Vehicle> vehiclesOnGreenLight = waitingVehicles.get(directionWithGreenLight);

                int timerToSwitchLights = 5;
                while (!vehiclesOnGreenLight.isEmpty() && timerToSwitchLights > 0 ) {
                    Vehicle vehicle = vehiclesOnGreenLight.removeFirst();
                    vehiclesThatLeftInStep.add(vehicle);
                    numOfVehiclesPerDirection.put(vehicle.getStartDirection(), numOfVehiclesPerDirection.get(vehicle.getStartDirection()) - 1);
                    Thread.sleep(500);
                    timerToSwitchLights--;
                }

                updateLights();

            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            } finally {
                latch.countDown();
            }
        }, 500, TimeUnit.MILLISECONDS);

    }


    private void updateLights() {
        scheduler.schedule(() -> {
            for (int i = 0; i < 2; i++) {
                trafficLightStrategy.switchLights(trafficLights);

                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        }, 1, TimeUnit.SECONDS);
    }


    public void shutdown() {
        scheduler.shutdown();
        try {
            if (!scheduler.awaitTermination(5, TimeUnit.SECONDS)) {
                scheduler.shutdownNow();
            }
        } catch (InterruptedException e) {
            scheduler.shutdownNow();
        }
    }


    public List<String> getVehiclesThatLeftInStep() {
        return vehiclesThatLeftInStep.stream()
                .map(Vehicle::getVehicleId)
                .collect(Collectors.toList());
    }

    public void resetVehiclesThatLeftInStep() {
        vehiclesThatLeftInStep.clear();
    }
}
