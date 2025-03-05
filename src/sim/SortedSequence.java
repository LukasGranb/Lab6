package src.sim;

import java.util.*;

public class SortedSequence implements Queue<Event> {

    private PriorityQueue<Event> queue = new PriorityQueue<Event>();

    // Sorts list after time and returns an array list
    public ArrayList<Event> sorter() {
        ArrayList<Event> sortedList = new ArrayList<>();
        while (!queue.isEmpty()) {
            sortedList.add(queue.poll());
        }
        return sortedList;
    }
}