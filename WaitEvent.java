import java.util.Optional;
import java.util.function.Supplier;

class WaitEvent extends Event {
    private static final int priority = 3;
    private final Server server;

    WaitEvent(Customer customer, Server server) {
        super(customer);
        this.server = server;
    }

    @Override
    Pair<Optional<Event>, ServerManager> nextEvent(ServerManager serverM) {
        Server currentServer = serverM.getServer(server.getID());
        BufferEvent nextE = new BufferEvent(customer, currentServer, this.timeOfEvent());
        return new Pair<Optional<Event>, ServerManager>(Optional.ofNullable(nextE), serverM);
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
    public String toString() {
        return String.format("%.3f %s waits at %s\n", timeOfEvent(), customer, this.server);
    }
}