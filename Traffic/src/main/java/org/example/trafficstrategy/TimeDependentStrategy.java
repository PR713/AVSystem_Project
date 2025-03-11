package org.example.trafficstrategy;

import org.example.enums.RoadDirection;
import org.example.model.TrafficLights;
import org.example.model.Vehicle;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ScheduledExecutorService;

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
    public void nextCycleStep(Map<RoadDirection, Integer> vehicleQueue, Map<RoadDirection, List<Vehicle>> waitingVehicles, TrafficLights trafficLights, ScheduledExecutorService scheduler) {

    }
}
