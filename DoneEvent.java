import java.util.Optional;
import java.util.function.Supplier;

class DoneEvent extends Event {
    private static final int priority = 6;
    private final Server server;
    private final double currentTime;

    DoneEvent(Customer customer, Server server, double currentTime) {
        super(customer);
        this.server = server;
        this.currentTime = currentTime;
    }

    @Override
    Pair<Optional<Event>, ServerManager> nextEvent(ServerManager serverM) {
        Server currentServer = serverM.getServer(server.getID());

        // check to see if server needs break
        Double breaktime = serverM.getBreak();
        currentServer = currentServer.updateServerTime(currentTime, breaktime);
        serverM = serverM.updateServerList(currentServer);
        return new Pair<Optional<Event>, ServerManager>(Optional.empty(), serverM);
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
        return String.format("%.3f %s done serving by %s\n", 
                            this.timeOfEvent(), customer, server);
    }
}
