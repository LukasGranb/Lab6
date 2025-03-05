package src.state;

import src.sim.SimState

public class CarWashState extends SimState {
    private int fastMachines;
    private int slowMachines;

    public CarWashState(int fastMachines, int slowMachines){
        fastMachines = this.fastMachines;
        slowMachines = this.slowMachines;
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
