package src.sim;

import java.util.*;

public class SortedSequence implements Queue{

    Queue<> queue = new PriorityQueue<>();

    //Sorts list after time and returns an array list.
    public ArrayList<> sorter() {
        while (!queue.isEmpty()) {
            queue.poll();
        }
    }
}
