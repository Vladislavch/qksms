package com.moez.QKSMS.ui.filters;

import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.moez.QKSMS.R;
import com.moez.QKSMS.common.FilterMessagesHelper;
import com.moez.QKSMS.common.LiveViewManager;
import com.moez.QKSMS.enums.QKPreference;
import com.moez.QKSMS.ui.ThemeManager;
import com.moez.QKSMS.ui.base.QKActivity;
import com.moez.QKSMS.ui.base.RecyclerCursorAdapter;

import java.util.ArrayList;

/**
 * Created by VladislavEmets on 6/9/17.
 */

public class FilteredMessagesListAdapter extends RecyclerCursorAdapter<FilteredMessageListViewHolder, String> {

    private final SharedPreferences mPrefs;

    private ArrayList<String> messages = new ArrayList<String>();

    public FilteredMessagesListAdapter(QKActivity context) {
        super(context);
        mPrefs = mContext.getPrefs();
        messages.addAll(FilterMessagesHelper.allFilteredMessages(mPrefs));
    }

    public void clear() {
        messages.clear();
        notifyDataSetChanged();
    }


    public FilteredMessageListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.list_item_filtered_message, null);

        FilteredMessageListViewHolder holder = new FilteredMessageListViewHolder(mContext, view);

        LiveViewManager.registerView(QKPreference.BACKGROUND, this, key -> {
            holder.message.setBackgroundColor(ThemeManager.getColor());
        });

        return holder;
    }


    @Override
    public void onBindViewHolder(FilteredMessageListViewHolder holder, int position) {
        final String message = getItem(position);

        holder.mData = message;
        holder.mContext = mContext;
        holder.mClickListener = mItemClickListener;
        holder.root.setOnClickListener(holder);
        holder.root.setOnLongClickListener(holder);
        holder.message.setText(message);
    }

    @Override
    protected String getItem(int position) {
        return messages.get(position);
    }

    @Override
    public int getItemCount() {
        return messages.size();
    }
}
