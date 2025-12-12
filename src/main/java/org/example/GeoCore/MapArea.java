package org.example.GeoCore;

import java.util.Random;

public class MapArea {
    private final Location topLeft;      // Północny-Zachód
    private final Location bottomRight;  // Południowy-Wschód
    private final Random random;

    public MapArea(Location topLeft, Location bottomRight) {
        this.topLeft = topLeft;
        this.bottomRight = bottomRight;
        this.random = new Random();
    }

    public Location generateRandomPoint() {
        double minLat = Math.min(topLeft.getLatitude(), bottomRight.getLatitude());
        double maxLat = Math.max(topLeft.getLatitude(), bottomRight.getLatitude());

        double minLon = Math.min(topLeft.getLongitude(), bottomRight.getLongitude());
        double maxLon = Math.max(topLeft.getLongitude(), bottomRight.getLongitude());

        // 2. Obliczmy różnice (zawsze dodatnie)
        double latDiff = maxLat - minLat;
        double lonDiff = maxLon - minLon;

        // 3. Losujemy przesunięcie
        double randomLatDelta = random.nextDouble() * latDiff;
        double randomLonDelta = random.nextDouble() * lonDiff;

        // 4. Dodajemy przesunięcie do najmniejszej wartości (dolny lewy róg logiczny)
        double newLat = minLat + randomLatDelta;
        double newLon = minLon + randomLonDelta;

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
