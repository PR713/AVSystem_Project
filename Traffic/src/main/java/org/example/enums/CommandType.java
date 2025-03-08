package org.example.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum CommandType {
    ADD_VEHICLE("addVehicle"),
    STEP("step");


    private final String jsonValue;

    CommandType(String jsonValue) {
        this.jsonValue = jsonValue;
    }

    @JsonCreator
    public static CommandType fromJsonValue(String jsonValue) {
        for (CommandType commandType : CommandType.values()) {
            if ((commandType.jsonValue).equals(jsonValue)) {
                return commandType;
            }
        }
        throw new IllegalArgumentException("Unknown command type: " + jsonValue);
    }

    @JsonValue
    public String toJsonValue() {
        return jsonValue;
    }
}
