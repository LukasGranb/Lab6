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
    //do we need this aswell?
    private CarWashState state;
    private EventQueue queue;

    // Innan anton börjar stöka var denna den vi hade.
//    public CarLeaves(double timeEntered, MachineType machineType, int carId) {
//        // Calculate the departure time when creating the event
//        super(timeEntered + getServiceTime(machineType));
//        this.machineType = machineType;
//        this.carId = carId;
//    }

    /*
    Include CarWashState and eventQueue so it becomes this constr instead.
    What i do is add EventQueue and MachineType because
    when a carLeaves event occurs, it might trigger a new CarLeaves
    event for the next car in the queue. To add this event to the
    simulation we need to be able to access EventQueue.

    Then we try to calculate the servicetime we need to know the
    type of machine this car is in. Therefore we need to pass it
    the Machinetyp argument. Pretty much assumed this because i
    took it as they're relevant in carArrives?
     */
    public CarLeaves(CarWashState state, EventQueue queue, double timeEntered, MachineType machineType, int carId){
        super(state, queue, timeEntered + getServiceTime(machineType));
        this.machineType = machineType;
        this.carId = carId;
        this.state = state;
        this.queue = queue;
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

//    public void execute(SimState simState, EventQueue queue) {
//        if (machineType == MachineType.FAST) {
//            state.carLeavesFastMachine();
//        } else if (machineType == MachineType.SLOW) {
//            state.carLeavesSlowMachine();
//        }
//        if (state.getQueueSize() > 0) {
//            if (nextCar != null) {
//                MachineType nextMachineType = state.getLastUsedMachineType();
//                queue.add(new CarLeaves(getTime(), nextMachineType, nextCar.getCarId()));
//            }
//        }
//    }

    // and then this instead?
    public void execute() {
        if (machineType == MachineType.FAST) {
            state.carLeavesFastMachines();
        } else if (machineType == MachineType.SLOW) {
            state.carLeavesSlowMachines();
        }

        Car nextCar = carWashState.processNextFromQueue();
        if (state.getQueueSize() > 0) {
            //Här vet jag ej vad tanken är?
            if (nextCar != null) {
                MachineType nextMachineType = state.getLastUsedMachineType();
                queue.add(new CarLeaves(getTime(), nextMachineType, nextCar.getCarId()));
            }
        }
    }
}