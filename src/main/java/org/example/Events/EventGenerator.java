package org.example.Events;

import org.example.GeoCore.Location;
import org.example.GeoCore.MapArea;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.concurrent.ThreadLocalRandom;

public class EventGenerator implements Iterator<Event> {
    private final MapArea mapArea;
    private Event nextEvent;

    public EventGenerator(MapArea mapArea) {
        this.mapArea = mapArea;
        this.nextEvent = null;
    }

    @Override
    public boolean hasNext() {
        if (nextEvent != null) {
            return true;
        }
        // Attempt to generate a new event based on probability
        if (ThreadLocalRandom.current().nextDouble() < 0.10) {
            nextEvent = generateEvent();
            return true;
        }
        return false;
    }

    @Override
    public Event next() {
        if (nextEvent == null && !hasNext()) {
            throw new NoSuchElementException("No new event generated in this tick.");
        }
        Event eventToReturn = nextEvent;
        nextEvent = null; // Consume the event
        return eventToReturn;
    }

    private Event generateEvent() {
        double roll = ThreadLocalRandom.current().nextDouble();
        EventType type = (roll < 0.70) ? EventType.MZ : EventType.PZ;
        Location location = mapArea.generateRandomPoint();
        return new Event(type, location);
    }
}
