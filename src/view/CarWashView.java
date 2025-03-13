package src.view;

import src.sim.SimView;
import src.state.CarWashState;

import java.util.Formatter;
import java.util.List;
import java.util.Observable;

/**
 * @author Lukas Granberg, Amund Knutsson
 * CarWashView uses formatter to creates the strings to be printed
 * extends SimView
 */

public class CarWashView extends SimView {

    private Formatter formatter;
    //Used to check if the header is printed
    private boolean headerPrinted = false;

    /**
     * Constructor that creates a new Formatter object
     */

    public CarWashView() {
        this.formatter = new Formatter();
    }

    /**
     * Ouput prints the current string and resets the formatter
     */

    @Override
    public void output() {

        System.out.print(formatter.toString());
        this.formatter = new Formatter();  // Reset buffer to prevent duplicate printing
    }

    /**
     * Formats the determined configuration made at the creation of a Simluator instance
     * @param state
     * @param fastMachineMinTime
     * @param fastMachineMaxTime
     * @param slowMachineMinTime
     * @param slowMachineMaxTime
     * @param lambda
     * @param seed
     */

    public void printConfiguration(CarWashState state, double fastMachineMinTime, double fastMachineMaxTime, double slowMachineMinTime, double slowMachineMaxTime, double lambda, long seed) {
        formatter.format("Fast Machines: %d\n", state.getFastMachines());
        formatter.format("Slow Machines: %d\n", state.getSlowMachines());
        formatter.format("Fast Machine Time Distrubution: %.1f, %.1f\n", fastMachineMinTime, fastMachineMaxTime);
        formatter.format("Slow Machine Time Distrubution: %.1f, %.1f\n", slowMachineMinTime, slowMachineMaxTime);
        formatter.format("Lambda: %.1f\n", lambda);
        formatter.format("Seed %d\n", seed);
        formatter.format("Max Queue Size: %d\n", state.getParkingLotSize());
        formatter.format("--------------------------------\n");

        printHeader();
    }

    /**
     * Formats a header and turn headerPrinted = true to track that it has been printed
     */

    public void printHeader() {
        formatter.format("%-6s %-10s %-3s %-5s %-5s %-9s %-10s %-10s %-8s\n",
                "Time", "Event", "Id", "Fast", "Slow", "IdleTime",
                "QueueTime", "QueueSize", "Rejected");
        headerPrinted = true;
    }

    /**
     *Prints the result after the simulation is done
     * @param state
     */

    public void printResults(CarWashState state) {
        formatter.format("---------------------------------\n");
        formatter.format("Total idle machine time: %.2f\n", state.getIdleTime());
        formatter.format("Total queueing time: %.2f\n", state.getTotalQueueTime());

        int totalCarsServed = state.getCompletedCars();
        if (totalCarsServed > 0) {
            formatter.format("Mean queueing time: %.2f\n",
                    state.getMeanQueueTime());
        } else {
            formatter.format("No cars served\n");
        }

        formatter.format("Rejected cars: %d\n", state.getRejected());
    }

    /**
     * Updates the formatter when notifyed
     * @param o     the observable object.
     * @param arg   an argument passed to the {@code notifyObservers}
     *                 method.
     */

    @Override
    public void update(Observable o, Object arg) {
        CarWashState state = (CarWashState) o;

        if (!headerPrinted) {
            printConfiguration(state,
                    state.getFastMachineLowerBound(),
                    state.getFastMachineUpperBound(),
                    state.getSlowMachineLowerBound(),
                    state.getSlowMachineUpperBound(),
                    state.getLambda(),
                    state.getSeed());
        }

        if (arg != null) {
            List eventInfo = (List) arg;
            CarWashState.EventType eventType = (CarWashState.EventType) eventInfo.get(0);
            int carId = (int) eventInfo.get(1);
            double time = (double) eventInfo.get(2);

            if (eventType == CarWashState.EventType.START) { // start
                formatter.format("%-6.2f %-10s\n", time, "Start");
            } else if (eventType == CarWashState.EventType.STOP) { //stop
                formatter.format("%-6.2f %-10s\n", time, "Stop");
                printResults(state);
            } else if (eventType == CarWashState.EventType.ARRIVAL) { // arrive
                formatter.format("%-6.2f %-10s %-3d %-5d %-5d %-9.2f %-10.2f %-10d %-8d\n",
                        time, "Arrive", carId,
                        state.getFastMachines(), state.getSlowMachines(),
                        state.getIdleTime(), state.getTotalQueueTime(),
                        state.getQueueSize(), state.getRejected());
            } else if (eventType == CarWashState.EventType.LEAVE) { // leave
                formatter.format("%-6.2f %-10s %-3d %-5d %-5d %-9.2f %-10.2f %-10d %-8d\n",
                        time, "Leave", carId,
                        state.getFastMachines(), state.getSlowMachines(),
                        state.getIdleTime(), state.getTotalQueueTime(),
                        state.getQueueSize(), state.getRejected());
            }
        }
        output();
    }
}