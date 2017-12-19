package com.example.ganeshr.easykeep.utils;

import java.util.ArrayList;

/**
 * Created by root on 25/9/17.
 */

interface PermissionResultCallback {

    void PermissionGranted(int request_code);

    void PartialPermissionGranted(int request_code, ArrayList<String> granted_permissions);

    void PermissionDenied(int request_code);

    void NeverAskAgain(int request_code);
}
