package ca.ualberta.cmput301w18.jdorn_subbook;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.Date;

/**
 * Activity to view and edit a subscription. Provides editable
 * and validated fields for each subscription parameter (name,
 * charge, date, comment) and allows saving and deleting the
 * subscription being edited. Must be launched with an appropriate
 * Intent specifying the subscription to edit (see onCreate).
 */
public class EditSubscriptionActivity extends Activity {
    /**
     * Extra identifier for a serialized subscription to be
     * returned to the list.
     */
    public static final String EXTRA_SUBSCRIPTION_DATA =
            "ca.ualberta.cmput301w18.jdorn_subbook.SUBSCRIPTION_DATA";
    
    /**
     * Result identifier that is returned if the user deletes the
     * subscription from the edit activity.
     */
    public static final int RESULT_SUBSCRIPTION_DELETED = 2;
    
    /** Converter for parsing an entered charge. */
    private SubscriptionChargeConverter chargeConverter = new SubscriptionChargeConverter();
    
    /** Converter for parsing an entered date. */
    private SubscriptionDateConverter dateConverter = new SubscriptionDateConverter();
    
    /** Validator for the name field. */
    private SubscriptionNameValidator nameValidator;
    
    /** Validator for the charge field. */
    private SubscriptionChargeValidator chargeValidator;
    
    /** Validator for the date field. */
    private SubscriptionDateValidator dateValidator;
    
    /** Validator for the comment field. */
    private SubscriptionCommentValidator commentValidator;
    
    /**
     * Initializes the EditSubscriptionActivity. If the Intent used
     * to start the activity contains a serialized Subscription as
     * MainActivity.EXTRA_TARGET_DATA, that Subscription will be used
     * to initialize the fields. Other extras in the calling Intent
     * will be preserved and passed back when this activity finishes.
     *
     * @param savedInstanceState passed to Activity.onCreate, not used here
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_edit);
        
        // Get subscription data from Intent
        Intent intent = this.getIntent();
        Subscription subscription = (Subscription) intent.getSerializableExtra(
                MainActivity.EXTRA_TARGET_DATA);
        
        // Find editing fields
        EditText nameField = this.findViewById(R.id.edit_name);
        EditText chargeField = this.findViewById(R.id.edit_charge);
        EditText dateField = this.findViewById(R.id.edit_date);
        EditText commentField = this.findViewById(R.id.edit_comment);
        
        // Populate editing fields with existing subscription data and set delete button state
        if (subscription != null) {
            // convert non-text fields to text with converters
            this.chargeConverter.setObject(subscription.getCharge());
            this.dateConverter.setObject(subscription.getDate());
            
            // populate fields
            nameField.setText(subscription.getName());
            chargeField.setText(this.chargeConverter.getString());
            dateField.setText(this.dateConverter.getString());
            commentField.setText(subscription.getComment());
        }
        else {
            // this is a new subscription - don't fill anything, but do disable the delete button
            Button deleteButton = this.findViewById(R.id.button_delete);
            
            deleteButton.setVisibility(View.INVISIBLE);
        }
        
        // Set interaction handlers
        this.findViewById(R.id.button_save).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finishEditAndSave();
            }
        });
        this.findViewById(R.id.button_delete).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finishEditAndDelete();
            }
        });
        
        // Initialize validators
        this.nameValidator = new SubscriptionNameValidator(nameField);
        this.chargeValidator = new SubscriptionChargeValidator(chargeField);
        this.dateValidator = new SubscriptionDateValidator(dateField);
        this.commentValidator = new SubscriptionCommentValidator(commentField);
        
        // Initialize field validity
        this.nameValidator.validate();
        this.chargeValidator.validate();
        this.dateValidator.validate();
        this.commentValidator.validate();
    }
    
    /**
     * Finish the activity by producing a new Subscription from the current
     * contents of the editor fields. The new Subscription will be returned
     * as the extra EXTRA_SUBSCRIPTION_DATA to the calling activity. This
     * method will only cause the activity to actually finish if the Subscription
     * was successfully created; otherwise, it will simply return.
     */
    private void finishEditAndSave() {
        // Get fields
        EditText nameField = this.findViewById(R.id.edit_name);
        EditText chargeField = this.findViewById(R.id.edit_charge);
        EditText dateField = this.findViewById(R.id.edit_date);
        EditText commentField = this.findViewById(R.id.edit_comment);
        
        // Ensure entered data is valid
        if (!(this.nameValidator.isValid()
            && this.chargeValidator.isValid()
            && this.dateValidator.isValid()
            && this.commentValidator.isValid())) {
                return;
        }
        
        Intent intent = this.getIntent();
        try {
            // Attempt to create a new subscription out of the entered data
            
            // get contents of fields
            String nameText = nameField.getText().toString();
            String chargeText = chargeField.getText().toString();
            String dateText = dateField.getText().toString();
            String commentText = commentField.getText().toString();
            
            // try to convert to non-text fields using converters
            if (this.chargeConverter.setString(chargeText) != SubscriptionChargeConverter.VALID) {
                return;
            }
            else if (this.dateConverter.setString(dateText) != SubscriptionDateConverter.VALID) {
                return;
            }
            
            Integer charge = chargeConverter.getObject();
            Date date = dateConverter.getObject();
            
            // create subscription and save it to the Intent
            Subscription subscription = new Subscription(nameText, date, charge, commentText);
            intent.putExtra(EXTRA_SUBSCRIPTION_DATA, subscription);
        } catch (InvalidSubscriptionParameterException e) {
            // Creating subscription failed
            return;
        }
        
        this.setResult(RESULT_OK, intent);
        this.finish();
    }
    
    /**
     * Finish the activity by returning RESULT_SUBSCRIPTION_DELETED. This still
     * returns any extras passed in when the activity was started (by reusing the
     * calling Intent), but does not add any extras of its own (there will be
     * no EXTRA_SUBSCRIPTION_DATA). Nothing is checked for validity, and this
     * method should always actually cause the activity to finish.
     */
    private void finishEditAndDelete() {
        this.setResult(RESULT_SUBSCRIPTION_DELETED, this.getIntent());
        this.finish();
    }
}
