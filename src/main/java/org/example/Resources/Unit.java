package org.example.Resources;

import org.example.GeoCore.Location;

import java.util.ArrayList;
import java.util.List;

public class Unit {
    private final String name;
    private final Location location;
    private final List<Vehicle> vehicles;

    public Unit(String name, Location location) {
        this.name = name;
        this.location = location;
        this.vehicles = new ArrayList<>();

        for (int i = 1; i <= 5; i++) {
            vehicles.add(new Vehicle(name + "-V" + i, this));
        }
    }

    public Location getLocation() {
        return location;
    }

    public String getName() {
        return name;
    }

    public List<Vehicle> getVehicles() {
        return vehicles;
    }

    public List<Vehicle> getAvailableVehicles() {
        List<Vehicle> available = new ArrayList<>();
        for (Vehicle v : vehicles) {
            if (v.isFree()) {
                available.add(v);
            }
        }
        return available;
    }
}
