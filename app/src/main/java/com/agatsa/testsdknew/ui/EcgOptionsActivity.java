package com.agatsa.testsdknew.ui;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
import com.agatsa.sanketlife.models.EcgTypes;
import com.agatsa.testsdknew.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class EcgOptionsActivity extends AppCompatActivity {

   LinearLayout singleleadecg,chestleadecg,limbsixlead,Twelvelead,FitnessECG;
    Toolbar toolbar;
    Button pairbtn;
    ProgressDialog mpd;
    ImageView syncimg;
    private final static int REQUEST_CODE_ASK_PERMISSIONS = 1;
    private static final String CLIENT_ID = "5a3b4c16b4a56b000132f5d5b4580266565440bda51dcb4122d39844";
    private static final String[] REQUIRED_SDK_PERMISSIONS = new String[]{
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION};


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_ecg_options);
        checkPermissions();
        toolbar = findViewById(R.id.toolbar);
        mpd=new ProgressDialog(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("ECG");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        singleleadecg=findViewById(R.id.singleleadecg);
        chestleadecg=findViewById(R.id.chestleadecg);
        limbsixlead=findViewById(R.id.limbsixlead);
        Twelvelead=findViewById(R.id.Twelvelead);
        FitnessECG=findViewById(R.id.FitnessECG);
        pairbtn=findViewById(R.id.register);
        syncimg=findViewById(R.id.syncimg);





        singleleadecg.setOnClickListener(v -> {
            Intent i=new Intent(EcgOptionsActivity.this,SingleLeadECG.class);
            startActivity(i);

        });

        limbsixlead.setOnClickListener(v -> {
            Intent i=new Intent(EcgOptionsActivity.this, LimbSixLead.class);
            startActivity(i);

        });

        FitnessECG.setOnClickListener(v -> {
            Intent i=new Intent(EcgOptionsActivity.this,FitnessECG.class);
            startActivity(i);

        });

        chestleadecg.setOnClickListener(v -> {
            Intent i=new Intent(EcgOptionsActivity.this,ChestSixLead.class);
            startActivity(i);

        });

        Twelvelead.setOnClickListener(v -> {
            Intent i=new Intent(EcgOptionsActivity.this,TwelveLeadEcg.class);
            startActivity(i);

        });

        syncimg.setOnClickListener(v -> {
            Intent i=new Intent(EcgOptionsActivity.this, HistoryActivity.class);
            i.putExtra("type", EcgTypes.NORMAL_ECG);
            startActivity(i);

        });











    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_pair, menu);
        return true;
    }






    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(EcgOptionsActivity.this, RegisterDeviceActivity.class);
        startActivity(intent);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.action_pair:
                mpd.show();
                mpd.setMessage("Registering...Please Wait");
                InitiateEcg initiateEcg = new InitiateEcg();
                initiateEcg.registerDevice(this, CLIENT_ID, new RegisterDeviceResponse() {
                    @Override
                    public void onSuccess(String s) {
                        mpd.dismiss();
                        Toast.makeText(EcgOptionsActivity.this, s, Toast.LENGTH_SHORT).show();

                    }

                    @Override
                    public void onError(Errors errors) {
                        mpd.hide();
                        Toast.makeText(EcgOptionsActivity.this, errors.getErrorMsg(), Toast.LENGTH_SHORT).show();


                    }
                });

                return true;
            case android.R.id.home:
                Intent intent = new Intent(EcgOptionsActivity.this, RegisterDeviceActivity.class);
                startActivity(intent);
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
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




}
