    package src;
    import src.events.Car;
    import src.events.CarArrives;
    import src.random.ExponentialRandomStream;
    import src.sim.EventQueue;
    import src.sim.SimState;
    import src.sim.Simulator;
    import src.state.CarWashState;
    import src.view.CarWashView;


    public class MainSim {

        public static void Simulator1() {
            CarWashView cwv = new CarWashView();

            CarWashState carWashState = new CarWashState(
                    2, // fastMachines
                    2.0, // fastLower
                    4.0, // fastUpper
                    2, // slowMachines
                    3.0, // slowLower
                    5.0, // slowUpper
                    5, // parkingLotSize
                    0.0, // initial time
                    0.5, // lambda
                    123456789L, // seed
                    cwv// SimView
            );

            Simulator sim = new Simulator(carWashState, cwv);

            sim.run();




        }

        public static void main(String[] args) {

            Simulator1();


        }

    }
