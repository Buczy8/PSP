package org.example.Logic.Strategy;

public class LocalThreatStrategy implements IDispatchStrategy {
    @Override
    public int getRequiredVehiclesCount() {
        return 2;
    }

}
