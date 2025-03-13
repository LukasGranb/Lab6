package src.events;

import src.sim.Event;
import src.sim.EventQueue;
import src.state.CarWashState;

/**
 * @author Lukas Granberg, Amund Knutsson
 * CarArrives extends Event<CarWashState>
 * The class creates carArrive events
 */

public class CarArrives extends Event<CarWashState> {

    private Car car;
    private CarWashState state;
    private EventQueue eventQueue;


    /**
     * Constructor for CarArrive events
     * @param state
     * @param eventQueue
     * @param time
     */

    public CarArrives(CarWashState state, EventQueue eventQueue, double time) {
        super(state, eventQueue, time);
        this.state = state;
        this.eventQueue = eventQueue;

    }

    /**
     * execute is the class in cararrives that runs when the instance of carArrives is first in Queue
     * it createts a new car object and gets assigned to a machine, the queue or get rejected
     * Then it creates a new instance of CarArrives
     */

    @Override
    public void execute() {

        if (this.state.getFastMachines() > 0) {
            this.car = new Car(this.state.idCounter());
            this.state.notifyCarEvent(CarWashState.EventType.ARRIVAL, car.getCarId(), this.getTime(), state.getIdleTime(), state.getTotalQueueTime());
            this.state.carArrivesFastMachines(car, this.getTime(), this.eventQueue);
        } else if (this.state.getSlowMachines() > 0) {
            this.car = new Car(this.state.idCounter());
            this.state.notifyCarEvent(CarWashState.EventType.ARRIVAL, car.getCarId(), this.getTime(), state.getIdleTime(), state.getTotalQueueTime());
            this.state.carArrivesSlowMachines(car, this.getTime(), this.eventQueue);
        } else if (this.state.getQueue() > 0) {
            this.car = new Car(this.state.idCounter());
            this.state.notifyCarEvent(CarWashState.EventType.ARRIVAL, car.getCarId(), this.getTime(), state.getIdleTime(), state.getTotalQueueTime());
            this.state.carArrivesQueue(this.car, car.getCarId(), this.getTime());
        } else {
            this.car = new Car(this.state.idCounter());
            this.state.notifyCarEvent(CarWashState.EventType.ARRIVAL, car.getCarId(), this.getTime(), state.getIdleTime(), state.getTotalQueueTime());
            this.state.rejected();
        }
        eventQueue.addEvent(new CarArrives(this.state, this.eventQueue, this.state.getTimeSpread()));
    }
}
