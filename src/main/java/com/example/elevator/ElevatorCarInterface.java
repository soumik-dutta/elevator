package com.example.elevator;

public interface ElevatorCarInterface {
    int getCurrentFloor();

    ElevatorCarState getCarState();

    int getDestination();

    String getName();

    ElevatorCarImpl getDeepCopy();

    void setState(ElevatorCarState state);

    void go();

    void setDestination(int destination);

}
