package src.view;

import src.sim.SimView;
import src.state.CarWashState;

import java.util.Formatter;
import java.util.Observable;

public class CarWashView extends SimView {

    private Formatter formatter;
    //Used to check if the header is printed
    private boolean headerPrinted = false;

    public CarWashView() {
        this.formatter = new Formatter();
    }

    @Override
    public void output() {

        System.out.print(formatter.toString());
        this.formatter = new Formatter();  // Reset buffer to prevent duplicate printing
    }


    public void printConfiguration(CarWashState state, double fastMachineMinTime, double fastMachineMaxTime, double slowMachineMinTime, double slowMachineMaxTime, double lambda, long seed) {
        formatter.format("Fast Machines: %d\n", state.getFastMachines());
        formatter.format("Slow Machines: %d\n", state.getSlowMachines());
        formatter.format("Fast Machine Time Distrubution: %.1f, %.1f\n", fastMachineMinTime, fastMachineMaxTime);
        formatter.format("Slow Machine Time Distrubution: %.1f, %.1f\n", slowMachineMinTime, slowMachineMaxTime);
        formatter.format("Lambda: %.1f\n", lambda);
        formatter.format("Seed %d\n", seed);
        formatter.format("Max Queue Size: %d\n", state.getQueueSize());
        formatter.format("--------------------------------\n");

        printHeader();
    }

    public void printHeader() {
        formatter.format("%-6s %-10s %-3s %-5s %-5s %-9s %-10s %-10s %-8s\n",
                "Time", "Event", "Id", "Fast", "Slow", "IdleTime",
                "QueueTime", "QueueSize", "Rejected");
        headerPrinted = true;
    }

    //Det som printas när simulatorn körts klart
    //Fattar ej hur ni har implementerat CarWashState state, Känner mig lite trög men tror ni fattar koden så kanske ni kan lösa det
    // detta går att skriva om men tror det kan vara smart att hålla reda på idletime och total queue time i carwashstate
    public void printResults(CarWashState state) {
        formatter.format("---------------------------------\n");
        formatter.format("Total idle machine time: %.2f\n", state.getIdleTime());
        formatter.format("Total queueing time: %.2f\n", state.getTotalQueueTime());

        int totalCarsServed = state.getCompletedCars();
        if (totalCarsServed > 0) {
            formatter.format("Mean queueing time: %.2f\n",
                    state.getTotalQueueTime() / totalCarsServed);
        } else {
            formatter.format("No cars served\n");
        }

        formatter.format("Rejected cars: %d\n", state.getRejected());
    }

    public void updateStatistics(CarWashState state, String eventType, int carId, double idleTime, double queueTime) {
        state.setIdleTime(state.getIdleTime() + idleTime);
        state.setTotalQueueTime(state.getTotalQueueTime() + queueTime);
    }

    @Override
    public void update(Observable o, Object arg) {
        CarWashState state = (CarWashState) o;

        if (!headerPrinted) {
            // skapa getter för dessa?
            printConfiguration(state,
                    state.getFastMachineLowerBound(),
                    state.getFastMachineUpperBound(),
                    state.getSlowMachineLowerBound(),
                    state.getSlowMachineUpperBound(),
                    state.getLambda(),
                    state.getSeed());
        }

        if (arg != null && arg instanceof String[]) {
            String[] eventInfo = (String[]) arg;
            String eventType = eventInfo[0];
            int carId = Integer.parseInt(eventInfo[1]);
            double time = Double.parseDouble(eventInfo[2]);
            double idleTime = Double.parseDouble(eventInfo[3]);
            double queueTime = Double.parseDouble(eventInfo[4]);

            updateStatistics(state, eventType, carId, idleTime, queueTime);

            if (eventType.equals("START")) {
                formatter.format("%-6.2f %-10s\n", time, "Start");
            } else if (eventType.equals("STOP")) {
                formatter.format("%-6.2f %-10s\n", time, "Stop");
                printResults(state);
            } else if (eventType.equals("ARRIVE")) {
                formatter.format("%-6.2f %-10s %-3d %-5d %-5d %-9.2f %-10.2f %-10d %-8d\n",
                        time, "Arrive", carId,
                        state.getFastMachines(), state.getSlowMachines(),
                        state.getIdleTime(), state.getTotalQueueTime(),
                        state.getQueueSize(), state.getRejected());
            } else if (eventType.equals("LEAVE")) {
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