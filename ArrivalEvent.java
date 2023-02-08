import java.util.Optional;
import java.util.function.Supplier;

class ArrivalEvent extends Event {
    private static final int priority = 5;

    ArrivalEvent(Customer customer) {
        super(customer);
    }

    @Override
    Pair<Optional<Event>, ServerManager> nextEvent(ServerManager serverM) {
        Pair<Server, Boolean> check = serverM.getFreeServer(customer);

        if (check.second() == false) {
            LeaveEvent nextE = new LeaveEvent(customer);
            return new Pair<Optional<Event>, ServerManager>(Optional.ofNullable(nextE), serverM);
        } else {
            Server server = check.first();
            if (this.timeOfEvent() < server.getNextServingTime()) {
                server = server.enQ();
                serverM = serverM.updateServerList(server);
                WaitEvent nextE = new WaitEvent(customer, server);
                return new Pair<Optional<Event>, ServerManager>(Optional.ofNullable(nextE),
                                                                 serverM);
            } else {
                if (server.getnumOfCustomers() > 1) {
                    server = server.enQ();
                    serverM = serverM.updateServerList(server);
                    WaitEvent nextE = new WaitEvent(customer, server);
                    return new Pair<Optional<Event>, ServerManager>(Optional.ofNullable(nextE),
                                                                     serverM);
                } else {
                    server = server.enQ();
                    serverM = serverM.updateServerList(server);
                    ServeEvent nextE = new ServeEvent(customer, server, this.timeOfEvent());
                    return new Pair<Optional<Event>, ServerManager>(Optional.ofNullable(nextE),
                                                                     serverM);
                }
            }
        }
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
        return String.format("%.3f %s arrives\n", this.timeOfEvent(), customer);
    }
}
