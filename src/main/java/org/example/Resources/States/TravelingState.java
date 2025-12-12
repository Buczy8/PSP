package org.example.Resources.States;

import org.example.Resources.Vehicle;
import static org.example.Utils.ConsoleColors.*;


public class TravelingState implements IVehicleState {

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
                System.out.println(" -> " + YELLOW + vehicle.getId() + RESET + ": " + RED + "Alarm Fałszywy! Powrót." + RESET);
                vehicle.setState(new ReturningState(returnTime));
            } else {
                System.out.println(" -> " + YELLOW + vehicle.getId() + RESET + ": " + CYAN + "Przystąpienie do działań." + RESET);
                vehicle.setState(new ActionState(actionTime, returnTime));
            }
        }
    }

    @Override
    public boolean canBeDispatched() {
        return false;
    }
}
