package src.events;

import src.sim.Event;
import src.sim.EventQueue;
import src.state.CarWashState;

/**
 * @author Lukas Granberg, Amund Knutsson
 * CarLeaves extends Event<CarWashState>
 * The class creates CarLeave events
 * CarLeave events are responsible for handling cars that have been in the carwash
 */

public class CarLeaves extends Event<CarWashState> {


    private int carId;
    private CarWashState state;
    private EventQueue queue;
    private MachineType machineType;

    /**
     * Constructor for creating CarLeave events
     * @param state
     * @param queue
     * @param machine
     * @param timeEntered
     * @param serviceTime
     * @param carId
     */

    public CarLeaves(CarWashState state, EventQueue queue, MachineType machine, double timeEntered, double serviceTime, int carId){
        super(state, queue, timeEntered + serviceTime);
        this.carId = carId;
        this.state = state;
        this.queue = queue;
        this.machineType = machine;
    }

    /**
     * Execute is responsible for doing everything that should happen when a CarLeave event runs
     * Updates amount of cars in washers
     * checks if there is any car in the queue and handels
     * It updates queue time and idle time
     */

    public void execute() {
        if (machineType == MachineType.FAST) {
            state.carLeavesFastMachines(carId, this.getTime());
        } else if (machineType == MachineType.SLOW) {
            state.carLeavesSlowMachines(carId, this.getTime());

        }
        state.processNextFromQueue(this.queue);

    }
}


