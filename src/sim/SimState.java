package src.sim;

import java.util.Observable;

public abstract class SimState extends Observable {

    private enum State {
        START,
        STOP;
    }

    private State state;
    private double currentTime;

    public SimState(double time, SimView view) {
        super();
        this.currentTime = time;
        this.state = State.START;

        this.addObserver(view);
    }
}