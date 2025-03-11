package org.example;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class SimulationResult {
    private final List<StepStatus> stepStatuses;
    public static String outputFilePath;

    public SimulationResult() {
        this.stepStatuses = new ArrayList<>();
    }

    public void add(StepStatus stepStatus) {
        stepStatuses.add(stepStatus);
    }

    public void saveResultsToFile() {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.writerWithDefaultPrettyPrinter().writeValue(new File(outputFilePath), this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<StepStatus> getStepStatuses() {
        return stepStatuses;
    }
}
