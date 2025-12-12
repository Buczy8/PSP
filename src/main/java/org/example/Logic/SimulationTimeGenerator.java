package org.example.Logic;

import java.util.concurrent.ThreadLocalRandom;

public class SimulationTimeGenerator {
    public int generateTravelTime() {
        return ThreadLocalRandom.current().nextInt(0, 4);
    }

    public int generateActionTime() {
        return ThreadLocalRandom.current().nextInt(5, 26);
    }

    public int generateReturnTime() {
        return ThreadLocalRandom.current().nextInt(0, 4);
    }

    public boolean isFalseAlarm() {
        return ThreadLocalRandom.current().nextDouble() < 0.05;
    }
}