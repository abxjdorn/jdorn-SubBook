package ca.ualberta.cmput301w18.jdorn_subbook;


import android.text.Editable;
import android.widget.EditText;

public class SubscriptionChargeValidator extends InputValidator {
    private SubscriptionChargeConverter converter;
    
    public SubscriptionChargeValidator(EditText editText) {
        super(editText);
        this.converter = new SubscriptionChargeConverter();
    }
    
    @Override
    protected String validateEditable(Editable editable) {
        if (editable.length() == 0) {
            return "Charge is required";
        }
        else if (this.converter.setString(editable.toString()) != SubscriptionChargeConverter.VALID) {
            return "Invalid charge format";
        }
        return null;
    }
}
