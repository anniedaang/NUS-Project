class Statistics {
    private final double totalWaitTime;
    private final int numOfServed;
    private final int numOfLeft;

    Statistics(double totalWaitTime, int numOfServed, int numOfLeft) {
        this.totalWaitTime = totalWaitTime;
        this.numOfServed = numOfServed;
        this.numOfLeft = numOfLeft;
    }

    Statistics() {
        this.totalWaitTime = 0.0;
        this.numOfServed = 0;
        this.numOfLeft = 0;
    }

    Statistics incrementWaitTime(double timeOfServe, Customer customer) {
        double x = timeOfServe - customer.getArrivalTime();
        double newtime = this.totalWaitTime + x;
        return new Statistics(newtime, this.numOfServed, this.numOfLeft);
    }

    Statistics incrementServed() {
        int n = this.numOfServed + 1;
        return new Statistics(this.totalWaitTime, n, this.numOfLeft);
    }

    Statistics incrementLeft() {
        int n = this.numOfLeft + 1;
        return new Statistics(this.totalWaitTime, this.numOfServed, n);
    }

    public String toString() {
        double average = this.totalWaitTime / this.numOfServed;
        return String.format("[%.3f %s %s]", 
                            average, this.numOfServed, this.numOfLeft);
    }
}