package org.example;

import org.example.exceptions.CommandParsingException;
import org.example.model.Command;
import org.example.enums.CommandType;
import org.example.enums.RoadDirection;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class JsonCommandParserTest {

    @Test
    void testParseCommandsFromInput() {
        String inputFile = "src/test/resources/test_input.json";
        List<Command> commands = JsonCommandParser.parseCommandsFromInput(inputFile);

        assertNotNull(commands);
        assertEquals(2, commands.size());

        Command firstCommand = commands.getFirst();
        assertEquals(CommandType.ADD_VEHICLE, firstCommand.type());
        assertEquals("vehicle1", firstCommand.vehicleId());
        assertEquals(RoadDirection.SOUTH, firstCommand.startDirection());
        assertEquals(RoadDirection.NORTH, firstCommand.endDirection());

        Command secondCommand = commands.get(1);
        assertEquals(CommandType.STEP, secondCommand.type());
    }


    @Test
    void testParseCommandsFromEmptyInput() {
        String inputFile = "src/test/resources/test_empty_input.json";

        assertThrows(CommandParsingException.class,
                () -> JsonCommandParser.parseCommandsFromInput(inputFile));
    }
}