package ca.ualberta.cmput301w18.jdorn_subbook;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Parcel;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;

import java.io.IOException;
import java.util.Date;

public class MainActivity extends ListActivity {
    public static final String EXTRA_TARGET_INDEX =
            "ca.ualberta.cmput301w18.jdorn_subbook.TARGET_INDEX";
    public static final String EXTRA_TARGET_DATA =
            "ca.ualberta.cmput301w18.jdorn_subbook.TARGET_DATA";
    public static final int REQUEST_EDIT_SUBSCRIPTION = 1;
    
    public static final int INDEX_NEW = -1;
    
    private static final String FILENAME = "subscriptions";
    
    private SubscriptionList subscriptionList;
    private SubscriptionListLoader subscriptionListLoader;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_main);
        
        this.subscriptionListLoader = new SubscriptionListLoader(this, FILENAME);
        
        try {
            this.subscriptionList = this.subscriptionListLoader.load();
        } catch (IOException e) {
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
        
        this.getListView().setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                startEditActivity(i);
            }
        });
    }
    
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        
        if (requestCode == REQUEST_EDIT_SUBSCRIPTION) {
            if (resultCode == EditSubscriptionActivity.RESULT_SUBSCRIPTION_DELETED) {
                // Edit activity reports delete button pressed - delete the subscription
                int index = data.getIntExtra(EXTRA_TARGET_INDEX, INDEX_NEW);
                
                if (index != INDEX_NEW) {
                    this.subscriptionList.deleteSubscriptionAt(index);
                }
            }
            else if (resultCode == EditSubscriptionActivity.RESULT_OK) {
                // Edit activity reports changes were made
                int index = data.getIntExtra(EXTRA_TARGET_INDEX, INDEX_NEW);
                
                // get the new/edited subscription data
                Subscription subscription = (Subscription) data.getSerializableExtra(
                        EditSubscriptionActivity.EXTRA_SUBSCRIPTION_DATA);
                
                if (index == INDEX_NEW) {
                    // add a new subscription
                    this.subscriptionList.addSubscription(subscription);
                }
                else {
                    // edit an existing subscription by replacing it
                    this.subscriptionList.replaceSubscriptionAt(index, subscription);
                }
                
                // update the list view
                ((SubscriptionListAdapter)(this.getListAdapter())).notifyDataSetChanged();
            }
        }
    }
    
    @Override
    protected void onPause() {
        super.onPause();
    
        try {
            this.subscriptionListLoader.save(this.subscriptionList);
        } catch (IOException e) {
            e.printStackTrace();
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
