package com.example.elevator;

public interface ElevatorCarInterface {
    int getCurrentFloor();

    ElevatorCarState getCarState();

    int getDestination();

    String getName();

    ElevatorCarImpl getDeepCopy();

    ElevatorCarImpl setDestinationAndState(int destination, ElevatorCarState state);

    void reachedDestination();

}
