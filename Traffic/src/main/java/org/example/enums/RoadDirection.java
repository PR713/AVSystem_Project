package org.example.enums;

public enum RoadDirection {
    NORTH("north"),
    SOUTH("south"),
    EAST("east"),
    WEST("west");


    private final String jsonValue;

    RoadDirection(String jsonValue) {
        this.jsonValue = jsonValue;
    }

    public static RoadDirection fromJsonValue(String jsonValue) {
        for(RoadDirection roadDirection : RoadDirection.values()) {
            if (roadDirection.jsonValue.equals(jsonValue)) {
                return roadDirection;
            }
        }
        throw new IllegalArgumentException("Unknown road direction: " + jsonValue);
    }
}
