package com.agatsa.testsdknew.ui;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.agatsa.testsdknew.Models.PatientModel;
import com.agatsa.testsdknew.R;
import com.agatsa.testsdknew.customviews.DialogUtil;
import com.agatsa.testsdknew.databinding.ActivityPerformTestListBinding;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.channels.FileChannel;

public class PerformTestListActivity extends AppCompatActivity {

    ActivityPerformTestListBinding binding;
    String pt_id;

    String patientname;
    SharedPreferences pref;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_perform_test_list);
        pref = this.getSharedPreferences("sunyahealth", Context.MODE_PRIVATE);
        PatientModel patientModel = getIntent().getParcelableExtra("patient");
        pt_id = getIntent().getStringExtra("ptid");


     patientname=patientModel.getPtName();
       if(patientModel!=null){

           binding.patientName.setText(patientModel.getPtName());

       }else{
           binding.patientName.setText(patientname);
       }

       binding.btnVItal.setOnClickListener(view -> {

      Intent i = new Intent(PerformTestListActivity.this, VitalSignActivity.class);
      i.putExtra("PTNO", pt_id);
      i.putExtra("patient",patientModel);
      startActivity(i); });
     binding.ecgTest.setOnClickListener(view -> {

     Intent i = new Intent(PerformTestListActivity.this, EcgOptionsActivity.class);
     i.putExtra("PTNO", pt_id);
       i.putExtra("patient",patientModel);
      startActivity(i);

      });

     binding.diabetesTest.setOnClickListener(v -> {
      Intent i = new Intent(PerformTestListActivity.this, DiabetesActivity.class);
      i.putExtra("PTNO", pt_id);
     i.putExtra("patient",patientModel);
     startActivity(i);


     });

       binding.tblUrineTest.setOnClickListener(view -> {
       Intent i = new Intent(PerformTestListActivity.this, StartUrineActivity.class);
       i.putExtra("PTNO", pt_id);
       i.putExtra("patient",patientModel);
       startActivity(i);

       });

        binding.report.setOnClickListener(view -> {
            Intent i = new Intent(PerformTestListActivity.this, PrintReport.class);
            i.putExtra("PTNO", pt_id);
            i.putExtra("patient",patientModel);
            startActivity(i);

        });
        binding.completetest.setOnClickListener(v -> {
            DialogUtil.getOKCancelDialog(this, "", "Do you want to complete the  test of " + patientname, "Yes","No", (dialogInterface, i) -> {
                exportDatabse("Test1.db");

                pref.edit().putInt("VTF",0).apply();
                pref.edit().putInt("UTF",0).apply();
                pref.edit().putInt("DF",0).apply();
                pref.edit().putInt("SLF",0).apply();
                pref.edit().putInt("CSLF",0).apply();
                pref.edit().putInt("LISLF",0).apply();
                pref.edit().putInt("TLF",0).apply();
                pref.edit().putInt("LSLF",0).apply();

                Intent i1=new Intent(this, PatientEntryActivity.class);
                i1.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i1);
                finish();


            });

        });


        binding.bloodpressure.setOnClickListener(v -> {
            Intent i = new Intent(PerformTestListActivity.this, BloodPressureActivity.class);
            i.putExtra("PTNO", pt_id);
            i.putExtra("patient",patientModel);
            startActivity(i);


        });









    }


    @Override
    public void onBackPressed() {

        DialogUtil.getOKCancelDialog(this, "", "Do you want to discard the  test of " + patientname, "Yes","No", (dialogInterface, i) -> {

            pref.edit().putInt("VTF",0).apply();
            pref.edit().putInt("UTF",0).apply();
            pref.edit().putInt("DF",0).apply();
            pref.edit().putInt("SLF",0).apply();
            pref.edit().putInt("CSLF",0).apply();
            pref.edit().putInt("LISLF",0).apply();
            pref.edit().putInt("TLF",0).apply();
            pref.edit().putInt("LSLF",0).apply();

         Intent i1=new Intent(this, PatientEntryActivity.class);
            i1.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(i1);
            finish();


        });





    }

    public void exportDatabse(String databaseName) {
        try {
            File sd = Environment.getExternalStorageDirectory();
            File data = Environment.getDataDirectory();

            if (sd.canWrite()) {
                String currentDBPath = "//data//"+getPackageName()+"//databases//"+databaseName+"";
                String backupDBPath = "sunyahealth.db";
                File currentDB = new File(data, currentDBPath);
                File backupDB = new File(sd, backupDBPath);

                if (currentDB.exists()) {
                    FileChannel src = new FileInputStream(currentDB).getChannel();
                    FileChannel dst = new FileOutputStream(backupDB).getChannel();
                    dst.transferFrom(src, 0, src.size());
                    src.close();
                    dst.close();
                }
            }
        } catch (Exception e) {

        }
    }
















}

