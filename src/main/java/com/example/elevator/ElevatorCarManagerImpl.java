package com.example.elevator;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ForkJoinPool;
import java.util.stream.Collectors;

@Setter
@Getter
@Slf4j
@Component
public class ElevatorCarManagerImpl implements ElevatorCarManagerInterface {


    private ConcurrentHashMap<String, ElevatorCarInterface> elevatorNameMap= new ConcurrentHashMap<>();
    private static ConcurrentHashMap<ElevatorCarState, List<ElevatorCarInterface>> elevatorStateMap = new ConcurrentHashMap<>();

    private ExecutorService service = new ForkJoinPool();

    static {
        elevatorStateMap.put(ElevatorCarState.UP, new ArrayList<>());
        elevatorStateMap.put(ElevatorCarState.DOWN, new ArrayList<>());
        elevatorStateMap.put(ElevatorCarState.UNASSIGNED, new ArrayList<>());
    }




    @Override
    public ElevatorCarInterface getElevatorCar(ElevatorCarState state) {
        Optional<ElevatorCarInterface> elevatorCar = Optional.empty();
        var stateElevatorCar = elevatorStateMap.get(state);
        elevatorCar = stateElevatorCar.stream().findFirst();
        if (elevatorCar.isEmpty()) {
            if (!elevatorStateMap.get(ElevatorCarState.UNASSIGNED).isEmpty()) {
                var stillStateCar = elevatorStateMap.get(ElevatorCarState.UNASSIGNED).get(0);
                // set the destication and state
                stillStateCar.setState(state);
                elevatorStateMap.get(state).add(stillStateCar);
                return stillStateCar.getDeepCopy();
            }
        } else {
            var notAvailable = true;
            while (notAvailable){
                try {
                    Thread.sleep(10000);
                } catch (InterruptedException e) {
                    log.warn("thread interrupted");
                }
                notAvailable = !elevatorStateMap.get(ElevatorCarState.UNASSIGNED).isEmpty();
            }
        }

        return elevatorCar.get().getDeepCopy();
    }


    @Override
    public Map<ElevatorCarState, ElevatorCarInterface> getByState() {
        return null;
    }

    public void reachedDestination(ElevatorCarInterface elevatorCar) {
        var car = elevatorNameMap.get(elevatorCar.getName());
        try {
            Thread.sleep(10000);
            log.info("destination reached ..{}", car.getDestination());
            var preciousState = car.getCarState();
            // shuffle the state map
            if (elevatorStateMap.get(preciousState).remove(car)) {
                elevatorStateMap.get(car.getCarState()).add(car);
            }
            car.go();
            log.info("Reached destination {} : {}", car.getName(), car.getDestination());
        } catch (InterruptedException e) {
            log.warn("thread interrupted");
        }
    }

    @Override
    public void addElevator(ElevatorCarInterface elevatorCar) {
        // adding new elevator car
        elevatorNameMap.put(elevatorCar.getName(), elevatorCar);
        elevatorStateMap.get(ElevatorCarState.UNASSIGNED).add(elevatorCar);
    }

    @Override
    public void setDestination(ElevatorCarInterface elevatorCar, int destination) {
        elevatorNameMap.get(elevatorCar.getName()).setDestination(destination);
    }

    @Override
    public Map<String, Integer> displayElevatorFloor() {
        return elevatorNameMap.entrySet()
                .stream()
                .collect(Collectors.toMap(Map.Entry::getKey, k->k.getValue().getDestination(), (a, b)->a ));
    }
}
