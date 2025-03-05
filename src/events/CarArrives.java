package src.events;

import src.sim.Event;
import src.events.Car;

import java.util.Random;


public class CarArrives extends Event {
    private Car car;
    private Random random;

    public CarArrives(double time, Car car) {
        time = this.random;
        id = Car.getCarId(this.car);
    }

    
}
