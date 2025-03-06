package src.sim;

public abstract class Event implements Comparable<Event> {

    public Event(SimState state, EventQueue queue, double time) {}

    public abstract void execute();

    public abstract double time();

    @Override
    public int compareTo(Event other) {
        return Double.compare(this.time(), other.time());
    }
}
