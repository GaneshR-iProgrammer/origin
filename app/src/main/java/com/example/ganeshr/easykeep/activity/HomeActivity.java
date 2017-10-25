package com.example.ganeshr.easykeep.activity;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ShareActionProvider;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ganeshr.easykeep.R;
import com.example.ganeshr.easykeep.adapter.NotesAdapter;
import com.example.ganeshr.easykeep.model.NotesModel;
import com.example.ganeshr.easykeep.rest.RealmManger;
import com.example.ganeshr.easykeep.utils.Pref;
import com.example.ganeshr.easykeep.utils.Utility;

import java.util.ArrayList;

import io.realm.RealmResults;

public class HomeActivity extends AppCompatActivity implements SearchView.OnQueryTextListener {

    // double tap to exit implementation
    private static final int TIME_INTERVAL = 1500; // # milliseconds, desired time passed between two back presses.
    private static final int SPEECH_REQUEST_CODE = 0;
    public static MenuItem shareItem;
    public static MenuItem menuSearch;
    RecyclerView recyclerView;
    RealmResults<NotesModel> list;
    TextView mSearchText;
    SearchView searchView;
    ShareActionProvider shareActionProvider;
    Toolbar toolbar;
    NotesAdapter adapter;
    Pref pref;
    private long mBackPressed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.toolbar);
        mSearchText = new TextView(this);

        Utility.setStatusBarGradiant(this);

        setDefaultKeyMode(DEFAULT_KEYS_SEARCH_LOCAL);

        recyclerView = (RecyclerView) findViewById(R.id.rv_main1);
        toolbar = (Toolbar) findViewById(R.id.toolbar1);
        setSupportActionBar(toolbar);

        final FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab1);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent addNote = new Intent(HomeActivity.this, Notes.class);
                startActivity(addNote);

            }
        });


        // Get the intent, verify the action and get the query
        Intent intent = getIntent();
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            Log.d("test--",query);
            onQueryTextChange(query);
        }

    }


    public void setAdapter() {
        adapter = new NotesAdapter(HomeActivity.this, list);
        RecyclerView.LayoutManager manager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(manager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setAdapter(adapter);
    }

    @Override
    protected void onResume() {
        super.onResume();

        list = RealmManger.getInstance(getApplicationContext()).getAll();

        if (list.size() == 0) {
            Toast.makeText(getApplicationContext(), "Empty List nothing to Show", Toast.LENGTH_LONG).show();
        } else {
            setAdapter();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.menu_main, menu);
        menuSearch = menu.findItem(R.id.action_search);
        shareItem = menu.findItem(R.id.action_share);
        shareItem.setVisible(false);
        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //return super.onOptionsItemSelected(item);
        switch (item.getItemId()) {

            case R.id.action_exit:
                super.onBackPressed();
            case R.id.refresh:
                adapter.notifyDataSetChanged();
                return true;

            case R.id.action_search:
                searchView = (SearchView) MenuItemCompat.getActionView(menuSearch);
                searchView.setOnQueryTextListener(this);
                return true;

            case R.id.action_share:
                pref = Pref.getInstance();
                Intent share = new Intent(Intent.ACTION_SEND);
                share.setType("text/plain");
                String shareMsg = pref.getNoteTilte() + "\n \n" + pref.getNoteText();
                share.putExtra(Intent.EXTRA_TEXT, shareMsg);
                startActivity(Intent.createChooser(share, "Share using"));
                Utility.clearPref(pref);
                HomeActivity.shareItem.setVisible(false);
                return true;

            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }

    }

    @Override
    public boolean onSearchRequested() {
//        pauseSomeStuff();
        return super.onSearchRequested();
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return true;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        newText = newText.toLowerCase();
        ArrayList<NotesModel> newList = new ArrayList();
        for (NotesModel model1 : list) {
            String strTitle = model1.getTitle().toLowerCase();

            if (strTitle.contains(newText) || model1.getNote().contains(newText)) {
                newList.add(model1);
            }
        }
        adapter.setFilter(newList);
        return true;

    }

    @Override
    public void onBackPressed() {
        if (mBackPressed + TIME_INTERVAL > System.currentTimeMillis()) {
            super.onBackPressed();
            return;
        } else {
            Toast.makeText(getBaseContext(), "Top back again to exit", Toast.LENGTH_SHORT).show();
        }

        mBackPressed = System.currentTimeMillis();
    }
}

