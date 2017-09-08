package com.example.ganeshr.easykeep.activity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ganeshr.easykeep.R;
import com.example.ganeshr.easykeep.model.NotesModel;
import com.example.ganeshr.easykeep.rest.RealmManger;

import java.util.Collections;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTextChanged;

public class Notes extends AppCompatActivity {

    EditText txtTitle,txtNote;
    NotesModel m;
    @BindView(R.id.update_btn)
    Button updateButton;
    @BindView(R.id.save_btn)
    Button saveButton;
    AlertDialog dialog;
    AlertDialog.Builder builder;
    private HashMap<EditText, Boolean> hashMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes);

        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        txtTitle = (EditText) findViewById(R.id.txt_title);
        txtNote = (EditText) findViewById(R.id.txt_note);

        ButterKnife.bind(this);
        hashMap = new HashMap<>();
        updateButton.setVisibility(View.GONE);
        saveButton.setVisibility(View.VISIBLE);
        m = new NotesModel();

    }



    @OnTextChanged({R.id.txt_title, R.id.txt_note})
    void OnTextChanged() {
        if (validateMandatoryFields()) {
           saveButton.setClickable(true);
        } else {
            //Utility.displayErrorMessage(edt_last_date_apply, "Please fill mandatory fields.");
            saveButton.setClickable(false);
        }
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

    @OnClick(R.id.save_btn)
    public void manageSave () {
        if (validateMandatoryFields()) {
            getData();
            RealmManger.getInstance(Notes.this).addorUpadte(m);
            Toast.makeText(getApplicationContext(),"Successful !",Toast.LENGTH_LONG).show();
        }
        finish();
        }

    public void getData(){
        m.setTitle(txtTitle.getText().toString());
        m.setNote(txtNote.getText().toString());

    }

    @Override
    public void onBackPressed() {
        if (validateMandatoryFields()) {
            saveDialog();
        }else {
            super.onBackPressed();
        }
    }

    public void saveDialog(){
        builder = new AlertDialog.Builder(this);
        builder.setCancelable(false);


        builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
                manageSave ();

            }
        });
        builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
                finish();
            }
        });

        dialog=builder.create();
        dialog.setTitle("Save changes");
        dialog.setMessage("are you sure want to leave? to save chages click on save button.");
        dialog.show();

    }

    private boolean validateMandatoryFields() {
        checkEmptyFields();

        if (hashMap.size() == Collections.frequency(hashMap.values(), true)) {
            return true;
        }
        return false;
    }

    private void checkEmptyFields() {
        isEmpty(txtNote);
        isEmpty(txtTitle);

    }

    private boolean isEmpty(EditText etText) {
        if (etText.getText().toString().trim().length() == 0) {
            hashMap.put(etText, false);
            return true;
        } else {
            hashMap.put(etText, true);
            return false;
        }
    }

}
