package src.events;

import src.sim.Event;
import src.sim.EventQueue;
import src.state.CarWashState;

public class CarLeaves extends Event<CarWashState> {


    private int carId;
    private CarWashState state;
    private EventQueue queue;
    private MachineType machineType;

    public CarLeaves(CarWashState state, EventQueue queue, MachineType machine, double timeEntered, double serviceTime, int carId){
        super(state, queue, timeEntered + serviceTime);
        this.carId = carId;
        this.state = state;
        this.queue = queue;
        this.machineType = machine;
    }

    public void execute() {
        if (machineType == MachineType.FAST) {
            state.carLeavesFastMachines(carId, this.getTime());
            handleQueue(MachineType.FAST);
        } else if (machineType == MachineType.SLOW) {
            state.carLeavesSlowMachines(carId, this.getTime());
            handleQueue(MachineType.SLOW);
        }

    }

    private void handleQueue(MachineType machineType) {
        Car nextCar = state.processNextFromQueue(this.getTime());
        if (nextCar != null) {
                queue.addEvent(new CarLeaves(state, queue, machineType, this.getTime(), state.getMachineTime(machineType), nextCar.getCarId()));
        }
    }
}