package ca.ualberta.cmput301w18.jdorn_subbook;


import java.util.Locale;

/**
 * Represents an amount of currency in the limited representation supported
 * by a Subscription. A charge can be accessed both as an integer number of
 * cents and as a string of the form [digits...] '.' [digit] [digit].
 */
public class SubscriptionCharge {
    private int amount;
    
    public SubscriptionCharge(int amount) {
        this.amount = amount;
    }
    
    public SubscriptionCharge(String string) throws InvalidSubscriptionParameterException {
        int decimal_index = string.lastIndexOf(".");
        String dollarString = string.substring(0, decimal_index);
        String centString = string.substring(decimal_index+1, string.length());
        
        if (dollarString.length() < 1 || centString.length() != 2) {
            throw new InvalidSubscriptionParameterException();
        }
        
        this.amount = Integer.valueOf(dollarString) * 100 + Integer.valueOf(centString);
    }
    
    /**
     * Formats the amount as a fractional number of dollars.
     */
    public String toString() {
        return String.format(Locale.US, "%d.%02d", this.amount/100, this.amount%100);
    }
}
