    package src.sim;

    import src.random.ExponentialRandomStream;
    import java.util.function.Function;

    /**
     * @author Amund Knutsen, Lukas Granberg
     * An EventQueue that keeps makes sure that the next events gets executed in order
     * @param <S> SimState
     */
    public class EventQueue<S extends SimState> {

        private SimState state;
        private SortedSequence sorter;
        private double timeLimit;

        /**
         *
         * @param state SimState
         * @param timeLimit Time limit for when the simulator is supposed to stop
         */
        public EventQueue(SimState state, double timeLimit) {
            this.state = state;
            this.sorter = new SortedSequence();
            this.timeLimit = timeLimit;
        }

        /**
         * Executes next event from the EventQueue
         */
        public void nextEvent() {
            Event e = this.sorter.get();

            if(e != null) {

                this.state.advanceTime(e.getTime());
                e.execute();

            }
        }

        /**
         * Adds a new event to the EventQueue using an internal SortedSequence
         * @param e Event
         */
        public void addEvent(Event e) {
            this.sorter.add(e);
        }

        // If you want to generate a set of future events, unused for now.
        public void generateEvents(Function<Double, Event<S>> eventSupplier, double lambda) {

            ExponentialRandomStream stream = new ExponentialRandomStream(lambda);

            if(state.getTime() >= timeLimit) {
                double eventTime = stream.next();
                Event<S> e = eventSupplier.apply(eventTime);
                this.addEvent(e);

            }
        }

    }
