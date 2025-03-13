package src.sim;

import src.sim.SimState.State;

/**
 * @author Amund Knutsen, Lukas Granberg
 * A simulator class used to be able to simulate different simulations based on the SimState and SimView provided
 */
public class Simulator {

    private SimState state;
    private EventQueue eventQueue;
    private SimView view;

    /**
     * Constructor for the Simulator
     * @param state
     * @param view
     */
    public Simulator(SimState state, SimView view, double timeLimit) {
        this.state = state;
        this.view = view;
        this.eventQueue = new EventQueue(this.state, timeLimit);
    }

    /**
     * Starts the simulation
     */
    public void run() {
        eventQueue.nextEvent();
        while(this.state.getState() == State.RUN) {
            eventQueue.nextEvent();
        }
    }

    /**
     * Getter for the EventQueue of this simulation
     * @return
     */
    public EventQueue getEventQueue() {
        return this.eventQueue;
    }
}
