package org.example.Resources.States;

import org.example.Resources.Vehicle;

import java.util.concurrent.ThreadLocalRandom;

public class ActionState implements IVehicleState{
    private int timeLeft;
    private final int returnTime;

    public ActionState(int actionTime, int returnTime) {
        this.timeLeft = actionTime;
        this.returnTime = returnTime;
    }

    @Override
    public void handleTick(Vehicle vehicle) {
        if (timeLeft > 0) {
            timeLeft--;
        } else {
            System.out.println(" -> " + vehicle.getId() + ": Zakończono działania. Powrót.");
            vehicle.setState((new ReturningState(returnTime)));
        }
    }

    @Override
    public boolean canBeDispatched() {
        return false;
    }
}
