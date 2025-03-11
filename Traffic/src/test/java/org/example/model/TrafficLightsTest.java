package org.example.model;

import org.example.enums.RoadDirection;
import org.example.enums.TrafficLightState;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TrafficLightsTest {

    @Test
    void testChangeState() {
        TrafficLights trafficLights = new TrafficLights();
        trafficLights.changeState(RoadDirection.NORTH, TrafficLightState.GREEN);

        assertEquals(TrafficLightState.GREEN, trafficLights.getState(RoadDirection.NORTH));
    }
}