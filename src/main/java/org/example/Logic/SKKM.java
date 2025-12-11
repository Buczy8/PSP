package org.example.Logic;

import org.example.Events.Event;
import org.example.Events.EventType;
import org.example.GeoCore.Calculators.IDistanceCalculator;
import org.example.GeoCore.Location;
import org.example.Logic.Strategy.FireStrategy;
import org.example.Logic.Strategy.IDispatchStrategy;
import org.example.Logic.Strategy.LocalThreatStrategy;
import org.example.Resources.States.TravelingState;
import org.example.Resources.Unit;
import org.example.Resources.Vehicle;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class SKKM {
    private final List<Unit> units;
    private final IDistanceCalculator distanceCalculator;

    public SKKM(List<Unit> units, IDistanceCalculator distanceCalculator) {
        this.units = units;
        this.distanceCalculator = distanceCalculator;
    }

    public void onEventReceived(Event event) {
        System.out.println("\n[SKKM] Otrzymano zgłoszenie: " + event);
        handleDispatch(event);
    }

    private void handleDispatch(Event event) {
        IDispatchStrategy strategy = getStrategyForEvent(event);

        int vehiclesNeeded = strategy.getRequiredVehiclesCount();
        int vehiclesDispatched = 0;

        System.out.println("[SKKM] Wymagane siły: " + vehiclesNeeded);

        List<Unit> closestUnits = getUnitsSortedByDistance(event.getLocation());

        for (Unit currentUnit : closestUnits) {
            if (vehiclesDispatched >= vehiclesNeeded) {
                break;
            }

            List<Vehicle> availableVehicles = currentUnit.getAvailableVehicles();
            int vehiclesToTake = Math.min(availableVehicles.size(), vehiclesNeeded - vehiclesDispatched);

            if (vehiclesToTake > 0) {
                dispatchVehiclesFromUnit(currentUnit, availableVehicles, vehiclesToTake, event.getLocation());
                vehiclesDispatched += vehiclesToTake;
            }
        }

        logDispatchResult(vehiclesNeeded, vehiclesDispatched);

        if (vehiclesNeeded > 0) {
            System.out.println("[SKKM] UWAGA: Brak dostępnych sił i środków! Zadysponowano tylko: " + vehiclesDispatched);
        } else {
            System.out.println("[SKKM] Dysponowanie zakończone sukcesem.");
        }
    }
    private List<Unit> getUnitsSortedByDistance(Location targetLocation) {
        return units.stream()
                .sorted(Comparator.comparingDouble(unit ->
                        distanceCalculator.calculateDistance(unit.getLocation(), targetLocation)))
                .collect(Collectors.toList());
    }
    private IDispatchStrategy getStrategyForEvent(Event event) {
        if (event.getType() == EventType.PZ) {
            return new FireStrategy();
        } else {
            return new LocalThreatStrategy();
        }
    }
    private void dispatchVehiclesFromUnit(Unit unit, List<Vehicle> vehicles, int count, Location targetLocation) {
        double dist = distanceCalculator.calculateDistance(unit.getLocation(), targetLocation);
        System.out.printf(" -> Dysponowanie z %s (odległość: %.4f). Ilość: %d%n",
                unit.getName(), dist, count);

        // Symulacja parametrów czasu (RNG)
        int groupTravelTime = java.util.concurrent.ThreadLocalRandom.current().nextInt(0, 4);
        int groupActionTime = java.util.concurrent.ThreadLocalRandom.current().nextInt(5, 26);
        int groupReturnTime = java.util.concurrent.ThreadLocalRandom.current().nextInt(0, 4);
        boolean groupFalseAlarm = java.util.concurrent.ThreadLocalRandom.current().nextDouble() < 0.05;

        for (int i = 0; i < count; i++) {
            Vehicle v = vehicles.get(i);
            System.out.println("    [START] " + v.getId() + " (T:" + groupTravelTime +
                    " | A:" + (groupFalseAlarm ? "FA" : groupActionTime) +
                    " | R:" + groupReturnTime + ")");

            v.setState(new TravelingState(groupTravelTime, groupActionTime, groupReturnTime, groupFalseAlarm));
        }
    }

    private void logDispatchResult(int needed, int dispatched) {
        if (dispatched < needed) {
            System.out.println("[SKKM] UWAGA: Brak dostępnych sił i środków! Zadysponowano tylko: " + dispatched);
        } else {
            System.out.println("[SKKM] Dysponowanie zakończone sukcesem.");
        }
    }
}
