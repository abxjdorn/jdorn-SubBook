package ca.ualberta.cmput301w18.jdorn_subbook;


import android.text.Editable;
import android.widget.EditText;

public class SubscriptionDateValidator extends InputValidator {
    private SubscriptionDateConverter converter;
    
    public SubscriptionDateValidator(EditText editText) {
        super(editText);
        this.converter = new SubscriptionDateConverter();
    }
    
    @Override
    protected String validateEditable(Editable editable) {
        if (editable.length() == 0) {
            return "Date is required";
        }
        else if (this.converter.setString(editable.toString()) != SubscriptionDateConverter.VALID) {
            return "Invalid date format";
        }
        return null;
    }
}
