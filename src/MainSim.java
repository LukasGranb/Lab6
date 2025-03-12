    package src;
    import src.events.CarArrives;
    import src.sim.EventQueue;
    import src.sim.SimState;
    import src.state.CarWashState;
    import src.view.CarWashView;


    public class MainSim {

        public static void main(String[] args) {
            CarWashView view = new CarWashView();
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
                    view // SimView
            );

            // Notify the start of simulation
            carWashState.notifySimulationStart();

            EventQueue<CarWashState> queue = new EventQueue<>(carWashState);

            // Generate car arrival events
            queue.generateEvents(eventTime -> new CarArrives(carWashState, queue, eventTime), 0.5, 15);

            // Run simulation
            while(carWashState.getState() == SimState.State.RUN) {
                queue.nextEvent();
            }

            // Notify the end of simulation
            carWashState.notifySimulationStop();
        }

    }
