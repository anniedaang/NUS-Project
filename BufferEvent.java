import java.util.Optional;
import java.util.function.Supplier;

class BufferEvent extends Event {
    private static final int priority = 4;
    private final Server server;
    private final double currentTime;

    BufferEvent(Customer customer, Server server, double currentTime) {
        super(customer);
        this.server = server;
        this.currentTime = currentTime;
    }

    @Override
    Pair<Optional<Event>, ServerManager> nextEvent(ServerManager serverM) {
        Server currentServer = serverM.getServer(server.getID());
        
        // check server's next available time
        // available -> ServeEvent : unavailable -> new BufferEvent

        double serversTime = currentServer.getNextServingTime();
        if (this.currentTime < serversTime) {
            BufferEvent nextE = new BufferEvent(customer, currentServer, serversTime);
            return new Pair<Optional<Event>, ServerManager>(Optional.ofNullable(nextE), serverM);
        } else {
            ServeEvent nextE = new ServeEvent(customer, currentServer, serversTime);
            return new Pair<Optional<Event>, ServerManager>(Optional.ofNullable(nextE), serverM);
        }
    }

    @Override
    public int priority() {
        return priority;
    }

    @Override
    double timeOfEvent() {
        return this.currentTime;
    }

    @Override
    public String toString() {
        return String.format("");
    }
}