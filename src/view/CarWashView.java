package src.view;

import src.events.CarArrives;
import src.events.CarLeaves;
import src.events.MachineType;
import src.sim.SimView;
import src.state.CarWashState;

import java.util.Formatter;
import java.util.Observable;
import java.util.Observer;

public class CarWashView extends SimView {

    private CarWashState carWashState;

    public CarWashView(CarWashState carWashState) {
        this.carWashState = carWashState;


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

// Lukas försök. Ej säker på detta är korrekt och kräver en del anpassning i CarWashState som jag ej vet om det är så bra.

//package src.view;
//
//import java.util.Formatter;
//import java.util.Observable;
//import java.util.Observer;
//
//import src.sim.SimView;
//import src.state.CarWashState;
//
//public class CarWashView extends SimView {
//
//    private Formatter formatter;
//    private boolean headerPrinted = false;
//    private CarWashState state;
//
//    // Sim configurations
//    private double fastWashMinTime;
//    private double fastWashMaxTime;
//    private double slowWashMinTime;
//    private double slowWashMaxTime;
//    private double lambda;
//    private long seed;
//
//    // Statistics
//    private double totalIdleTime = 0.0;
//    private double totalQueueTime = 0.0;
//    private int completedCars = 0;
//    private int lastCarId = 0;
//
//    public CarWashView(CarWashState state, double fastWashMinTime, double fastWashMaxTime, double slowWashMinTime,
//                       double slowWashMaxTime, double lambda, long seed) {
//        this.state = state;
//        this.formatter = new Formatter();
//        this.fastWashMinTime = fastWashMinTime;
//        this.fastWashMaxTime = fastWashMaxTime;
//        this.slowWashMinTime = slowWashMinTime;
//        this.slowWashMaxTime = slowWashMaxTime;
//        this.lambda = lambda;
//        this.seed = seed;
//    }
//
//    @Override
//    public void output() {
//        // Output all the formatted content
//        System.out.println(formatter.toString());
//    }
//
//    public void printConfiguration() {
//        formatter.format("Fast machines: %d\n", state.getFastMachines());
//        formatter.format("Slow machines: %d\n", state.getSlowMachines());
//        formatter.format("Fast machine distribution: %.1f, %.1f\n", fastWashMinTime, fastWashMaxTime);
//        formatter.format("Slow machine distribution: %.1f, %.1f\n", slowWashMinTime, slowWashMaxTime);
//        formatter.format("Exponential distribution with lambda =  %.1f\n", lambda);
//        formatter.format("Seed = %d\n", seed);
//        formatter.format("Max Queue size: %d\n", state.getMaxQueueSize());
//        formatter.format("----------------------------------------\n");
//
//        printHeader();
//    }
//
//    // Prints Table header
//    private void printHeader() {
//        formatter.format("%-6s %-10s %-3s %-5s %-5s %-9s %-10s %-10s %-8s\n", "Time", "Event", "Id", "Fast", "Slow", "IdleTime", "QueueTime", "QueueSize", "Rejected");
//        headerPrinted = true;
//    }
//
//    public void updateStatistics(String eventType, int carId, double idleTime, double queueTime) {
//        this.lastCarId = carId;
//        this.totalIdleTime += idleTime;
//        this.totalQueueTime += queueTime;
//
//        if (eventType.equals("LEAVE")) {
//            this.completedCars++;
//        }
//    }
//
//    public void printResults() {
//        formatter.format("---------------------------------\n");
//        formatter.format("Total idle machine time: %.2f\n", totalIdleTime);
//        formatter.format("Total queueing time: %.2f\n", totalQueueTime);
//
//        int totalCarsServed = completedCars + state.getQueueSize();
//        if (totalCarsServed > 0) {
//            formatter.format("Mean queueing time: %.2f\n",
//                    totalQueueTime / totalCarsServed);
//        } else {
//            formatter.format("Mean queueing time: 0.00\n");
//        }
//
//        formatter.format("Rejected cars: %d\n", state.getRejected());
//    }
//
//    @Override
//    public void update(Observable o, Object arg) {
//        if (!headerPrinted) {
//            printHeader();
//        }
//
//        if (arg != null && arg instanceof String[]) {
//            String[] eventInfo = (String[]) arg;
//            String eventType = eventInfo[0];
//            int carId = Integer.parseInt(eventInfo[1]);
//            double time = Double.parseDouble(eventInfo[2]);
//            double idleTime = Double.parseDouble(eventInfo[3]);
//            double queueTime = Double.parseDouble(eventInfo[4]);
//
//            updateStatistics(eventType, carId, idleTime, queueTime);
//
//            if (eventType.equals("START")) {
//                formatter.format("%-6.2f %-10s\n", time, "Start");
//            } else if (eventType.equals("STOP")) {
//                formatter.format("%-6.2f %-10s\n", time, "Stop");
//                printResults();
//            } else if (eventType.equals("ARRIVE")) {
//                formatter.format("%-6.2f %-10s %-3d %-5d %-5d %-9.2f %-10.2f %-10d %-8d\n",
//                        time, "Arrive", carId,
//                        state.getFastMachines(), state.getSlowMachines(),
//                        totalIdleTime, totalQueueTime,
//                        state.getQueueSize(), state.getRejected());
//            } else if (eventType.equals("LEAVE")) {
//                formatter.format("%-6.2f %-10s %-3d %-5d %-5d %-9.2f %-10.2f %-10d %-8d\n",
//                        time, "Leave", carId,
//                        state.getFastMachines(), state.getSlowMachines(),
//                        totalIdleTime, totalQueueTime,
//                        state.getQueueSize(), state.getRejected());
//            } else if (eventType.equals("REJECTED")) {
//                formatter.format("%-6.2f %-10s %-3d %-5d %-5d %-9.2f %-10.2f %-10d %-8d\n",
//                        time, "Rejected", carId,
//                        state.getFastMachines(), state.getSlowMachines(),
//                        totalIdleTime, totalQueueTime,
//                        state.getQueueSize(), state.getRejected());
//            }
//        }
//    }
//}

