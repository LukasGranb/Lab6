package src.events;

import src.sim.Event;
import src.state.CarWashState;
import src.events.Car;

import java.util.Random;


public class CarArrives extends Event {

    private Car car;
    private Random random;

    public CarArrives(double time, Car car) {
        time = this.random;
        this.random = new Random();
    }

    public void executeCarArrives() {
        Car car = new Car();
        // Check conditions for fast machines
        if (CarWashState.getFastMachines() != 0) {
            CarWashState.carArrivesFastMachines();
            CarLeaves(time, fast);
            return;
        }

        // Check conditions for slow machines
        if (CarWashState.getSlowMachines() != 0) {
            CarWashState.carArrivesSlowMachines();
            CarLeaves(time, slow)
            return;
        }

        // Check conditions for queue
        if (CarWashState.getQueue() != 0) {
            CarWashState.carArrivesQueue();

            return;
        }

        // If all other options fail, reject the car
        CarWashState.rejected();
    }
    }
}
