package src.sim;

import java.util.Observable;

/**
 * @author Amund Knutsen, Lukas Granberg
 * An abstract class which can be extended to provide functionality to specific SimStates
 */
public abstract class SimState extends Observable {
    /**
     * States
     */
    public enum State {
        RUN,
        STOP;
    }

    private State state;
    private double time;


    /**
     * @param time time
     * @param view  an instance of SimView
     */
    public SimState(double time, SimView view) {
        super();
        this.addObserver(view);
        this.time = time;
        this.state = null;

    }

    /**
     * abstract method to advance time in the simulation
     * @param time
     */
    public abstract void advanceTime(double time);

    /**
     * abstract method to notify start of simulation
     */
    public abstract void notifySimulationStart();

    /**
     * abstract method to notify end of simulation
     */
    public abstract void notifySimulationStop();

    /**
     * Returns the state
     * @return State
     */
    public State getState() {
        return this.state;
    }

    /**
     * Return the time for this state
     * @return Time
     */
    public double getTime() {
        return this.time;
    }

    /**
     * Sets time for this state
     * @param newTime Time
     */
    public void setTime(double newTime) {
        this.time = newTime;
    }

    /**
     * Sets state of this State
     * @param state State
     */
    public void setState(State state) {
        this.state = state;
    }
}