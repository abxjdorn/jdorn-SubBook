package ca.ualberta.cmput301w18.jdorn_subbook;


import android.text.Editable;
import android.widget.EditText;

/**
 * Validator for subscription name field.
 *
 * @see InputValidator
 */
class SubscriptionNameValidator extends InputValidator {
    /**
     * Creates a validator that validates the editText as a subscription name.
     *
     * @param editText field to validate
     */
    SubscriptionNameValidator(EditText editText) {
        super(editText);
    }
    
    /**
     * Implementation of the validation of a subscription name. Fails if the name
     * is too long (greater than 20 characters) or if it is empty.
     *
     * @param editable Content of the field to validate.
     * @return a String containing an error message if invalid, or null if valid.
     */
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
