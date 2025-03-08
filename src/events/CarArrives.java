package src.events;

import src.sim.Event;
import src.sim.EventQueue;
import src.state.CarWashState;



public class CarArrives extends Event<CarWashState> {

    private Car car;
    private CarWashState state;
    private EventQueue eventQueue;


    public CarArrives(CarWashState state, EventQueue eventQueue, double time) {
        super(state, eventQueue, time);
        this.car = new Car(this.state.idCounter());
        this.state = state;
        this.eventQueue = eventQueue;
    }

    @Override
    public void execute() {


        fastOrSlowMachine();

        if (this.state.getQueue() != 0) {
            this.state.carArrivesQueue(this.car);
            return;

        }
        this.state.rejected();

    }

    public void fastOrSlowMachine() {
        if (this.state.getFastMachines() != 0) {
            this.state.carArrivesFastMachines();
            new CarLeaves(this.getTime(), MachineType.FAST, car.getCarId());
            return;
        }

        if (state.getSlowMachines() != 0) {
            state.carArrivesSlowMachines();
            new CarLeaves(this.getTime(), MachineType.SLOW, car.getCarId());
        }
    }

}
