package org.example.Resources.States;

import org.example.Resources.Vehicle;

public class FreeState implements IVehicleState {
    @Override
    public void handleTick(Vehicle vehicle) {
        // Pojazd stoi w garażu, nic nie robi. Czeka na dyspozycję.
    }

    @Override
    public boolean canBeDispatched() {
        return true;
    }
}
