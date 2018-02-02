package ca.ualberta.cmput301w18.jdorn_subbook;


import android.text.Editable;
import android.widget.EditText;

/**
 * Validator for subscription date field.
 *
 * @see InputValidator
 */
class SubscriptionDateValidator extends InputValidator {
    /** Converter to use for validation. */
    private SubscriptionDateConverter converter;
    
    /**
     * Creates a validator that validates the editText as a subscription date.
     *
     * @param editText field to validate
     */
    SubscriptionDateValidator(EditText editText) {
        super(editText);
        this.converter = new SubscriptionDateConverter();
    }
    
    /**
     * Implementation of the validation of a subscription date.
     * Should accept all values that the SubscriptionDateConverter
     * can successfully convert, or at least no values that it cannot.
     *
     * @param editable Content of the field to validate.
     * @return a String containing an error message if invalid, or null if valid.
     */
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
