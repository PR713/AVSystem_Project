package org.example.trafficstrategy;

import org.example.enums.RoadDirection;
import org.example.model.TrafficLights;
import org.example.model.Vehicle;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class FixedCycleStrategy extends AbstractTrafficLightStrategy {

    private static final Map<RoadDirection, List<RoadDirection>> OPPOSITE_PAIRS1 = Map.of(
            RoadDirection.NORTH, List.of(RoadDirection.SOUTH, RoadDirection.WEST),
            RoadDirection.SOUTH, List.of(RoadDirection.NORTH, RoadDirection.EAST),
            RoadDirection.EAST, List.of(RoadDirection.WEST, RoadDirection.NORTH),
            RoadDirection.WEST, List.of(RoadDirection.EAST, RoadDirection.SOUTH)
    );

    private static final Map<RoadDirection, List<RoadDirection>> OPPOSITE_PAIRS2 = Map.of(
            RoadDirection.NORTH, List.of(RoadDirection.EAST),
            RoadDirection.SOUTH, List.of(RoadDirection.WEST),
            RoadDirection.EAST, List.of(RoadDirection.SOUTH),
            RoadDirection.WEST, List.of(RoadDirection.NORTH)
    );

    private static final Map<RoadDirection, List<RoadDirection>> ADJACENT_PAIRS1 = Map.of(
            RoadDirection.NORTH, List.of(RoadDirection.WEST, RoadDirection.SOUTH, RoadDirection.EAST),
            RoadDirection.EAST, List.of(RoadDirection.NORTH)
    );

    private static final Map<RoadDirection, List<RoadDirection>> ADJACENT_PAIRS2 = Map.of(
            RoadDirection.SOUTH, List.of(RoadDirection.WEST, RoadDirection.NORTH, RoadDirection.EAST),
            RoadDirection.WEST, List.of(RoadDirection.SOUTH)
    );

    private static final Map<RoadDirection, List<RoadDirection>> ADJACENT_PAIRS3 = Map.of(
            RoadDirection.NORTH, List.of(RoadDirection.WEST),
            RoadDirection.WEST, List.of(RoadDirection.SOUTH, RoadDirection.NORTH, RoadDirection.EAST)
    );

    private static final Map<RoadDirection, List<RoadDirection>> ADJACENT_PAIRS4 = Map.of(
            RoadDirection.SOUTH, List.of(RoadDirection.EAST),
            RoadDirection.EAST, List.of(RoadDirection.NORTH, RoadDirection.SOUTH, RoadDirection.WEST)
    );


    public FixedCycleStrategy() {
        super();
    }

    @Override
    public void nextCycleStep(Map<RoadDirection, Integer> vehicleQueue, Map<RoadDirection, List<Vehicle>> waitingVehicles, TrafficLights trafficLights, ScheduledExecutorService scheduler) {
        //skip if there are no cars on the road... next light
        List<RoadDirection> fixedDirections = new ArrayList<>();
        for (int i = 0; i <= 3; i++) {
            if (vehicleQueue.get(RoadDirection.fromNumericValue(i)) > 0) {
                fixedDirections.add(RoadDirection.fromNumericValue(i));
            }
        } //for sure if we invoke that method there exists index 'i' that meets the condition

        fixedDirections = chooseRoadsToBeOn(fixedDirections, vehicleQueue, waitingVehicles);

        trafficLights.setDirectionsFixed(fixedDirections);


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


    private List<RoadDirection> chooseRoadsToBeOn(List<RoadDirection> directions, Map<RoadDirection, Integer> vehicleQueue, Map<RoadDirection, List<Vehicle>> waitingVehicles) {

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

                    if (sum == 2 || sum == 4) {
                        if (OPPOSITE_PAIRS1.get(firstDir).contains(destinationVehicle1)
                                && OPPOSITE_PAIRS1.get(secondDir).contains(destinationVehicle2)
                                || OPPOSITE_PAIRS2.get(firstDir).contains(destinationVehicle1)
                                && OPPOSITE_PAIRS2.get(secondDir).contains(destinationVehicle2)) {
                            return List.of(firstDir, secondDir);
                        }

                    } else if (ADJACENT_PAIRS1.get(firstDir).contains(destinationVehicle1)//sum 1, 3, 5
                                && ADJACENT_PAIRS1.get(secondDir).contains(destinationVehicle2)
                                || ADJACENT_PAIRS2.get(firstDir).contains(destinationVehicle1)
                                && ADJACENT_PAIRS2.get(secondDir).contains(destinationVehicle2)
                                || ADJACENT_PAIRS3.get(firstDir).contains(destinationVehicle1)
                            && ADJACENT_PAIRS3.get(secondDir).contains(destinationVehicle2)
                            || ADJACENT_PAIRS4.get(firstDir).contains(destinationVehicle1)
                            && ADJACENT_PAIRS4.get(secondDir).contains(destinationVehicle2)) {
                        return List.of(firstDir, secondDir);
                    }
                }
            }
        }

        return List.of(directions.get((int) (Math.random() * directionsLength)));
    }
}

