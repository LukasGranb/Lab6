    package src.sim;

    import src.random.ExponentialRandomStream;
    import java.util.function.Function;
    public class EventQueue<S extends SimState> {

        private SimState state;
        private SortedSequence sorter;
        private double timeLimit;
        private double lambda;
        ExponentialRandomStream stream;

        public EventQueue(SimState state, double timeLimit) {
            this.state = state;
            this.sorter = new SortedSequence();
            this.timeLimit = timeLimit;
            this.lambda = state.getLambda();
            ExponentialRandomStream stream = new ExponentialRandomStream(lambda);
        }

        public void nextEvent() {
            Event e = this.sorter.get();

            if(e != null) {
                if(e.getTime() > timeLimit) {
                    state.setState(SimState.State.STOP);
                }
                e.execute();
                this.state.advanceTime(e.getTime());
            }
        }

        public void addEvent(Event e) {
            this.sorter.add(e);
        }

        // If you want to generate a set of future events, unused for now.
        public void generateEvents(Function<Double, Event<S>> eventSupplier, double lambda) {

            if(state.getTime() >= timeLimit) {
                double eventTime = stream.next();
                Event<S> e = eventSupplier.apply(eventTime);
                this.addEvent(e);

            }
        }

    }
