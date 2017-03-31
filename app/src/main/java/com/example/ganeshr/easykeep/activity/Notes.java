package com.example.ganeshr.easykeep.activity;

import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ganeshr.easykeep.R;
import com.example.ganeshr.easykeep.adapter.NotesAdapter;
import com.example.ganeshr.easykeep.model.NotesModel;
import com.example.ganeshr.easykeep.rest.RealmManger;

public class Notes extends AppCompatActivity {

    TextView tvTitle,tvNote;
    EditText txtTitle,txtNote,txtId;
    Button save;
    NotesAdapter.MViewHolder holder;

    Toolbar toolbar;

    NotesModel m;


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes);


        toolbar=(Toolbar)findViewById(R.id.my_awesome_toolbar);
        setSupportActionBar(toolbar);

        tvTitle=(TextView)findViewById(R.id.tv_title);
        tvNote=(TextView)findViewById(R.id.tv_note);
        txtTitle=(EditText)findViewById(R.id.txt_title);
        txtNote=(EditText)findViewById(R.id.txt_note);
        txtId=(EditText)findViewById(R.id.txt_id);
        save=(Button) findViewById(R.id.btn_save);

        m=new NotesModel();

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getData();
                RealmManger.getInstance(Notes.this).addorUpadte(m);

                Toast.makeText(getApplicationContext(),"Successful !",Toast.LENGTH_LONG).show();

                finish();
            }
        });
    }


    public void getData(){
        m.setTitle(txtTitle.getText().toString());
        m.setNote(txtNote.getText().toString());
        m.setId(txtId.getText().toString());
    }


}
