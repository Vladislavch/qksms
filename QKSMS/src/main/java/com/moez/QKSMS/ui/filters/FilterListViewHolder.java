package com.moez.QKSMS.ui.filters;

import android.view.View;

import com.moez.QKSMS.R;
import com.moez.QKSMS.ui.base.ClickyViewHolder;
import com.moez.QKSMS.ui.base.QKActivity;
import com.moez.QKSMS.ui.view.QKTextView;

/**
 * Created by VladislavEmets on 6/6/17.
 */

public class FilterListViewHolder extends ClickyViewHolder<String> {

    protected View root;
    protected QKTextView pattern;

    public FilterListViewHolder(QKActivity context, View view) {
        super(context, view);

        root = view;
        pattern = (QKTextView) view.findViewById(R.id.label_filter);

    }

}
