package org.example.model.utils;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

public class ScheduleProvider {

    public static ScheduledExecutorService getScheduler() {
        return Executors.newScheduledThreadPool(1);
    }
}
