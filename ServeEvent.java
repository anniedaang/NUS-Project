import java.util.Optional;
import java.util.function.Supplier;

class ServeEvent extends Event {
    private static final int priority = 7;
    private final Server server;
    private final double currentTime;
    
    ServeEvent(Customer customer, Server server, double currentTime) {
        super(customer);
        this.server = server;
        this.currentTime = currentTime;
    }

    @Override
    Pair<Optional<Event>, ServerManager> nextEvent(ServerManager serverM) {
        Server currentServer = serverM.getServer(server.getID());

        //update server's next serving time
        double start = this.timeOfEvent();
        Double end = customer.getServiceTime().get();
        currentServer = currentServer.updateServerTime(start, end);
        currentServer = currentServer.deQ();
        
        // update server's queue
        serverM = serverM.updateServerList(currentServer);

        DoneEvent nextE = new DoneEvent(customer, currentServer, 
                                        currentServer.getNextServingTime());
        return new Pair<Optional<Event>, ServerManager>(Optional.ofNullable(nextE), serverM);
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
    Statistics updateStats(Statistics stats) {
        stats = stats.incrementServed();
        stats = stats.incrementWaitTime(this.timeOfEvent(), this.customer);
        return stats;
    }

    @Override
    public String toString() {
        return String.format("%.3f %s serves by %s\n", 
                            this.timeOfEvent(), customer, this.server);
    }
}