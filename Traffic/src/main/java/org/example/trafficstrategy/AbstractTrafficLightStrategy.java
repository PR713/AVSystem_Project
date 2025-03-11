package org.example.trafficstrategy;

import org.example.enums.RoadDirection;
import org.example.enums.TrafficLightState;
import org.example.model.TrafficLights;

import java.util.List;

public abstract class AbstractTrafficLightStrategy implements TrafficLightStrategy {

    protected List<RoadDirection> fixedDirections;

    @Override
    public void switchLights(TrafficLights trafficLights) {

        for (RoadDirection direction : RoadDirection.values()) {
            TrafficLightState currentState = trafficLights.getState(direction);

            switch (currentState) {
                case GREEN -> {
                    if (!fixedDirections.contains(direction)){
                        trafficLights.changeState(direction, TrafficLightState.YELLOW);
                    }
                }
                case YELLOW -> trafficLights.changeState(direction, TrafficLightState.RED);
                case RED -> {
                    if (fixedDirections.contains(direction)) {
                        trafficLights.changeState(direction, TrafficLightState.RED_YELLOW);
                    }
                }
                case RED_YELLOW -> trafficLights.changeState(direction, TrafficLightState.GREEN);

            }
        }
    }
}
