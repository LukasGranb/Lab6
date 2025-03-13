package src.events;

import src.sim.Event;
import src.sim.EventQueue;
import src.sim.SimState;
import src.state.CarWashState;

/**
 * @author Lukas Granberg, Amund Knutsson
 * Start class Creates initial CarArrive event and makes the simulator start
 */

public class Start extends Event<SimState> {
    private Car car;
    private CarWashState state;
    private EventQueue eventQueue;

    /**
     * Constructor for creating a Start event
     * @param state
     * @param eventQueue
     * @param time
     */

    public Start(CarWashState state, EventQueue eventQueue, double time){
        super(state, eventQueue, time);
        this.state = state;
        this.eventQueue = eventQueue;
    }

    /**
     * execute creates a CarArrives event sets the Simstate to run and notifys carwashView
     */

    @Override
    public void execute() {
        this.eventQueue.addEvent(new CarArrives(this.state, this.eventQueue, this.state.getTimeSpread()));
        this.state.setState(SimState.State.RUN);
        state.notifySimulationStart();

    }
}
