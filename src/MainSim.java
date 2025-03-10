package src;
import src.events.CarArrives;
import src.sim.Event;
import src.sim.EventQueue;
import src.state.CarWashState;
import src.view.CarWashView;


public class MainSim {

    public static void main(String[] args) {
        CarWashView view = new CarWashView();
        CarWashState carWashState = new CarWashState(2, 2, 10, 0, view );
        EventQueue<CarWashState> queue = new EventQueue<>(carWashState);

        // Use Function<Double, Event<CarWashState>> since eventTime is passed dynamically
        queue.generateEvents(eventTime -> new CarArrives(carWashState, queue, eventTime), 0.5, 10);


        for(int i = 0; i < 100; i++) {
            queue.nextEvent();
        }





    }

}
