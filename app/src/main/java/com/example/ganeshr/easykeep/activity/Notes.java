package com.example.ganeshr.easykeep.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ganeshr.easykeep.R;
import com.example.ganeshr.easykeep.model.NotesModel;
import com.example.ganeshr.easykeep.rest.RealmManger;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class Notes extends AppCompatActivity {

    TextView tvTitle,tvNote;
    EditText txtTitle,txtNote;
    NotesModel m;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes);

        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        tvTitle = (TextView) findViewById(R.id.tv_title);
        tvNote = (TextView) findViewById(R.id.tv_note);
        txtTitle = (EditText) findViewById(R.id.txt_title);
        txtNote = (EditText) findViewById(R.id.txt_note);

        ButterKnife.bind(this);
        m = new NotesModel();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                finish();

                return true;
        }
        return super.onOptionsItemSelected(item);
    }


        @OnClick(R.id.btn_save)
        public void manageSave () {
            getData();
            RealmManger.getInstance(Notes.this).addorUpadte(m);
            Toast.makeText(getApplicationContext(),"Successful !",Toast.LENGTH_LONG).show();

            finish();

        }

    public void getData(){
        m.setTitle(txtTitle.getText().toString());
        m.setNote(txtNote.getText().toString());

    }


}
