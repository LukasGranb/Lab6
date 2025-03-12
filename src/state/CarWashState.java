package src.state;

import jdk.jfr.EventType;
import src.events.MachineType;
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

    private final UniformRandomStream fastMachineTime;
    private final UniformRandomStream slowMachineTime;

    private double totalIdleTime = 0.0;
    private double totalQueueTime = 0.0;
    private double lambda;
    private long seed;
    private double fastLower;
    private double fastUpper;
    private double slowLower;
    private double slowUpper;
    private int lastCarId = 0;



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
        this.completedCars = 0;
        this.lambda = lambda;
        this.seed = seed;
        this.fastLower = fastLower;
        this.fastUpper = fastUpper;
        this.slowLower = slowLower;
        this.slowUpper = slowUpper;
    }

        public int idCounter(){
        int carId = this.carNextId;
        this.carNextId++;
        return carId;
    }

    @Override
    public void advanceTime(double time) {
        this.setTime(time);
    }

    public void notifyCarEvent(String eventType, int carId, double time, double idleTime, double queueTime) {
        String[] eventInfo = {eventType, String.valueOf(carId),
                String.valueOf(time), String.valueOf(idleTime),
                String.valueOf(queueTime)};
        this.setChanged();
        this.notifyObservers(eventInfo);
    }

    public void carArrivesFastMachines(int carId, double time) {
        this.fastMachines--;
        notifyCarEvent("ARRIVE", carId, time, 0, 0);
    }

    public void carArrivesSlowMachines(int carId, double time) {
        this.slowMachines--;
        notifyCarEvent("ARRIVE", carId, time, 0, 0);
    }

    public void rejected(int carId, double time) {
        this.rejected++;
        notifyCarEvent("REJECTED", carId, time, 0, 0);
    }

    public void carLeavesFastMachines(int carId, double time) {
        this.fastMachines++;
        this.completedCars++;
        notifyCarEvent("LEAVE", carId, time, 0, 0);
    }

    public void carLeavesSlowMachines(int carId, double time) {
        this.slowMachines++;
        this.completedCars++;
        notifyCarEvent("LEAVE", carId, time, 0, 0);
    }

    // For simulation start and stop events
    public void notifySimulationStart() {
        notifyCarEvent("START", 0, this.getTime(), 0, 0);
    }

    public void notifySimulationStop() {
        notifyCarEvent("STOP", 0, this.getTime(), 0, 0);
    }

    public void carArrivesQueue(Car car, int carId, double time) {
        carQueue.add(car);
        notifyCarEvent("ARRIVE", carId, time, 0, 0);
    }

    public Car processNextFromQueue() {
        if (carQueue.isEmpty()) {
            return null;
        }
        Car nextCar = carQueue.poll();

        if (fastMachines > 0) {
            carArrivesFastMachines(nextCar.getCarId(), this.getTime());
            return nextCar;
        } else if (slowMachines > 0) {
            carArrivesSlowMachines(nextCar.getCarId(), this.getTime());
            return nextCar;
        } else {
            carQueue.add(nextCar);
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

    public double getMachineTime(MachineType machineType) {
        if (machineType == MachineType.FAST) {
            return fastMachineTime.next();
        } else {
            return slowMachineTime.next();
        }
    }

    public int getRejected() {
        return this.rejected;
    }

    public int getCompletedCars() {
        return this.completedCars;
    }

    public double getIdleTime() {
        return totalIdleTime;
    }

    public void setIdleTime(double idleTime) {
        this.totalIdleTime = idleTime;
    }

    public double getTotalQueueTime() {
        return totalQueueTime;
    }

    public void setTotalQueueTime(double queueTime) {
        this.totalQueueTime = queueTime;
    }

    public double getLambda() {
        return lambda;
    }

    public long getSeed() {
        return seed;
    }

    public double getFastMachineLowerBound() {
        return fastLower;
    }

    public double getFastMachineUpperBound() {
        return fastUpper;
    }

    public double getSlowMachineLowerBound() {
        return slowLower;
    }

    public double getSlowMachineUpperBound() {
        return slowUpper;
    }
}
