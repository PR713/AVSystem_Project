package org.example;

import org.example.model.Command;
import org.example.trafficstrategy.FixedCycleStrategy;
import org.example.trafficstrategy.TrafficLightStrategy;

import java.util.List;

import static org.example.JsonCommandParser.parseCommandsFromInput;

public class Main {
    public static void main(String[] args) {

        List<Command> commands = parseCommandsFromInput("src/main/resources/input.json");

        TrafficLightStrategy trafficLightStrategy = new FixedCycleStrategy();
        TrafficSimulation simulation = new TrafficSimulation(trafficLightStrategy);
        simulation.executeCommands(commands);
    }
}