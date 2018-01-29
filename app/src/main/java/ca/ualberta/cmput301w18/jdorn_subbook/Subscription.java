package ca.ualberta.cmput301w18.jdorn_subbook;

import android.text.format.DateFormat;

import java.io.Serializable;
import java.util.Date;
import java.util.Locale;

/**
 * Entity class representing a single subscription.
 * A Subscription has a name, date, charge, and comment,
 * all of which are mutable.
 */
public class Subscription implements Serializable {
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
    
    public Subscription(String name, Date date, Integer charge, String comment)
            throws InvalidSubscriptionParameterException {
        // Calling the setter methods invokes validity checking without having
        // to duplicate it.
        this.setName(name);
        this.setDate(date);
        this.setCharge(charge);
        this.setComment(comment);
    }
    
    /** Returns the name of the subscription. */
    public String getName() {
        return this.name;
    }
    
    /** Returns the date of the subscription. */
    public Date getDate() {
        return this.date;
    }
    
    /** Returns the date of the subscription formatted as a string. */
    public String getDateAsString() {
        return DateFormat.format("yyyy-MM-dd", this.date).toString();
    }
    
    /** Returns the charge of the subscription, as an integer number of cents. */
    public Integer getCharge() {
        return this.charge;
    }
    
    /** Returns a string representation of the charge of the subscription.
     * (This implementation probably isn't readily localizable.)
     */
    public String getChargeAsString() {
        return String.format(Locale.getDefault(),
                "$%d.%d", this.charge/100, this.charge%100);
    }
    
    /** Returns the comment attached to the subscription (which may be empty). */
    public String getComment() {
        return this.comment;
    }
    
    /** Sets the name of the subscription. Name must have from 1 to 20 characters (inclusive). */
    public void setName(String name) throws InvalidSubscriptionParameterException {
        if (name.length() < 1 || name.length() > 20) {
            throw new InvalidSubscriptionParameterException();
        }
        this.name = name;
    }
    
    /** Sets the date of the description. (Any valid date can be set.) */
    public void setDate(Date date) {
        this.date = date;
    }
    
    /** Sets the charge of the description, as an integer number of cents.
     * Charge must not be negative.
     */
    public void setCharge(Integer charge) throws InvalidSubscriptionParameterException {
        if (charge < 0) {
            throw new InvalidSubscriptionParameterException();
        }
        this.charge = charge;
    }
    
    /** Sets the comment of the subscription. Comment must have 30 or fewer characters,
     * and can be empty.
     */
    public void setComment(String comment) throws InvalidSubscriptionParameterException {
        if (comment.length() > 30) {
            throw new InvalidSubscriptionParameterException();
        }
        this.comment = comment;
    }
}
