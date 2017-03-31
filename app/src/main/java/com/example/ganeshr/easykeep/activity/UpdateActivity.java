package com.example.ganeshr.easykeep.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.ganeshr.easykeep.R;
import com.example.ganeshr.easykeep.model.NotesModel;
import com.example.ganeshr.easykeep.rest.RealmManger;

public class UpdateActivity extends AppCompatActivity {
    EditText edtTitle,edtNote;
    Button updateButton;


    NotesModel model;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        edtTitle=(EditText)findViewById(R.id.edt_title);
        edtNote=(EditText)findViewById(R.id.edt_note);
        updateButton=(Button)findViewById(R.id.update_btn);

        setDataAtLaunch();


        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickedUpdate();
            }
        });
    }

    private void setDataAtLaunch() {
        model=getIntent().getParcelableExtra("AddUpdate");
        edtTitle.setText(model.getTitle());
        edtNote.setText(model.getNote());

    }

    private void onClickedUpdate() {
        model.setTitle(edtTitle.getText().toString());
        model.setNote(edtNote.getText().toString());

        RealmManger.update(model);
        finish();


    }
}
