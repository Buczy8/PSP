package org.example.Resources.States;

import org.example.Resources.Vehicle;

import java.util.concurrent.ThreadLocalRandom;

public class ActionState implements IVehicleState{
    private int timeLeft;

    public ActionState() {
        this.timeLeft = ThreadLocalRandom.current().nextInt(5, 26);
    }

    @Override
    public void handleTick(Vehicle vehicle) {
        if (timeLeft > 0) {
            timeLeft--;
        } else {
            System.out.println(" -> " + vehicle.getId() + ": Zakończono działania. Powrót.");
            vehicle.setState(new ReturningState());
        }
    }

    @Override
    public boolean canBeDispatched() {
        return false;
    }
}
