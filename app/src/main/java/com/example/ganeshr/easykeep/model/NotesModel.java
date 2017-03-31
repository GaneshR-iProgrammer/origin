package com.example.ganeshr.easykeep.model;

import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by ganeshr on 23/3/17.
 */

public class NotesModel extends RealmObject implements Parcelable {

    String title,note;
    @PrimaryKey
    String id;
    public NotesModel(String title, String note, String id) {
        this.title = title;
        this.note = note;
        this.id = id;
    }

    public NotesModel() {
    }

    protected NotesModel(Parcel in) {
        title = in.readString();
        note = in.readString();
        id = in.readString();
    }

    public static final Creator<NotesModel> CREATOR = new Creator<NotesModel>() {
        @Override
        public NotesModel createFromParcel(Parcel in) {
            return new NotesModel(in);
        }

        @Override
        public NotesModel[] newArray(int size) {
            return new NotesModel[size];
        }
    };

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeString(note);
        dest.writeString(String.valueOf(id));
    }
}
