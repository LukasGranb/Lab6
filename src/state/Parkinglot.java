package src.state;

import java.util.concurrent.ArrayBlockingQueue;

public class Parkinglot {

    private ArrayBlockingQueue<Object> parkinglot;

    //parkinglot[]?

    public Parkinglot(int size) {
        this.parkinglot = new ArrayBlockingQueue<Object>(size);
    }
}
