package org.example.Resources;

import org.example.Resources.States.ActionState;
import org.example.Resources.States.FreeState;
import org.example.Resources.States.IVehicleState;

public class Vehicle {
    private final String id;
    private final Unit parentUnit;
    private IVehicleState state;

    public Vehicle(String id, Unit parentUnit) {
        this.id = id;
        this.parentUnit = parentUnit;
        this.state = new FreeState();
    }

    public void update() {
        state.handleTick(this);
    }

    public void setState(IVehicleState newState) {
        this.state = newState;
    }

    public IVehicleState getState() {
        return state;
    }

    public boolean isFree() {
        return state.canBeDispatched();
    }

    public String getId() {
        return id;
    }

    @Override
    public String toString() {
        return id + " [" + state.getClass().getSimpleName() + "]";
    }

    public Unit getParentUnit() {
        return parentUnit;
    }
}
