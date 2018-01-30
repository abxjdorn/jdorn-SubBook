package ca.ualberta.cmput301w18.jdorn_subbook;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import java.util.Date;


public class EditSubscriptionActivity extends Activity {
    public static final String EXTRA_SUBSCRIPTION_DATA =
            "ca.ualberta.cmput301w18.jdorn_subbook.SUBSCRIPTION_DATA";
    public static final int RESULT_SUBSCRIPTION_DELETED = 2;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_edit);
        
        Intent intent = this.getIntent();
        Subscription subscription = (Subscription) intent.getSerializableExtra(
                MainActivity.EXTRA_TARGET_DATA);
        
        // Populate editing fields with existing subscription data
        if (subscription != null) {
            EditText nameField = this.findViewById(R.id.edit_name);
            EditText chargeField = this.findViewById(R.id.edit_charge);
            EditText dateField = this.findViewById(R.id.edit_date);
            EditText commentField = this.findViewById(R.id.edit_comment);
            
            nameField.setText(subscription.getName());
            chargeField.setText(subscription.getCharge().toString());
            dateField.setText(subscription.getDateAsString());
            commentField.setText(subscription.getComment());
        }
        
        // Set interaction handlers
        this.findViewById(R.id.button_save).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finishEditActivity();
            }
        });
    }
    
    private void finishEditActivity() {
        EditText nameField = this.findViewById(R.id.edit_name);
        EditText chargeField = this.findViewById(R.id.edit_charge);
        EditText dateField = this.findViewById(R.id.edit_date);
        EditText commentField = this.findViewById(R.id.edit_comment);
        
        Intent intent = this.getIntent();
        try {
            String name = nameField.getText().toString();
            SubscriptionCharge charge = new SubscriptionCharge(chargeField.getText().toString());
            Date date = new Date(dateField.getText().toString());
            String comment = commentField.getText().toString();
            
            Subscription subscription = new Subscription(name, date, charge, comment);
            intent.putExtra(EXTRA_SUBSCRIPTION_DATA, subscription);
        } catch (InvalidSubscriptionParameterException e) {
            e.printStackTrace();
        }
        
        this.setResult(RESULT_OK, intent);
        this.finish();
    }
}
