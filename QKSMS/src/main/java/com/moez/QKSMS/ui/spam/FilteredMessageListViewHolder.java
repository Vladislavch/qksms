package com.moez.QKSMS.ui.spam;

import android.view.View;

import com.moez.QKSMS.R;
import com.moez.QKSMS.ui.base.ClickyViewHolder;
import com.moez.QKSMS.ui.base.QKActivity;
import com.moez.QKSMS.ui.view.QKTextView;

/**
 * Created by VladislavEmets on 6/9/17.
 */

public class FilteredMessageListViewHolder extends ClickyViewHolder<String> {

    protected View root;
    protected QKTextView message;

    public FilteredMessageListViewHolder(QKActivity context, View view) {
        super(context, view);

        root = view;
        message = (QKTextView) view.findViewById(R.id.label_filtered_message);

    }

}