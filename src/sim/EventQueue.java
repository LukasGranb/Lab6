package src.sim;

import src.random.ExponentialRandomStream;

public class EventQueue {

    private SimState state;
    private SortedSequence sorter;

    public EventQueue(SimState state) {
        this.state = state;
        this.sorter = new SortedSequence();
    }

    public void nextEvent() {
        Event e = this.sorter.get();

        if(e != null) {
            e.execute();
        }
        else {
            state.setState(SimState.State.STOP);
        }
    }

    public void addEvent(Event e) {
        this.sorter.add(e);
    }

    private void generateEvents(double lambda, double eventSize) {

        ExponentialRandomStream stream = new ExponentialRandomStream(lambda);

        for(int i = 0; i < eventSize; i++) {

            this.addEvent(new Event(this.state, this, stream.next()) {
            });
        }
    }

}
