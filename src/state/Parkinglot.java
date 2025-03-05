package src.state;
import src.sim.SimState;

public class Parkinglot extends SimState{
    private int queue;

    public SimState(int queue){
        queue = this.queue;
    }

    public carLeavesQueue() {
        this.queue++;
    }

    public carArrivesQueue() {
        this.queue--;
    }
    //----------- GETTER -----------
    public int getQueue(){
        return this.queue
    }

}
