package src.sim;

import src.random.ExponentialRandomStream;

public abstract class Event<S extends SimState> implements Comparable<Event<S>> {

    private double time;
    private S state;
    private EventQueue eventQueue;

    public Event(S state, EventQueue queue, double time) {
        this.time = time;
        this.state = state;
        this.eventQueue = queue;

    }

    public abstract void execute();

    public double getTime() {
        return this.time;
    }

    @Override
    public int compareTo(Event<S> other) {
        return Double.compare(this.time, other.time);
    }
}
