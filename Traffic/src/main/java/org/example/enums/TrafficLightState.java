package org.example.enums;

public enum TrafficLightState {
    RED, YELLOW, GREEN, RED_YELLOW;


    public TrafficLightState getNextState() {
        return switch (this) {
            case GREEN -> YELLOW;
            case YELLOW -> RED;
            case RED -> RED_YELLOW;
            case RED_YELLOW -> GREEN;
        };
    }
}
