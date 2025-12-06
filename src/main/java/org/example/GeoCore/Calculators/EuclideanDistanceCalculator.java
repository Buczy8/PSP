package org.example.GeoCore.Calculators;

import org.example.GeoCore.Location;

public class EuclideanDistanceCalculator implements IDistanceCalculator {

    @Override
    public double calculateDistance(Location loc1, Location loc2) {
        double latDiff = loc1.getLatitude() - loc2.getLatitude();
        double lonDiff = loc1.getLongitude() - loc2.getLongitude();

        return Math.sqrt((latDiff * latDiff) + (lonDiff * lonDiff));
    }

}
