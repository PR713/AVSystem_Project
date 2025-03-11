package org.example.trafficstrategy;

import org.example.enums.RoadDirection;
import org.example.model.TrafficLights;
import org.example.model.Vehicle;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class FixedCycleStrategy extends AbstractTrafficLightStrategy {

    public FixedCycleStrategy() {
        super();
    }

    @Override
    public void nextCycleStep(Map<RoadDirection, Integer> vehiclesQueue, Map<RoadDirection, List<Vehicle>> waitingVehicles, TrafficLights trafficLights, ScheduledExecutorService scheduler) {

        super.updateGreenLightDirections(vehiclesQueue);

        this.greenLightDirections = chooseRoadsToBeOn(this.greenLightDirections, waitingVehicles);

        scheduler.schedule(() -> {
            for (int i = 0; i < 2; i++) {
                super.switchLights(trafficLights);

                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        }, 1, TimeUnit.SECONDS);
    }


    private List<RoadDirection> chooseRoadsToBeOn(List<RoadDirection> directions, Map<RoadDirection, List<Vehicle>> waitingVehicles) {

        int directionsLength = directions.size();

        if (directionsLength >= 2) {
            for (int i = 0; i < directionsLength - 1; i++) {
                RoadDirection firstDir = directions.get(i);
                int firstDirNumeric = RoadDirection.getNumericValue(firstDir);

                for (int j = i + 1; j < directionsLength; j++) {
                    RoadDirection secondDir = directions.get(j);
                    int secondDirNumeric = RoadDirection.getNumericValue(secondDir);
                    int sum = firstDirNumeric + secondDirNumeric;
                    RoadDirection destinationVehicle1 = waitingVehicles.get(firstDir).getFirst().getEndDirection();
                    RoadDirection destinationVehicle2 = waitingVehicles.get(secondDir).getFirst().getEndDirection();

                    List<RoadDirection> chosenDirections = super.validateCollision(sum, firstDir, secondDir, destinationVehicle1, destinationVehicle2);
                    if (!chosenDirections.isEmpty()) {
                        return chosenDirections;
                    }
                }
            }
        }

        return List.of(directions.get((int) (Math.random() * directionsLength)));
    }
}

