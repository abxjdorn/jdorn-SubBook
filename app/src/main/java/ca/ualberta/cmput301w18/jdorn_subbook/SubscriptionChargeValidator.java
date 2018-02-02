package ca.ualberta.cmput301w18.jdorn_subbook;


import android.text.Editable;
import android.widget.EditText;

/**
 * Validator for subscription charge field.
 *
 * @see InputValidator
 */
class SubscriptionChargeValidator extends InputValidator {
    /** Converter to use for validation. */
    private SubscriptionChargeConverter converter;
    
    /**
     * Creates a validator that validates the editText as a subscription charge.
     *
     * @param editText field to validate
     */
    SubscriptionChargeValidator(EditText editText) {
        super(editText);
        this.converter = new SubscriptionChargeConverter();
    }
    
    /**
     * Implementation of the validation of a subscription charge.
     * Should accept all values that the SubscriptionChargeConverter
     * can successfully convert, or at least no values that it cannot.
     *
     * @param editable Content of the field to validate.
     * @return a String containing an error message if invalid, or null if valid.
     */
    @Override
    protected String validateEditable(Editable editable) {
        if (editable.length() == 0) {
            return "Charge is required";
        }
        else if (this.converter.setString(editable.toString())
                != SubscriptionChargeConverter.VALID) {
            return "Invalid charge format";
        }
        return null;
    }
}
