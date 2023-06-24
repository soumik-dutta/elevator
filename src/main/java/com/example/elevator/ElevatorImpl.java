package com.example.elevator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

public class ElevatorImpl implements ElevatorInterface{

    private ElevatorCarManagerInterface elevatorCarManagerInterface = new ElevatorCarManagerImpl();

    @Override
    public ElevatorCarInterface getElevatorCar(ElevatorCarState state) {
        return elevatorCarManagerInterface.getElevatorCar(state);
    }

    public void addElevator(ElevatorCarInterface elevatorCar){
        elevatorCarManagerInterface.addElevator(elevatorCar);
    }

    @Override
    public ElevatorCarInterface setDestination(ElevatorCarInterface carInterface, int destination) {
        elevatorCarManagerInterface.setDestination(carInterface, destination);
        return carInterface;
    }


    @Override
    public void go(ElevatorCarInterface carInterface) {
        elevatorCarManagerInterface.reachedDestination(carInterface);
    }

    @Override
    public Map<String, Integer> getCurrentFloor() {
        return elevatorCarManagerInterface.displayElevatorFloor();
    }
}
