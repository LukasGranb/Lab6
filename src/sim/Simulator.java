package src.sim;

public class Simulator {

    private SimState state;
    private EventQueue eventQueue;
    private SimView view;

    public Simulator() {


    }

    public void run(SimState state, EventQueue eventQueue, SimView view) {
        this.state = state;
        this.eventQueue = eventQueue;
        this.view = view;
    }
}
