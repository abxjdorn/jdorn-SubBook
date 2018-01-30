package ca.ualberta.cmput301w18.jdorn_subbook;


import android.text.format.DateFormat;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class SubscriptionDateConverter implements FieldConverter<Date> {
    private Date date;
    private String string;
    
    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
    
    @Override
    public Date getObject() {
        return this.date;
    }
    
    @Override
    public String getString() {
        return this.string;
    }
    
    @Override
    public void setObject(Date date) {
        this.date = date;
        
        // Convert date to string
        this.string = dateFormat.format(date);
    }
    
    @Override
    public int setString(String string) {
        this.string = string;
        
        // Convert string to date
        try {
            this.date = dateFormat.parse(string);
        } catch (ParseException e) {
            this.string = null;
            this.date = null;
            return INVALID_FORMAT;
        }
        
        return VALID;
    }
}
