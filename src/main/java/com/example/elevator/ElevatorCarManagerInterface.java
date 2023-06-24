package com.example.elevator;

import java.util.Map;

public interface ElevatorCarManagerInterface {
    Map<ElevatorCarState, ElevatorCarInterface> getByState();
    ElevatorCarInterface getElevatorCar(ElevatorCarState state);
    void addElevator(ElevatorCarInterface elevatorCar);
    void setDestination(ElevatorCarInterface elevatorCar, int destination);
    void reachedDestination(ElevatorCarInterface elevatorCar);

}
