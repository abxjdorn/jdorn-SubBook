package ca.ualberta.cmput301w18.jdorn_subbook;

import android.app.ListActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.Date;

public class MainActivity extends ListActivity {
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_main);
        
        SubscriptionList subscriptionList = new SubscriptionList();
        
        // temporary
        try {
            Subscription testSubscription = new Subscription("TEST",
                    new Date(2018, 1,5), 2222, "");
            subscriptionList.addSubscription(testSubscription);
        } catch (InvalidSubscriptionParameterException e) {
            e.printStackTrace();
        }
        
        this.setListAdapter(new SubscriptionListAdapter(this, subscriptionList));
    }
}
