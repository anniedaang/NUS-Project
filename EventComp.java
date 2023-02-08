import java.util.Comparator;

class EventComp implements Comparator<Event> {
    private static final double EPSILON = 1e-15;

    // sort event by their time and then priority
    @Override
    public int compare(Event event1, Event event2) {
        if (event1.timeOfEvent() < event2.timeOfEvent()) {
            return -1;
        } else if (Math.abs(event1.timeOfEvent() - event2.timeOfEvent()) < EPSILON) {
            if (event1.customer.getCustomerID() == event2.customer.getCustomerID()) {
                return event1.priority() - event2.priority();
            } else if (event1.customer.getCustomerID() < event2.customer.getCustomerID()) {
                return -1;
            } else {
                return 1;
            }
        } else {
            return 1;
        }
    }
}
