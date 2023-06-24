package com.example.elevator;

public interface ElevatorInterface {
    ElevatorCarInterface getElevatorCar(ElevatorCarState state);
    ElevatorCarInterface setDestination(ElevatorCarInterface carInterface, int destination);

    void addElevator(ElevatorCarInterface elevatorCar);

}
