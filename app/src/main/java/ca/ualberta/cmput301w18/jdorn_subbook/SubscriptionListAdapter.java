package ca.ualberta.cmput301w18.jdorn_subbook;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

/**
 * ListAdapter that wraps a SubscriptionList in a ListView-able form.
 */
public class SubscriptionListAdapter extends ArrayAdapter<Subscription> {
    /** LayoutInflater used to produce Views */
    private LayoutInflater layoutInflater;
    
    public SubscriptionListAdapter(Context context, SubscriptionList subscriptionList) {
        super(context, R.layout.listitem_subscription, subscriptionList.getSubscriptions());
        
        this.layoutInflater = (LayoutInflater)
                context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    
    @Override
    @NonNull
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        Subscription subscription = this.getItem(position);
        
        View view;
        if (convertView == null) {
            view = this.layoutInflater.inflate(
                    R.layout.listitem_subscription, parent, false);
        }
        else {
            view = convertView;
        }
        
        TextView nameView = view.findViewById(R.id.subscription_name);
        TextView dateView = view.findViewById(R.id.subscription_date);
        TextView chargeView = view.findViewById(R.id.subscription_charge);
        TextView commentView = view.findViewById(R.id.subscription_comment);
        
        
        nameView.setText(subscription.getName());
        dateView.setText(subscription.getDate().toString());
        chargeView.setText(subscription.getCharge().toString());
        commentView.setText(subscription.getComment());
        
        return view;
    }
}
