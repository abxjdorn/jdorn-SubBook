package ca.ualberta.cmput301w18.jdorn_subbook;


import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * FieldConverter for subscription charge.
 * Accepted text format is [digits].[digit][digit]
 * (must have two decimal places, and no '$' prefix)
 *
 * @see FieldConverter
 */
public class SubscriptionChargeConverter implements FieldConverter<Integer> {
    /** Target object: integer representing charge as a number of cents */
    private Integer charge;
    
    /** Target string */
    private String string;
    
    /** Regular expression matching valid inputs ([digits].[digit][digit]) */
    private static Pattern pattern = Pattern.compile("^(\\d+)\\.(\\d\\d)$");
    
    @Override
    public Integer getObject() {
        return this.charge;
    }
    
    @Override
    public String getString() {
        return this.string;
    }
    
    @Override
    public void setObject(Integer charge) {
        this.charge = charge;
        
        // Convert charge to string
        // (this implementation probably isn't very localizable)
        this.string = String.format(Locale.US, "%01d.%02d",
                this.charge/100, this.charge%100);
    }
    
    @Override
    public int setString(String string) {
        this.string = string;
        
        // Convert string to charge: match appropriate pattern
        Matcher matcher = pattern.matcher(string);
        if (!matcher.matches()) {
            this.string = null;
            this.charge = null;
            return INVALID_FORMAT;
        }
        
        // Extract dollars and cents ranges from string
        int dollars = Integer.valueOf(matcher.group(1));
        int cents = Integer.valueOf(matcher.group(2));
        
        // Combine to get number of cents --> total charge
        this.charge = dollars * 100 + cents;
        
        return VALID;
    }
}
