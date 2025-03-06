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
    private int nexCarId = 1;


    public CarWashState(int fastMachines, int slowMachines, int parkingLotSize, double time, SimView view) {
        super(time, view);
        this.fastMachines = fastMachines;
        this.slowMachines = slowMachines;
        this.parkingLotSize = parkingLotSize;
        this.rejected = 0;
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

    public void carArrivesSlowMachins() {
        this.slowMachines--;
    }

    /**
     * Adds a car to the parking lot queue
     */

    public void carArrivesQueue() {
        if (getQueue() > 0) {
            Car car = new Car();
            carQueue.add(car);
        }
    }

    /**
     * Process the next car from the queue and assign it to a machine
     * @return The Car objext that was processed, or null if the queue is empty
     */

    public Car processNextFromQueue() {
        if (carQueue.isEmpty()) {
            return null;ggVG
        }

        Car nextCar = carQueue.poll();

        return nextCar;
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
