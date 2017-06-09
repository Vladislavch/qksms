package com.moez.QKSMS.ui.filters;

import android.app.FragmentManager;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
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
 * Created by VladislavEmets on 6/6/17.
 */

public class FiltersFragment extends QKFragment implements RecyclerCursorAdapter.ItemClickListener<String> {

    public static final String TAG = "FiltersFragment";

    @Bind(R.id.filters_list) RecyclerView mRecyclerView;
    @Bind(R.id.fab) FloatingActionButton mFab;
    @Bind(R.id.button_filtered_list) Button buttonFiltered;

    private FiltersListAdapter  mAdapter;
    private LinearLayoutManager mLayoutManager;
    private SharedPreferences   mPrefs;
    private FilteredMessagesFragment mFilteredMessages;

    private boolean mViewHasLoaded = false;


    protected static FiltersFragment newInstance(int category) {
        FiltersFragment fragment = new FiltersFragment();

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

        mAdapter = new FiltersListAdapter(mContext);
        mAdapter.setItemClickListener(this);
        mLayoutManager = new LinearLayoutManager(mContext);
        mContext.setTitle("Filters");

        LiveViewManager.registerView(QKPreference.THEME, this, key -> {
            if (!mViewHasLoaded) {
                return;
            }
            mFab.setColorNormal(ThemeManager.getColor());
            mFab.setColorPressed(ColorUtils.lighten(ThemeManager.getColor()));
            mFab.getDrawable().setColorFilter(ThemeManager.getTextOnColorPrimary(), PorterDuff.Mode.SRC_ATOP);
        });
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_filters, null);
        ButterKnife.bind(this, view);

        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);

        mFab.setColorNormal(ThemeManager.getColor());
        mFab.setColorPressed(ColorUtils.lighten(ThemeManager.getColor()));
        mFab.attachToRecyclerView(mRecyclerView);
        mFab.setColorFilter(ThemeManager.getTextOnColorPrimary());
        mFab.setOnClickListener(v -> {
            showCreateFilterDialog();
        });

        buttonFiltered.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showFilteredMessages();
            }
        });

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
    public void onItemLongClick(String object, View view) {
        showDeleteFilterDialog(object);
    }


    private void showCreateFilterDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setTitle("Add new filter");

        final EditText input = new EditText(mContext);
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        builder.setView(input);

        builder.setPositiveButton("Add", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                onAddFilter(input.getText().toString());
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        builder.show();
    }


    private void showDeleteFilterDialog(String patternToDelete) {
        if (patternToDelete == null) {
            return;
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setTitle("Remove filter?");

        builder.setPositiveButton("Remove", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                onDeleteFilter(patternToDelete);
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        builder.show();
    }


    private void showFilteredMessages() {
        FragmentManager fm = getFragmentManager();
        mFilteredMessages = (FilteredMessagesFragment) fm.findFragmentByTag(FilteredMessagesFragment.TAG);
        if (mFilteredMessages == null) {
            mFilteredMessages = FilteredMessagesFragment.newInstance(R.layout.fragment_filtered_messages);
            fm.beginTransaction()
                    .replace(R.id.content_frame, mFilteredMessages, FilteredMessagesFragment.TAG)
                    .commit();
        } else {
            fm.beginTransaction()
                    .show(mFilteredMessages)
                    .commit();
        }
    }


    private void onDeleteFilter(String patternToDelete) {
        if (patternToDelete == null) {
            return;
        }
        FilterMessagesHelper.removeFilter(mPrefs, patternToDelete);
        mAdapter.remove(patternToDelete);
    }

    private void onAddFilter(String patternToAdd) {
        if (patternToAdd == null) {
            return;
        }
        FilterMessagesHelper.addFilter(mPrefs, patternToAdd);
        mAdapter.add(patternToAdd);
    }

}
