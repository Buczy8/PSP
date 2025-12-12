package org.example.Logic;

import org.example.Events.Event;
import org.example.GeoCore.Calculators.IDistanceCalculator;
import org.example.GeoCore.Location;
import org.example.Logic.Strategy.IDispatchStrategy;
import org.example.Resources.States.TravelingState;
import org.example.Resources.Unit;
import org.example.Resources.Vehicle;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static org.example.Utils.ConsoleColors.*;

public class DispatcherService {
    private final IDistanceCalculator distanceCalculator;
    private final SimulationTimeGenerator timeGen;

    public DispatcherService(IDistanceCalculator distanceCalculator,
                             SimulationTimeGenerator timeGen) {
        this.distanceCalculator = distanceCalculator;
        this.timeGen = timeGen;
    }

    public void dispatch(Event event, IDispatchStrategy strategy, List<Unit> units) {
        if (strategy == null) {
            throw new IllegalStateException("Strategia nie została ustawiona!");
        }

        int vehiclesNeeded = strategy.getRequiredVehiclesCount();
        int vehiclesDispatched = 0;

        System.out.println("[SKKM] Wymagane siły: " + WHITE_BOLD + vehiclesNeeded + RESET);

        List<Unit> closestUnits = getUnitsSortedByDistance(units, event.getLocation());

        for (Unit currentUnit : closestUnits) {
            if (vehiclesDispatched >= vehiclesNeeded) {
                break;
            }

            List<Vehicle> availableVehicles = currentUnit.getAvailableVehicles();
            int vehiclesToTake = Math.min(availableVehicles.size(), vehiclesNeeded - vehiclesDispatched);

            if (vehiclesToTake > 0) {
                dispatchVehiclesFromUnit(currentUnit, availableVehicles, vehiclesToTake, event);
                vehiclesDispatched += vehiclesToTake;
            }
        }

        logDispatchResult(vehiclesNeeded, vehiclesDispatched);

    }

    private List<Unit> getUnitsSortedByDistance(List<Unit> units, Location targetLocation) {
        return units.stream()
                .sorted(Comparator.comparingDouble(unit ->
                        distanceCalculator.calculateDistance(unit.getLocation(), targetLocation)))
                .collect(Collectors.toList());
    }

    private void dispatchVehiclesFromUnit(Unit unit, List<Vehicle> vehicles, int count, Event event) {
        double dist = distanceCalculator.calculateDistance(unit.getLocation(), event.getLocation());
        System.out.printf(PURPLE + " -> Dysponowanie z %s (odległość: %.2f m). Ilość: %d" + RESET + "%n",
                unit.getName(), dist, count);

        int groupTravelTime = timeGen.generateTravelTime();
        int groupActionTime = timeGen.generateActionTime();
        int groupReturnTime = timeGen.generateReturnTime();
        boolean groupFalseAlarm = timeGen.isFalseAlarm();

        for (int i = 0; i < count; i++) {
            Vehicle v = vehicles.get(i);
            System.out.println("    [START] " + YELLOW + v.getId() + RESET + " (T:" + groupTravelTime +
                    " | A:" + (groupFalseAlarm ? RED + "FA" + RESET : groupActionTime) +
                    " | R:" + groupReturnTime + ")");

            v.setState(new TravelingState(
                    unit.getLocation(),
                    event,
                    groupTravelTime,
                    groupActionTime,
                    groupReturnTime,
                    groupFalseAlarm
            ));
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