package org.example.model;

import org.example.enums.RoadDirection;
import org.example.trafficstrategy.FixedCycleStrategy;
import org.junit.jupiter.api.Test;

import java.util.concurrent.CountDownLatch;

import static org.junit.jupiter.api.Assertions.*;

class IntersectionTest {

    @Test
    void testAddVehicle() {
        Intersection intersection = new Intersection(new FixedCycleStrategy());
        Vehicle vehicle = new Vehicle("vehicle1", RoadDirection.NORTH, RoadDirection.EAST);

        intersection.addVehicle(vehicle);
        intersection.shutdown();
        assertEquals(1, intersection.getWaitingVehicles().get(RoadDirection.NORTH).size());
    }

    @Test
    void testStep() {
        Intersection intersection = new Intersection(new FixedCycleStrategy());
        Vehicle vehicle = new Vehicle("vehicle1", RoadDirection.NORTH, RoadDirection.EAST);
        intersection.addVehicle(vehicle);

        CountDownLatch latch = new CountDownLatch(1);
        intersection.step(latch);
        latch.countDown();
        intersection.shutdown();

        assertEquals(1, intersection.getVehiclesThatLeftInStep().size());
    }
}