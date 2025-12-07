package org.example;

import org.example.Events.EventGenerator;
import org.example.GeoCore.Calculators.HaversineDistanceCalculator;
import org.example.GeoCore.Calculators.IDistanceCalculator;
import org.example.GeoCore.Location;
import org.example.GeoCore.MapArea;
import org.example.Logic.SKKM;
import org.example.Resources.Unit;
import org.example.Resources.Vehicle;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        System.out.println(">>> INICJALIZACJA SYSTEMU SKKM KRAKÓW <<<");

        // 1. Konfiguracja Domeny Geograficznej (GeoCore)
        // Granice obszaru operacyjnego (zgodnie z poleceniem)
        Location topLeft = new Location(50.154564013341734, 19.688292482742394);
        Location bottomRight = new Location(49.56855025648944, 20.02470275868903);
        MapArea krakowArea = new MapArea(topLeft, bottomRight);
        IDistanceCalculator distanceCalculator = new HaversineDistanceCalculator();

        // 2. Konfiguracja Zasobów (Resources)
        // Tworzymy jednostki ze współrzędnymi
        List<Unit> units = new ArrayList<>();
        units.add(new Unit("JRG-1 (Centrum)", new Location(50.0645, 19.9430)));
        units.add(new Unit("JRG-2 (Podgórze)", new Location(50.0350, 19.9380)));
        units.add(new Unit("JRG-3 (Bronowice)", new Location(50.0750, 19.8870)));
        units.add(new Unit("JRG-4 (Nowa Huta)", new Location(50.0810, 20.0150)));
        units.add(new Unit("JRG-5 (Krowodrza)", new Location(50.0880, 19.9200)));
        units.add(new Unit("JRG-6 (Bieżanów)", new Location(50.0120, 20.0050)));
        units.add(new Unit("JRG-7 (Prądnik)", new Location(50.0950, 19.9800)));
        units.add(new Unit("SA PSP", new Location(50.0780, 20.0350)));
        units.add(new Unit("JRG Skawina", new Location(49.9750, 19.8280)));
        units.add(new Unit("LSP Balice", new Location(50.0770, 19.7850)));

        System.out.println("Zainicjalizowano " + units.size() + " jednostek ratowniczych.");

        // 3. Konfiguracja Logiki i Zdarzeń (Logic & Events)
        // SKKM - Mózg operacji (Obserwator)
        SKKM skkm = new SKKM(units, distanceCalculator);

        // Generator - Źródło problemów (Subject)
        EventGenerator generator = new EventGenerator(krakowArea);

        // Rejestracja obserwatora (Wpięcie SKKM do systemu powiadamiania)
        generator.addObserver(skkm);

        // 4. Pętla Główna Symulacji
        System.out.println(">>> START SYMULACJI (Ctrl+C aby zatrzymać) <<<");

        int tick = 0;
        try {
            while (true) {
                // A. Generator sprawdza, czy wygenerować nowe zdarzenie
                //    (Jeśli wygeneruje, automatycznie powiadomi SKKM, a SKKM zadysponuje wozy)
                generator.tick();

                // B. Aktualizacja stanu wszystkich pojazdów (upływ czasu)
                for (Unit unit : units) {
                    for (Vehicle vehicle : unit.getVehicles()) {
                        vehicle.update(); // Delegacja do wzorca State
                    }
                }

                // C. Obsługa czasu symulacji
                tick++;
                if (tick % 10 == 0) {
                    // Co 10 cykli wypisujemy kropkę, żeby widzieć, że system żyje
                    System.out.print(".");
                }

                // Jedna iteracja pętli to jednostka czasu (np. 1 sekunda w świecie gry)
                // Usypiamy wątek, żeby dało się czytać logi
                Thread.sleep(5000);
            }
        } catch (InterruptedException e) {
            System.out.println("\nSymulacja przerwana.");
        }
    }
}