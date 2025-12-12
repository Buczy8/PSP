package org.example.Resources.States;

import org.example.Resources.Vehicle;
import static org.example.Utils.ConsoleColors.*;

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
            System.out.println(" -> " + YELLOW + vehicle.getId() + RESET + ": " + GREEN + "Powrót do bazy zakończony (Wolny)." + RESET);
            vehicle.setState(new FreeState());
        }
    }

    @Override
    public boolean canBeDispatched() {
        return false;
    }
}
