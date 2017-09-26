package com.example.ganeshr.easykeep.model;

import android.os.Parcel;
import android.os.Parcelable;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by ganeshr on 23/3/17.
 */

public class NotesModel extends RealmObject implements Parcelable {

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
    String title, note;
    String date;
    @PrimaryKey
    String id;

    public NotesModel(String title, String note, String id, String date) {
        this.title = title;
        this.note = note;
        this.id = id;
        this.date = date;
    }

    public NotesModel() {
    }

    protected NotesModel(Parcel in) {
        title = in.readString();
        note = in.readString();
        date = in.readString();
        id = in.readString();
    }

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

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeString(note);
        dest.writeString(date);
        dest.writeString(String.valueOf(id));
    }
}
