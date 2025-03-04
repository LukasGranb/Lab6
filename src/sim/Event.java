package src.sim;

import src.events.CarArrives;
import src.events.CarLeaves;

import java.util.Random;

public abstract class Event {
    private Random time;

    public Event(Random time) {
        this.time = time;
    }

    public void effect(){
    }
}
