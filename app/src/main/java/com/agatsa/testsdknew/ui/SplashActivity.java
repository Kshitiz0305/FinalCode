package com.agatsa.testsdknew.ui;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;

import com.agatsa.testsdknew.R;

import java.util.HashMap;
import java.util.Map;


public class SplashActivity extends Activity {
    private static final long SPLASH_DISPLAY_LENGTH = 4000;
    private static final String CRS_PREF = "cr_pref";
    SharedPreferences pref;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        pref = this.getSharedPreferences("sunyahealth", Context.MODE_PRIVATE);

            navigateToNextActivity();
            Toast.makeText(this,"Ready To Start",Toast.LENGTH_SHORT).show();

    }

    public void navigateToNextActivity() {
//        final LabDB db = new LabDB(getApplicationContext());
//        final int last_id = db.getLastID("pt_details");
//         now normal navigation
        //        if (last_id != 0) {
//            if (db.getCountForLastDetails(last_id) == 0) {
//                AlertDialog.Builder alertDialog = new AlertDialog.Builder(
//                        SplashActivity.this);
//                alertDialog.setTitle("Warning!!");
//                alertDialog.setMessage("Do you wish to continue with the last detail that has not been completed?\n\n" +
//                        "Note: If Discarded the last detail that has not been completed will be deleted.");
//                alertDialog.setCancelable(false);
//                alertDialog.setPositiveButton("CONTINUE",
//                        (dialog, which) -> new Handler().postDelayed(() -> {
//                            /* Create an Intent that will start the Menu-Activity. */
//                            //to reset value
//                            pref = getSharedPreferences(CRS_PREF, MODE_PRIVATE);
//                            editor = pref.edit();
//                            editor.apply();
//
//                            pref = SplashActivity.this.getSharedPreferences("sunyahealth", Context.MODE_PRIVATE);
//                            SharedPreferences.Editor editor2 = pref.edit();
//                            editor2.putInt("pt_id", last_id);
//                            editor2.apply();
//
//// this is to be implemented for now
//                            Intent patientIntent = new Intent(SplashActivity.this, PatientActivity.class);
//                            SplashActivity.this.startActivity(patientIntent);
//                            SplashActivity.this.finish();
////   This is in development phase
////                            Intent patientIntent = new Intent(SplashActivity.this, UserLogIn.class);
////                            SplashActivity.this.startActivity(patientIntent);
////                            SplashActivity.this.finish();
//
//
//                        }, SPLASH_DISPLAY_LENGTH));
//                alertDialog.setNegativeButton("DISCARD",
//                        (dialog, which) -> {
//                            db.deleteData("pt_details", "id="+last_id);
//                            db.deleteData("vital_sign", "ptno='"+last_id +"'");
//                            normalNavigation();
//                        });
//                alertDialog.show();
//            } else {
//                normalNavigation();
//            }
//        } else {
//            normalNavigation();
//        }

        normalNavigation();

    }

    public void normalNavigation() {
        new Handler().postDelayed(() -> {
            /* Create an Intent that will start the Menu-Activity. */
            //to reset value
            pref = SplashActivity.this.getSharedPreferences("sunyahealth", Context.MODE_PRIVATE);
            editor = pref.edit();
            editor.apply();
// this is to be done now
            Intent mainIntent = new Intent(SplashActivity.this, PatientEntryActivity.class);
            SplashActivity.this.startActivity(mainIntent);
            SplashActivity.this.finish();

            //   This is in development phase
//                            Intent patientIntent = new Intent(SplashActivity.this, UserLogIn.class);
//                            SplashActivity.this.startActivity(patientIntent);
//                            SplashActivity.this.finish();
        }, SPLASH_DISPLAY_LENGTH);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions,
                                           int[] grantResults) {
        if (requestCode == PermissionUtils.REQUEST_CODE_ASK_MULTIPLE_PERMISSIONS) {
            Map<String, Integer> perms = new HashMap<String, Integer>();
            // Initial
            perms.put(Manifest.permission.ACCESS_NETWORK_STATE, PackageManager.PERMISSION_GRANTED);
            perms.put(Manifest.permission.INTERNET, PackageManager.PERMISSION_GRANTED);
            perms.put(Manifest.permission.BLUETOOTH, PackageManager.PERMISSION_GRANTED);
            perms.put(Manifest.permission.BLUETOOTH_ADMIN, PackageManager.PERMISSION_GRANTED);
            perms.put(Manifest.permission.CAMERA, PackageManager.PERMISSION_GRANTED);
            perms.put(Manifest.permission.WRITE_EXTERNAL_STORAGE, PackageManager.PERMISSION_GRANTED);
            perms.put(Manifest.permission.READ_EXTERNAL_STORAGE, PackageManager.PERMISSION_GRANTED);
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
            perms.put(Manifest.permission.READ_PHONE_STATE, PackageManager.PERMISSION_GRANTED);
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
//                perms.put(Manifest.permission.FOREGROUND_SERVICE, PackageManager.PERMISSION_GRANTED);
//            }

            // Fill with results
            for (int i = 0; i < permissions.length; i++)
                perms.put(permissions[i], grantResults[i]);
            // Check for permissions   && perms.get(Manifest.permission.ACCESS_WIFI_STATE) == PackageManager.PERMISSION_GRANTED
            if (perms.get(Manifest.permission.ACCESS_NETWORK_STATE) == PackageManager.PERMISSION_GRANTED
                    && perms.get(Manifest.permission.INTERNET) == PackageManager.PERMISSION_GRANTED
                    && perms.get(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED
                    && perms.get(Manifest.permission.BLUETOOTH) == PackageManager.PERMISSION_GRANTED
                    && perms.get(Manifest.permission.BLUETOOTH_ADMIN) == PackageManager.PERMISSION_GRANTED
                    && perms.get(Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED
                    && perms.get(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED
                    && perms.get(Manifest.permission.READ_PHONE_STATE) == PackageManager.PERMISSION_GRANTED
            ) {
                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    ActivityCompat.requestPermissions(this,
                            new String[]{Manifest.permission.READ_PHONE_STATE},
                            3);
                    return;
                }

                PermissionUtils.all_permission_allowed = true;
                navigateToNextActivity();
                // All Permissions Granted
                Toast.makeText(this, "All permission accepted", Toast.LENGTH_SHORT).show();
//                }
            } else {
                PermissionUtils.all_permission_allowed = false;
                this.finish();
                // Permission Denied
                Toast.makeText(this, "Some Permission is Denied", Toast.LENGTH_SHORT)
                        .show();
            }
        } else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

}