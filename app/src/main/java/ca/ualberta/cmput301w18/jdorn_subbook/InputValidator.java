package ca.ualberta.cmput301w18.jdorn_subbook;


import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

public abstract class InputValidator {
    private class ValidatorTextWatcher implements TextWatcher {
        private InputValidator validator;
        
        public ValidatorTextWatcher(InputValidator validator) {
            this.validator = validator;
        }
        
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) { }
    
        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) { }
    
        @Override
        public void afterTextChanged(Editable editable) {
            this.validator.validate();
        }
    }
    
    private EditText editText;
    private boolean isValid;
    
    public InputValidator(EditText editText) {
        this.editText = editText;
        this.editText.addTextChangedListener(new ValidatorTextWatcher(this));
    }
    
    public final EditText getEditText() {
        return this.editText;
    }
    
    public final boolean isValid() {
        return this.isValid;
    }
    
    public final void validate() {
        String result = this.validateEditable(this.editText.getText());
        this.editText.setError(result);
        this.isValid = (result == null);
    }
    
    protected abstract String validateEditable(Editable editable);
}
