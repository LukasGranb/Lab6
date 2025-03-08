package src.sim;

import java.util.Observable;

public abstract class SimState extends Observable {

    public enum State {
        RUN,
        STOP;
    }

    private State state;
    private double time;

    public SimState(double time, SimView view) {
        super();
        this.addObserver(view);this.time = time;
        this.state = State.RUN;

    }

    public abstract double advanceTime(double time);

    public State getState() {

        return this.state;
    }

    public void setState(State state) {

        this.state = state;
    }
}