package com.example.elevator;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.ForkJoinPool;

@SpringBootApplication
@Slf4j
public class ElevatorApplication {



	@Autowired

	public static void main(String[] args) {
		SpringApplication.run(ElevatorApplication.class, args);
		ElevatorInterface elevatorInterface = new ElevatorImpl();
		// setting up the elevator
		elevatorInterface.addElevator(new ElevatorCarImpl(0,ElevatorCarState.UNASSIGNED, 0, "A"));
		elevatorInterface.addElevator(new ElevatorCarImpl(0,ElevatorCarState.UNASSIGNED, 0, "B"));
		elevatorInterface.addElevator(new ElevatorCarImpl(0,ElevatorCarState.UNASSIGNED, 0, "C"));
		elevatorInterface.addElevator(new ElevatorCarImpl(0,ElevatorCarState.UNASSIGNED, 0, "D"));

		ExecutorService executorService = new ForkJoinPool();
		executorService.submit(()-> {
			for (int i = 0; i < 10; i++) {
				var car = elevatorInterface.getElevatorCar(ElevatorCarState.UP);
				// enter destination
				elevatorInterface.setDestination(car,  (int)Math.floor(Math.random() * 10) + 1);
				elevatorInterface.go(car);
			}
		});



		var car = elevatorInterface.getElevatorCar(ElevatorCarState.UP);
		// enter destination
		elevatorInterface.setDestination(car, 6);
		log.info("Get current destination of the elevators : {} ",elevatorInterface.getCurrentFloor() );
		elevatorInterface.go(car);

	}

}
