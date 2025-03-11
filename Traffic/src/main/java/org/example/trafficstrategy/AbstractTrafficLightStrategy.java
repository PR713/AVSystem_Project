package org.example.trafficstrategy;

import org.example.enums.RoadDirection;
import org.example.enums.TrafficLightState;
import org.example.model.TrafficLights;

public abstract class AbstractTrafficLightStrategy implements TrafficLightStrategy {

    protected int currentCycleStep = 0;
    protected RoadDirection directionFixed;

    @Override
    public void switchLights(TrafficLights trafficLights) {

        for (RoadDirection direction : RoadDirection.values()) {
            TrafficLightState currentState = trafficLights.getState(direction);

            switch (currentState) {
                case GREEN -> trafficLights.changeState(direction, TrafficLightState.YELLOW);
                case YELLOW -> trafficLights.changeState(direction, TrafficLightState.RED);
                case RED -> {
                    if (direction == directionFixed) {
                        trafficLights.changeState(direction, TrafficLightState.RED_YELLOW);
                    }
                }
                case RED_YELLOW -> trafficLights.changeState(direction, TrafficLightState.GREEN);

            }
        }
    }
}
