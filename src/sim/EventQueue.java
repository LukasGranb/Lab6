    package src.sim;

    import src.random.ExponentialRandomStream;
    import java.util.function.Function;
    public class EventQueue<S extends SimState> {

        private S state;
        private SortedSequence sorter;

        public EventQueue(S state) {
            this.state = state;
            this.sorter = new SortedSequence();
        }

        public void nextEvent() {
            Event e = this.sorter.get();

            if(e != null) {
                e.execute();
                this.state.advanceTime(e.getTime());
            }
            else {
                state.setState(SimState.State.STOP);
            }
        }

        public void addEvent(Event e) {
            this.sorter.add(e);
        }

        public void generateEvents(Function<Double, Event<S>> eventSupplier, double lambda, double eventSize) {

            ExponentialRandomStream stream = new ExponentialRandomStream(lambda);

            for(int i = 0; i < eventSize; i++) {
                double eventTime = stream.next();
                Event<S> e = eventSupplier.apply(eventTime);
                this.addEvent(e);

            }
        }

    }
