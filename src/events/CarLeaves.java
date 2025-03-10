package src.events;

import src.random.UniformRandomStream;
import src.sim.Event;
import src.sim.EventQueue;
import src.sim.SimState;
import src.state.CarWashState;

public class CarLeaves extends Event<CarWashState> {


    private int carId;
    //do we need this aswell?
    private CarWashState state;
    private EventQueue queue;
    private double serviceTime;
    private MachineType machineType;

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
    public CarLeaves(CarWashState state, EventQueue queue, MachineType machine, double timeEntered, double serviceTime, int carId){
        super(state, queue, timeEntered + serviceTime);
        this.carId = carId;
        this.state = state;
        this.queue = queue;
        this.machineType = machine;
    }

    /**
     * Calculates a random service time based on what machines it gets into
     *
     * @param
     * @return time between 2-4
     * @return time between 4-8
     */


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

    }
}