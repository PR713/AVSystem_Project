package org.example;

import org.example.model.Command;
import org.example.trafficstrategy.FixedCycleStrategy;
import org.example.trafficstrategy.TimeDependentStrategy;
import org.example.trafficstrategy.TrafficLightStrategy;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        if (args.length != 2) {
            System.out.println("Usage: java -jar TrafficSimulation.jar <input_file> <output_file>");
            return;
        }

        String inputFile = args[0];
        SimulationResult.outputFilePath = args[1];

        List<Command> commands = JsonCommandParser.parseCommandsFromInput(inputFile);

        TrafficLightStrategy trafficLightStrategy = new FixedCycleStrategy();
//        TrafficLightStrategy trafficLightStrategy = new TimeDependentStrategy();
        TrafficSimulation simulation = new TrafficSimulation(trafficLightStrategy);
        simulation.executeCommands(commands);
    }
}