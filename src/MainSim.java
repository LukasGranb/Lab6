    package src;
    import src.events.Start;
    import src.events.Stop;
    import src.sim.EventQueue;
    import src.sim.SimState;
    import src.sim.Simulator;
    import src.state.CarWashState;
    import src.view.CarWashView;

    public class MainSim {

        public static void Simulator1(Double start, double stop) {
            CarWashView cwv = new CarWashView();

            CarWashState carWashState = new CarWashState(
                    2, // fastMachines
                    2.8, // fastLower
                    4.6, // fastUpper
                    2, // slowMachines
                    3.5, // slowLower
                    6.7, // slowUpper
                    5, // parkingLotSize
                    0.0, // initial time
                    2.0, // lambda
                    1234, // seed
                    cwv// SimView
            );

            Simulator sim = new Simulator(carWashState, cwv, stop);

            EventQueue queue = sim.getEventQueue();
            queue.addEvent(new Start(carWashState, queue, start));
            queue.addEvent(new Stop(carWashState, queue, stop));

            sim.run();




        }

        public static void main(String[] args) {

            Simulator1(0.0, 15.0);


        }

    }
