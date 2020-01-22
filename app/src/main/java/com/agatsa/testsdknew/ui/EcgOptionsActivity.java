package com.agatsa.testsdknew.ui;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;

import com.agatsa.sanketlife.callbacks.RegisterDeviceResponse;
import com.agatsa.sanketlife.development.Errors;
import com.agatsa.sanketlife.development.InitiateEcg;
import com.agatsa.sanketlife.models.EcgTypes;
import com.agatsa.testsdknew.Models.PatientModel;
import com.agatsa.testsdknew.R;
import com.agatsa.testsdknew.customviews.DialogUtil;
import com.agatsa.testsdknew.databinding.ActivityNewEcgOptionsBinding;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class EcgOptionsActivity extends AppCompatActivity {

   LinearLayout singleleadecg,chestleadecg,limbsixlead,Twelvelead, fitnessECG;
    TextView pairbtn;
    ProgressDialog mpd;
    ImageView syncimg,longsyncimg;
    private final static int REQUEST_CODE_ASK_PERMISSIONS = 1;
    private static final String CLIENT_ID = "5a3b4c16b4a56b000132f5d5b4580266565440bda51dcb4122d39844";
    private static final String[] REQUIRED_SDK_PERMISSIONS = new String[]{
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.CHANGE_WIFI_STATE};

    String pt_id;
    PatientModel patientModel;
    ActivityNewEcgOptionsBinding binding;
    SharedPreferences sharedPreferences;

    ArrayList <String> keys = new  ArrayList<>(Arrays.asList("SLF","CSLF","LISLF","TLF","LSLF"));



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_new_ecg_options);
//        setContentView(R.layout.activity_new_ecg_options);
        checkPermissions();
        pt_id = getIntent().getStringExtra("ptid");
        patientModel = getIntent().getParcelableExtra("patient");
//        if(patientModel==null){
//
//            DialogUtil.getOKDialog(this,"","barbag","");
//        }
//        pref = this.getSharedPreferences("sunyahealth", Context.MODE_PRIVATE);
        sharedPreferences = getSharedPreferences("sunyahealth", Context.MODE_PRIVATE);

        singleleadecg=findViewById(R.id.singleleadecg);
        chestleadecg=findViewById(R.id.chestleadecg);
        mpd=new ProgressDialog(this);
        limbsixlead=findViewById(R.id.limbsixlead);
        Twelvelead=findViewById(R.id.Twelvelead);
        fitnessECG =findViewById(R.id.FitnessECG);
        pairbtn=findViewById(R.id.pairtxt);
        syncimg=findViewById(R.id.syncimg);
        longsyncimg=findViewById(R.id.longsyncimg);

        binding.btComplete.setOnClickListener(view -> {

             int oneatleastSet =0;
            for(String s : keys){

                Log.d("rantestlastvalue","value of "+s+" "+String.valueOf(sharedPreferences.getInt(s,0)));
                if(sharedPreferences.getInt(s,0)==1){


                    Log.d("rantestlastvalue",String.valueOf(sharedPreferences.getInt(s,0)));
                    oneatleastSet =sharedPreferences.getInt(s,0);

                }


            }
            Log.d("rantestlastvalue",String.valueOf(oneatleastSet));

            if(oneatleastSet==1){


                Toast.makeText(EcgOptionsActivity.this, "Tick hune khalko back", Toast.LENGTH_SHORT).show();

                sharedPreferences.edit().putInt("ECGF",1);
                EcgOptionsActivity.this.onBackPressed();




            }
            else {
                Toast.makeText(EcgOptionsActivity.this, "Tick nahune khalko back", Toast.LENGTH_SHORT).show();

                EcgOptionsActivity.this.onBackPressed();




            }





        });






        singleleadecg.setOnClickListener(v -> {
//            intiaializing the first state in navigation
            SingleLeadECG.state=0;

            Intent i=new Intent(EcgOptionsActivity.this,SingleLeadECG.class);
            i.putExtra("patient",patientModel);
            i.putExtra("pt_id",pt_id);
            startActivity(i);

        });

        limbsixlead.setOnClickListener(v -> {
//            initializing first state in navigation
            LimbSixLead.again=false;
            LimbSixLead.leadIndex =0;
            LimbSixLead.x=0;
            Intent i=new Intent(EcgOptionsActivity.this, LimbSixLead.class);
            i.putExtra("patient",patientModel);
            i.putExtra("pt_id",pt_id);
            startActivity(i);

        });

        fitnessECG.setOnClickListener(v -> {
            FitnessECG.state=0;
            Intent i=new Intent(EcgOptionsActivity.this,FitnessECG.class);
            i.putExtra("patient",patientModel);
            startActivity(i);

        });

        chestleadecg.setOnClickListener(v -> {
            ChestSixLead.x=0;
            ChestSixLead.leadIndex=0;
            ChestSixLead.again=false;
            Intent i=new Intent(EcgOptionsActivity.this,ChestSixLead.class);
            i.putExtra("patient",patientModel);
            i.putExtra("pt_id",pt_id);
            startActivity(i);

        });

        Twelvelead.setOnClickListener(v -> {
            TwelveLeadEcg.x=0;
            TwelveLeadEcg.leadIndex=0;
            TwelveLeadEcg.again=false;
            Intent i=new Intent(EcgOptionsActivity.this,TwelveLeadEcg.class);
            i.putExtra("patient",patientModel);
            i.putExtra("pt_id",pt_id);
            startActivity(i);

        });

        syncimg.setOnClickListener(v -> {
            Intent i=new Intent(EcgOptionsActivity.this, HistoryActivity.class);
            i.putExtra("patient",patientModel);
            i.putExtra("type", EcgTypes.NORMAL_ECG);
            startActivity(i);

        });

        longsyncimg.setOnClickListener(v -> {
            Intent i=new Intent(EcgOptionsActivity.this, HistoryActivity.class);
            i.putExtra("patient",patientModel);
            i.putExtra("type", EcgTypes.LONG_ECG);
            startActivity(i);

        });
        pairbtn.setOnClickListener(view -> {
            mpd.show();
            mpd.setMessage("Registering...Please Wait");
            InitiateEcg initiateEcg = new InitiateEcg();
            initiateEcg.registerDevice(EcgOptionsActivity.this, CLIENT_ID, new RegisterDeviceResponse() {
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


        });











    }


    @Override
    public void onBackPressed() {

        DialogUtil.getOKCancelDialog(this, "", "Do you want to save the  test of " + patientModel.getPtName(), "Yes","No", (dialogInterface, i) -> {

          EcgOptionsActivity.super.onBackPressed();


        });





    }







    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_pair, menu);
        return true;
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

//    @Override
//    public void onBackPressed() {
//
//
//        super.onBackPressed();
//    }
}
