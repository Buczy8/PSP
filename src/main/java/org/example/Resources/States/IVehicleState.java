package org.example.Resources.States;

import org.example.Resources.Vehicle;

public interface IVehicleState {
    void handleTick(Vehicle vehicle);
    boolean canBeDispatched();
}
