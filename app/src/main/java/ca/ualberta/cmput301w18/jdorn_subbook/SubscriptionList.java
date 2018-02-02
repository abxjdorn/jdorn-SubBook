package ca.ualberta.cmput301w18.jdorn_subbook;

import java.util.ArrayList;
import java.util.List;

/**
 * Entity class representing a set of subscriptions. Fundamentally
 * encapsulates a List of Subscriptions, allowing adding/deleting/
 * replacing Subscriptions, but also provides a specific method to
 * compute the total charge of all subscriptions.
 */
class SubscriptionList {
    /**
     * Encapsulated concrete list of subscriptions.
     */
    private ArrayList<Subscription> subscriptions;
    
    /**
     * Creates an empty SubscriptionList.
     */
    SubscriptionList() {
        this.subscriptions = new ArrayList<>();
    }
    
    /**
     * Appends a new subscription to the subscription list as per ArrayList.add.
     */
    void addSubscription(Subscription subscription) {
        this.subscriptions.add(subscription);
    }
    
    /**
     * Places the given subscription at the given index, overwriting the
     * subscription already at that position.
     *
     * @param index Index to replace. Should be a valid index in the encapsulated list.
     * @param subscription Subscription to place in the list. Should not be null.
     */
    void replaceSubscriptionAt(int index, Subscription subscription) {
        this.subscriptions.set(index, subscription);
    }
    
    /**
     * Removes the subscription with the given index from the list.
     *
     * @param index Index to remove. Should be a valid index in the encapsulated list.
     */
    void deleteSubscriptionAt(int index) {
        this.subscriptions.remove(index);
    }
    
    /**
     * Returns the concrete list of subscriptions stored in the object.
     *
     * @return list of subscriptions encapsulated by the SubscriptionList.
     */
    List<Subscription> getSubscriptions() {
        return subscriptions;
    }
    
    /**
     * Returns the subscription at the given index in the list.
     *
     * @param index Index to get. Should be a valid index in the encapsulated list.
     * @return subscription at the specified index.
     */
    Subscription getSubscriptionAt(int index) {
        return this.subscriptions.get(index);
    }
    
    /**
     * Calculates the total charge of all the subscriptions in the list.
     *
     * @return sum of charges of all Subscriptions in the list.
     */
    Integer getTotalCharge() {
        int total = 0;
        for (Subscription s: subscriptions) {
            total += s.getCharge();
        }
        return total;
    }
}
