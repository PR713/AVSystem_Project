package org.example.trafficstrategy;

import org.example.enums.RoadDirection;
import org.example.model.TrafficLights;
import org.example.model.Vehicle;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ScheduledExecutorService;

public interface TrafficLightStrategy {

    void nextCycleStep(Map<RoadDirection, Integer> vehicleQueue, Map<RoadDirection, List<Vehicle>> waitingVehicles, TrafficLights trafficLights, ScheduledExecutorService scheduler);

    void switchLights(TrafficLights trafficLights);

    List<RoadDirection> validateCollision(int sum, RoadDirection dirVehicle1, RoadDirection dirVehicle2,
                                          RoadDirection destinationVehicle1, RoadDirection destinationVehicle2);

    void updateGreenLightDirections(Map<RoadDirection, Integer> vehiclesQueue);

    List<RoadDirection> getGreenLightDirections();
}
