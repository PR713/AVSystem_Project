package org.example.trafficstrategy;

import org.example.enums.RoadDirection;
import org.example.model.TrafficLights;

import java.util.Map;

public interface TrafficLightStrategy {

    void updateCurrentCycleStep(Map<RoadDirection, Integer> vehicleQueue, TrafficLights trafficLights);

    void makeMove(TrafficLights trafficLights);
}
