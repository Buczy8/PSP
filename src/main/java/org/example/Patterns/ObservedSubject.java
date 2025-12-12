package org.example.Patterns;

import java.util.ArrayList;
import java.util.List;
import org.example.Events.Event;

public class ObservedSubject {
    private final List<Observer> observersCollection = new ArrayList<>();

    public void addObserver(Observer observer) {
        observersCollection.add(observer);
    }

    public void removeObserver(Observer observer) {
        observersCollection.remove(observer);
    }

    protected void notifyObservers(Event event) {
        for (Observer observer : observersCollection) {
            observer.update(event);
        }
    }
}
