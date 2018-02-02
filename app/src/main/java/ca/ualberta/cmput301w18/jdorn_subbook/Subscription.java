package ca.ualberta.cmput301w18.jdorn_subbook;

import java.io.Serializable;
import java.util.Date;

/**
 * Entity class representing a single subscription.
 * A Subscription has a name, date, charge, and comment,
 * all of which are mutable.
 */
class Subscription implements Serializable {
    /** Name of the subscription. Limited to 20 characters; cannot be empty. */
    private String name;
    
    /** Date the subscription started. Cannot be empty. */
    private Date date;
    
    /** Monthly charge of the subscription. Stored as an integer number of cents
     * (so eg. $1.23 is the integer 123). Cannot be negative.
     */
    private Integer charge;
    
    /** Comment attached to the subscription. Limited to 30 characters. Can be empty. */
    private String comment;
    
    /**
     * Creates a new Subscription with the specified parameters. Parameters are checked
     * for validity, and an exception will be thrown if they are invalid.
     *
     * @param name Name of the subscription. Limited to 20 characters; cannot be empty or null.
     * @param date Date the subscription started. Cannot be null.
     * @param charge Monthly charge of the subscription. Represents a dollar amount as an integer
     *               number of cents. Cannot be negative or null (but can be zero).
     * @param comment Comment attached to the subscription. Limited to 30 characters; cannot be
     *                null but can be empty.
     * @throws InvalidSubscriptionParameterException if any parameter is invalid.
     */
    Subscription(String name, Date date, Integer charge, String comment)
            throws InvalidSubscriptionParameterException {
        // Calling the setter methods invokes validity checking without having
        // to duplicate it.
        this.setName(name);
        this.setDate(date);
        this.setCharge(charge);
        this.setComment(comment);
    }
    
    /** Returns the name of the subscription. */
    String getName() {
        return this.name;
    }
    
    /** Returns the date of the subscription. */
    Date getDate() {
        return this.date;
    }
    
    /** Returns the charge of the subscription, as an integer number of cents. */
    Integer getCharge() {
        return this.charge;
    }
    
    /** Returns the comment attached to the subscription (which may be empty). */
    String getComment() {
        return this.comment;
    }
    
    /**
     * Sets the name of the subscription.
     *
     * @param name Name of the subscription. Cannot be empty, null, or longer than 20 characters.
     */
    void setName(String name) throws InvalidSubscriptionParameterException {
        if (name == null || name.length() < 1 || name.length() > 20) {
            throw new InvalidSubscriptionParameterException();
        }
        this.name = name;
    }
    
    /**
     * Sets the date of the subscription.
     *
     * @param date Date of the subscription. Cannot be null.
     */
    void setDate(Date date) throws InvalidSubscriptionParameterException {
        if (date == null) {
            throw new InvalidSubscriptionParameterException();
        }
        this.date = date;
    }
    
    /**
     * Sets the monthly charge of the subscription, as an integer number of cents.
     *
     * @param charge Monthly charge of the subscription. Cannot be negative or null.
     */
    void setCharge(Integer charge) throws InvalidSubscriptionParameterException {
        if (charge == null || charge < 0) {
            throw new InvalidSubscriptionParameterException();
        }
        this.charge = charge;
    }
    
    /**
     * Sets the comment attached to the subscription.
     *
     * @param comment Comment of the subscription. Cannot be null or longer than 30 characters.
     *                May be empty.
     */
    void setComment(String comment) throws InvalidSubscriptionParameterException {
        if (comment == null || comment.length() > 30) {
            throw new InvalidSubscriptionParameterException();
        }
        this.comment = comment;
    }
}
