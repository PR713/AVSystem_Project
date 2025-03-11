package org.example.enums;

public enum RoadDirection {
    NORTH("north", 0),
    SOUTH("south", 2),
    EAST("east", 1),
    WEST("west", 3);


    private final String jsonValue;
    private final int value;

    RoadDirection(String jsonValue, int value) {
        this.jsonValue = jsonValue;
        this.value = value;
    }

    public static RoadDirection fromJsonValue(String jsonValue) {
        for(RoadDirection roadDirection : RoadDirection.values()) {
            if (roadDirection.jsonValue.equals(jsonValue)) {
                return roadDirection;
            }
        }
        throw new IllegalArgumentException("Unknown road direction: " + jsonValue);
    }

    public static RoadDirection fromNumericValue(int numericValue) {
        return switch (numericValue) {
            case 0 -> NORTH;
            case 1 -> EAST;
            case 2 -> SOUTH;
            case 3 -> WEST;
            default -> throw new IllegalArgumentException("Unknown road direction: " + numericValue);
        };
    }

    public static int getNumericValue(RoadDirection direction) {
        return switch (direction) {
            case NORTH -> NORTH.value;
            case SOUTH -> SOUTH.value;
            case EAST -> EAST.value;
            case WEST -> WEST.value;
        };
    }
}
