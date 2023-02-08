import java.util.Optional;
import java.util.function.Supplier;

class LeaveEvent extends Event {
    private static final int priority = 2;

    LeaveEvent(Customer customer) {
        super(customer);
    }

    @Override
    Pair<Optional<Event>, ServerManager> nextEvent(ServerManager serverM) {
        return new Pair<Optional<Event>, ServerManager>(Optional.empty(), serverM);
    }

    @Override
    public int priority() {
        return priority;
    }

    @Override
    double timeOfEvent() {
        return customer.getArrivalTime();
    }

    @Override
    Statistics updateStats(Statistics stats) {
        stats = stats.incrementLeft();
        return stats;
    }

    @Override
    public String toString() {
        return String.format("%.3f %s leaves\n", this.timeOfEvent(), customer);
    }
}