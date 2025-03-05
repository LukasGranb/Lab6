package src.sim;

import src.events.CarArrives;
import src.events.CarLeaves;

import java.util.Random;

public abstract class Event implements Comparable<Event> {
    private double time;

    public Event(double time) {
        this.time = time;
    }

    public abstract void execute();
}
