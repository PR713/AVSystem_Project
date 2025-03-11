package org.example;

import org.example.enums.CommandType;
import org.example.model.Command;
import org.example.model.Intersection;
import org.example.model.Vehicle;
import org.example.trafficstrategy.TrafficLightStrategy;

import java.util.List;
import java.util.concurrent.CountDownLatch;

public class TrafficSimulation {

    private final Intersection intersection;
    private final SimulationResult simulationResult;

    public TrafficSimulation(TrafficLightStrategy trafficLightStrategy) {
        this.intersection = new Intersection(trafficLightStrategy);
        this.simulationResult = new SimulationResult();
    }

    public void executeCommands(List<Command> commands) {
        for (Command command : commands) {
            if (command.type() == CommandType.ADD_VEHICLE) {
                Vehicle vehicle = new Vehicle(command.vehicleId(), command.startDirection(),
                                            command.endDirection());
                intersection.addVehicle(vehicle);
            } else if (command.type() == CommandType.STEP) {
                CountDownLatch latch = new CountDownLatch(1);
                intersection.step(latch);
                try {
                    latch.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                simulationResult.add(new StepStatus(intersection.getVehiclesThatLeftInStep()));
                intersection.resetVehiclesThatLeftInStep();
            }
            //TODO draw() co np 500ms
        }

        intersection.shutdown();

        simulationResult.saveResultsToFile("src/main/resources/output.json");
    }
}
