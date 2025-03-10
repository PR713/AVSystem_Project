package org.example.trafficstrategy;

import org.example.enums.RoadDirection;
import org.example.model.TrafficLights;

import java.util.Map;

public class FixedCycleStrategy extends AbstractTrafficLightStrategy {

    public FixedCycleStrategy() {
        super();
    }

    @Override
    public void updateCurrentCycleStep(Map<RoadDirection, Integer> vehicleQueue, TrafficLights trafficLights) {
        //skip if there are no cars on the road... next light
        for (int i = currentCycleStep + 1; i <= currentCycleStep + 3; i++) {
            if (vehicleQueue.get(RoadDirection.fromNumericValue(i % 4)) > 0) {
                currentCycleStep = i % 4;
                break;
            }
        } //for sure if we invoke that method there exists index 'i' that meets the condition

        trafficLights.setDirectionFixed(RoadDirection.fromNumericValue(currentCycleStep));
    }
}
