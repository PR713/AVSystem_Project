package org.example;

import java.util.List;

public class StepStatus {

    private final List<String> vehiclesMovedInStep;

    public StepStatus(List<String> vehicles) {
        this.vehiclesMovedInStep = vehicles;
    }

    public List<String> getLeftVehicles() {
        return vehiclesMovedInStep;
    }
}
