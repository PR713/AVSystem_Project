package org.example.trafficstrategy;

import org.example.enums.RoadDirection;
import org.example.model.TrafficLights;
import org.example.model.Vehicle;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.Executors;

import static org.junit.jupiter.api.Assertions.*;

class FixedCycleStrategyTest {

    @Test
    void testNextCycleStepOppositeDirections() {
        FixedCycleStrategy strategy = new FixedCycleStrategy();
        Map<RoadDirection, Integer> vehiclesQueue = new HashMap<>();
        vehiclesQueue.put(RoadDirection.NORTH, 1);
        vehiclesQueue.put(RoadDirection.SOUTH, 1);
        vehiclesQueue.put(RoadDirection.WEST, 0);
        vehiclesQueue.put(RoadDirection.EAST, 0);

        Map<RoadDirection, List<Vehicle>> waitingVehicles = new HashMap<>();
        waitingVehicles.put(RoadDirection.NORTH, List.of(new Vehicle("vehicle1", RoadDirection.NORTH, RoadDirection.SOUTH)));
        waitingVehicles.put(RoadDirection.SOUTH, List.of(new Vehicle("vehicle2", RoadDirection.SOUTH, RoadDirection.EAST)));

        TrafficLights trafficLights = new TrafficLights();
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

        strategy.nextCycleStep(vehiclesQueue, waitingVehicles, trafficLights, scheduler);

        scheduler.shutdown();

        assertTrue(strategy.getGreenLightDirections().contains(RoadDirection.NORTH));
        assertTrue(strategy.getGreenLightDirections().contains(RoadDirection.SOUTH));
    }

    @Test
    void testNextCycleStepAdjacentDirections() {
        FixedCycleStrategy strategy = new FixedCycleStrategy();
        Map<RoadDirection, Integer> vehiclesQueue = new HashMap<>();
        vehiclesQueue.put(RoadDirection.NORTH, 1);
        vehiclesQueue.put(RoadDirection.SOUTH, 0);
        vehiclesQueue.put(RoadDirection.WEST, 0);
        vehiclesQueue.put(RoadDirection.EAST, 1);

        Map<RoadDirection, List<Vehicle>> waitingVehicles = new HashMap<>();
        waitingVehicles.put(RoadDirection.NORTH, List.of(new Vehicle("vehicle1", RoadDirection.NORTH, RoadDirection.SOUTH)));
        waitingVehicles.put(RoadDirection.EAST, List.of(new Vehicle("vehicle2", RoadDirection.EAST, RoadDirection.WEST)));

        TrafficLights trafficLights = new TrafficLights();
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

        strategy.nextCycleStep(vehiclesQueue, waitingVehicles, trafficLights, scheduler);

        scheduler.shutdown();
        assertEquals(1, strategy.getGreenLightDirections().size());
    }
}