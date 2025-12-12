package org.example.Events;

import org.example.GeoCore.Location;

public class Event {
    private final EventType type;
    private final Location location;
    private boolean active = true;

    public Event(EventType type, Location location) {
        this.type = type;
        this.location = location;
    }

    public EventType getType() {
        return type;
    }

    public Location getLocation() {
        return location;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    @Override
    public String toString() {
        return String.format("Zdarzenie [%s] w %s", type, location);
    }
}
