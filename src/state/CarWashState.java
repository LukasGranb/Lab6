package src.state;

import src.events.CarLeaves;
import src.events.MachineType;
import src.random.ExponentialRandomStream;
import src.random.UniformRandomStream;
import src.sim.EventQueue;
import src.sim.SimState;
import src.sim.SimView;
import src.events.Car;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * @Author Amund Knutsen, Lukas Granberg
 * a state specific for car wash simulations utilizing SimState
 */
public class CarWashState extends SimState {

    public enum EventType {
        START,
        STOP,
        ARRIVAL,
        LEAVE;
    }

    private int fastMachines;
    private int slowMachines;
    private int parkingLotSize;
    private int rejected;
    private Queue<Car> carQueue;
    private int carNextId = 0;
    private int completedCars = 0;

    private final UniformRandomStream fastMachineTime;
    private final UniformRandomStream slowMachineTime;
    private final ExponentialRandomStream timeSpreading;


    private double totalIdleTime = 0.0;
    private double totalQueueTime = 0.0;
    private double previousQueueTime = 0.0;
    private double previousIdleTime = 0.0;
    private double lambda;
    private long seed;
    private double fastLower;
    private double fastUpper;
    private double slowLower;
    private double slowUpper;
    private int lastCarId = 0;
    private double meanQueueTime = 0.0;


    /**
     * Constructor for a CarWashState
     * @param fastMachines amount of fast machines avaiable
     * @param fastLower Lower bound for fast machine times
     * @param fastUpper Upper bound for fast machine times
     * @param slowMachines amount of slow machines avaiable
     * @param slowLower Lower bound for slow machine times
     * @param slowUpper Upper bound for fast machine times
     * @param parkingLotSize Size for internal parking lot
     * @param time Starting time for this CarWashState
     * @param lambda lambda for the random time generators
     * @param seed seed for the random time generators
     * @param simView Instance of SimView used for this specific CarWashState
     */
    public CarWashState(
            int fastMachines,
            double fastLower,
            double fastUpper,
            int slowMachines,
            double slowLower,
            double slowUpper,
            int parkingLotSize,
            double time,
            double lambda,
            long seed,
            SimView simView
    ) {
        super(time, simView);
        this.fastMachines = fastMachines;
        this.slowMachines = slowMachines;
        this.parkingLotSize = parkingLotSize;
        this.rejected = 0;
        this.carQueue = new LinkedList<>();
        this.fastMachineTime = new UniformRandomStream(fastLower, fastUpper, seed);
        this.slowMachineTime = new UniformRandomStream(slowLower, slowUpper, seed);
        this.timeSpreading = new ExponentialRandomStream(lambda, seed);
        this.completedCars = 0;
        this.lambda = lambda;
        this.seed = seed;
        this.fastLower = fastLower;
        this.fastUpper = fastUpper;
        this.slowLower = slowLower;
        this.slowUpper = slowUpper;

    }

    /**
     * Gets the next carId
     * @return carId
     */
    public int idCounter(){
        int carId = this.carNextId;
        this.carNextId++;
        return carId;
    }

    /**
     * Advances time in the simulation
     * @param time time
     */
    @Override
    public void advanceTime(double time) {
        updateIdleTime(time);
        updateQueueTime(time);
        this.setTime(time);
    }

    /**
     * Updates the SimView to display information about the car wash
     * @param eventType
     * @param carId
     * @param time
     * @param idleTime
     * @param queueTime
     */
    public void notifyCarEvent(EventType eventType, int carId, double time, double idleTime, double queueTime) {
        List<Object> eventInfo = new ArrayList<>();
        eventInfo.add(eventType);
        eventInfo.add(carId);
        eventInfo.add(time);
        eventInfo.add(idleTime);
        eventInfo.add(queueTime);
        this.setChanged();
        this.notifyObservers(eventInfo);
    }

    /**
     * method for handling a car arriving to a fast machine
     * @param car
     * @param time
     * @param queue
     */
    public void carArrivesFastMachines(Car car, double time, EventQueue queue) {
        this.fastMachines--;
        queue.addEvent(new CarLeaves(this, queue, MachineType.FAST, time, this.getMachineTime(MachineType.FAST), car.getCarId()));
    }

    /**
     * method for handling a car arriving to a slow mahcine
     * @param car
     * @param time
     * @param queue
     */
    public void carArrivesSlowMachines(Car car, double time, EventQueue queue) {
        this.slowMachines--;
        queue.addEvent(new CarLeaves(this, queue, MachineType.SLOW, time, this.getMachineTime(MachineType.SLOW), car.getCarId()));
    }

    /**
     * method for handling rejected cars
     */
    public void rejected() {
        this.rejected++;
    }

    /**
     * Method for handling a car leaving a fast machine
     * @param carId
     * @param time
     */
    public void carLeavesFastMachines(int carId, double time) {
        this.fastMachines++;
        this.completedCars++;
        notifyCarEvent(EventType.LEAVE, carId, time, 0, 0);
    }

    /**
     * Method for handling a car leaving a slow machine
     * @param carId
     * @param time
     */
    public void carLeavesSlowMachines(int carId, double time) {
        this.slowMachines++;
        this.completedCars++;
        notifyCarEvent(EventType.LEAVE, carId, time, 0, 0);
    }

    // For simulation start and stop events
    @Override
    public void notifySimulationStart() {
        notifyCarEvent(EventType.START, 0, this.getTime(), 0, 0);
    }
    @Override
    public void notifySimulationStop() {
        notifyCarEvent(EventType.STOP, 0, this.getTime(), 0, 0);
    }

    /**
     * Method for handling a car arriving to the parking lot if no machines are avaiable
     * @param car
     * @param carId
     * @param time
     */
    public void carArrivesQueue(Car car, int carId, double time) {
        carQueue.add(car);

    }

    /**
     * Method for processing the next car from the queue if a machine becomes avaiable
     * @param queue
     */
    public void processNextFromQueue(EventQueue queue) {
       if(carQueue.size() != 0) {
           Car nextCar = carQueue.poll();
           if (fastMachines > 0) {
               carArrivesFastMachines(nextCar, this.getTime(), queue);
           } else if (slowMachines > 0) {
               carArrivesSlowMachines(nextCar, this.getTime(), queue);

           } else {
               carQueue.add(nextCar);
           }
       }
    }

    /**
     * Updates the Queue times
     * @param time
     */
    public void updateQueueTime(double time) {
        if (this.getQueueSize() != 0) {
            double timeElapsed = time - this.getTime();
            totalQueueTime += timeElapsed * this.getQueueSize();
            meanQueueTime = totalQueueTime / ((this.carNextId) - this.rejected);
        }
    }

    /**
     * Updates the idle times
     * @param time
     */
    public void updateIdleTime(double time) {
        if (this.getIdleMachines() != 0) {
            double timeElapsed = time - this.getTime();
            totalIdleTime += timeElapsed * this.getIdleMachines();
        }
    }

    //------- Getters --------

    /**
     * Getter for the timespread from an ExponentialTimeStream
     * @return
     */
    public double getTimeSpread() {
        return timeSpreading.next() + getTime();
    }

    /**
     * Getter for the car queue
     * @return
     */
    public int getQueue() {
        return this.parkingLotSize - carQueue.size();
    }

    /**
     * Getter for the current size of the car Queue
     * @return
     */
    public int getQueueSize(){
        return carQueue.size();
    }

    /**
     * Getter for the amount of fast machines avaiable
     * @return
     */
    public int getFastMachines() {
        return this.fastMachines;
    }

    /**
     * Getter for the amount of slow machines avaiable
     * @return
     */
    public int getSlowMachines() {
        return this.slowMachines;
    }

    /**
     * Gets the process times for the different machine types
     * @param machineType
     * @return
     */
    public double getMachineTime(MachineType machineType) {
        if (machineType == MachineType.FAST) {
            return fastMachineTime.next();
        } else {
            return slowMachineTime.next();
        }
    }

    /**
     * Getter for the amount of idle machines
     * @return
     */
    private int getIdleMachines(){
        return getSlowMachines() + getFastMachines();
    }

    /**
     * Getter for rejected cars
     * @return
     */
    public int getRejected() {
        return this.rejected;
    }

    /**
     * Getter for amount of cars that have been handled
     * @return
     */
    public int getCompletedCars() {
        return this.completedCars;
    }

    /**
     * Getter for the idle times for the machines
     * @return
     */
    public double getIdleTime() {
        return totalIdleTime;
    }

    /**
     * Getter for the total queuing time
     * @return
     */
    public double getTotalQueueTime() {
        return totalQueueTime;
    }

    /**
     * Getter for the mean queuing time
     * @return
     */
    public double getMeanQueueTime() {
        return meanQueueTime;
    }

    /**
     * Getter for the lambda
     * @return
     */
    public double getLambda() {
        return lambda;
    }

    /**
     * Getter for the seed
     * @return
     */
    public long getSeed() {
        return seed;
    }

    /**
     * Getter for the fast machines lower time bound
     * @return
     */
    public double getFastMachineLowerBound() {
        return fastLower;
    }

    /**
     * Getter for the fast machines upper time bound
     * @return
     */
    public double getFastMachineUpperBound() {
        return fastUpper;
    }

    /**
     * Getter for the slow machines lower time bound
     * @return
     */
    public double getSlowMachineLowerBound() {
        return slowLower;
    }

    /**
     * Getter for slow machines upper time bound
     * @return
     */
    public double getSlowMachineUpperBound() {
        return slowUpper;
    }

    /**
     * Getter for the internal parking lot size
     * @return
     */
    public int getParkingLotSize() {
        return this.parkingLotSize;
    }
}
