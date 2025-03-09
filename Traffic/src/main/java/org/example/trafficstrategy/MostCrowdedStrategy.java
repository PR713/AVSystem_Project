package org.example.trafficstrategy;

import org.example.enums.RoadDirection;

import java.util.Map;

public class MostCrowdedStrategy implements TrafficLightStrategy {
    @Override
    public Map<RoadDirection, Boolean> getGreenLights(Map<RoadDirection, Integer> vehicleQueue) {
        return Map.of();
    }
}
