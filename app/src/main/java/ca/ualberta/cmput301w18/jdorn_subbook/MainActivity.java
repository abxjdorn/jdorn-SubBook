package ca.ualberta.cmput301w18.jdorn_subbook;

import android.app.ListActivity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import java.util.Date;

public class MainActivity extends ListActivity {
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_main);
        
        SubscriptionList subscriptionList = new SubscriptionList();
        
        // temporary
        try {
            Subscription testSubscription1 = new Subscription("Subscription 1",
                    new Date(118, 1,5), 2222, "");
            subscriptionList.addSubscription(testSubscription1);
            Subscription testSubscription2 = new Subscription("Subscription 2",
                    new Date(118, 1, 7), 2345,
                    "This is a thirty char comment.");
            subscriptionList.addSubscription(testSubscription2);
        } catch (InvalidSubscriptionParameterException e) {
            e.printStackTrace();
        }
        
        this.setListAdapter(new SubscriptionListAdapter(this, subscriptionList));
        
        // Set interaction handlers
        this.findViewById(R.id.button_add).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getBaseContext(), EditSubscriptionActivity.class);
                startActivity(intent);
            }
        });
    }
}
