import java.util.Optional;
import java.util.function.Supplier;

class ServerManager {
    private final ImList<Server> serverList;
    private final Supplier<Double> restTimes;


    ServerManager(ImList<Server> serverList, Supplier<Double> restTimes) {
        this.serverList = serverList;
        this.restTimes = restTimes;
    }

    int getNumOfcustomers() {
        int totalCustomers = 0;
        for (Server s : serverList) {
            totalCustomers = totalCustomers + s.getnumOfCustomers();
        }
        return totalCustomers;
    }

    Server getServer(int i) {
        int n = i - 1;
        Server server = this.serverList.get(n);
        return server;
    }

    int maxQ() {
        int serversMaxQ = getServer(0).getMaxQ();
        return serversMaxQ;
    }

    Pair<Server, Boolean> getFreeServer(Customer customer) {
        for (Server s : this.serverList) {
            if (customer.getArrivalTime() >= s.getNextServingTime()) {
                return new Pair<Server, Boolean>(s, true);
            } else {
                continue;
            }
        }
        return getFreeQ();
    }

    Pair<Server, Boolean> getFreeQ() {
        for (Server s : this.serverList) {
            if (s.fullQ() == false) {
                return new Pair<Server, Boolean>(s, true);
            }
        }
        Server dummyServer = new Server(0, 0, 0.0, 0);
        return new Pair<Server, Boolean>(dummyServer, false);
    }

    Double getBreak() {
        Double breaktime = this.restTimes.get();
        return breaktime;
    }

    ServerManager updateServerList(Server server) {
        int i = server.getID() - 1;
        ImList<Server> newserverList = this.serverList;
        newserverList = newserverList.set(i, server);
        return new ServerManager(newserverList, this.restTimes);
    }
}