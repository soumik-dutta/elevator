package com.example.elevator;


import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

@Getter
@Setter
public class ElevatorCarImpl implements ElevatorCarInterface{
    private int currentFloor = 0;
    private ElevatorCarState state = ElevatorCarState.UNASSIGNED;
    private int destinationFloor = 0;
    private final String name;
    private Boolean inTransit = false;


    public ElevatorCarImpl(int currentFloor, ElevatorCarState state, int destinationFloor, String name) {
        this.currentFloor = currentFloor;
        this.state = state;
        this.destinationFloor = destinationFloor;
        this.name = name;
    }

    public int getCurrentFloor() {
        return currentFloor;
    }

    public void go(){
        this.setCurrentFloor(this.destinationFloor);
        this.setState(ElevatorCarState.UNASSIGNED);
    }

    @Override
    public ElevatorCarState getCarState() {
        return this.getState();
    }

    @Override
    public int getDestination() {
        return getDestinationFloor();
    }

    @Override
    public ElevatorCarImpl getDeepCopy() {
        return new ElevatorCarImpl(this.getCurrentFloor(), this.getState(), this.getDestinationFloor(), this.getName());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ElevatorCarImpl that)) return false;
        return getName().equals(that.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName());
    }

    @Override
    public boolean setDestination(int destination) {
        if (inTransit){
            return false;
        }
        synchronized (this){
            this.inTransit = true;
            this.setDestinationFloor(destination);
            return true;
        }
    }

}
