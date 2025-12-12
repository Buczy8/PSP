package org.example;

import org.example.Simulation.SimulationConfig;
import org.example.Simulation.SimulationEngine;
import org.example.Simulation.SimulationGUI;

import javax.swing.*;


public class Main {
    public static void main(String[] args) {
        SimulationConfig config = new SimulationConfig();

        SimulationEngine engine = new SimulationEngine(config);

        SwingUtilities.invokeLater(() -> {
            new SimulationGUI(config);
        });

        Thread simulationThread = new Thread(engine::run);
        simulationThread.start();
    }
}