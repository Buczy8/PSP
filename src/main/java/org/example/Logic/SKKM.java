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

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class SKKM {
    private final List<Unit> units;
    private final Map<EventType, IDispatchStrategy> strategyMap;

    // Nowa zależność zamiast calculatora i timeGen bezpośrednio
    private final DispatcherService dispatcherService;

    public final List<Event> activeEvents = new ArrayList<>();

    public SKKM(List<Unit> units,
                Map<EventType, IDispatchStrategy> strategyMap,
                DispatcherService dispatcherService) { // Wstrzykujemy serwis
        this.units = units;
        this.strategyMap = strategyMap;
        this.dispatcherService = dispatcherService;
    }

    public void onEventReceived(Event event) {
        activeEvents.add(event);
        System.out.println("\n" + CYAN + "[SKKM] Otrzymano zgłoszenie: " + event + RESET);

        IDispatchStrategy strategy = strategyMap.get(event.getType());

        if (strategy == null) throw new RuntimeException("Brak strategii!");

        dispatcherService.dispatch(event, strategy, units);
    }
}