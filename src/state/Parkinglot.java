package src.state;
import src.sim.SimState;

public class Parkinglot extends SimState{
    private int queue;

    public Parkinglot(int queue){
        queue = this.queue;
    }

    public void carLeavesQueue() {
        this.queue++;
    }

    public void carArrivesQueue() {
        this.queue--;
    }
    //----------- GETTER -----------
    public int getQueue(){
        return this.queue;
    }

}
