package com.androidgeeeek.retaintoolbarsearchview;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity {

    // set up toolbar
    // https://developer.android.com/training/appbar/setting-up.html

    private MenuItem mActionSearchMenuItem;
    private SearchView mActionSearchView;
    public static final String SEARCH_QUERY_TAG = "SEARCH_QUERY_TAG";
    private String mSearchQuery;
    private Handler mHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);

        if(savedInstanceState != null) {
            mSearchQuery = savedInstanceState.getString(SEARCH_QUERY_TAG);
        }

        mHandler = new Handler();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.activity_main_menu, menu);

        mActionSearchMenuItem = menu.findItem(R.id.action_search);
        mActionSearchView = (SearchView) mActionSearchMenuItem.getActionView();

        mActionSearchView.setIconifiedByDefault(false); // Do not iconify the widget; expand it by default
        mActionSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newQuery) {
                mSearchQuery = newQuery;
                return false;
            }
        });

        mActionSearchMenuItem.setShowAsAction(MenuItem.SHOW_AS_ACTION_COLLAPSE_ACTION_VIEW | MenuItem.SHOW_AS_ACTION_ALWAYS);
        mActionSearchMenuItem.setActionView(mActionSearchView);

        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        if(mSearchQuery != null && !mSearchQuery.isEmpty()){
            final String query = mSearchQuery;

            if(mHandler != null
                    && mActionSearchView != null
                    && mActionSearchMenuItem != null) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        mActionSearchMenuItem.expandActionView();
                        mActionSearchView.setQuery(query, true);
                        mActionSearchView.clearFocus();
                    }
                });
            }
        }

        return super.onPrepareOptionsMenu(menu);
    }


    @Override
    public void invalidateOptionsMenu() {
        // detach all listeners from toolbar to avoid callbacks
        if(mActionSearchView != null) mActionSearchView.setOnQueryTextListener(null);
        super.invalidateOptionsMenu();
    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putString(SEARCH_QUERY_TAG, mSearchQuery);
        super.onSaveInstanceState(outState);
    }
}
