import java.util.Optional;
import java.util.function.Supplier;

abstract class Event {
    protected final Customer customer;

    Event(Customer customer) {
        this.customer = customer;
    }

    abstract Pair<Optional<Event>, ServerManager> nextEvent(ServerManager serverM);

    abstract double timeOfEvent();

    abstract int priority();

    Statistics updateStats(Statistics stats) {
        return stats;
    }

    @Override
    public String toString() {
        return String.format("Event");
    }
}