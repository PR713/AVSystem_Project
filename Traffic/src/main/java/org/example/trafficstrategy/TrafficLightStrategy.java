package org.example.trafficstrategy;

import org.example.enums.RoadDirection;
import org.example.model.TrafficLight;

import java.util.Map;

public interface TrafficLightStrategy {

    void updateCurrentCycleStep(Map<RoadDirection, Integer> vehicleQueue);

    void makeMove(TrafficLight trafficLight);
}
