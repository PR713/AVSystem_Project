package org.example;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.enums.CommandType;
import org.example.enums.RoadDirection;
import org.example.model.Command;
import org.example.trafficstrategy.FixedCycleStrategy;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class TrafficSimulationTest {

    @Test
    void testExecuteCommands() {
        TrafficSimulation simulation = new TrafficSimulation(new FixedCycleStrategy());

        List<Command> commands = List.of(
                new Command(CommandType.ADD_VEHICLE, "vehicle1", RoadDirection.NORTH, RoadDirection.EAST),
                new Command(CommandType.STEP, null, null, null),
                new Command(CommandType.ADD_VEHICLE, "vehicle2", RoadDirection.EAST, RoadDirection.WEST)
        );

        String outputFilePath = "src/test/resources/test_output.json";
        try (FileWriter writer = new FileWriter(outputFilePath)) {
            writer.write("");  //clears json file
        } catch (IOException e) {
            e.printStackTrace();
        }
        SimulationResult.outputFilePath = outputFilePath;
        simulation.executeCommands(commands);

        ObjectMapper objectMapper = new ObjectMapper();

        try {
            Map<String, List<Map<String, List<String>>>> result = objectMapper.readValue(
                    new File(outputFilePath),
                    new TypeReference<>() {}
            );

            assertTrue(result.containsKey("stepStatuses"));

            List<Map<String, List<String>>> stepStatuses = result.get("stepStatuses");

            assertEquals(1, stepStatuses.size());

        } catch (IOException e) {
            fail("Nie udało się odczytać pliku JSON: " + e.getMessage());
        }
    }
}