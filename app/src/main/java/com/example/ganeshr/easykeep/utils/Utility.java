package com.example.ganeshr.easykeep.utils;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

/**
 * Created by root on 22/9/17.
 */

public class Utility {

    public static void clearPref(Pref pref){
        pref.setNoteText("");
        pref.setNoteTilte("");
        pref.setDate("");
    }

    public static void hideSoftKeyboard(Activity activity){

        // Check if no view has focus:
        View view = activity.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager)activity.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

}
