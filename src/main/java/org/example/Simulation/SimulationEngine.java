package org.example.Simulation;

import org.example.Events.Event;
import org.example.Resources.Unit;
import org.example.Resources.Vehicle;

public class SimulationEngine {

    private final SimulationConfig config;

    public SimulationEngine(SimulationConfig config) {
        this.config = config;
    }


    public void run() {

        int tick = 0;
        try {
            while (true) {
                if (config.generator.hasNext()) {
                    Event event = config.generator.next();
                }

                for (Unit unit : config.units) {
                    for (Vehicle vehicle : unit.getVehicles()) {
                        vehicle.update();
                    }
                }

                tick++;
                if (tick % 10 == 0) {
                    System.out.print(".");
                }

                Thread.sleep(500);
            }
        } catch (InterruptedException e) {
            System.out.println("\nSymulacja przerwana.");
        }
    }
}