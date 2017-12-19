package com.example.ganeshr.easykeep.utils;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by root on 25/9/17.
 */

public class CheckPermissions {

    public static final int REQUEST_PERMISSION_ALL = 5555;
    public static final int REQUEST_FINE_LOCATION = 5556;
    public static final int EXTERNAL_STORAGE = 854;
    public static final int CAMERA = 1444;

    Context context;
    Activity current_activity;
    PermissionResultCallback permissionResultCallback;
    ArrayList<String> permission_list = new ArrayList<>();
    ArrayList<String> listPermissionsNeeded = new ArrayList<>();
    String dialog_content = "";
    int req_code;


    public CheckPermissions(Context context) {
        this.context = context;
        this.current_activity = (Activity) context;

        permissionResultCallback = (PermissionResultCallback) context;
    }


    public static boolean checkStoragePermission(Activity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            if (ContextCompat.checkSelfPermission(activity, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                // Should we show an explanation?
                if (activity.shouldShowRequestPermissionRationale(Manifest.permission.READ_EXTERNAL_STORAGE)) {
                    activity.requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, CheckPermissions.EXTERNAL_STORAGE);
                    return false;
                } else {
                    activity.requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, CheckPermissions.EXTERNAL_STORAGE);
                    return false;
                }
            }
            return true;
        } else {
            return true;
        }

    }


    public static boolean checkCameraPermission(Activity activity, int requestCode) {

        if (ContextCompat.checkSelfPermission(activity, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(activity, Manifest.permission.CAMERA)) {
                ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.CAMERA}, requestCode);
                return false;
            } else {
                ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.CAMERA}, requestCode);
                return false;
            }
        }
        return true;
    }

    public static void openPermissionConfirmDialog(final Activity context, final String permission, final int requestCode, String message, Boolean showAppSetting) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage(message);

        if (!showAppSetting) {
            builder.setPositiveButton("Allow", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    ActivityCompat.requestPermissions(context, new String[]{permission}, requestCode);
                    dialog.cancel();
                }
            });
        } else if (showAppSetting) {
            builder.setPositiveButton("Setting", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                            Uri.parse("package:" + context.getApplicationContext().getPackageName()));
                    context.startActivity(intent);
                }
            });
        }

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        builder.show();
    }



    /**
     * Check the API Level & Permission
     *
     * @param permissions
     * @param dialog_content
     * @param request_code
     */

    public void check_permission(ArrayList<String> permissions, String dialog_content, int request_code) {
        this.permission_list = permissions;
        this.dialog_content = dialog_content;
        this.req_code = request_code;

        if (Build.VERSION.SDK_INT >= 23) {
            if (checkAndRequestPermissions(permissions, request_code)) {
                permissionResultCallback.PermissionGranted(request_code);
                // Log.i("all permissions", "granted");
                // Log.i("proceed", "to callback");
            }
        } else {
            permissionResultCallback.PermissionGranted(request_code);

            // Log.i("all permissions", "granted");
            // Log.i("proceed", "to callback");
        }

    }




    /**
     * Check and request the Permissions
     *
     * @param permissions
     * @param request_code
     * @return
     */

    private boolean checkAndRequestPermissions(ArrayList<String> permissions, int request_code) {

        if (permissions.size() > 0) {
            listPermissionsNeeded = new ArrayList<>();

            for (int i = 0; i < permissions.size(); i++) {
                int hasPermission = ContextCompat.checkSelfPermission(current_activity, permissions.get(i));

                if (hasPermission != PackageManager.PERMISSION_GRANTED) {
                    listPermissionsNeeded.add(permissions.get(i));
                }

            }

            if (!listPermissionsNeeded.isEmpty()) {
                ActivityCompat.requestPermissions(current_activity, listPermissionsNeeded.toArray(new String[listPermissionsNeeded.size()]), request_code);
                return false;
            }
        }

        return true;
    }




    /**
     * @param requestCode
     * @param permissions
     * @param grantResults
     */
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case REQUEST_PERMISSION_ALL:
                if (grantResults.length > 0) {
                    Map<String, Integer> perms = new HashMap<>();

                    for (int i = 0; i < permissions.length; i++) {
                        perms.put(permissions[i], grantResults[i]);
                    }

                    final ArrayList<String> pending_permissions = new ArrayList<>();

                    for (int i = 0; i < listPermissionsNeeded.size(); i++) {
                        if (perms.get(listPermissionsNeeded.get(i)) != PackageManager.PERMISSION_GRANTED) {
                            if (ActivityCompat.shouldShowRequestPermissionRationale(current_activity, listPermissionsNeeded.get(i)))
                                pending_permissions.add(listPermissionsNeeded.get(i));
                            else {
                                // Log.i("Go to settings","and enable permissions");
                                permissionResultCallback.NeverAskAgain(req_code);
                                //Toast.makeText(current_activity, context.getResources().getString(R.string.go_to_settings), Toast.LENGTH_LONG).show();
                                return;
                            }
                        }

                    }

                    if (pending_permissions.size() > 0) {
                        showMessageOKCancel(dialog_content,
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {

                                        switch (which) {
                                            case DialogInterface.BUTTON_POSITIVE:
                                                check_permission(permission_list, dialog_content, req_code);
                                                break;
                                            case DialogInterface.BUTTON_NEGATIVE:
                                                // Log.i("permisson","not fully given");
                                                if (permission_list.size() == pending_permissions.size())
                                                    permissionResultCallback.PermissionDenied(req_code);
                                                else
                                                    permissionResultCallback.PartialPermissionGranted(req_code, pending_permissions);
                                                break;
                                        }


                                    }
                                });

                    } else {
                        // Log.i("all","permissions granted");
                        // Log.i("proceed","to next step");
                        permissionResultCallback.PermissionGranted(req_code);

                    }


                }
                break;
        }
    }


    /**
     * Explain why the app needs permissions
     *
     * @param message
     * @param okListener
     */
    private void showMessageOKCancel(String message, DialogInterface.OnClickListener okListener) {
        new android.support.v7.app.AlertDialog.Builder(current_activity)
                .setMessage(message)
                .setPositiveButton("Ok", okListener)
                .setNegativeButton("Cancel", okListener)
                .create()
                .show();
    }


    public static boolean checkCameraPermssion(Activity context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            if (ContextCompat.checkSelfPermission(context, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                // Should we show an explanation?
                if (ActivityCompat.shouldShowRequestPermissionRationale(context, Manifest.permission.CAMERA)) {
                    ActivityCompat.requestPermissions(context, new String[]{Manifest.permission.CAMERA}, CheckPermissions.CAMERA);
                    return false;
                } else {
                    ActivityCompat.requestPermissions(context, new String[]{Manifest.permission.CAMERA}, CheckPermissions.CAMERA);
                    return false;
                }
            } else {
                return true;
            }

        } else {
            return true;
        }
    }



}
