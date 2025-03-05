package src.state;

import src.sim.SimState;
import src.sim.SimView;

public class CarWashState extends SimState {
    private int fastMachines;
    private int slowMachines;
    private int parkingLotSize;

    public CarWashState(int fastMachines, int slowMachines, int parkingLotSize, double time, SimView view) {
        super(time, view);
        this.fastMachines = fastMachines;
        this.slowMachines = slowMachines;
        this.parkingLotSize = parkingLotSize;
    }

    public void carLeavesFastMachines() {
        this.fastMachines++;
    }

    public void carArrivesFastMachines() {
        this.fastMachines--;
    }

    public void carLeavesSlowMachines() {
        this.slowMachines++;
    }

    public void carArrivesSlowMachins() {
        this.slowMachines--;
    }

    //------- Getters --------
    public int getFastMachines() {
        return this.fastMachines;
    }

    public int getSlowMachines() {
        return this.slowMachines;
    }



}
