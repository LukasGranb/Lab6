package src.sim;

import src.sim.SimState.State;

public class Simulator {

    private SimState state;
    private EventQueue eventQueue;
    private SimView view;

    public Simulator(SimState state, SimView view) {
        this.state = state;
        this.view = view;
        this.eventQueue = new EventQueue(this.state, 15);
    }

    public void run() {
        state.notifySimulationStart();
        while(this.state.getState() == State.RUN) {
            eventQueue.nextEvent();
        }
        state.notifySimulationStop();
    }
}
