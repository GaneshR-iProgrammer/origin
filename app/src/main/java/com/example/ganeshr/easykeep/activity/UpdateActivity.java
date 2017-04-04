package com.example.ganeshr.easykeep.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;
import android.widget.Toast;

import com.example.ganeshr.easykeep.R;
import com.example.ganeshr.easykeep.model.NotesModel;
import com.example.ganeshr.easykeep.rest.RealmManger;

import butterknife.ButterKnife;
import butterknife.OnClick;


public class UpdateActivity extends AppCompatActivity {
    EditText edtTitle,edtNote;
    NotesModel model;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);
        edtTitle=(EditText)findViewById(R.id.edt_title);
        edtNote=(EditText)findViewById(R.id.edt_note);
        ButterKnife.bind(this);

        setDataAtLaunch();

    }


    @OnClick(R.id.update_btn)
        public void manageUpdate () {
        Toast.makeText(UpdateActivity.this,
                "Hello from Butterknife OnClick annotation", Toast.LENGTH_SHORT).show();
        onClickedUpdate();

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
