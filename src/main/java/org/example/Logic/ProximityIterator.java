package org.example.Logic;

import org.example.GeoCore.Calculators.IDistanceCalculator;
import org.example.GeoCore.Location;
import org.example.Resources.Unit;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

public class ProximityIterator implements Iterator<Unit> {
    private final List<Unit> sortedUnits;
    private int currentIndex = 0;

    public ProximityIterator(List<Unit> allUnits, Location targetLocation, IDistanceCalculator distanceCalculator) {
        this.sortedUnits = new ArrayList<>(allUnits);

        this.sortedUnits.sort(Comparator.comparingDouble(unit ->
                distanceCalculator.calculateDistance(unit.getLocation(), targetLocation)
        ));
    }

    @Override
    public boolean hasNext() {
        return currentIndex < sortedUnits.size();
    }

    @Override
    public Unit next() {
        return sortedUnits.get(currentIndex++);
    }
}
