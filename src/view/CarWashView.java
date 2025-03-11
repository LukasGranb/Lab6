//package src.view;
//
//import src.events.CarArrives;
//import src.events.CarLeaves;
//import src.events.MachineType;
//import src.sim.SimView;
//import src.state.CarWashState;
//
//import java.util.Formatter;
//import java.util.Observable;
//import java.util.Observer;
//
//public class CarWashView extends SimView {
//
//    private CarWashState carWashState;
//
//    public CarWashView(CarWashState carWashState) {
//        this.carWashState = carWashState;
//
//
//        System.out.println("Fast machines: " + carWashState.getFastMachines());
//        System.out.println("Slow machines: " + carWashState.getSlowMachines());
//        //Kommenterar ut dessa för vi har ej getFastDist metoderna osv men jag tror dock vi behöver något sånt..
//        //System.out.println("Fast distrubution: " + carWashState.getFastDist());
//        //System.out.println("Slow distrubution: " + carWashState.getSlowDist());
//        //System.out.println("Exponential distrubution with lambda = " + carWashState.getLambda());
//        //System.out.println("Seed = " + CarWashState.getSeed());
//        System.out.println("Max queue size: " + carWashState.getQueueSize());
//    }
//
//    @Override
//    public void update(Observable o, Object arg) {
//        if (arg instanceof CarArrives || arg instanceof CarLeaves) {
//            CarArrives carArrivesEvent = (CarArrives) arg;
//
//            // hur ska man göra typ detta-> double currentTime = carArrives.getTime();
//            //int carID = carArrives.getCarId();
//            int fastMachines = carWashState.getFastMachines();
//            int slowMachines = carWashState.getSlowMachines();
//            //double idleTime = carWashState.getIdleTime();
//            //double queueTime = carWashState.getQueueTime();
//            int queueSize = carWashState.getQueueSize();
//            int rejected = carWashState.getRejected();
//
//            String output = String.format(
//                    "%-10.2f %-10s",
//                    currentTime, "Arrive " + carId, FastMachines...
//            );
//            System.out.print(output);
//        }
//
//    }
//
//
//    public void output() {
//        System.out.println("Total idle machinetime: ");
//    }
//}
//




// Lukas försök. Ej säker på detta är korrekt och kräver en del anpassning i CarWashState som jag ej vet om det är så bra.
// En del av detta är lite sudo kod pga att jag ej är 100 på hur carwashstate fungerar hehe.
package src.view;

import src.sim.SimView;
import src.state.CarWashState;

import java.util.Formatter;
import java.util.Observer;

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
    public void printResults(CarWashSate state) {
        formatter.format("---------------------------------\n");
        formatter.format("Total idle machine time: %.2f\n", state.getIdleTime());
        formatter.format("Total queueing time: %.2f\n", state.getTotalQueueTime);

        int totalCarsServed = state.getCompletedCars();
        if (totalCarsServed > 0) {
            formatter.format("Mean queueing time: %.2f\n",
                    totalQueueTime / totalCarsServed);
        } else {
            formatter.format("No cars served\n");
        }

        formatter.format("Rejected cars: %d\n", state.getRejected());
    }

    //denna är so far bara kopierad från den gamla. kommer behöva justering.
    // kan hända att denna i princip inte ens behövs om vi hanterar all uppdatering i carWashState. vilket kanske är smartare
    public void updateStatistics(String eventType, int carId, double idleTime, double queueTime) {
        this.lastCarId = carId;
        state.setTotalIdleTime(state.getTotalIdleTime += idleTime);
        state.setTotalQueueTime(state.getTotalQueueTime  += queueTime);
    }

    // Samma gäller med denna. Behöver fokusera på matten när jag höll på här så ska göra om detta. vill ni börja så är
    // det bara att köra på men annars så fixar vi det sen.
    @Override
    public void update(Observable o, Object arg) {
        if (!headerPrinted) {
            // vet ej hur jag ska skriva in alla parametrar ännu.
            //fastMachineMinTime ska alltså vara det snabbaste de snabba maskiner kan vara och max det söligaste
            //Samma gäller för slowMachines
            //Alternativt att vi gör en metod som hämtar dessa värden. idk
            printConfiguration();
        }

        if (arg != null && arg instanceof String[]) {
            String[] eventInfo = (String[]) arg;
            String eventType = eventInfo[0];
            int carId = Integer.parseInt(eventInfo[1]);
            double time = Double.parseDouble(eventInfo[2]);
            double idleTime = Double.parseDouble(eventInfo[3]);
            double queueTime = Double.parseDouble(eventInfo[4]);

            updateStatistics(eventType, carId, idleTime, queueTime);

            if (eventType.equals("START")) {
                formatter.format("%-6.2f %-10s\n", time, "Start");
            } else if (eventType.equals("STOP")) {
                formatter.format("%-6.2f %-10s\n", time, "Stop");
                printResults();
            } else if (eventType.equals("ARRIVE")) {
                formatter.format("%-6.2f %-10s %-3d %-5d %-5d %-9.2f %-10.2f %-10d %-8d\n",
                        time, "Arrive", carId,
                        state.getFastMachines(), state.getSlowMachines(),
                        totalIdleTime, totalQueueTime,
                        state.getQueueSize(), state.getRejected());
            } else if (eventType.equals("LEAVE")) {
                formatter.format("%-6.2f %-10s %-3d %-5d %-5d %-9.2f %-10.2f %-10d %-8d\n",
                        time, "Leave", carId,
                        state.getFastMachines(), state.getSlowMachines(),
                        totalIdleTime, totalQueueTime,
                        state.getQueueSize(), state.getRejected());
            } else if (eventType.equals("REJECTED")) {
                formatter.format("%-6.2f %-10s %-3d %-5d %-5d %-9.2f %-10.2f %-10d %-8d\n",
                        time, "Rejected", carId,
                        state.getFastMachines(), state.getSlowMachines(),
                        totalIdleTime, totalQueueTime,
                        state.getQueueSize(), state.getRejected());
            }
        }
    }
}
