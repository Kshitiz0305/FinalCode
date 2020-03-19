package com.agatsa.testsdknew.ui;

import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class LongEcgHistoryActivity extends AppCompatActivity implements HistoryCallback {
    private static final String CLIENT_ID = "5a3b4c16b4a56b000132f5d571f97b25b6554f0aaef54c3faee69746";
    private static final String SECRET_ID = "5a3b4c16b4a56b000132f5d58a378d1d9e494ebab4b0c3a57a6b4b07";

    Toolbar toolbar;
    List<LongEcgConfig> longEcgConfigInternals = new ArrayList<>();
    RecyclerView recyclerView;
    InitiateEcg initiateEcg;
    PatientModel patientModel;
    ImageView syncallimg;
    SharedPreferences pref;
    ProgressDialog dialog;
    String ptno="";
    int i;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        toolbar = findViewById(R.id.toolbar);
        pref = this.getSharedPreferences("sunyahealth", Context.MODE_PRIVATE);
        ptno = pref.getString("PTNO", "");
        patientModel = getIntent().getParcelableExtra("patient");
        syncallimg=findViewById(R.id.syncallimg);
        dialog=new ProgressDialog(this);


        initiateEcg = new InitiateEcg();

        recyclerView = findViewById(R.id.recyclerView);

        if(getIntent().getStringExtra("type").equalsIgnoreCase(EcgTypes.LONG_ECG)) {
            longEcgConfigInternals = initiateEcg.getListOfUnsyncedLongEcg(getApplicationContext(),"test");
            recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext(),LinearLayoutManager.VERTICAL,false));
            recyclerView.setAdapter(new HistoryStressAdapter(getApplicationContext(), longEcgConfigInternals,this));
        } 

        syncallimg.setOnClickListener(v -> {
            dialog.setMessage("Syncing all data");
            dialog.show();
            if(longEcgConfigInternals.isEmpty()){
                Toast.makeText(this, "No Data Available", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
               LongEcgHistoryActivity.super.onBackPressed();

            }else{
                setlongecgButtonSyncAll(longEcgConfigInternals.get(0));


            }




        });


    }

    @Override
    public void syncEcgData(EcgConfig ecgConfig) {

    }

    public void setlongecgButtonSyncAll(LongEcgConfig longEcgConfig) {
        initiateEcg.syncLongEcgData(getApplicationContext(), longEcgConfig, new UserDetails(patientModel.getPtName(), patientModel.getPtAge(), patientModel.getPtSex()),
                true, SECRET_ID, new SyncLongEcgCallBack() {

                    @Override
                    public void onSuccess(Success success, LongEcgConfig longEcgConfig) {
                        if(longEcgConfig.isSynced()){
                            Log.e("Sync ", "onSuccess: " + longEcgConfig.getFileUrl());
                            i++;
                            if(i<longEcgConfigInternals.size()) {
                                setlongecgButtonSyncAll(longEcgConfigInternals.get(i));
                            } else {
                                Toast.makeText(LongEcgHistoryActivity.this, "Sync Successfull", Toast.LENGTH_SHORT).show();
                                dialog.dismiss();
                               LongEcgHistoryActivity.super.onBackPressed();


                                // All ECG Synced, do things that you need to do after all the ecg is synced
                            }

                        }
                    }

                    @Override
                    public void onError(Errors errors) {
                        if(i<longEcgConfigInternals.size()) {
                            setlongecgButtonSyncAll(longEcgConfigInternals.get(i));
                        }

                    }
                });
    }

    @Override
    public void setButtonSyncAll(EcgConfig ecgConfig) {

    }


    public void viewPdf(EcgConfig ecgConfig) {
        try {
            if (!ecgConfig.getFileUrl().equals("")) {
                File file = new File(ecgConfig.getFileUrl());
                try {
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                    Uri photoURI = FileProvider.getUriForFile(LongEcgHistoryActivity.this,
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
                    Uri photoURI = FileProvider.getUriForFile(LongEcgHistoryActivity.this,
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
                        Intent intent = new Intent(LongEcgHistoryActivity.this, PdfViewActivity.class);
                        intent.putExtra("fileUrl", filePath);
                        startActivity(intent);
                    }

                    @Override
                    public void onError(Errors errors) {
                        Toast.makeText(getApplicationContext(), errors.getErrorMsg(), Toast.LENGTH_SHORT).show();
                    }
                });
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
               LongEcgHistoryActivity.super.onBackPressed();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
       LongEcgHistoryActivity.super.onBackPressed();


    }
    
}
