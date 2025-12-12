package org.example.Resources.States;

import org.example.Resources.Vehicle;
import static org.example.Utils.ConsoleColors.*;

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
            System.out.println(" -> " + YELLOW + vehicle.getId() + RESET + ": " + PURPLE + "Zakończono działania. Powrót." + RESET);
            vehicle.setState((new ReturningState(returnTime)));
        }
    }

    @Override
    public boolean canBeDispatched() {
        return false;
    }
}
