package org.example.Event;

import org.example.GeoCore.Location;

public class Event {
    private final EventType type;
    private final Location location;

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

    @Override
    public String toString() {
        return String.format("Zdarzenie [%s] w %s", type, location);
    }
}
