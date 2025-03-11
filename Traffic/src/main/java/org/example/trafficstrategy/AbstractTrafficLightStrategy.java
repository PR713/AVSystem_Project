package org.example.trafficstrategy;

import org.example.enums.RoadDirection;
import org.example.enums.TrafficLightState;
import org.example.model.TrafficLights;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public abstract class AbstractTrafficLightStrategy implements TrafficLightStrategy {

    protected List<RoadDirection> greenLightDirections;

    protected static final Map<RoadDirection, List<RoadDirection>> OPPOSITE_PAIRS1 = Map.of(
            RoadDirection.NORTH, List.of(RoadDirection.SOUTH, RoadDirection.WEST),
            RoadDirection.SOUTH, List.of(RoadDirection.NORTH, RoadDirection.EAST),
            RoadDirection.EAST, List.of(RoadDirection.WEST, RoadDirection.NORTH),
            RoadDirection.WEST, List.of(RoadDirection.EAST, RoadDirection.SOUTH)
    );

    protected static final Map<RoadDirection, List<RoadDirection>> OPPOSITE_PAIRS2 = Map.of(
            RoadDirection.NORTH, List.of(RoadDirection.EAST),
            RoadDirection.SOUTH, List.of(RoadDirection.WEST),
            RoadDirection.EAST, List.of(RoadDirection.SOUTH),
            RoadDirection.WEST, List.of(RoadDirection.NORTH)
    );

    protected static final Map<RoadDirection, List<RoadDirection>> ADJACENT_PAIRS1 = Map.of(
            RoadDirection.NORTH, List.of(RoadDirection.WEST, RoadDirection.SOUTH, RoadDirection.EAST),
            RoadDirection.EAST, List.of(RoadDirection.NORTH),
            RoadDirection.WEST, List.of(),
            RoadDirection.SOUTH, List.of()
    );

    protected static final Map<RoadDirection, List<RoadDirection>> ADJACENT_PAIRS2 = Map.of(
            RoadDirection.SOUTH, List.of(RoadDirection.WEST, RoadDirection.NORTH, RoadDirection.EAST),
            RoadDirection.WEST, List.of(RoadDirection.SOUTH),
            RoadDirection.EAST, List.of(),
            RoadDirection.NORTH, List.of()
    );

    protected static final Map<RoadDirection, List<RoadDirection>> ADJACENT_PAIRS3 = Map.of(
            RoadDirection.NORTH, List.of(RoadDirection.WEST),
            RoadDirection.WEST, List.of(RoadDirection.SOUTH, RoadDirection.NORTH, RoadDirection.EAST),
            RoadDirection.EAST, List.of(),
            RoadDirection.SOUTH, List.of()
    );

    protected static final Map<RoadDirection, List<RoadDirection>> ADJACENT_PAIRS4 = Map.of(
            RoadDirection.SOUTH, List.of(RoadDirection.EAST),
            RoadDirection.EAST, List.of(RoadDirection.NORTH, RoadDirection.SOUTH, RoadDirection.WEST),
            RoadDirection.WEST, List.of(),
            RoadDirection.NORTH, List.of()
    );


    @Override
    public void switchLights(TrafficLights trafficLights) {

        for (RoadDirection direction : RoadDirection.values()) {
            TrafficLightState currentState = trafficLights.getState(direction);

            switch (currentState) {
                case GREEN -> {
                    if (!greenLightDirections.contains(direction)){
                        trafficLights.changeState(direction, TrafficLightState.YELLOW);
                    }
                }
                case YELLOW -> trafficLights.changeState(direction, TrafficLightState.RED);
                case RED -> {
                    if (greenLightDirections.contains(direction)) {
                        trafficLights.changeState(direction, TrafficLightState.RED_YELLOW);
                    }
                }
                case RED_YELLOW -> trafficLights.changeState(direction, TrafficLightState.GREEN);

            }
        }
    }

    @Override
    public List<RoadDirection> validateCollision(int sum, RoadDirection dirVehicle1, RoadDirection dirVehicle2,
                                                    RoadDirection destinationVehicle1, RoadDirection destinationVehicle2){
        if (sum == 2 || sum == 4) {
            if (OPPOSITE_PAIRS1.get(dirVehicle1).contains(destinationVehicle1)
                    && OPPOSITE_PAIRS1.get(dirVehicle2).contains(destinationVehicle2)
                    || OPPOSITE_PAIRS2.get(dirVehicle1).contains(destinationVehicle1)
                    && OPPOSITE_PAIRS2.get(dirVehicle2).contains(destinationVehicle2)) {
                return List.of(dirVehicle1, dirVehicle2);
            }

        } else if (ADJACENT_PAIRS1.get(dirVehicle1).contains(destinationVehicle1)//sum 1, 3, 5
                && ADJACENT_PAIRS1.get(dirVehicle2).contains(destinationVehicle2)
                || ADJACENT_PAIRS2.get(dirVehicle1).contains(destinationVehicle1)
                && ADJACENT_PAIRS2.get(dirVehicle2).contains(destinationVehicle2)
                || ADJACENT_PAIRS3.get(dirVehicle1).contains(destinationVehicle1)
                && ADJACENT_PAIRS3.get(dirVehicle2).contains(destinationVehicle2)
                || ADJACENT_PAIRS4.get(dirVehicle1).contains(destinationVehicle1)
                && ADJACENT_PAIRS4.get(dirVehicle2).contains(destinationVehicle2)) {
            return List.of(dirVehicle1, dirVehicle2);
        }
        return List.of();
    }

    @Override
    public void updateGreenLightDirections(Map<RoadDirection, Integer> vehiclesQueue){
        this.greenLightDirections = new ArrayList<>();
        for (int i = 0; i <= 3; i++) {
            if (vehiclesQueue.get(RoadDirection.fromNumericValue(i)) > 0) {
                this.greenLightDirections.add(RoadDirection.fromNumericValue(i));
            }
        } //for sure if we invoke that method there exists index 'i' that meets the condition
    }

    public List<RoadDirection> getGreenLightDirections() {
        return this.greenLightDirections;
    }
}
