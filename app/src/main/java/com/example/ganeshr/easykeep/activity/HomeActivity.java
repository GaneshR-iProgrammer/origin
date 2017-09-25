package com.example.ganeshr.easykeep.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
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
    public static MenuItem shareItem;
    public static MenuItem menu_Delete;
    RecyclerView recyclerView;
    RealmResults<NotesModel> list;
    TextView mSearchText;
    SearchView searchView;
    MenuItem menuSearch;
    ShareActionProvider shareActionProvider;
    Toolbar toolbar;
    NotesAdapter adapter;
    private long mBackPressed;

    Pref pref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.toolbar);
        mSearchText = new TextView(this);

        recyclerView = (RecyclerView) findViewById(R.id.rv_main1);
        toolbar = (Toolbar) findViewById(R.id.toolbar1);
        setSupportActionBar(toolbar);

        list = RealmManger.getInstance(getApplicationContext()).getAll();


//      list = RealmManger.getInstance(getApplicationContext()).where(MyTable.class).findAllSorted("date",Sort.DESCENDING);

        if (list.size() == 0) {

            Toast.makeText(getApplicationContext(), "Empty List nothing to Show", Toast.LENGTH_LONG).show();
        } else {
            setAdapter();
        }

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab1);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent addNote = new Intent(HomeActivity.this, Notes.class);
                startActivity(addNote);

            }
        });
    }


    public void setAdapter() {
        adapter = new NotesAdapter(HomeActivity.this, list);
        RecyclerView.LayoutManager manager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        list = RealmManger.getInstance(getApplicationContext()).getAll();
        setAdapter();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);

        getMenuInflater().inflate(R.menu.menu_main, menu);

        menuSearch = menu.findItem(R.id.action_search);
        searchView = (SearchView) MenuItemCompat.getActionView(menuSearch);
        searchView.setOnQueryTextListener(this);

        shareItem = menu.findItem(R.id.action_share);
        shareItem.setVisible(false);

        menu_Delete = menu.findItem(R.id.action_delete);
        menu_Delete.setVisible(false);

        shareItem.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {

                pref=Pref.getInstance();

                Intent share = new Intent(Intent.ACTION_SEND);
                share.setType("text/plain");

                String shareMsg = pref.getNoteTilte() +"\n \n" + pref.getNoteText();
                share.putExtra(Intent.EXTRA_TEXT, shareMsg);
                startActivity(Intent.createChooser(share, "Share using"));

                Utility.clearPref(pref);
                HomeActivity.shareItem.setVisible(false);
                return false;
            }
        });

        menu_Delete.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {

                pref=Pref.getInstance();
//                adapter.removeData();
                HomeActivity.shareItem.setVisible(false);
                return false;
            }
        });



       /* MenuItem shareItem = menu.findItem(R.id.action_share);
        shareActionProvider = (ShareActionProvider) MenuItemCompat.getActionProvider(shareItem);
        shareActionProvider.setShareIntent(adapter.getDefaultShareIntent());*/


        return super.onCreateOptionsMenu(menu);

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //return super.onOptionsItemSelected(item);
        switch (item.getItemId()) {

            case R.id.action_exit:
                onBackPressed();
                return true;
            case R.id.refresh:
                adapter.notifyDataSetChanged();
                return true;

//            case R.id.menu_delete:
////                adapter.delteRow();
//
////                actionMode.finish();
//                return true;


            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }

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

            if (strTitle.contains(newText)) {
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

