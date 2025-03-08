package src.view;

import src.events.CarArrives;
import src.events.CarLeaves;
import src.events.MachineType;
import src.sim.SimView;
import src.state.CarWashState;

import java.util.Observable;
import java.util.Observer;

public class CarWashView extends SimView {

    private CarWashState carWashState;

    public CarWashView(CarWashState carWashState) {
        this.carWashState = carWashState;
        carWashState.addObserver(this);

        System.out.println("Fast machines: " + carWashState.getFastMachines());
        System.out.println("Slow machines: " + carWashState.getSlowMachines());
        //Kommenterar ut dessa för vi har ej getFastDist metoderna osv men jag tror dock vi behöver något sånt..
        //System.out.println("Fast distrubution: " + carWashState.getFastDist());
        //System.out.println("Slow distrubution: " + carWashState.getSlowDist());
        //System.out.println("Exponential distrubution with lambda = " + carWashState.getLambda());
        //System.out.println("Seed = " + CarWashState.getSeed());
        System.out.println("Max queue size: " + carWashState.getQueueSize());
    }

    @Override
    public void update(Observable o, Object arg) {
        if (arg instanceof CarArrives || arg instanceof CarLeaves) {
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
        }

    }


    public void output() {
        System.out.println("Total idle machinetime: ");
    }
}



