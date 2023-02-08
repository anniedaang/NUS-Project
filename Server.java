import javax.management.openmbean.OpenMBeanAttributeInfo;

class Server {
    private final int nameID;
    private final int maxQ;
    private final double nextServingTime;
    private final int numOfCustomers;

    Server(int nameID, int maxQ, double nextServingTime, int numOfCustomers) {
        this.nameID = nameID;
        this.maxQ = maxQ;
        this.nextServingTime = nextServingTime;
        this.numOfCustomers = numOfCustomers;
    }

    boolean fullQ() {
        if (numOfCustomers == maxQ) {
            return true;
        } else {
            return false;
        }
    }

    int getnumOfCustomers() {
        return this.numOfCustomers;
    }

    int getID() {
        return this.nameID;
    }

    int getMaxQ() {
        return this.maxQ;
    }

    double getNextServingTime() {
        return this.nextServingTime;
    }

    Server enQ() {
        int newnr = this.numOfCustomers + 1;
        return new Server(this.nameID, this.maxQ, this.nextServingTime, newnr);
    }

    Server deQ() {
        if (this.numOfCustomers == 0) {
            return this;
        } else {
            int newnr = this.numOfCustomers - 1;
            return new Server(this.nameID, this.maxQ, this.nextServingTime, newnr);
        }
    }

    Server updateServerTime(double newStart, Double newEnd) {
        double newTime = newStart + newEnd;
        return new Server(this.nameID, this.maxQ, newTime, this.numOfCustomers);
    }

    public String toString() {
        return String.format("%d", this.nameID);
    }
}