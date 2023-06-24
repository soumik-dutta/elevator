package com.example.elevator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class ElevatorApplication {

	@Autowired
	private static ElevatorInterface elevatorInterface;

	@Autowired
	ConfigurableApplicationContext applicationContext;

	public static void main(String[] args) {
		SpringApplication.run(ElevatorApplication.class, args);

		// setting up the elevator
		elevatorInterface.addElevator(new ElevatorCarImpl(0,ElevatorCarState.UNASSIGNED, 0, "A"));
		elevatorInterface.addElevator(new ElevatorCarImpl(0,ElevatorCarState.UNASSIGNED, 0, "B"));
		elevatorInterface.addElevator(new ElevatorCarImpl(0,ElevatorCarState.UNASSIGNED, 0, "C"));
		elevatorInterface.addElevator(new ElevatorCarImpl(0,ElevatorCarState.UNASSIGNED, 0, "D"));


		var car = elevatorInterface.getElevatorCar(ElevatorCarState.UP);
		// enter destination
		elevatorInterface.setDestination(car, 6);
		elevatorInterface.go(car);

	}

}
