package org.example.Logic;

import org.example.Events.Event;
import org.example.Events.EventType;
import org.example.Events.IEventObserver;
import org.example.GeoCore.Calculators.IDistanceCalculator;
import org.example.Logic.Strategy.FireStrategy;
import org.example.Logic.Strategy.IDispatchStrategy;
import org.example.Logic.Strategy.LocalThreatStrategy;
import org.example.Resources.States.TravelingState;
import org.example.Resources.Unit;
import org.example.Resources.Vehicle;

import java.util.List;

public class SKKM implements IEventObserver {
    private final List<Unit> units;
    private final IDistanceCalculator distanceCalculator;

    public SKKM(List<Unit> units, IDistanceCalculator distanceCalculator) {
        this.units = units;
        this.distanceCalculator = distanceCalculator;
    }

    @Override
    public void onEventReceived(Event event) {
        System.out.println("\n[SKKM] Otrzymano zgłoszenie: " + event);
        handleDispatch(event);
    }

    private void handleDispatch(Event event) {
        IDispatchStrategy strategy;
        if (event.getType() == EventType.PZ) {
            strategy = new FireStrategy();
        } else {
            strategy = new LocalThreatStrategy();
        }

        int vehiclesNeeded = strategy.getRequiredVehiclesCount();
        int vehiclesDispatched = 0;

        System.out.println("[SKKM] Wymagane siły: " + vehiclesNeeded);

        ProximityIterator iterator = new ProximityIterator(units, event.getLocation(), distanceCalculator);

        while (iterator.hasNext() && vehiclesNeeded > 0) {
            Unit currentUnit = iterator.next();
            List<Vehicle> availableVehicles = currentUnit.getAvailableVehicles();

            int vehiclesToTake = Math.min(availableVehicles.size(), vehiclesNeeded);

            if (vehiclesToTake > 0) {
                double dist = distanceCalculator.calculateDistance(currentUnit.getLocation(), event.getLocation());
                System.out.printf(" -> Dysponowanie z %s (odległość: %.4f). Ilość: %d%n",
                        currentUnit.getName(), dist, vehiclesToTake);

                for (int i = 0; i < vehiclesToTake; i++) {
                    Vehicle v = availableVehicles.get(i);
                    v.setState(new TravelingState());
                }

                vehiclesNeeded -= vehiclesToTake;
                vehiclesDispatched += vehiclesToTake;
            }
        }

        if (vehiclesNeeded > 0) {
            System.out.println("[SKKM] UWAGA: Brak dostępnych sił i środków! Zadysponowano tylko: " + vehiclesDispatched);
        } else {
            System.out.println("[SKKM] Dysponowanie zakończone sukcesem.");
        }
    }
}
