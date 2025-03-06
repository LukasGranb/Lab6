package src.events;

import src.random.UniformRandomStream;
import src.sim.Event;
import src.sim.EventQueue;
import src.sim.SimState;
import src.state.CarWashState;

public class CarLeaves extends Event {
    private static final UniformRandomStream fastMachineTime = new UniformRandomStream(2.0, 4.0);
    private static final UniformRandomStream slowMachineTime = new UniformRandomStream(4.0, 8.0);

    private int carId;
    private MachineType machineType;

    public CarLeaves(double timeEntered, MachineType machineType, int carId) {
        // Calculate the departure time when creating the event
        super(timeEntered + getServiceTime(machineType));
        this.machineType = machineType;
        this.carId = carId;
    }

    /**
     * Calculates a random service time based on what machines it gets into
     *
     * @param machineType
     * @return time between 2-4
     * @return time between 4-8
     */

    private static double getServiceTime(MachineType machineType) {
        if (machineType == MachineType.FAST) {
            return fastMachineTime.next();
        } else {
            return slowMachineTime.next();
        }
    }

    public void execute(SimState simState, EventQueue queue) {
        if (machineType == MachineType.FAST) {
            state.carLeavesFastMachine();
        } else if (machineType == MachineType.SLOW) {
            state.carLeavesSlowMachine();
        }
        if (state.getQueueSize() > 0) {
            if (nextCar != null) {
                MachineType nextMachineType = state.getLastUsedMachineType();
                queue.add(new CarLeaves(getTime(), nextMachineType, nextCar.getCarId()));
            }
        }
    }
}