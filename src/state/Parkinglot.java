package src.state;

import src.events.Car;

import java.util.concurrent.ArrayBlockingQueue;

public class Parkinglot {

    private int parkingLotSize;
    private ArrayBlockingQueue<e> parkinglot;


    public Parkinglot(int size) {
        this.parkingLotSize = size;
    }

    public void carLeavesQueue() {
        this.queue++;
    }

    public void carArrivesQueue() {
        this.queue--;
    }
    //----------- GETTER -----------
    public int getQueue(){
        return this.queue;
    }

}
