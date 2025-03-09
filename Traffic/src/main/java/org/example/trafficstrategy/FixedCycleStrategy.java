package org.example.trafficstrategy;

import org.example.enums.RoadDirection;

import java.util.HashMap;
import java.util.Map;

public class FixedCycleStrategy implements TrafficLightStrategy{

    private int currentCycleStep = 0;

    @Override
    public Map<RoadDirection, Boolean> getGreenLights(Map<RoadDirection, Integer> vehicleQueue) {

        Map<RoadDirection, Boolean> greenLights = new HashMap<>();
        //skip if there are no cars on the road.. next light
        for (int i = currentCycleStep; i <= currentCycleStep + 3; i++) {
            if (vehicleQueue.get(RoadDirection.fromNumericValue(i)) > 0) {
                currentCycleStep = i;
            }
        }

        switch (currentCycleStep) {
            case 0:
                greenLights.put(RoadDirection.NORTH, true);
                greenLights.put(RoadDirection.SOUTH, false);
                greenLights.put(RoadDirection.EAST, false);
                greenLights.put(RoadDirection.WEST, false);
                break;
            case 1:
                greenLights.put(RoadDirection.NORTH, false);
                greenLights.put(RoadDirection.SOUTH, true);
                greenLights.put(RoadDirection.EAST, false);
                greenLights.put(RoadDirection.WEST, false);
                break;
            case 2:
                greenLights.put(RoadDirection.NORTH, false);
                greenLights.put(RoadDirection.SOUTH, false);
                greenLights.put(RoadDirection.EAST, true);
                greenLights.put(RoadDirection.WEST, false);
                break;
            case 3:
                greenLights.put(RoadDirection.NORTH, false);
                greenLights.put(RoadDirection.SOUTH, false);
                greenLights.put(RoadDirection.EAST, false);
                greenLights.put(RoadDirection.WEST, true);
                break;
        }


        return greenLights;
    }
}
