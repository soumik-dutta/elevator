package com.example.elevator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ElevatorApplication {

	@Autowired
	private static ElevatorInterface elevatorInterface;

	public static void main(String[] args) {
		SpringApplication.run(ElevatorApplication.class, args);


		var car = elevatorInterface.getElevatorCar(ElevatorCarState.UP);
		// enter destination
		elevatorInterface.setDestination(car, 6).go();

	}

}
