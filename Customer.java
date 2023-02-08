import java.util.function.Supplier;

class Customer {
    private final int customerID;
    private final double arrivalTime;
    private final Supplier<Double> serviceTime;


    Customer(int customerID, double arrivalTime, Supplier<Double> serviceTime) {
        this.customerID = customerID;
        this.arrivalTime = arrivalTime;
        this.serviceTime = serviceTime;
    }

    int getCustomerID() {
        return this.customerID;
    }

    double getArrivalTime() {
        return this.arrivalTime;
    }

    Supplier<Double> getServiceTime() {
        return this.serviceTime;
    }

    @Override
    public String toString() {
        return String.format("%s", this.customerID);
    }
}