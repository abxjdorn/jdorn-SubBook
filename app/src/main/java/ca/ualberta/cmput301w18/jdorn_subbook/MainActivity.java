package ca.ualberta.cmput301w18.jdorn_subbook;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Parcel;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import java.util.Date;

public class MainActivity extends ListActivity {
    public static final String EXTRA_TARGET_INDEX =
            "ca.ualberta.cmput301w18.jdorn_subbook.TARGET_INDEX";
    public static final String EXTRA_TARGET_DATA =
            "ca.ualberta.cmput301w18.jdorn_subbook.TARGET_DATA";
    public static final int REQUEST_EDIT_SUBSCRIPTION = 1;
    
    public static final int INDEX_NEW = -1;
    
    private SubscriptionList subscriptionList;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_main);
        
        this.subscriptionList = new SubscriptionList();
        
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
                startEditActivity(INDEX_NEW);
            }
        });
    }
    
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_EDIT_SUBSCRIPTION) {
            if (resultCode == EditSubscriptionActivity.RESULT_SUBSCRIPTION_DELETED) {
                int index = data.getIntExtra(EXTRA_TARGET_INDEX, INDEX_NEW);
                if (index != INDEX_NEW) {
                    this.subscriptionList.deleteSubscriptionAt(index);
                }
            }
            else if (resultCode == EditSubscriptionActivity.RESULT_OK) {
                int index = data.getIntExtra(EXTRA_TARGET_INDEX, INDEX_NEW);
                Subscription subscription = (Subscription) data.getSerializableExtra(
                        EditSubscriptionActivity.EXTRA_SUBSCRIPTION_DATA);
                if (index == INDEX_NEW) {
                    this.subscriptionList.addSubscription(subscription);
                    ((SubscriptionListAdapter)(this.getListAdapter())).notifyDataSetChanged();
                }
                else {
                    this.subscriptionList.replaceSubscriptionAt(index, subscription);
                }
            }
        }
    }
    
    private void startEditActivity(int index) {
        Intent intent = new Intent(this, EditSubscriptionActivity.class);
        
        intent.putExtra(EXTRA_TARGET_INDEX, index);
        
        if (index != INDEX_NEW) {
            intent.putExtra(EXTRA_TARGET_DATA, this.subscriptionList.getSubscriptionAt(index));
        }
        
        startActivityForResult(intent, REQUEST_EDIT_SUBSCRIPTION);
    }
}
