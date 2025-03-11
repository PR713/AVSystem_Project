package org.example.model;

import org.example.enums.RoadDirection;
import org.example.enums.TrafficLightState;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TrafficLights {

    private final Map<RoadDirection, TrafficLightState> states;

    public TrafficLights() {
        this.states = new HashMap<>();

        states.put(RoadDirection.EAST, TrafficLightState.RED);
        states.put(RoadDirection.WEST, TrafficLightState.RED);
        states.put(RoadDirection.SOUTH, TrafficLightState.RED);
        states.put(RoadDirection.NORTH, TrafficLightState.RED);
    }

    public TrafficLightState getState(RoadDirection direction) {
        return states.get(direction);
    }

    public void changeState(RoadDirection direction, TrafficLightState newState) {
        states.put(direction, newState);
    }
}
