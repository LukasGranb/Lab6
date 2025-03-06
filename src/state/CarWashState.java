package src.state;

import src.sim.SimState;
import src.sim.SimView;

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

    public static void carLeavesFastMachines() {
        this.fastMachines++;
    }

    public static void carArrivesFastMachines() {
        this.fastMachines--;
    }

    public static void carLeavesSlowMachines() {
        this.slowMachines++;
    }

    public static void carArrivesSlowMachins() {
        this.slowMachines--;
    }

    /**
     * Adds a car to the parking lot queue
     * @return
     */

    public void carArrivesQueue() {
        if (getQueue() > 0) {
            Car car = new Car(nextCarId++);
            varQueue.add(car);
        }
    }

    /**
     * Process the next car from the queue and assign it to a machine
     * @return The Car objext that was processed, or null if the queue is empty
     */

    public Car processNextFromQueue() {
        if (carQueue.isEmpty()) {
            return null;
        }

        Car nextCar = carQueue.poll();

        return nextCar;
    }

    //------- Getters --------

    public int getQueue() {
        return this.parkingLotSize - carQueue.size();
    }

    public int getQueue.size(){
        return carQueue.size();
    }

    public static int getFastMachines() {
        return this.fastMachines;
    }

    public static int getSlowMachines() {
        return this.slowMachines;
    }

    public int getRejected() {
        return this.rejected;
    }
}
