package org.example.Resources.States;

import org.example.Events.Event;
import org.example.GeoCore.Location;
import org.example.Resources.Vehicle;
import static org.example.Utils.ConsoleColors.*;


public class TravelingState implements IVehicleState {

    private int timeLeft;
    private final int totalTime;
    private final int actionTime;
    private final int returnTime;
    private final boolean isFalseAlarm;

    private final Location startLocation;
    private final Event targetEvent;

    public TravelingState(Location start, Event event, int travelTime, int actionTime, int returnTime, boolean isFalseAlarm) {
        this.startLocation = start;
        this.targetEvent = event;
        this.timeLeft = travelTime;
        this.totalTime = Math.max(1, travelTime);
        this.actionTime = actionTime;
        this.returnTime = returnTime;
        this.isFalseAlarm = isFalseAlarm;
    }

    public Location getCurrentLocation() {
        double progress = 1.0 - ((double) timeLeft / totalTime);
        Location targetLocation = targetEvent.getLocation();
        double lat = startLocation.getLatitude() + (targetLocation.getLatitude() - startLocation.getLatitude()) * progress;
        double lon = startLocation.getLongitude() + (targetLocation.getLongitude() - startLocation.getLongitude()) * progress;
        return new Location(lat, lon);
    }

    @Override
    public void handleTick(Vehicle vehicle) {
        if (timeLeft > 0) {
            timeLeft--;
        } else {
            if (isFalseAlarm) {
                System.out.println(" -> " + YELLOW + vehicle.getId() + RESET + ": " + RED + "Alarm Fałszywy! Powrót." + RESET);
                targetEvent.setActive(false);
                vehicle.setState(new ReturningState(targetEvent.getLocation(), vehicle.getParentUnit().getLocation(), returnTime));
            } else {
                System.out.println(" -> " + YELLOW + vehicle.getId() + RESET + ": " + CYAN + "Przystąpienie do działań." + RESET);
                vehicle.setState(new ActionState(targetEvent, actionTime, returnTime));
            }
        }
    }

    @Override
    public boolean canBeDispatched() {
        return false;
    }
}
