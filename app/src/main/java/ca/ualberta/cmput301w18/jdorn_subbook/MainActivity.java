package ca.ualberta.cmput301w18.jdorn_subbook;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;

import java.io.IOException;
import java.util.Locale;

/**
 * Main 'view' screen of the app. Displays the list of
 * currently extant subscriptions, the total charge, and
 * has interactions to select a subscription and to add
 * a new subscription (both of which spawn EditSubscriptionActivity).
 *
 * @see EditSubscriptionActivity
 */
public class MainActivity extends ListActivity {
    /**
     * Extra identifier for an index into this class's SubscriptionList,
     * expecting a value that is either a valid index or INDEX_NEW.
     */
    public static final String EXTRA_TARGET_INDEX =
            "ca.ualberta.cmput301w18.jdorn_subbook.TARGET_INDEX";
    
    /**
     * Extra identifier for a serialized Subscription object being used
     * as source data.
     */
    public static final String EXTRA_TARGET_DATA =
            "ca.ualberta.cmput301w18.jdorn_subbook.TARGET_DATA";
    
    /**
     * Request code for opening the EditSubscriptionActivity to
     * edit a subscription. This is the only request code this
     * Activity can send.
     */
    public static final int REQUEST_EDIT_SUBSCRIPTION = 1;
    
    /**
     * Value for Extra of type EXTRA_TARGET_INDEX specifying to create
     * a new Subscription rather than targeting one at an existing index.
     */
    public static final int INDEX_NEW = -1;
    
    /**
     * Filename that will be used for saving and restoring persistent data.
     */
    private static final String FILENAME = "subscriptions";
    
    /**
     * Contains the currently existing subscriptions.
     */
    private SubscriptionList subscriptionList;
    
    /**
     * Instance to save and restore the SubscriptionList.
     */
    private SubscriptionListLoader subscriptionListLoader;
    
    /**
     * Initialization for the MainActivity. Sets up the user interface and
     * loads subscription data from the file specified by MainActivity.FILENAME.
     *
     * @param savedInstanceState passed to ListActivity.onCreate, not used here
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_main);
        
        // Restore any existing subscriptions from file
        this.subscriptionListLoader = new SubscriptionListLoader(this, FILENAME);
        
        try {
            this.subscriptionList = this.subscriptionListLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        // Initialize list and summary
        this.setListAdapter(new SubscriptionListAdapter(this, subscriptionList));
        this.updateSummary();
        
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
    
    /**
     * Called when the EditSubscriptionActivity completes. (If called from
     * a different state, will probably do nothing. This shouldn't happen.)
     * If the EditSubscriptionActivity was successful, creates/modifies/deletes
     * a subscription as appropriate based on the returned Intent and updates
     * the user interface to reflect the changes.
     *
     * @param requestCode Request code of the returning activity (should always be
     *                    MainActivity.REQUEST_EDIT_SUBSCRIPTION, others are ignored)
     * @param resultCode Result code of the returning activity (EditSubscriptionActivity.RESULT_OK
     *                   triggers add if EXTRA_TARGET_INDEX is INDEX_NEW or otherwise modify;
     *                   EditSubscriptionActivity.RESULT_SUBSCRIPTION_DELETED triggers delete.
     *                   All others are ignored)
     * @param data Resulting intent of the returning activity (should have extra
     *             EXTRA_TARGET_INDEX, and should also have EXTRA_SUBSCRIPTION_DATA if
     *             adding or modifying a subscription)
     */
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
            }
    
            // update the list view and summary
            ((SubscriptionListAdapter)(this.getListAdapter())).notifyDataSetChanged();
            this.updateSummary();
        }
    }
    
    /**
     * Called when the activity defocuses. Saves subscription data to file.
     */
    @Override
    protected void onPause() {
        super.onPause();
        
        try {
            this.subscriptionListLoader.save(this.subscriptionList);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Updates the summary with a new total charge from the subscription list.
     */
    private void updateSummary() {
        SubscriptionChargeConverter chargeConverter = new SubscriptionChargeConverter();
        TextView totalView = findViewById(R.id.summary_total);
        
        // Get total charge and convert to string
        Integer totalCharge = this.subscriptionList.getTotalCharge();
        chargeConverter.setObject(totalCharge);
        
        // Add a dollar sign to the string and place it in the TextView
        totalView.setText(String.format(Locale.US, getString(R.string.format_charge),
                chargeConverter.getString()));
        
        // Ensures that the TextView stays right-aligned even if the content width changes
        totalView.forceLayout();
    }
    
    /**
     * Spawns an EditSubscriptionActivity targeting the specified index.
     *
     * @param index Target index. If a valid index in subscriptionList is specified,
     *              the spawned activity will edit the subscription at the index.
     *              If INDEX_NEW is specified, the spawned activity will create a new
     *              subscription.
     */
    private void startEditActivity(int index) {
        Intent intent = new Intent(this, EditSubscriptionActivity.class);
        
        intent.putExtra(EXTRA_TARGET_INDEX, index);
        
        if (index != INDEX_NEW) {
            intent.putExtra(EXTRA_TARGET_DATA, this.subscriptionList.getSubscriptionAt(index));
        }
        
        startActivityForResult(intent, REQUEST_EDIT_SUBSCRIPTION);
    }
}
