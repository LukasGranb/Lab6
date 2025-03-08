package src.view;

import src.events.CarArrives;
import src.events.CarLeaves;
import src.events.MachineType;
import src.sim.SimView;
import src.state.CarWashState;

import java.util.Observable;
import java.util.Observer;

public class CarWashView extends SimView {
//    private double time;
//    private int carId;
//    private Event event;
//    private MachineType fast;
//    private MachineType slow;
//    private double idleTime;
//    private double QueueTime;
//    private int queueSize;
//    private int rejected;

    private CarWashState carWashState;

    public CarWashView(CarWashState carWashState){
        this.carWashState = carWashState;
        carWashState.addObserver(this);
    }

    @Override
    public void update(Observable o, Object arg) {
        if (arg instanceof CarArrives) {
            CarArrives carArrivesEvent = (CarArrives) arg;

            // hur ska man göra typ detta-> double currentTime = carArrives.getTime();
            //int carID = carArrives.getCarId();
            int fastMachines = carWashState.getFastMachines();
            int slowMachines = carWashState.getSlowMachines();
            //double idleTime = carWashState.getIdleTime();
            //double queueTime = carWashState.getQueueTime();
            int queueSize = carWashState.getQueueSize();
            int rejected = carWashState.getRejected();

            String output = String.format(
                    "%-10.2f %-10s",
                    currentTime, "Arrive " + carId, FastMachines...
            );
            System.out.print(output);
        } else if(arg instanceof CarLeaves){
            CarLeaves carLeavesEvent = (CarLeaves) arg;
            //blir detta fucked? jag vill ju bara ha en print av (output).. Man måste nog göra så att
        }

    }

    public void output(){
        System.out.println("Total idle machinetime: " );
    }
}



