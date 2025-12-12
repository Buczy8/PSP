package org.example.Logic;

import org.example.Events.Event;
import org.example.Events.EventType;
import org.example.Logic.Strategy.IDispatchStrategy;
import org.example.Patterns.Observer;
import org.example.Resources.Unit;

import static org.example.Utils.ConsoleColors.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SKKM implements Observer{
    private final List<Unit> units;
    private final Map<EventType, IDispatchStrategy> strategyMap;

    private final DispatcherService dispatcherService;

    public final List<Event> activeEvents = new ArrayList<>();

    public SKKM(List<Unit> units,
                Map<EventType, IDispatchStrategy> strategyMap,
                DispatcherService dispatcherService) { // Wstrzykujemy serwis
        this.units = units;
        this.strategyMap = strategyMap;
        this.dispatcherService = dispatcherService;
    }

    @Override
    public void update(Event event) {
        activeEvents.add(event);
        System.out.println("\n" + CYAN + "[SKKM] Otrzymano zgłoszenie: " + event + RESET);

        IDispatchStrategy strategy = strategyMap.get(event.getType());

        if (strategy == null) throw new RuntimeException("Brak strategii!");

        dispatcherService.dispatch(event, strategy, units);
    }
}