package ca.ualberta.cmput301w18.jdorn_subbook;


import android.text.Editable;
import android.widget.EditText;

public class SubscriptionCommentValidator extends InputValidator {
    public SubscriptionCommentValidator(EditText editText) {
        super(editText);
    }
    
    @Override
    protected String validateEditable(Editable editable) {
        if (editable.length() > 30) {
            return "Comment too long";
        }
        return null;
    }
}
