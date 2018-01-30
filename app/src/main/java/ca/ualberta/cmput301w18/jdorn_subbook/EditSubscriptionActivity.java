package ca.ualberta.cmput301w18.jdorn_subbook;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.Date;


public class EditSubscriptionActivity extends Activity {
    public static final String EXTRA_SUBSCRIPTION_DATA =
            "ca.ualberta.cmput301w18.jdorn_subbook.SUBSCRIPTION_DATA";
    public static final int RESULT_SUBSCRIPTION_DELETED = 2;
    
    private SubscriptionChargeConverter chargeConverter = new SubscriptionChargeConverter();
    private SubscriptionDateConverter dateConverter = new SubscriptionDateConverter();
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_edit);
        
        Intent intent = this.getIntent();
        Subscription subscription = (Subscription) intent.getSerializableExtra(
                MainActivity.EXTRA_TARGET_DATA);
        
        // Populate editing fields with existing subscription data
        if (subscription != null) {
            // extract raw fields
            EditText nameField = this.findViewById(R.id.edit_name);
            EditText chargeField = this.findViewById(R.id.edit_charge);
            EditText dateField = this.findViewById(R.id.edit_date);
            EditText commentField = this.findViewById(R.id.edit_comment);
            
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
    }
    
    private void finishEditAndSave() {
        // get fields
        EditText nameField = this.findViewById(R.id.edit_name);
        EditText chargeField = this.findViewById(R.id.edit_charge);
        EditText dateField = this.findViewById(R.id.edit_date);
        EditText commentField = this.findViewById(R.id.edit_comment);
        
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
            
            Subscription subscription = new Subscription(nameText, date, charge, commentText);
            intent.putExtra(EXTRA_SUBSCRIPTION_DATA, subscription);
        } catch (InvalidSubscriptionParameterException e) {
            e.printStackTrace();
        }
        
        this.setResult(RESULT_OK, intent);
        this.finish();
    }
    
    private void finishEditAndDelete() {
        this.setResult(RESULT_SUBSCRIPTION_DELETED, this.getIntent());
        this.finish();
    }
}
