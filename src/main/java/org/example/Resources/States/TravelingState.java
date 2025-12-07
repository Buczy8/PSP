package org.example.Resources.States;

import org.example.Resources.Vehicle;

import java.util.concurrent.ThreadLocalRandom;

public class TravelingState implements IVehicleState{

    private int timeLeft;

    public TravelingState() {
        this.timeLeft = ThreadLocalRandom.current().nextInt(0, 4);
    }

    @Override
    public void handleTick(Vehicle vehicle) {
        if (timeLeft > 0) {
            timeLeft--;
        } else {
            boolean isFalseAlarm = ThreadLocalRandom.current().nextDouble() < 0.05;

            if (isFalseAlarm) {
                System.out.println(" -> " + vehicle.getId() + ": Alarm Fałszywy! Powrót.");
                vehicle.setState(new ReturningState());
            } else {
                System.out.println(" -> " + vehicle.getId() + ": Przystąpienie do działań.");
                vehicle.setState(new ActionState());
            }
        }
    }

    @Override
    public boolean canBeDispatched() {
        return false;
    }
}
