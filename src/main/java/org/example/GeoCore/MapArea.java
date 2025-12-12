package org.example.GeoCore;

import java.util.Random;

public class MapArea {
    private final Location topLeft;
    private final Location bottomRight;
    private final Random random;

    public MapArea(Location topLeft, Location bottomRight) {
        this.topLeft = topLeft;
        this.bottomRight = bottomRight;
        this.random = new Random();
    }

    public Location generateRandomPoint() {
        double latDiff = topLeft.getLatitude() - bottomRight.getLatitude();
        double lonDiff = bottomRight.getLongitude() - topLeft.getLongitude();

        double randomLatDelta = random.nextDouble() * latDiff;
        double randomLonDelta = random.nextDouble() * lonDiff;

        double newLat = bottomRight.getLatitude() + randomLatDelta;
        double newLon = topLeft.getLongitude() + randomLonDelta;


        return new Location(newLat, newLon);
    }


    public boolean contains(Location location) {
        boolean latInside = location.getLatitude() <= topLeft.getLatitude() &&
                location.getLatitude() >= bottomRight.getLatitude();

        boolean lonInside = location.getLongitude() >= topLeft.getLongitude() &&
                location.getLongitude() <= bottomRight.getLongitude();

        return latInside && lonInside;
    }
}
