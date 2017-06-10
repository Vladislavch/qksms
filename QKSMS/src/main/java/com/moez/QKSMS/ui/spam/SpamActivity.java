package com.moez.QKSMS.ui.spam;

import android.app.FragmentManager;
import android.os.Bundle;

import com.moez.QKSMS.R;
import com.moez.QKSMS.ui.base.QKSwipeBackActivity;
import com.moez.QKSMS.ui.filters.FiltersFragment;

/**
 * Created by Anastasiya on 6/10/2017.
 */

public class SpamActivity  extends QKSwipeBackActivity {

    private FilteredMessagesFragment mSpamFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FragmentManager fm = getFragmentManager();
        mSpamFragment = (FilteredMessagesFragment) fm.findFragmentByTag(FilteredMessagesFragment.TAG);
        if (mSpamFragment == null) {
            mSpamFragment = FilteredMessagesFragment.newInstance(R.layout.fragment_filtered_messages);
            fm.beginTransaction()
                    .replace(R.id.content_frame, mSpamFragment, FilteredMessagesFragment.TAG)
                    .commit();
        } else {
            fm.beginTransaction()
                    .show(mSpamFragment)
                    .commit();
        }
    }

    @Override
    public void onBackPressed() {
        if (getFragmentManager().getBackStackEntryCount() > 0) {
            getFragmentManager().popBackStack();
        } else {
            super.onBackPressed();
        }
    }

}
