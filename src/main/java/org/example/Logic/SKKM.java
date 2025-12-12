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
import static org.example.Utils.ConsoleColors.*;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class SKKM {
    private final List<Unit> units;
    private final IDistanceCalculator distanceCalculator;
    private IDispatchStrategy dispatchStrategy;

    public SKKM(List<Unit> units, IDistanceCalculator distanceCalculator) {
        this.units = units;
        this.distanceCalculator = distanceCalculator;
    }

    public void setDispatchStrategy(IDispatchStrategy dispatchStrategy) {
        this.dispatchStrategy = dispatchStrategy;
    }

    public void onEventReceived(Event event) {
        System.out.println("\n" + CYAN + "[SKKM] Otrzymano zgłoszenie: " + event + RESET);

        if (event.getType() == EventType.PZ) {
            setDispatchStrategy(new FireStrategy());
        } else {
            setDispatchStrategy(new LocalThreatStrategy());
        }
        handleDispatch(event);
    }

    private void handleDispatch(Event event) {
        if (dispatchStrategy == null) {
            throw new IllegalStateException("Strategia nie została ustawiona!");
        }
        int vehiclesNeeded = dispatchStrategy.getRequiredVehiclesCount();
        int vehiclesDispatched = 0;

        System.out.println("[SKKM] Wymagane siły: " + WHITE_BOLD + vehiclesNeeded + RESET);

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

    }

    private List<Unit> getUnitsSortedByDistance(Location targetLocation) {
        return units.stream()
                .sorted(Comparator.comparingDouble(unit ->
                        distanceCalculator.calculateDistance(unit.getLocation(), targetLocation)))
                .collect(Collectors.toList());
    }

    private void dispatchVehiclesFromUnit(Unit unit, List<Vehicle> vehicles, int count, Location targetLocation) {
        double dist = distanceCalculator.calculateDistance(unit.getLocation(), targetLocation);
        System.out.printf(PURPLE + " -> Dysponowanie z %s (odległość: %.2f m). Ilość: %d" + RESET + "%n",
                unit.getName(), dist, count);

        // Symulacja parametrów czasu (RNG)
        int groupTravelTime = java.util.concurrent.ThreadLocalRandom.current().nextInt(0, 4);
        int groupActionTime = java.util.concurrent.ThreadLocalRandom.current().nextInt(5, 26);
        int groupReturnTime = java.util.concurrent.ThreadLocalRandom.current().nextInt(0, 4);
        boolean groupFalseAlarm = java.util.concurrent.ThreadLocalRandom.current().nextDouble() < 0.05;

        for (int i = 0; i < count; i++) {
            Vehicle v = vehicles.get(i);
            System.out.println("    [START] " + YELLOW + v.getId() + RESET + " (T:" + groupTravelTime +
                    " | A:" + (groupFalseAlarm ? RED + "FA" + RESET : groupActionTime) +
                    " | R:" + groupReturnTime + ")");

            v.setState(new TravelingState(groupTravelTime, groupActionTime, groupReturnTime, groupFalseAlarm));
        }
    }

    private void logDispatchResult(int needed, int dispatched) {
        if (dispatched < needed) {
            System.out.println(RED + "\033[1m" + "[SKKM] UWAGA: Brak dostępnych sił i środków! Zadysponowano tylko: " + dispatched + "/" + needed + RESET);
        } else {
            System.out.println(GREEN + "[SKKM] Dysponowanie zakończone sukcesem." + RESET);
        }
    }
}
