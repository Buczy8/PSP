package org.example.Logic.Strategy;

public class FireStrategy implements IDispatchStrategy {
    @Override
    public int getRequiredVehiclesCount() {
        return 3;
    }
}
