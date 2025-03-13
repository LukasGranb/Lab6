package src.events;

import src.random.ExponentialRandomStream;
import src.sim.Event;
import src.sim.EventQueue;
import src.state.CarWashState;



public class CarArrives extends Event<CarWashState> {

    private Car car;
    private CarWashState state;
    private EventQueue eventQueue;




    public CarArrives(CarWashState state, EventQueue eventQueue, double time) {
        super(state, eventQueue, time);
        this.state = state;
        this.eventQueue = eventQueue;

    }

    @Override
    public void execute() {

        eventQueue.addEvent(new CarArrives(this.state, this.eventQueue, this.state.getTimeSpread()));

        if (this.state.getFastMachines() > 0) {
            this.car = new Car(this.state.idCounter());
            this.state.carArrivesFastMachines(car.getCarId(), this.getTime());
            this.state.notifyCarEvent("ARRIVE", car.getCarId(), this.getTime(), 0, 0);
            eventQueue.addEvent(new CarLeaves(this.state, this.eventQueue, MachineType.FAST, this.getTime(), state.getMachineTime(MachineType.FAST), car.getCarId()) );

        } else if (this.state.getSlowMachines() > 0) {
            this.car = new Car(this.state.idCounter());
            this.state.carArrivesSlowMachines(car.getCarId(), this.getTime());
            this.state.notifyCarEvent("ARRIVE", car.getCarId(), this.getTime(), 0, 0);
            eventQueue.addEvent(new CarLeaves(this.state, this.eventQueue, MachineType.SLOW, this.getTime(), state.getMachineTime(MachineType.SLOW), car.getCarId()));

        } else if (this.state.getQueue() > 0) {
            this.car = new Car(this.state.idCounter());
            this.state.carArrivesQueue(this.car, car.getCarId(), this.getTime());
            this.state.notifyCarEvent("ARRIVE", car.getCarId(), this.getTime(), 0, 0);

        } else {
            this.state.rejected();
        }
    }

}
