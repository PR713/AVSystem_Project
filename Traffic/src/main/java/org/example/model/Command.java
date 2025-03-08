package org.example.model;

import org.example.enums.CommandType;
import org.example.enums.RoadDirection;

public record Command(CommandType type, String vehicleId, RoadDirection startDirection,
                      RoadDirection endDirection) {

    public Command(CommandType type, String vehicleId, RoadDirection startDirection, RoadDirection endDirection) {
        this.type = type;

        if (type == CommandType.ADD_VEHICLE && (vehicleId == null || startDirection == null || endDirection == null)) {
            throw new IllegalArgumentException("ADD_VEHICLE command requires vehicleId, startDirection and endDirection");
        }


        this.vehicleId = (type == CommandType.ADD_VEHICLE) ? vehicleId : null;
        this.startDirection = (type == CommandType.ADD_VEHICLE) ? startDirection : null;
        this.endDirection = (type == CommandType.ADD_VEHICLE) ? endDirection : null;
    }
}
