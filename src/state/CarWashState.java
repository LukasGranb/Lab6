package src.state;

import jdk.jfr.EventType;
import src.random.UniformRandomStream;
import src.sim.SimState;
import src.sim.SimView;
import src.events.Car;
import java.util.LinkedList;
import java.util.Queue;

public class CarWashState extends SimState {


    private int fastMachines;
    private int slowMachines;
    private int parkingLotSize;
    private int rejected;
    private Queue<Car> carQueue;
    private int carNextId = 1;
    private int completedCars = 0;
    private double carIdleTime;
    private double machineIdleTime;

    private final UniformRandomStream fastMachineTime;
    private final UniformRandomStream slowMachineTime;



    public CarWashState(
            int fastMachines,
            double fastLower,
            double fastUpper,
            int slowMachines,
            double slowLower,
            double slowUpper,
            int parkingLotSize,
            double time,
            SimView simView
    ) {
        super(time, simView);
        this.fastMachines = fastMachines;
        this.slowMachines = slowMachines;
        this.parkingLotSize = parkingLotSize;
        this.rejected = 0;
        this.carQueue = new LinkedList<>();
        this.fastMachineTime = new UniformRandomStream(fastLower, fastUpper);
        this.slowMachineTime = new UniformRandomStream(slowLower, slowUpper);
        this.completedCars = completedCars;
    }
    /*
    private Type transition(EVENTTYPE) {
        case(EVENTTYPE) {
            switch REJECTED:
                notifyobserver() och skicka med lämplig information
            switch CarArrive:

            //etc.....
            // skapad enum för eventtypes
            //sen när nått händer so kallar man bara transition(EVENTTYPE)
        }
    }

     */

    public int idCounter(){
        int carId = this.carNextId;
        this.carNextId++;
        return carId;
    }

    @Override
    public void advanceTime(double time) {
        this.setTime(time);
    }

    public void rejected() {
        this.rejected++;

        setChanged();
        notifyObservers(new String[]{"REJECTED", String.valueOf(lastCarId), String.valueOf(currentTime), "0.0", "0.0"});
    }

    public void carLeavesFastMachines() {
        this.fastMachines++;
        this.completedCars++;
    }
    public void carArrivesFastMachines() {
        this.fastMachines--;
    }
    public void carLeavesSlowMachines() {
        this.slowMachines++;
        this.completedCars++;
    }
    public void carArrivesSlowMachines() {
        this.slowMachines--;
    }

    /**
     * Adds a car to the parking lot queue
     */

    public void carArrivesQueue(Car car) {
        carQueue.add(car);
    }

    public Car processNextFromQueue() {
        if (carQueue.isEmpty()) {
            return null;
        }
        Car nextCar = carQueue.poll();
        if (fastMachines > 0) {
            carArrivesFastMachines();
            return nextCar;
        } else if (slowMachines > 0) {
            carArrivesSlowMachines();
            return nextCar;
        } else {
            return null;
        }
    }

    //------- Getters --------

    public int getQueue() {
        return this.parkingLotSize - carQueue.size();
    }

    public int getQueueSize(){
        return carQueue.size();
    }

    public int getFastMachines() {
        return this.fastMachines;
    }

    public double getFastMachineTime(){
        return fastMachineTime.next();
    }

    public int getSlowMachines() {
        return this.slowMachines;
    }

    public double getSlowMachineTime(){
        return slowMachineTime.next();
    }

    public int getRejected() {
        return this.rejected;
    }

    public int getCompletedCars() {
        return this.completedCars;
    }
}
