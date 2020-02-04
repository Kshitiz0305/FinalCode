package com.agatsa.testsdknew.ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.agatsa.testsdknew.R;
import com.agatsa.testsdknew.databinding.ActivityPatientEntryBinding;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.channels.FileChannel;


public class PatientEntryActivity extends AppCompatActivity {

    ActivityPatientEntryBinding binding;

    boolean processClick=true;

    LabDB labDB;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_patient_entry);
        labDB=new LabDB(this);



        binding.btnNewPatient.setOnClickListener(view -> {


            Intent i = new Intent(PatientEntryActivity.this,PersonalDetailsActivity.class);
            startActivity(i);


        });

        binding.existingBtn.setOnClickListener(view -> {

            Intent i = new Intent(PatientEntryActivity.this, ExistingPatientActivity.class);
            startActivity(i);


        });

       binding.logout.setOnClickListener(view -> {
           finishAffinity();

       });




    }

//    public void exportDatabse(String databaseName) {
//        try {
//            File sd = Environment.getExternalStorageDirectory();
//            File data = Environment.getDataDirectory();
//
//            if (sd.canWrite()) {
//                String currentDBPath = "//data//"+getPackageName()+"//databases//"+databaseName+"";
//                String backupDBPath = "sunyahealth.db";
//                File currentDB = new File(data, currentDBPath);
//                File backupDB = new File(sd, backupDBPath);
//
//                if (currentDB.exists()) {
//                    FileChannel src = new FileInputStream(currentDB).getChannel();
//                    FileChannel dst = new FileOutputStream(backupDB).getChannel();
//                    dst.transferFrom(src, 0, src.size());
//                    src.close();
//                    dst.close();
//                }
//            }
//        } catch (Exception e) {
//
//        }
//    }

    @Override
    public void onBackPressed() {
      finishAffinity();

    }




}

