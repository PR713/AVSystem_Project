package org.example;

import org.example.enums.CommandType;
import org.example.model.Command;
import org.example.model.Intersection;
import org.example.model.Vehicle;
import org.example.trafficstrategy.TrafficLightStrategy;

import java.util.ArrayList;
import java.util.List;


public class TrafficSimulation {

    private final Intersection intersection;
    private final List<Vehicle> vehiclesThatLeft;

    public TrafficSimulation(TrafficLightStrategy trafficLightStrategy) {
        this.intersection = new Intersection(trafficLightStrategy);
        this.vehiclesThatLeft = new ArrayList<>();
    }

    public void executeCommands(List<Command> commands) {
        for (Command command : commands) {
            if (command.type() == CommandType.ADD_VEHICLE) {
                Vehicle vehicle = new Vehicle(command.vehicleId(), command.startDirection(),
                                            command.endDirection());
                intersection.addVehicle(vehicle);

            } else if (command.type() == CommandType.STEP) {
                intersection.step();
            }
            //TODO zapis?
            //TODO draw() co np 500ms
        }



    }
}
