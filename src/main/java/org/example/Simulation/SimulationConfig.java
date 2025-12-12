package org.example.Simulation;

import org.example.Events.EventGenerator;
import org.example.GeoCore.Calculators.HaversineDistanceCalculator;
import org.example.GeoCore.Calculators.IDistanceCalculator;
import org.example.GeoCore.Location;
import org.example.GeoCore.MapArea;
import org.example.Logic.SKKM;
import org.example.Resources.Unit;

import java.util.ArrayList;
import java.util.List;

public class SimulationConfig {
    public final List<Unit> units;
    public final SKKM skkm;
    public final EventGenerator generator;

    public SimulationConfig() {

        // 1. GeoCore
        Location topLeft = new Location(50.154564013341734, 19.688292482742394);
        Location bottomRight = new Location(49.95855025648944, 20.02470275868903);
        MapArea krakowArea = new MapArea(topLeft, bottomRight);
        IDistanceCalculator distanceCalculator = new HaversineDistanceCalculator();

        // 2. Resources
        this.units = new ArrayList<>();
        units.add(new Unit("JRG-1", new Location(50.05996035882522, 19.943262426757617)));
        units.add(new Unit("JRG-2", new Location(50.03353223075458, 19.93577368023673)));
        units.add(new Unit("JRG-3", new Location(50.07573879306926, 19.88731879299039)));
        units.add(new Unit("JRG-4", new Location(50.03773413953251, 20.005748474005014)));
        units.add(new Unit("JRG-5", new Location(50.09172712138809, 19.919990030031112)));
        units.add(new Unit("JRG-6", new Location(50.01589674630601, 20.015585773609654)));
        units.add(new Unit("JRG-7", new Location(50.094128525666704, 19.977419062585525)));
        units.add(new Unit("SA PSP", new Location(50.077389907335885, 20.03302073453739)));
        units.add(new Unit("JRG Skawina", new Location(49.96841205098495, 19.799509902622734)));

        // 3. Logic
        this.skkm = new SKKM(units, distanceCalculator);


        // 4. Events
        this.generator = new EventGenerator(krakowArea);
    }
}