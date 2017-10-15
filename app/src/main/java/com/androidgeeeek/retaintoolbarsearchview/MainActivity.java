package com.androidgeeeek.retaintoolbarsearchview;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    public static final String SEARCH_QUERY_TAG = "SEARCH_QUERY_TAG";
    private MenuItem mActionSearchMenuItem;
    private SearchView mActionSearchView;
    private String mSearchQuery;

    private Handler mHandler;
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = (TextView) findViewById(R.id.textView);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);

        mHandler = new Handler();

        if(savedInstanceState != null)
            mSearchQuery = savedInstanceState.getString(SEARCH_QUERY_TAG);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.activity_main_menu, menu);

        mActionSearchMenuItem = menu.findItem(R.id.action_search);
        mActionSearchView = (SearchView) mActionSearchMenuItem.getActionView();

        mActionSearchView.setIconifiedByDefault(false);
        mActionSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newQuery) {
                mSearchQuery = newQuery;
                if(mSearchQuery != null) textView.setText(mSearchQuery);
                return false;
            }
        });

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
    protected void onSaveInstanceState(Bundle outState) {
        outState.putString(SEARCH_QUERY_TAG, mSearchQuery);
        super.onSaveInstanceState(outState);
    }
}
