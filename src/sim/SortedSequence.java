package src.sim;

import java.util.*;

public class SortedSequence implements Queue{

    private PriorityQueue<> queue = new PriorityQueue<>();

    //Sorts list after time and returns an array list.
    public ArrayList<> sorter() {
        ArrayList<> sortedList = new ArrayList<>();
        while (!queue.isEmpty()) {
            sortedList.add(queue.poll());
        }
        retrun sortedList;
    }
}
