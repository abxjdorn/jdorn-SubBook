package ca.ualberta.cmput301w18.jdorn_subbook;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

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
        
        if (subscription == null) {
            try {
                subscription = new Subscription(
                        "New Subscription", new Date(118, 1, 31),
                        1234, "new");
            } catch (InvalidSubscriptionParameterException e) {
                e.printStackTrace();
            }
        }
        intent.putExtra(EXTRA_SUBSCRIPTION_DATA, subscription);
        
        this.setResult(RESULT_OK, intent);
        this.finish();
    }
}
