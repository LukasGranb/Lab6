package src.sim;

import src.random.ExponentialRandomStream;

public abstract class Event<S extends SimState> implements Comparable<Event<S>> {

    private double time;
    private S state;
    private EventQueue eventQueue;

    public Event(S state, EventQueue queue, double time) {
        this.time = time;
    }

    public abstract void execute(S state, EventQueue queue);

    public double getTime() {
        return this.time;
    }

    @Override
    public int compareTo(Event<S> other) {
        return Double.compare(this.time, other.time);
    }
}
