package org.example;

import org.example.Simulation.SimulationConfig;
import org.example.Simulation.SimulationEngine;


public class Main {
    public static void main(String[] args) {
        SimulationConfig config = new SimulationConfig();

        SimulationEngine runner = new SimulationEngine(config);

        runner.run();
    }
}