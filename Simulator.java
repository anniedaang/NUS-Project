import java.util.Optional;
import java.util.function.Supplier;

class Simulator {
    private final int numOfServers;
    private final int qmax;
    private final ImList<Pair<Double,Supplier<Double>>> inputTimes;
    private final Supplier<Double> restTimes;

    Simulator(int numOfServers, int qmax,
            ImList<Pair<Double,Supplier<Double>>> inputTimes, 
            Supplier<Double> restTimes) {
        this.numOfServers = numOfServers;
        this.qmax = qmax;
        this.inputTimes = inputTimes;
        this.restTimes = restTimes;
    }

    private PQ<Event> arrivalevents() {
        //create all the arrival events and add to PQ
        int customerID = 1;
        PQ<Event> eventpq = new PQ<Event>(new EventComp());

        for (int i = 0; i < inputTimes.size(); i++) {
            Pair<Double,Supplier<Double>> info = inputTimes.get(i);
            Customer newCustomer = new Customer(customerID, info.first(), info.second());
            
            eventpq = eventpq.add(new ArrivalEvent(newCustomer));
            customerID += 1;
        }
        return eventpq;
    }

    public String simulate() {
        double currentTime = 0.000;
        int nrOfcustomers = 0;
        PQ<Event> idlingEvents = this.arrivalevents();
        String readyToPrint = "";

        //create a list of servers
        ImList<Server> serverList = new ImList<Server>();
        for (int i = 1; i <= this.numOfServers; i++) {
            serverList = serverList.add(new Server(i, qmax, currentTime, nrOfcustomers));
        }

        //create a server manager
        ServerManager serverM = new ServerManager(serverList, this.restTimes);

        // create statistics class
        Statistics stat = new Statistics();

        // repeat until idling event list is empty
        while (idlingEvents.isEmpty() == false) {
            //poll() highest priority event in Q
            Pair<Event, PQ<Event>> pr = idlingEvents.poll();
            idlingEvents = pr.second(); 
            Event currentEvent = pr.first();

            //update the statistics
            stat = currentEvent.updateStats(stat);

            // adds the current event to the String
            readyToPrint = readyToPrint + currentEvent.toString();
            
            // create nextEvent() which also returns updated ServerManager class
            Pair<Optional<Event>, ServerManager> nextEventPair = currentEvent.nextEvent(serverM);
            Optional<Event> op = nextEventPair.first();
            final PQ<Event> stuff = idlingEvents;
            idlingEvents = op.map(x -> stuff.add(x)).orElse(stuff);
            serverM = nextEventPair.second();
        }

        return readyToPrint + stat.toString();
    }
}