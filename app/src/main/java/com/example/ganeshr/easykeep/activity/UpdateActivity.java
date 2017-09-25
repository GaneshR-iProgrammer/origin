package com.example.ganeshr.easykeep.activity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.ganeshr.easykeep.R;
import com.example.ganeshr.easykeep.model.NotesModel;
import com.example.ganeshr.easykeep.rest.RealmManger;

import java.text.DateFormat;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class UpdateActivity extends AppCompatActivity {
    EditText edtTitle,edtNote;
    NotesModel model;
    @BindView(R.id.update_btn)
    Button updateButton;
    @BindView(R.id.save_btn)
    Button saveButton;

    AlertDialog dialog;
    AlertDialog.Builder builder;

    String strTitle="";
    String strNote="";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        edtTitle=(EditText)findViewById(R.id.txt_title);
        edtNote=(EditText)findViewById(R.id.txt_note);
        ButterKnife.bind(this);

        saveButton.setVisibility(View.GONE);
        updateButton.setVisibility(View.VISIBLE);

        setDataAtLaunch();

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                saveDialog();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @OnClick(R.id.update_btn)
    public void manageUpdate () {
        Toast.makeText(UpdateActivity.this,
                "Hello from Butterknife OnClick annotation", Toast.LENGTH_SHORT).show();
        onClickedUpdate();
        finish();

    }

    public void saveDialog(){

        if(strTitle.length()<edtTitle.getText().length() || strNote.length()<edtNote.getText().length()) {
            builder = new AlertDialog.Builder(this);
            builder.setCancelable(false);

            builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    dialog.cancel();
                    manageUpdate();
                }
            });
            builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    dialog.cancel();
                    finish();
                }
            });

            dialog = builder.create();
            dialog.setTitle("Save changes");
            dialog.setMessage("are you sure want to leave? to save chages click on save button.");
            dialog.show();
        }else {
            super.onBackPressed();
        }
    }

    private void setDataAtLaunch() {
        model=getIntent().getParcelableExtra("AddUpdate");
        edtTitle.setText(model.getTitle());
        edtNote.setText(model.getNote());

        strTitle=model.getTitle();
        strNote=model.getNote();

    }

    private void onClickedUpdate() {
        model.setTitle(edtTitle.getText().toString());
        model.setNote(edtNote.getText().toString());
        model.setDate(DateFormat.getDateTimeInstance().format(new Date()));

        RealmManger.update(model);
    }

    @Override
    public void onBackPressed() {
        saveDialog();
    }

}
