package src.events;

import src.sim.Event;
import src.state.CarWashState;
import src.events.Car;

import java.util.Random;


public class CarArrives extends Event {

    private Random random;
    private double time;
    private Car car;

    public CarArrives(double time) {
        this.time = time;
        this.random = new Random();
    }

    public void executeCarArrives() {
        Car car = new Car();

        fastOrSlowMachine();

        // Check conditions for queue
        if (CarWashState.getQueue() != 0) {
            CarWashState.carArrivesQueue();
            return;
        }

        // If all other options fail, reject the car
        CarWashState.rejected();
    }

    public void fastOrSlowMachine() {
        // Check conditions for fast machines
        if (CarWashState.getFastMachines() != 0) {
            CarWashState.carArrivesFastMachines();
            new CarLeaves(this.time, MachineType.FAST, car.getID());
            return;
        }

        // Check conditions for slow machines
        if (CarWashState.getSlowMachines() != 0) {
            CarWashState.carArrivesSlowMachines();
            new CarLeaves(this.time, MachineType.SLOW, car.getID());
            return;
        }
    }

}
