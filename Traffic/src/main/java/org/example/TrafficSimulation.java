package org.example;

import org.example.enums.CommandType;
import org.example.model.Command;
import org.example.model.IntersectionLogic;
import org.example.model.Vehicle;

import java.util.ArrayList;
import java.util.List;


public class TrafficSimulation {

    private final IntersectionLogic intersectionLogic;
    private final List<Vehicle> vehiclesThatLeft;

    public TrafficSimulation() {
        this.intersectionLogic = new IntersectionLogic();
        this.vehiclesThatLeft = new ArrayList<>();
    }

    public void executeCommands(List<Command> commands) {
        for (Command command : commands) {
            if (command.type() == CommandType.ADD_VEHICLE) {
                Vehicle vehicle = new Vehicle(command.vehicleId(), command.startDirection(),
                                            command.endDirection());
                intersectionLogic.addVehicle(vehicle);

            } else if (command.type() == CommandType.STEP) {
                intersectionLogic.step();
            }
        }

        //zapis?

    }
}
