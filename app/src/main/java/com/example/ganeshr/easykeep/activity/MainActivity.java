package com.example.ganeshr.easykeep.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;
import android.widget.Toast;

import com.example.ganeshr.easykeep.R;
import com.example.ganeshr.easykeep.adapter.NotesAdapter;
import com.example.ganeshr.easykeep.model.NotesModel;
import com.example.ganeshr.easykeep.rest.RealmManger;

import io.realm.RealmResults;

public class MainActivity extends AppCompatActivity {


    RecyclerView recyclerView;

    RealmResults<NotesModel> list;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        recyclerView=(RecyclerView)findViewById(R.id.rv_main);



       list= RealmManger.getInstance(getApplicationContext()).getAll();

       if(list.size() == 0) {

           Toast.makeText(getApplicationContext(),"Empty List nothing to Show",Toast.LENGTH_LONG).show();
        }
        else {
            setAdapter();
       }

       FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent addNote=new Intent(MainActivity.this,Notes.class);
                startActivity(addNote);




            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        list= RealmManger.getInstance(getApplicationContext()).getAll();
        setAdapter();
    }


    public void setAdapter(){
        NotesAdapter adapter = new NotesAdapter(MainActivity.this,list);
        RecyclerView.LayoutManager manager = new LinearLayoutManager(MainActivity.this);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapter);
    }

}
