package com.example.elevator;

import java.util.Map;

public interface ElevatorCarManagerInterface {
    Map<String, ElevatorCarInterface> getByElevatorName(String name);
    Map<ElevatorCarState, ElevatorCarInterface> getByState();
    ElevatorCarInterface getElevatorCar(int destination, ElevatorCarState state);

}
