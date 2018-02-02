package ca.ualberta.cmput301w18.jdorn_subbook;


import android.text.Editable;
import android.widget.EditText;

/**
 * Validator for subscription comment field.
 *
 * @see InputValidator
 */
class SubscriptionCommentValidator extends InputValidator {
    /**
     * Creates a validator that validates the editText as a subscription comment.
     *
     * @param editText field to validate
     */
    SubscriptionCommentValidator(EditText editText) {
        super(editText);
    }
    
    /**
     * Implementation of the validation of a subscription comment. Only fails
     * if the comment is too long (greater than 30 characters).
     *
     * @param editable Content of the field to validate.
     * @return a String containing an error message if invalid, or null if valid.
     */
    @Override
    protected String validateEditable(Editable editable) {
        if (editable.length() > 30) {
            return "Comment too long";
        }
        return null;
    }
}
