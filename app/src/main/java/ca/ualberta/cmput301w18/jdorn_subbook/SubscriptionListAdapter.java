package ca.ualberta.cmput301w18.jdorn_subbook;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.Locale;

/**
 * ListAdapter that wraps a SubscriptionList in a ListView-able form.
 */
public class SubscriptionListAdapter extends ArrayAdapter<Subscription> {
    /** LayoutInflater used to produce Views */
    private LayoutInflater layoutInflater;
    
    /** Converter for formatting Subscription charges for display */
    private SubscriptionChargeConverter chargeConverter = new SubscriptionChargeConverter();
    
    /** Converter for formatting Subscription dates for display. */
    private SubscriptionDateConverter dateConverter = new SubscriptionDateConverter();
    
    /**
     * Creates a SubscriptionListAdapter over the specified SubscriptionList,
     * using the specified Context for instantiating the superclass Adapter.
     *
     * @param context passed to Adapter superclass
     * @param subscriptionList List of subscriptions to adapt
     */
    SubscriptionListAdapter(Context context, SubscriptionList subscriptionList) {
        super(context, R.layout.listitem_subscription, subscriptionList.getSubscriptions());
        
        this.layoutInflater = (LayoutInflater)
                context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    
    /**
     * View generator function
     * @param position index to create view for, as in ListAdapter.getView
     * @param convertView Existing view to reuse, as in ListAdapter.getView
     * @param parent Parent view, as in ListAdapter.getView
     * @return View representing the Subscription at the specified index
     */
    @Override
    @NonNull
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        // Create a new view if necessary, but reuse existing view if available
        View view;
        if (convertView == null) {
            view = this.layoutInflater.inflate(
                    R.layout.listitem_subscription, parent, false);
        }
        else {
            view = convertView;
        }
        
        // Get view at index
        Subscription subscription = this.getItem(position);
        if (subscription == null) {
            // don't throw exception if failed to get subscription
            // (but can't return a correct view)
            return view;
        }
        
        // Retrieve raw parameters from subscription
        TextView nameView = view.findViewById(R.id.subscription_name);
        TextView dateView = view.findViewById(R.id.subscription_date);
        TextView chargeView = view.findViewById(R.id.subscription_charge);
        TextView commentView = view.findViewById(R.id.subscription_comment);
        
        // Convert non-text fields to text using converters
        this.chargeConverter.setObject(subscription.getCharge());
        this.dateConverter.setObject(subscription.getDate());
        
        // Write subscription parameters to screen
        nameView.setText(subscription.getName());
        dateView.setText(this.dateConverter.getString());
        chargeView.setText(String.format(Locale.US, getContext().getString(R.string.format_charge),
                this.chargeConverter.getString()));
        commentView.setText(subscription.getComment());
        
        return view;
    }
}
