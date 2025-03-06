package src.sim;

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
            e.execute(this.state, this);
        }
        else {
            state.setState(SimState.State.STOP);
        }
    }

    public void addEvent(Event e) {
        this.sorter.add(e);
    }

}
