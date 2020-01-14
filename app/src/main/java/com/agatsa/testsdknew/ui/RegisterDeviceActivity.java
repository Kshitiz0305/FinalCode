package com.agatsa.testsdknew.ui;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.agatsa.sanketlife.callbacks.RegisterDeviceResponse;
import com.agatsa.sanketlife.development.Errors;
import com.agatsa.sanketlife.development.InitiateEcg;
import com.agatsa.testsdknew.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class RegisterDeviceActivity extends AppCompatActivity {

    Toolbar toolbar;
    Button registerbtn;
    Button nxtbtn;


    ProgressDialog mpd;

    InitiateEcg initiateEcg = new InitiateEcg();
    private static final String CLIENT_ID = "5a3b4c16b4a56b000132f5d5b4580266565440bda51dcb4122d39844";
    private static final String SECRET_ID = "5a3b4c16b4a56b000132f5d5746be305d56c49e49cc88b12ccb05d71";


    /**
     * Permissions that need to be explicitly requested from end user.
     */
    private static final String[] REQUIRED_SDK_PERMISSIONS = new String[]{
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE};


    private final static int REQUEST_CODE_ASK_PERMISSIONS = 1;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_device);
        checkPermissions();
        mpd = new ProgressDialog(this);
        toolbar = findViewById(R.id.toolbar);
        registerbtn = findViewById(R.id.register);
        nxtbtn = findViewById(R.id.next);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Registration");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        registerbtn.setOnClickListener(view -> {
            mpd.show();
            mpd.setMessage("Registering...Please Wait");
            InitiateEcg initiateEcg = new InitiateEcg();
            initiateEcg.registerDevice(this, CLIENT_ID, new RegisterDeviceResponse() {
                @Override
                public void onSuccess(String s) {
                    mpd.dismiss();
                    Toast.makeText(RegisterDeviceActivity.this, s, Toast.LENGTH_SHORT).show();

                }

                @Override
                public void onError(Errors errors) {
                    mpd.hide();
                    Toast.makeText(RegisterDeviceActivity.this, errors.getErrorMsg(), Toast.LENGTH_SHORT).show();


                }
            });


            });


            nxtbtn.setOnClickListener(view1 -> {
                Intent i = new Intent(RegisterDeviceActivity.this, EcgOptionsActivity.class);
                startActivity(i);
            });

}




    protected void checkPermissions() {
        final List<String> missingPermissions = new ArrayList<String>();
        // check all required dynamic permissions
        for (final String permission : REQUIRED_SDK_PERMISSIONS) {
            final int result = ContextCompat.checkSelfPermission(this, permission);
            if (result != PackageManager.PERMISSION_GRANTED) {
                missingPermissions.add(permission);
            }
        }
        if (!missingPermissions.isEmpty()) {
            // request all missing permissions
            final String[] permissions = missingPermissions
                    .toArray(new String[missingPermissions.size()]);
            ActivityCompat.requestPermissions(this, permissions, REQUEST_CODE_ASK_PERMISSIONS);
        } else {
            final int[] grantResults = new int[REQUIRED_SDK_PERMISSIONS.length];
            Arrays.fill(grantResults, PackageManager.PERMISSION_GRANTED);
            onRequestPermissionsResult(REQUEST_CODE_ASK_PERMISSIONS, REQUIRED_SDK_PERMISSIONS,
                    grantResults);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[],
                                           @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CODE_ASK_PERMISSIONS:
                for (int index = permissions.length - 1; index >= 0; --index) {
                    if (grantResults[index] != PackageManager.PERMISSION_GRANTED) {
                        // exit the app if one permission is not granted
                        Toast.makeText(this, "Required permission '" + permissions[index]
                                + "' not granted, exiting", Toast.LENGTH_LONG).show();
                        finish();
                        return;
                    }
                }
                // all permissions were granted
//                initialize();
                break;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Intent intent = new Intent(RegisterDeviceActivity.this, PatientActivity.class);
                startActivity(intent);
                finish();
                break;
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(RegisterDeviceActivity.this, PatientActivity.class);
        startActivity(intent);

    }




}
