package org.example.trafficstrategy;

import org.example.enums.RoadDirection;

import java.util.Map;

public interface TrafficLightStrategy {

    Map<RoadDirection, Boolean> getGreenLights(Map<RoadDirection, Integer> vehicleQueue);
}
