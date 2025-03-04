package src.sim;

public abstract class SimState {

    private enum State {
        START,
        STOP;
    }

    private State state;

    public SimState() {


    }
}