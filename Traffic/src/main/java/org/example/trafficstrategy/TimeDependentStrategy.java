package org.example.trafficstrategy;

import org.example.enums.RoadDirection;
import org.example.model.TrafficLights;
import org.example.model.Vehicle;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class TimeDependentStrategy extends AbstractTrafficLightStrategy {

    private final Map<RoadDirection, Integer> lastChangeLightTimers;

    public TimeDependentStrategy() {
        super();

        this.lastChangeLightTimers = new HashMap<>();
    }

    @Override
    public void nextCycleStep(Map<RoadDirection, Integer> vehiclesQueue, Map<RoadDirection, List<Vehicle>> waitingVehicles, TrafficLights trafficLights, ScheduledExecutorService scheduler) {

        super.updateGreenLightDirections(vehiclesQueue);
        updateTimersBasedOnMinVehiclesIds(waitingVehicles);

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

        List<RoadDirection> chosenDirections = new ArrayList<>();

        if (directionsLength >= 2) {
            int timer = Integer.MAX_VALUE;
            for (int i = 0; i < directionsLength - 1; i++) {
                RoadDirection firstDir = directions.get(i);
                int firstDirNumeric = RoadDirection.getNumericValue(firstDir);

                for (int j = i + 1; j < directionsLength; j++) {
                    RoadDirection secondDir = directions.get(j);
                    int secondDirNumeric = RoadDirection.getNumericValue(secondDir);
                    int sum = firstDirNumeric + secondDirNumeric;
                    RoadDirection destinationVehicle1 = waitingVehicles.get(firstDir).getFirst().getEndDirection();
                    RoadDirection destinationVehicle2 = waitingVehicles.get(secondDir).getFirst().getEndDirection();

                    List<RoadDirection> foundDirections = super.validateCollision(sum, firstDir, secondDir, destinationVehicle1, destinationVehicle2);

                    if (!foundDirections.isEmpty()) {
                        int newTimer = Math.min(timer, Math.min(lastChangeLightTimers.get(firstDir), lastChangeLightTimers.get(secondDir)));
                        if (newTimer < timer) {
                            timer = newTimer;
                            chosenDirections = foundDirections;
                        }
                    }
                }
            }

            if (!chosenDirections.isEmpty()) {
                return chosenDirections;
            }
        }

        int timer = Integer.MAX_VALUE;
        RoadDirection directionWithTheLongestTime = null;
        for (RoadDirection direction : directions) {
            int newTimer = lastChangeLightTimers.get(direction);

            if (newTimer < timer) {
                timer = newTimer;
                directionWithTheLongestTime = direction;
            }
        }

        assert directionWithTheLongestTime != null; //we invoke that function only if there are cars
        return List.of(directionWithTheLongestTime);
    }


    private void updateTimersBasedOnMinVehiclesIds(Map<RoadDirection, List<Vehicle>> waitingVehicles) {

        for (RoadDirection direction : RoadDirection.values()) {
            lastChangeLightTimers.put(direction, Integer.MAX_VALUE);
        }

        for (RoadDirection direction : RoadDirection.values()) {
            List<Vehicle> vehiclesList = waitingVehicles.get(direction);
            int minId = Integer.MAX_VALUE;
            for (Vehicle vehicle : vehiclesList) {
                int vehicleId = vehicle.getVehicleIdNumber();
                if (vehicleId < minId) {
                    minId = vehicleId;
                }
            }

            lastChangeLightTimers.put(direction, minId);
        }
    }
}
