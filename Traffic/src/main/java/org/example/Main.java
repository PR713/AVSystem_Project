package org.example;


import org.example.enums.CommandType;
import org.example.model.Command;

import java.util.List;

import static org.example.JsonCommandParser.parseCommandsFromInput;
import static org.example.JsonCommandParser.readFileAsString;

public class Main {
    public static void main(String[] args) {

        System.out.println("Hello, World!");

        String inputJSON = readFileAsString("src/main/resources/input.json");

        List<Command> commands = parseCommandsFromInput(inputJSON);

        for (Command command : commands) {
            System.out.println("Command Type: " + command.commandType());
            if (command.commandType() == CommandType.ADD_VEHICLE) {
                System.out.println("Vehicle ID: " + command.vehicleId());
                System.out.println("Start Direction: " + command.startDirection());
                System.out.println("End Direction: " + command.endDirection());
                System.out.println("\n");
            } else {
                System.out.println("Step{} \n");
            }
        }
    }
}