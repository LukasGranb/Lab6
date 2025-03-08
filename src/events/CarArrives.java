package src.events;

import src.sim.Event;
import src.sim.EventQueue;
import src.state.CarWashState;



public class CarArrives extends Event<CarWashState> {

    private Car car;
    private CarWashState state;
    private EventQueue eventQueue;
    private double time;

    public CarArrives(double time) {
        super(time);
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
