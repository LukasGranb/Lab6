package src.sim;

import java.util.Observable;

public abstract class SimState extends Observable {

    private enum State {
        START,
        STOP;
    }

    private State state;

    public SimState() {
        super();

    }
}