package org.example;

import org.example.GeoCore.*;
import org.example.GeoCore.Calculators.HaversineDistanceCalculator;
import org.example.GeoCore.Calculators.IDistanceCalculator;

public class Main {

    public static void main(String[] args) {
        Location leftUpperCorner = new Location(50.154564013341734, 19.688292482742394);
        Location rightBottomCorner = new Location(49.95855025648944, 20.02470275868903);
        MapArea Map = new MapArea(leftUpperCorner, rightBottomCorner);

        IDistanceCalculator calculator = new HaversineDistanceCalculator();

        double distance = calculator.calculateDistance(leftUpperCorner, rightBottomCorner);
        System.out.println(distance);

    }
    // TODO graficzny ui do symulacji
}