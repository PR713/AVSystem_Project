package org.example.trafficstrategy;

import org.example.enums.RoadDirection;
import org.example.model.TrafficLights;

import java.util.HashMap;
import java.util.Map;

public class TimeDependentStrategy extends AbstractTrafficLightStrategy {

    private final Map<RoadDirection, Long> lastLightChangeTimes;

    public TimeDependentStrategy() {
        super();
        this.lastLightChangeTimes = new HashMap<>();

        for (RoadDirection direction : RoadDirection.values()) {
            lastLightChangeTimes.put(direction, System.currentTimeMillis());
        }
    }

    @Override
    public void updateCurrentCycleStep(Map<RoadDirection, Integer> vehicleQueue, TrafficLights trafficLights) {
        //basing on time but allowing to move e.g. 5 cars if there are that many
    }
}
