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
 * Created by VladislavEmets on 6/6/17.
 */

public class FiltersListAdapter extends RecyclerCursorAdapter<FilterListViewHolder, String> {

    private final SharedPreferences mPrefs;

    private ArrayList<String> patterns = new ArrayList<String>();

    public FiltersListAdapter(QKActivity context) {
        super(context);
        mPrefs = mContext.getPrefs();
        patterns.addAll(FilterMessagesHelper.allFilters(mPrefs));
    }

    public void add(String element) {
        patterns.add(element);
        notifyDataSetChanged();
    }

    public void remove(String element) {
        patterns.remove(element);
        notifyDataSetChanged();
    }

    public void clear() {
        patterns.clear();
        notifyDataSetChanged();
    }


    public FilterListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.list_item_filter, null);

        FilterListViewHolder holder = new FilterListViewHolder(mContext, view);

        LiveViewManager.registerView(QKPreference.BACKGROUND, this, key -> {
            holder.pattern.setBackgroundColor(ThemeManager.getColor());
        });

        return holder;
    }


    @Override
    public void onBindViewHolder(FilterListViewHolder holder, int position) {
        final String filter = getItem(position);

        holder.mData = filter;
        holder.mContext = mContext;
        holder.mClickListener = mItemClickListener;
        holder.root.setOnClickListener(holder);
        holder.root.setOnLongClickListener(holder);
        holder.pattern.setText(filter);
    }

    @Override
    protected String getItem(int position) {
        return patterns.get(position);
    }

    @Override
    public int getItemCount() {
        return patterns.size();
    }


}
