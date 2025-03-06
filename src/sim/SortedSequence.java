package src.sim;

import java.util.*;

public class SortedSequence {

    private PriorityQueue<Event> sorted;

    public SortedSequence() {
        sorted = new PriorityQueue<Event>();
    }

    public void add(Event e) {
        sorted.add(e);
    }

    public Event get() {
        return sorted.poll();
    }
}
