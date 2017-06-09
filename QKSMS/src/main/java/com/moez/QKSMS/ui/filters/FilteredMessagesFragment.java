package com.moez.QKSMS.ui.filters;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.melnykov.fab.FloatingActionButton;
import com.moez.QKSMS.R;
import com.moez.QKSMS.common.FilterMessagesHelper;
import com.moez.QKSMS.common.LiveViewManager;
import com.moez.QKSMS.common.utils.ColorUtils;
import com.moez.QKSMS.enums.QKPreference;
import com.moez.QKSMS.ui.ThemeManager;
import com.moez.QKSMS.ui.base.QKFragment;
import com.moez.QKSMS.ui.base.RecyclerCursorAdapter;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by VladislavEmets on 6/8/17.
 */

public class FilteredMessagesFragment extends QKFragment implements RecyclerCursorAdapter.ItemClickListener<String> {

    public static final String TAG = "FilteredMessagesFragment";

    @Bind(R.id.list_filtered_messages) RecyclerView mRecyclerView;

    private FilteredMessagesListAdapter  mAdapter;
    private LinearLayoutManager mLayoutManager;
    private SharedPreferences   mPrefs;

    private boolean mViewHasLoaded = false;


    protected static FilteredMessagesFragment newInstance(int category) {
        FilteredMessagesFragment fragment = new FilteredMessagesFragment();
        Bundle args = new Bundle();
        args.putInt("category", category);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mPrefs = PreferenceManager.getDefaultSharedPreferences(mContext);
        setHasOptionsMenu(false);

        mAdapter = new FilteredMessagesListAdapter(mContext);
        mAdapter.setItemClickListener(this);
        mLayoutManager = new LinearLayoutManager(mContext);
        mContext.setTitle("Filtered messages");
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_filtered_messages, null);
        ButterKnife.bind(this, view);

        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);

        mViewHasLoaded = true;

        return view;
    }

    @Override
    public void onItemClick(String object, View view) {
        ClipboardManager clipboard = (ClipboardManager) mContext.getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData clip = ClipData.newPlainText(object, object);
        clipboard.setPrimaryClip(clip);
        Toast.makeText(getActivity(), "Copied to clipboard", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onItemLongClick(String object, View view) { }
}
