package com.agatsa.testsdknew.ui;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Softwel on 4/6/2018.
 */
public class PermissionUtils extends AppCompatActivity implements ActivityCompat.OnRequestPermissionsResultCallback {

    public static boolean all_permission_allowed;
    final public static int REQUEST_CODE_ASK_MULTIPLE_PERMISSIONS = 124;

    public void hasSelfPermission(final Context context) {
        List<String> permissionsNeeded = new ArrayList<String>();

        final List<String> permissionsList = new ArrayList<String>();
        if (!addPermission(permissionsList, Manifest.permission.ACCESS_NETWORK_STATE, context))
            permissionsNeeded.add("Network State");
        if (!addPermission(permissionsList, Manifest.permission.INTERNET, context))
            permissionsNeeded.add("Internet");
        if (!addPermission(permissionsList, Manifest.permission.WRITE_EXTERNAL_STORAGE, context))
            permissionsNeeded.add("Write External Storage");
        if (!addPermission(permissionsList, Manifest.permission.READ_EXTERNAL_STORAGE, context))
            permissionsNeeded.add("Read External Storage");
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        if (!addPermission(permissionsList, Manifest.permission.READ_PHONE_STATE, context))
            permissionsNeeded.add("Read Phone State");
//        }
        if (!addPermission(permissionsList, Manifest.permission.CAMERA, context))
            permissionsNeeded.add("Camera");
        if (!addPermission(permissionsList, Manifest.permission.BLUETOOTH, context))
            permissionsNeeded.add("Access Fine Location");
        if (!addPermission(permissionsList, Manifest.permission.BLUETOOTH_ADMIN, context))
            permissionsNeeded.add("Access Coarse Location");
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
//            if (!addPermission(permissionsList, Manifest.permission.FOREGROUND_SERVICE, context))
//                permissionsNeeded.add("Foreground service");
//        }

        if (permissionsList.size() > 0) {
            if (permissionsNeeded.size() > 0) {
                // Need Rationale
                String message = "You need to grant access to " + permissionsNeeded.get(0);
                for (int i = 1; i < permissionsNeeded.size(); i++) {
                    message = message + ", " + permissionsNeeded.get(i);
                }
                Alert("Permission Request!", message, context,
                        (dialog, which) -> {
                            dialog.dismiss();
                            ActivityCompat.requestPermissions((Activity) context, permissionsList.toArray(new String[permissionsList.size()]),
                                    REQUEST_CODE_ASK_MULTIPLE_PERMISSIONS);
                        });
                all_permission_allowed = false;
                return;
            }
            ActivityCompat.requestPermissions((Activity) context, permissionsList.toArray(new String[permissionsList.size()]),
                    REQUEST_CODE_ASK_MULTIPLE_PERMISSIONS);
        } else {
            all_permission_allowed = true;
        }
    }

    private boolean addPermission(List<String> permissionsList, String permission, Context context) {
        if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
            permissionsList.add(permission);
            // Check for Rationale Option
            if (!ActivityCompat.shouldShowRequestPermissionRationale((Activity) context, permission))
                return false;
        }
        return true;
    }

    public void Alert(String title, String message, Context context, DialogInterface.OnClickListener dialogInterface) {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(
                context);
        alertDialog.setTitle(title);
        alertDialog.setIcon(android.R.drawable.ic_dialog_alert);
        alertDialog.setCancelable(false);
        alertDialog.setMessage(message);
        alertDialog.setPositiveButton("OK", dialogInterface);
        alertDialog.show();
    }
}