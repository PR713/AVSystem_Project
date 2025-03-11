package org.example.model.utils;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

public class ScheduleProvider {

    private static final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

    public static ScheduledExecutorService getScheduler() {
        return scheduler;
    }
}
