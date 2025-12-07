package org.example.Event;

import org.example.GeoCore.Location;
import org.example.GeoCore.MapArea;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class EventGenerator {
    private final List<IEventObserver> observers = new ArrayList<>();
    private final MapArea mapArea;

    public EventGenerator(MapArea mapArea) {
        this.mapArea = mapArea;
    }

    // Rejestracja obserwatora (np. SKKM)
    public void addObserver(IEventObserver observer) {
        observers.add(observer);
    }

    public void tick() {
        if (ThreadLocalRandom.current().nextDouble() < 0.10) {
            generateEvent();
        }
    }

    private void generateEvent() {
        double roll = ThreadLocalRandom.current().nextDouble();
        EventType type = (roll < 0.70) ? EventType.MZ : EventType.PZ;

        Location location = mapArea.generateRandomPoint();

        Event event = new Event(type, location);

        notifyObservers(event);
    }

    private void notifyObservers(Event event) {
        for (IEventObserver observer : observers) {
            observer.onEventReceived(event);
        }
    }
}
