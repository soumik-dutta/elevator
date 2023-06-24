package com.example.elevator;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ForkJoinPool;

@Setter
@Getter
@Slf4j
public class ElevatorCarManagerImpl implements ElevatorCarManagerInterface {

    private Map<String, ElevatorCarInterface> elevatorNameMap;
    private Map<ElevatorCarState, List<ElevatorCarInterface>> elevatorStateMap;

    private ExecutorService service = new ForkJoinPool();


    @Override
    public ElevatorCarInterface getElevatorCar(int destination, ElevatorCarState state) {
        Optional<ElevatorCarInterface> elevatorCar = Optional.empty();
        var stateElevatorCar = elevatorStateMap.get(state);
        if (state.equals(ElevatorCarState.UP)) {
            // check the nearest one
            elevatorCar = stateElevatorCar.stream()
                    .filter(car -> car.getCurrentFloor() < destination)
                    .findFirst();

        } else {
            elevatorCar = stateElevatorCar.stream()
                    .filter(car -> car.getCurrentFloor() >= destination)
                    .findFirst();
        }
        if (elevatorCar.isEmpty()) {
            if (elevatorStateMap.containsKey(ElevatorCarState.UNASSIGNED) &&
                    !elevatorStateMap.get(ElevatorCarState.UNASSIGNED).isEmpty()) {
                var stillStateCar = elevatorStateMap.get(ElevatorCarState.UNASSIGNED).get(0);
                // set the destication and state
                stillStateCar.setDestinationAndState(destination, state);
                service.submit(()->reachedDestination(stillStateCar));
                return stillStateCar.getDeepCopy();
            }
        }
        var copiedElevatorCar = elevatorCar.get().getDeepCopy();
        service.submit(()->reachedDestination(copiedElevatorCar));

        return copiedElevatorCar;
    }

    @Override
    public Map<String, ElevatorCarInterface> getByElevatorName(String name) {
        return null;
    }

    @Override
    public Map<ElevatorCarState, ElevatorCarInterface> getByState() {
        return null;
    }

    private void reachedDestination(ElevatorCarInterface car){
        try {
            Thread.sleep(10000);
            log.info("destination reached ..{}", car.getDestination());
            var preciousState = car.getCarState();
            car.reachedDestination();
            // shuffle the state map
            if (elevatorStateMap.get(preciousState).remove(car)){
                elevatorStateMap.get(car.getCarState()).add(car);
            }
        } catch (InterruptedException e) {
            log.warn("thread interrupted");
        }


    }
}
