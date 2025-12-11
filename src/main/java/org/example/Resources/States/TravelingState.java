package org.example.Resources.States;

import org.example.Resources.Vehicle;

import java.util.concurrent.ThreadLocalRandom;

public class TravelingState implements IVehicleState{

    private int timeLeft;
    private final int actionTime;
    private final int returnTime;
    private final boolean isFalseAlarm;

    public TravelingState(int travelTime, int actionTime, int returnTime, boolean isFalseAlarm) {
        this.timeLeft = travelTime;
        this.actionTime = actionTime;
        this.returnTime = returnTime;
        this.isFalseAlarm = isFalseAlarm;
    }

    @Override
    public void handleTick(Vehicle vehicle) {
        if (timeLeft > 0) {
            timeLeft--;
        } else {
            if (isFalseAlarm) {
                System.out.println(" -> " + vehicle.getId() + ": Alarm Fałszywy! Powrót.");
                vehicle.setState(new ReturningState(returnTime));
            } else {
                System.out.println(" -> " + vehicle.getId() + ": Przystąpienie do działań.");
                vehicle.setState(new ActionState(actionTime, returnTime));
            }
        }
    }

    @Override
    public boolean canBeDispatched() {
        return false;
    }
}
