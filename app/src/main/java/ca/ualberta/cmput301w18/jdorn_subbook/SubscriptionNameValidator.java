package ca.ualberta.cmput301w18.jdorn_subbook;


import android.text.Editable;
import android.widget.EditText;

public class SubscriptionNameValidator extends InputValidator {
    public SubscriptionNameValidator(EditText editText) {
        super(editText);
    }
    
    @Override
    protected String validateEditable(Editable editable) {
        if (editable.length() < 1) {
            return "Name is required";
        }
        else if (editable.length() > 20) {
            return "Name is too long";
        }
        return null;
    }
}
