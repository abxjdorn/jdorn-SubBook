package ca.ualberta.cmput301w18.jdorn_subbook;


import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

/**
 * Watches an EditText and validates its contents as they change.
 */
public abstract class InputValidator {
    /**
     * Simple TextWatcher that calls an InputValidator's validate method
     * when the text of the watched field changes.
     */
    private class ValidatorTextWatcher implements TextWatcher {
        /**
         * InputValidator to notify of changes
         */
        private final InputValidator validator;
    
        /**
         * Creates a ValidatorTextWatcher for the specified InputValidator.
         * @param validator validator to notify
         */
        private ValidatorTextWatcher(InputValidator validator) {
            this.validator = validator;
        }
    
        /**
         * Method that ignores beforeTextChanged events (implementing TextWatcher)
         */
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) { }
    
        /**
         * Method that ignores onTextChanged events (implementing TextWatcher)
         */
        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) { }
    
        /**
         * Notifies the target InputValidator on afterTextChanged events.
         * @param editable ignored here (from TextWatcher)
         */
        @Override
        public void afterTextChanged(Editable editable) {
            this.validator.validate();
        }
    }
    
    /** Target text field. */
    private EditText editText;
    
    /** Whether the last validation check succeeded or failed. */
    private boolean isValid;
    
    /**
     * Creates an InputValidator that watches the specified text field.
     *
     * @param editText field to watch
     */
    InputValidator(EditText editText) {
        this.editText = editText;
        this.editText.addTextChangedListener(new ValidatorTextWatcher(this));
    }
    
    /** Gets the editText that this validator is watching. */
    final EditText getEditText() {
        return this.editText;
    }
    
    /**
     * Returns whether the text field was valid last time this validator looked at it
     * (which is probably the last time the field was edited).
     */
    final boolean isValid() {
        return this.isValid;
    }
    
    /**
     * Validates the text field. This will update the field's error message and
     * set the validator's state so that calling isValid() returns whether this
     * check succeeded. This is automatically called on afterTextChanged when the
     * validator is set up normally.
     */
    final void validate() {
        String result = this.validateEditable(this.editText.getText());
        this.editText.setError(result);
        this.isValid = (result == null);
    }
    
    /**
     * Implementation of validity checking.
     *
     * @param editable Content of the field to validate.
     * @return a String containing an error message if invalid, or null if valid.
     */
    protected abstract String validateEditable(Editable editable);
}
