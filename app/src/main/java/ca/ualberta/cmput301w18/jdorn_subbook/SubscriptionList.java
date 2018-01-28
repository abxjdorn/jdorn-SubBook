package ca.ualberta.cmput301w18.jdorn_subbook;

import java.util.ArrayList;
import java.util.List;

/**
 * Entity class representing a set of subscriptions.
 * Beyond simply holding a list of subscriptions, the class can
 * also provide the total of all subscription charges in the list.
 */
public class SubscriptionList {
    /** Concrete list of subscriptions constituting the object's contents. */
    private ArrayList<Subscription> subscriptions;
    
    public SubscriptionList() {
        this.subscriptions = new ArrayList<Subscription>();
    }
    
    /** Adds a new subscription to the subscription list. */
    public void addSubscription(Subscription subscription) {
        this.subscriptions.add(subscription);
    }
    
    /**
     * Removes the given subscription from the list. Returns true if the
     * subscription was originally in the list, as per ArrayList.remove.
     */
    public boolean deleteSubscription(Subscription subscription) {
        return this.subscriptions.remove(subscription);
    }
    
    /** Returns the concrete list of subscriptions stored in the object. */
    public List<Subscription> getSubscriptions() {
        return subscriptions;
    }
}
