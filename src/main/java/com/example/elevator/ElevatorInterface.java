package com.example.elevator;

import java.util.Map;

public interface ElevatorInterface {
    ElevatorCarInterface getElevatorCar(ElevatorCarState state);
    ElevatorCarInterface setDestination(ElevatorCarInterface carInterface, int destination);
    void go(ElevatorCarInterface carInterface);

    void addElevator(ElevatorCarInterface elevatorCar);

    Map<String, Integer> getCurrentFloor();

}
