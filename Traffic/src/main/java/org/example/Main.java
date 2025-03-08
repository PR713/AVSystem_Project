package org.example;

import org.example.model.Command;

import java.util.List;

import static org.example.JsonCommandParser.parseCommandsFromInput;

public class Main {
    public static void main(String[] args) {

        List<Command> commands = parseCommandsFromInput("src/main/resources/input.json");

        TrafficSimulation simulation = new TrafficSimulation();
        simulation.executeCommands(commands);
    }
}