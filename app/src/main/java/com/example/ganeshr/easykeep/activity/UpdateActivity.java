package com.example.ganeshr.easykeep.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ganeshr.easykeep.R;
import com.example.ganeshr.easykeep.model.NotesModel;
import com.example.ganeshr.easykeep.rest.RealmManger;
import com.example.ganeshr.easykeep.utils.Pref;
import com.example.ganeshr.easykeep.utils.Utility;

import java.text.DateFormat;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class UpdateActivity extends AppCompatActivity {
    NotesModel model;
    @BindView(R.id.update_btn)
    Button updateButton;
    @BindView(R.id.img_edit)
    ImageView img_edit;
    @BindView(R.id.save_btn)
    Button saveButton;

    @BindView(R.id.txt_title)
    EditText txt_title;

    @BindView(R.id.txt_note)
    EditText txt_note;

    @BindView(R.id.tv_date)
    TextView tv_date;
    @BindView(R.id.fab_share)
    FloatingActionButton fab_share;
    AlertDialog dialog;
    AlertDialog.Builder builder;

    String strTitle = "";
    String strNote = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes);
        ButterKnife.bind(this);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        saveButton.setVisibility(View.GONE);
        showHideFabShare(View.VISIBLE, View.GONE);
        setDataAtLaunch();

        fab_share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Click action

                Intent share = new Intent(Intent.ACTION_SEND);
                share.setType("text/plain");

                String shareMsg = txt_title.getText().toString() +"\n \n" + txt_note.getText().toString();
                share.putExtra(Intent.EXTRA_TEXT, shareMsg);
                startActivity(Intent.createChooser(share, "Share using"));

            }
        });


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                onBackPressed();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }


    public void showHideFabShare(int fabVal, int buttonVal) {
        fab_share.setVisibility(fabVal);
        updateButton.setVisibility(buttonVal);

        if (buttonVal == View.VISIBLE) {
            txt_note.setFocusableInTouchMode(true);
            txt_title.setFocusableInTouchMode(true);
        } else {
            txt_note.clearFocus();
            txt_title.clearFocus();
            txt_note.setFocusableInTouchMode(false);
            txt_title.setFocusableInTouchMode(false);
        }
    }

    @OnClick({R.id.update_btn})
    public void manageUpdate() {
        onClickedUpdate();
    }

    @OnClick(R.id.img_edit)
    public void editAction() {
        showHideFabShare(View.GONE, View.VISIBLE);
        txt_note.requestFocus();
        txt_note.setSelection(txt_note.getText().length());
    }

    public void saveDialog() {

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

    }

    private void setDataAtLaunch() {
        model = getIntent().getParcelableExtra("AddUpdate");
        txt_title.setText(model.getTitle());
        txt_note.setText(model.getNote());

        strTitle = model.getTitle();
        strNote = model.getNote();
        tv_date.setText(model.getDate());
        Log.d("date--", "" + model.getDate());

    }

    private void onClickedUpdate() {
        model.setTitle(txt_title.getText().toString());
        model.setNote(txt_note.getText().toString());
        model.setDate(DateFormat.getDateTimeInstance().format(new Date()));
        strTitle = txt_title.getText().toString();
        strNote = txt_note.getText().toString();
        RealmManger.update(model);
        showHideFabShare(View.VISIBLE, View.GONE);
        Utility.hideSoftKeyboard(UpdateActivity.this);

    }

    @Override
    public void onBackPressed() {
        if (strTitle.length() < txt_title.getText().length() || strNote.length() < txt_note.getText().length()) {
            saveDialog();
        } else {
            super.onBackPressed();
        }
    }

}
