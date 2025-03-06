package src.events;

import src.sim.Event;
import src.sim.EventQueue;
import src.state.CarWashState;
import src.events.Car;


public class CarArrives extends Event {

    private Car car;
    private CarWashState state;
    private EventQueue eventQueue;
    private double time;

    public CarArrives(CarWashState state, EventQueue queue, double time) {

        this.state = state;
        this.eventQueue = queue;
        this.time = time;
        this.car = new Car(this.state.idCounter());
    }
    @Override
    public double time() {
        return this.time;
    }

    @Override
    public void execute() {
        fastOrSlowMachine();

        if (this.state.getQueue() != 0) {
            this.state.carArrivesQueue();
            return;

        }
        this.state.rejected();

    }

    public void fastOrSlowMachine() {
        if (this.state.getFastMachines() != 0) {
            this.state.carArrivesFastMachines();
            new CarLeaves(time(), MachineType.FAST, car.getCarId());
            return;
        }

        if (state.getSlowMachines() != 0) {
            state.carArrivesSlowMachines();
            new CarLeaves(time(), MachineType.SLOW, car.getCarId());
        }
    }

}
