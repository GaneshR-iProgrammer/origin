package com.example.ganeshr.easykeep.rest;

import android.content.Context;
import android.util.Log;

import com.example.ganeshr.easykeep.model.NotesModel;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmResults;

/**
 * Created by ganeshr on 23/3/17.
 */

public class RealmManger {


    private static final java.util.UUID UUID = null;
    private static Realm realm;
    private static RealmManger realmManager;
    private String realmName="KeepEasy";

    private RealmManger(Context context){
        if(realm == null){
            realm.init(context);

            RealmConfiguration config0 = new RealmConfiguration.Builder()
                    .name(realmName)
                    .deleteRealmIfMigrationNeeded()
                    .build();

            realm=Realm.getInstance(config0);
        }
    }


    public static RealmManger getInstance(Context context) {
        if (realmManager==null){
            realmManager=new RealmManger(context);
        }
        return realmManager;
    }


    public static void addorUpadte(NotesModel model){
        try{
            realm.beginTransaction();
            model.setId(UUID.randomUUID().toString());
            realm.copyToRealmOrUpdate(model);
            realm.commitTransaction();


        }catch (Exception e){

        }

    }


    public RealmResults<NotesModel> getAll() {
        RealmResults<NotesModel> results;
            realm.beginTransaction();
        results = realm.where(NotesModel.class).findAll();
        realm.commitTransaction();


        return results;
    }


    public static void deleteUser(NotesModel m){
        realm.beginTransaction();
        m.deleteFromRealm();
        realm.commitTransaction();


        Log.i("Row Deleted",">>>>>>>");

    }

    public static RealmManger update(NotesModel m) {
        NotesModel model=realm.where(NotesModel.class).contains("id", m.getId()).findFirst();

        realm.beginTransaction();
        model.setTitle(m.getTitle());
        model.setNote(m.getNote());
        realm.commitTransaction();

        Log.i("Row Edited",">>>>>>>");

        return  null;

    }



}
