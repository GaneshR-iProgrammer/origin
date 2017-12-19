package com.example.ganeshr.easykeep.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
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

import java.util.ArrayList;

import io.realm.RealmResults;

public class Keep extends AppCompatActivity implements SearchView.OnQueryTextListener {

    // double tap to exit implementation
    private static final int TIME_INTERVAL = 1500; // # milliseconds, desired time passed between two back presses.
  public static  MenuItem shareItem;
    RecyclerView recyclerView;
    RealmResults<NotesModel> list;
    TextView mSearchText;
    SearchView searchView;
    MenuItem menuSearch;
    ShareActionProvider shareActionProvider;
    Toolbar toolbar;
    NotesAdapter adapter;
    private long mBackPressed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.toolbar);

        mSearchText = new TextView(this);

        recyclerView=(RecyclerView)findViewById(R.id.rv_main1);
        toolbar = (Toolbar) findViewById(R.id.toolbar1);
        setSupportActionBar(toolbar);


        list= RealmManger.getInstance(getApplicationContext()).getAll();
        if(list.size() == 0) {

            Toast.makeText(getApplicationContext(),"Empty List nothing to Show",Toast.LENGTH_LONG).show();
        }else {
            setAdapter();
        }

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab1);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent addNote=new Intent(Keep.this,Notes.class);
                startActivity(addNote);

            }
        });
    }

    public void setAdapter(){
        adapter = new NotesAdapter(Keep.this,list);
        RecyclerView.LayoutManager manager = new LinearLayoutManager(Keep.this);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        list= RealmManger.getInstance(getApplicationContext()).getAll();
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
        shareItem.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                Intent share = new Intent(Intent.ACTION_SEND);
                share.setType("text/plain");

                String shareMsg="Message Examole ";
                share.putExtra(Intent.EXTRA_TEXT, "" + shareMsg);
                startActivity(Intent.createChooser(share, "Share using"));
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
               // code to exit from app...
                Toast.makeText(getApplicationContext(),"way to exit",Toast.LENGTH_LONG).show();
                finish();
                return true;
            case R.id.refresh:
                Toast.makeText(getApplicationContext(),"Refreshing",Toast.LENGTH_SHORT).show();
                onResume();



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
        newText=newText.toLowerCase();
        ArrayList<NotesModel> newList=new ArrayList();
        for(NotesModel model1 : list)
        {
            String strTitle = model1.getTitle().toLowerCase();

            if(strTitle.contains(newText)){
                newList.add(model1);
            }
        }
        adapter.setFilter(newList);
        return true;

    }

    @Override
    public void onBackPressed()
    {
        if (mBackPressed + TIME_INTERVAL > System.currentTimeMillis())
        {
            super.onBackPressed();
            return;
        }
        else { Toast.makeText(getBaseContext(), "Top back again to exit", Toast.LENGTH_SHORT).show(); }

        mBackPressed = System.currentTimeMillis();
    }
}

