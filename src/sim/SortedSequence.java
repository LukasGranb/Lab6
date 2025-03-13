package src.sim;

import java.util.*;

/**
 * A sorted sequence that automaticlly sorts items using a priority queue.
 */
public class SortedSequence {

    private PriorityQueue<Event> sorted;

    /**
     * constructor creates a new PriorityQueue
     */
    public SortedSequence() {
        sorted = new PriorityQueue<Event>();
    }

    /**
     * Adds an event to the sorted sequence
     * @param e Event to be added to the sequence
     */
    public void add(Event e) {
        sorted.add(e);
    }

    /**
     * Gets next Event from the sorted sequence
     * @return Event
     */
    public Event get() {
        return sorted.poll();
    }
}
