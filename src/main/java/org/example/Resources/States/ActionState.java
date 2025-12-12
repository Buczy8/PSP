package org.example.Resources.States;

import org.example.Events.Event;
import org.example.GeoCore.Location;
import org.example.Resources.Vehicle;

import static org.example.Utils.ConsoleColors.*;

public class ActionState implements IVehicleState {
    private int timeLeft;
    private final int returnTime;
    private final Event event;

    public ActionState(Event event, int actionTime, int returnTime) {
        this.event = event;
        this.timeLeft = actionTime;
        this.returnTime = returnTime;
    }

    public Location getLocation() {
        return event.getLocation();
    }

    @Override
    public void handleTick(Vehicle vehicle) {
        if (timeLeft > 0) {
            timeLeft--;
        } else {
            System.out.println(" -> " + YELLOW + vehicle.getId() + RESET + ": " + PURPLE + "Zakończono działania. Powrót." + RESET);
            event.setActive(false);

            vehicle.setState((new ReturningState(event.getLocation(), vehicle.getParentUnit().getLocation(), returnTime)));
        }
    }

    @Override
    public boolean canBeDispatched() {
        return false;
    }
}
