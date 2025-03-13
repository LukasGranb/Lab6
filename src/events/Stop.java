package src.events;

import src.sim.Event;
import src.sim.SimState;
import src.sim.EventQueue;
import src.state.CarWashState;

/**
 * @author Lukas Granberg, Amund Knutsson
 * Stop Event for simulating the end of the day for the car wash simulation
 */

public class Stop extends Event<CarWashState> {

    private CarWashState state;
    private EventQueue queue;

    /**
     * Constructor for the stop event
     * @param state
     * @param eventQueue
     * @param time
     */
    public Stop(CarWashState state, EventQueue eventQueue, double time) {
        super(state, eventQueue, time);
        this.state = state;
        this.queue = eventQueue;
    }

    /**
     * Stops the simulation
     */
    @Override
    public void execute() {
        this.state.setState(SimState.State.STOP);
        state.notifySimulationStop();
    }
}
