package org.example.Resources.States;

import org.example.GeoCore.Location;
import org.example.Resources.Vehicle;
import static org.example.Utils.ConsoleColors.*;

public class ReturningState implements IVehicleState{
    private int timeLeft;
    private final int totalTime;
    private final Location startLocation;
    private final Location baseLocation;

    public ReturningState(Location start, Location base, int returnTime) {
        this.startLocation = start;
        this.baseLocation = base;
        this.timeLeft = returnTime;
        this.totalTime = Math.max(1, returnTime);
    }

    public Location getCurrentLocation() {
        double progress = 1.0 - ((double) timeLeft / totalTime);
        double lat = startLocation.getLatitude() + (baseLocation.getLatitude() - startLocation.getLatitude()) * progress;
        double lon = startLocation.getLongitude() + (baseLocation.getLongitude() - startLocation.getLongitude()) * progress;
        return new Location(lat, lon);
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
