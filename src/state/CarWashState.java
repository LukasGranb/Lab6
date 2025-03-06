package src.state;

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


    public CarWashState(int fastMachines, int slowMachines, int parkingLotSize, double time, SimView view) {
        super(time, view);
        this.fastMachines = fastMachines;
        this.slowMachines = slowMachines;
        this.parkingLotSize = parkingLotSize;
        this.rejected = 0;
        this.carQueue = new LinkedList<>();
    }

    public int idCounter(){
        int carId = this.carNextId;
        this.carNextId++;
        return carId;
    }

    public void rejected() {
        this.rejected++;
    }

    public void carLeavesFastMachines() {
        this.fastMachines++;
    }
    public void carArrivesFastMachines() {
        this.fastMachines--;
    }
    public void carLeavesSlowMachines() {
        this.slowMachines++;
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

    public int getSlowMachines() {
        return this.slowMachines;
    }

    public int getRejected() {
        return this.rejected;
    }
}
