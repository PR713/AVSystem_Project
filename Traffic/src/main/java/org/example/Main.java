package org.example;

import org.example.model.Command;
import org.example.trafficstrategy.FixedCycleStrategy;
import org.example.trafficstrategy.TimeDependentStrategy;
import org.example.trafficstrategy.TrafficLightStrategy;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        if (args.length != 3) {
            System.out.println("Usage: java -jar TrafficSimulation.jar <input_file> <output_file> <strategy>");
            System.out.println("Available strategies: fixed, time");
            return;
        }

        String inputFile = args[0];
        SimulationResult.outputFilePath = args[1];
        String strategyType = args[2];
        System.out.println("Chosen strategy: " + strategyType);

        List<Command> commands = JsonCommandParser.parseCommandsFromInput(inputFile);

        TrafficLightStrategy trafficLightStrategy;
        switch (strategyType.toLowerCase()) {
            case "fixed":
                trafficLightStrategy = new FixedCycleStrategy();
                break;
            case "time":
                trafficLightStrategy = new TimeDependentStrategy();
                break;
            default:
                System.out.println("Invalid strategy. Available strategies: fixed, time");
                return;
        }

        TrafficSimulation simulation = new TrafficSimulation(trafficLightStrategy);
        simulation.executeCommands(commands);
    }
}

//use command e.g.: ./gradlew run --args="src/main/resources/input.json src/main/resources/output.json fixed"