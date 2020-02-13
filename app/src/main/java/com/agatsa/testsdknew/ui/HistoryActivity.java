package com.agatsa.testsdknew.ui;

import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.agatsa.sanketlife.callbacks.SyncEcgCallBack;
import com.agatsa.sanketlife.callbacks.SyncLongEcgCallBack;
import com.agatsa.sanketlife.development.EcgConfig;
import com.agatsa.sanketlife.development.Errors;
import com.agatsa.sanketlife.development.InitiateEcg;
import com.agatsa.sanketlife.development.LongEcgConfig;
import com.agatsa.sanketlife.development.Success;
import com.agatsa.sanketlife.development.UserDetails;
import com.agatsa.sanketlife.models.EcgTypes;
import com.agatsa.testsdknew.BuildConfig;
import com.agatsa.testsdknew.Models.PatientModel;
import com.agatsa.testsdknew.R;
//import com.agatsa.sanketlife.models.EcgTypes;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class HistoryActivity extends AppCompatActivity implements HistoryCallback {

    private static final String CLIENT_ID = "5a3b4c16b4a56b000132f5d571f97b25b6554f0aaef54c3faee69746";
    private static final String SECRET_ID = "5a3b4c16b4a56b000132f5d58a378d1d9e494ebab4b0c3a57a6b4b07";

    Toolbar toolbar;
    List<EcgConfig> ecgConfigInternals = new ArrayList<>();
    List<LongEcgConfig> longEcgConfigInternals = new ArrayList<>();
    RecyclerView recyclerView;
    InitiateEcg initiateEcg;
    PatientModel patientModel;
    ImageView syncallimg;
    SharedPreferences pref;
    EcgConfig ecgConfig;
    TestHistoryAdapter testHistoryAdapter;
    String ptno="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        toolbar = findViewById(R.id.toolbar);
        pref = this.getSharedPreferences("sunyahealth", Context.MODE_PRIVATE);
        ptno = pref.getString("PTNO", "");
        patientModel = getIntent().getParcelableExtra("patient");
        syncallimg=findViewById(R.id.syncallimg);


        initiateEcg = new InitiateEcg();

        recyclerView = findViewById(R.id.recyclerView);

        if(getIntent().getStringExtra("type").equalsIgnoreCase(EcgTypes.NORMAL_ECG)) {
            ecgConfigInternals = initiateEcg.getListOfUnsyncedEcg(getApplicationContext(),"test");
          Log.d("List", String.valueOf(initiateEcg.getListOfUnsyncedEcg(getApplicationContext(),"test")));
            recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext(),LinearLayoutManager.VERTICAL,false));
           recyclerView.setAdapter(new HistoryAdapter(getApplicationContext(), ecgConfigInternals,this));
//            recyclerView.setAdapter(new TestHistoryAdapter(getApplicationContext(),ecgConfigInternals));
        } else {
            longEcgConfigInternals = initiateEcg.getListOfUnsyncedLongEcg(getApplicationContext(),"test");
            recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext(),LinearLayoutManager.VERTICAL,false));
           recyclerView.setAdapter(new HistoryStressAdapter(getApplicationContext(), longEcgConfigInternals,this));
        }

//        syncallimg.setOnClickListener(v -> {
//            if(ecgConfigInternals.isEmpty()){
//                Toast.makeText(this, "No Data To sync", Toast.LENGTH_SHORT).show();
//
//
//            }else{
//                for(int i=0;i<ecgConfigInternals.size();i++){
//                    syncEcgData(ecgConfigInternals.get(i));
//
//
//
//
//                }
//
//
//            }
//
//
//        });


    }





    public void syncEcgData(EcgConfig ecgConfig) {
        InitiateEcg initiateEcg = new InitiateEcg();
        initiateEcg.syncEcgData(getApplicationContext(), ecgConfig,
                new UserDetails(patientModel.getPtName(), patientModel.getPtAge(), patientModel.getPtSex()), true, SECRET_ID, new SyncEcgCallBack() {
                    @Override
                    public void onSuccess(Success success, EcgConfig ecgConfig) {
                        Toast.makeText(getApplicationContext(),success.getSuccessMsg(), Toast.LENGTH_SHORT).show();
                        refreshEcg();
                        String filePath = ecgConfig.getFileUrl();
                        Intent intent = new Intent(HistoryActivity.this, PdfViewActivity.class);
                        intent.putExtra("fileUrl", filePath);
                        startActivity(intent);
                    }

                    @Override
                    public void onError(Errors errors) {
                        Toast.makeText(getApplicationContext(), errors.getErrorMsg(), Toast.LENGTH_SHORT).show();
                    }
                });




    }






    public void viewPdf(EcgConfig ecgConfig) {
        try {
            if (!ecgConfig.getFileUrl().equals("")) {
                File file = new File(ecgConfig.getFileUrl());
                try {
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                    Uri photoURI = FileProvider.getUriForFile(HistoryActivity.this,
                            BuildConfig.APPLICATION_ID + ".provider",
                            file);
                    intent.setDataAndType(photoURI, "application/pdf");
                    startActivity(intent);
                } catch (ActivityNotFoundException e) {
                    Toast.makeText(this, "Please install a PDF Viewer.",
                            Toast.LENGTH_SHORT).show();
                }

            }
        } catch (NullPointerException e) {
            Log.d("sanketDoc", "Exception in opening pdf: " + e.toString());
            Toast.makeText(this, "No file found.",
                    Toast.LENGTH_SHORT).show();
        }
    }


    public void viewPdfStress(LongEcgConfig longEcgConfig) {
        try {
            if (!longEcgConfig.getFileUrl().equals("")) {
                File file = new File(longEcgConfig.getFileUrl());
                try {
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                    Uri photoURI = FileProvider.getUriForFile(HistoryActivity.this,
                            BuildConfig.APPLICATION_ID + ".provider",
                            file);
                    intent.setDataAndType(photoURI, "application/pdf");
                    startActivity(intent);
                } catch (ActivityNotFoundException e) {
                    Toast.makeText(this, "Please install a PDF Viewer.",
                            Toast.LENGTH_SHORT).show();
                }

            }
        } catch (NullPointerException e) {
            Log.d("sanketDoc", "Exception in opening pdf: " + e.toString());
            Toast.makeText(this, "No file found.",
                    Toast.LENGTH_SHORT).show();
        }
    }


    public void syncStressData(LongEcgConfig longEcgConfig) {
        InitiateEcg initiateEcg = new InitiateEcg();
        initiateEcg.syncLongEcgData(getApplicationContext(), longEcgConfig,
                new UserDetails(patientModel.getPtName(), patientModel.getPtAge(), patientModel.getPtSex()), true, SECRET_ID, new SyncLongEcgCallBack() {
                    @Override
                    public void onSuccess(Success success, LongEcgConfig longEcgConfig) {
                        Toast.makeText(getApplicationContext(),success.getSuccessMsg(), Toast.LENGTH_SHORT).show();
                        refreshStress();
                        String filePath = longEcgConfig.getFileUrl();
                        Intent intent = new Intent(HistoryActivity.this, PdfViewActivity.class);
                        intent.putExtra("fileUrl", filePath);
                        startActivity(intent);
                    }

                    @Override
                    public void onError(Errors errors) {
                        Toast.makeText(getApplicationContext(), errors.getErrorMsg(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void refreshEcg(){
        ecgConfigInternals = initiateEcg.getListOfUnsyncedEcg(getApplicationContext(),"test");
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext(),LinearLayoutManager.VERTICAL,false));
        recyclerView.setAdapter(new HistoryAdapter(getApplicationContext(), ecgConfigInternals,this));
    }

    private void refreshStress(){
        longEcgConfigInternals = initiateEcg.getListOfUnsyncedLongEcg(getApplicationContext(),"test");
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext(),LinearLayoutManager.VERTICAL,false));
        recyclerView.setAdapter(new HistoryStressAdapter(getApplicationContext(), longEcgConfigInternals,this));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                HistoryActivity.super.onBackPressed();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        HistoryActivity.super.onBackPressed();


    }


}
