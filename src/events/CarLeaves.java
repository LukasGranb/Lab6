package src.events;

import src.sim.Event;
import src.state.CarWashState;

public class CarLeaves {
    private static final Random random = new Random();
    private int carId;
    private double time;
    private MachineType machineType;

    public CarLeaves(double timeEntered, MachineType machineType, int carId){
        this.machineType = machineType;
        this.carId = carId;

        this.time = time + machineType
    }

    /**
     * Calculates a random service time based on what machines it gets into
     * @param machineType
     * @return time between 2-4
     * @retrun time between 4-8
     */

    private double getServiceTime(MachineType machineType){
        if (machineType == MachineType.FAST) {
            return 2.0 + (random.nextDouble() * 2.0);
        } else {
            return 4.0 + (random.nextDouble() * 4.0);
        }
    }

    pubnlic double getTime() {
        return this.time;
    }

    public void executeCarLeaves() {
        if (machineType == MachineType.FAST) {
            CarWashState.carleavesFastMachine();
        }
        else if (machineType == MachineType.SLOW) {
            CarWashState.carLeavesSlowMachine();
        }

        if (CarWashState.getQueueSize() > 0) {
            Car nextCar = CarWashState.processNextFromQueue();
            if (nextCar != null) {
                // Determine which machine type became available
                new CarLeaves(time, Car.getId(), machineType);
            }
        }
    }

}
