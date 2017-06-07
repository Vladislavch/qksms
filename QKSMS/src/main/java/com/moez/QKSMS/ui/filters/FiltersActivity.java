package com.moez.QKSMS.ui.filters;

import android.app.FragmentManager;
import android.os.Bundle;

import com.moez.QKSMS.R;
import com.moez.QKSMS.ui.base.QKSwipeBackActivity;

/**
 * Created by VladislavEmets on 6/6/17.
 */

public class FiltersActivity extends QKSwipeBackActivity {

    private FiltersFragment mFiltersFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FragmentManager fm = getFragmentManager();
        mFiltersFragment = (FiltersFragment) fm.findFragmentByTag(FiltersFragment.TAG);
        if (mFiltersFragment == null) {
            mFiltersFragment = FiltersFragment.newInstance(R.layout.fragment_filters);
            fm.beginTransaction()
                    .replace(R.id.content_frame, mFiltersFragment, FiltersFragment.TAG)
                    .commit();
        } else {
            fm.beginTransaction()
                    .show(mFiltersFragment)
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
