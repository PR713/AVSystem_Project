package org.example.model;

import org.example.enums.CommandType;
import org.example.enums.RoadDirection;

public record Command(CommandType commandType, String vehicleId, RoadDirection startDirection,
                      RoadDirection endDirection) {

    public Command(CommandType commandType, String vehicleId, RoadDirection startDirection, RoadDirection endDirection) {
        this.commandType = commandType;

        if (commandType == CommandType.ADD_VEHICLE && (vehicleId == null || startDirection == null || endDirection == null)) {
            throw new IllegalArgumentException("ADD_VEHICLE command requires vehicleId, startDirection and endDirection");
        }


        this.vehicleId = (commandType == CommandType.ADD_VEHICLE) ? vehicleId : null;
        this.startDirection = (commandType == CommandType.ADD_VEHICLE) ? startDirection : null;
        this.endDirection = (commandType == CommandType.ADD_VEHICLE) ? endDirection : null;
    }



}
