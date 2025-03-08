package org.example;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.enums.CommandType;
import org.example.enums.RoadDirection;
import org.example.exceptions.CommandParsingException;
import org.example.model.Command;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;

public class JsonCommandParser {

    private static final ObjectMapper objectMapper = new ObjectMapper();


    public static List<Command> parseCommandsFromInput(String filePath) {

        String input = readFileAsString(filePath);

        try {
            Map<String, List<Map<String, String>>> inputMap = objectMapper.readValue(input,
                                                                    new TypeReference<Map<String, List<Map<String, String>>>>() {});

            List<Map<String, String>> commandsMap = inputMap.get("commands");

            if (commandsMap == null) {
                throw new CommandParsingException("Invalid JSON format: missing 'commands' field!");
            }

            return commandsMap.stream()
                    .map(JsonCommandParser::mapToCommand)
                    .toList();

        } catch (Exception e) {
            throw new CommandParsingException("Failed to parse commands from input: " + e.getMessage());
        }
    }


    private static Command mapToCommand(Map<String, String> commandMap) {
        try {
            CommandType commandType = CommandType.fromJsonValue(commandMap.get("type"));

            if (commandType == CommandType.ADD_VEHICLE) {
                String vehicleId = commandMap.get("vehicleId");
                RoadDirection startDirection = RoadDirection.fromJsonValue(commandMap.get("startRoad"));
                RoadDirection endDirection = RoadDirection.fromJsonValue(commandMap.get("endRoad"));
                return new Command(commandType, vehicleId, startDirection, endDirection);
            } else {
                return new Command(commandType, null, null, null);
            }
        } catch (IllegalArgumentException e) {
            throw new CommandParsingException("Invalid command data in JSON: " + e.getMessage());
        }

    };


    private static String readFileAsString(String filePath) {

        Path path = Paths.get(filePath);

        if (!Files.exists(path)) {
            throw new RuntimeException("File does not exist: " + filePath);
        }

        try {
            return new String(Files.readAllBytes(path));
        } catch (IOException e) {
            throw new RuntimeException("Failed to read file: " + filePath, e);
        }
    }
}
