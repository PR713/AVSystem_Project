package org.example.trafficstrategy;

import org.example.enums.RoadDirection;
import org.example.enums.TrafficLightState;
import org.example.model.TrafficLight;

import java.util.Map;

public class FixedCycleStrategy implements TrafficLightStrategy{

    private int currentCycleStep = 0;
    private RoadDirection directionFixed;

    public void updateCurrentCycleStep(Map<RoadDirection, Integer> vehicleQueue) {
        //skip if there are no cars on the road... next light
        for (int i = currentCycleStep; i <= currentCycleStep + 3; i++) {
            if (vehicleQueue.get(RoadDirection.fromNumericValue(i % 4)) > 0) {
                currentCycleStep = i % 4;
                break;
            }
        } //for sure if we invoke that method there exists index 'i' that meets the condition

        directionFixed = RoadDirection.fromNumericValue(currentCycleStep);
    }


    @Override
    public void makeMove(TrafficLight trafficLight) {

        for (RoadDirection direction : RoadDirection.values()) {
            TrafficLightState currentState = trafficLight.getState(direction);

            switch (currentState) {
                case GREEN -> trafficLight.changeState(direction, TrafficLightState.YELLOW);
                case YELLOW -> trafficLight.changeState(direction, TrafficLightState.RED);
                case RED -> {
                    if (direction == directionFixed) {
                        trafficLight.changeState(direction, TrafficLightState.RED_YELLOW);
                    }
                }
                case RED_YELLOW -> trafficLight.changeState(direction, TrafficLightState.GREEN);

            }
        }
    }

    public RoadDirection getDirectionFixed() {
        return directionFixed;
    }
}
