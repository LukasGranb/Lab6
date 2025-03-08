package src.view;

import src.events.CarArrives;
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
            int slow
        }

    }

    public void output(){
        System.out.println("Total idle machinetime: " );
    }
}



