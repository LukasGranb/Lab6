package src.state;

import src.events.Car;

import java.util.concurrent.ArrayBlockingQueue;

public class Parkinglot {

    private ArrayBlockingQueue<Object> parkinglot;


    public Parkinglot(int size) {
        this.parkinglot = new ArrayBlockingQueue<Object>(size);
    }

    public boolean addCar(Object car) {


    }

}
