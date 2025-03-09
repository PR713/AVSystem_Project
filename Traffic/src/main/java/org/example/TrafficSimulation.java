package org.example;

import org.example.enums.CommandType;
import org.example.model.Command;
import org.example.model.Intersection;
import org.example.model.Vehicle;
import org.example.trafficstrategy.TrafficLightStrategy;

import java.util.ArrayList;
import java.util.List;


public class TrafficSimulation {

    private final Intersection intersectionLogic;
    private final List<Vehicle> vehiclesThatLeft;

    public TrafficSimulation(TrafficLightStrategy trafficLightStrategy) {
        this.intersectionLogic = new Intersection(trafficLightStrategy);
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

        //TODO zapis?

    }
}
