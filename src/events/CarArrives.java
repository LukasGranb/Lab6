package src.events;

import src.sim.Event;
import src.sim.EventQueue;
import src.state.CarWashState;



public class CarArrives extends Event<CarWashState> {

    private Car car;
    private CarWashState state;
    private EventQueue eventQueue;


    public CarArrives(CarWashState state, EventQueue eventQueue, double time) {
        super(state, eventQueue, time);
        this.car = new Car(this.state.idCounter());
        this.state = state;
        this.eventQueue = eventQueue;
    }
(
    @Override
    public void execute() {

        if (this.state.getFastMachines > 0) {
            this.state.carArrivesFastMachines();
            queue.add(new CarLeaves(this.getTime(), MachineType.FAST, car.getId()));
            return;
        } else if (this.state.getSlowMachines > 0) {
            this.state.carArrivesSlowMachines();
            queue.add(new CarLeaves(this.getTime(), MachineType.SLOW, car.getId()));
            return
        } else if (this.state.getQueue() != 0) {
            this.state.carArrivesQueue(this.car);
            return;
        } else {
            this.state.rejected();
        }

        //Tror man behöver göra något sånt här, denna syntax är såklart ej korrekt..
        // CarWashState.currentTime(this.getTime?) eller bara  = this.getTime();

        //insåg att det bör vara > istället för != 0. då det fallet när inputen är negativ. Av någon anledning... Blir det problem
    //Antons variant >(
    public void fastOrSlowMachine() {
        if (this.state.getFastMachines() != 0) {
            this.state.carArrivesFastMachines();
            new CarLeaves(this.state, this.eventQueue, this.getTime(), MachineType.FAST, car.getCarId());
            return;
        }

        if (state.getSlowMachines() != 0) {
            state.carArrivesSlowMachines();
            new CarLeaves(this.state, this.eventQueue, this.getTime(), MachineType.SLOW, car.getCarId());
        }
    }

}
