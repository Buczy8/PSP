package org.example.Resources.States;

import org.example.Resources.Vehicle;

import java.util.concurrent.ThreadLocalRandom;

public class ReturningState implements IVehicleState{
    private int timeLeft;

    public ReturningState(int returnTime) {
        this.timeLeft = returnTime;
    }

    @Override
    public void handleTick(Vehicle vehicle) {
        if (timeLeft > 0) {
            timeLeft--;
        } else {
            System.out.println(" -> " + vehicle.getId() + ": Powrót do bazy zakończony (Wolny).");
            vehicle.setState(new FreeState());
        }
    }

    @Override
    public boolean canBeDispatched() {
        return false;
    }
}
