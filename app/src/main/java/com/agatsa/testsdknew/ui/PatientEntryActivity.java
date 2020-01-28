package com.agatsa.testsdknew.ui;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.agatsa.testsdknew.R;
import com.agatsa.testsdknew.databinding.ActivityPatientEntryBinding;


public class PatientEntryActivity extends AppCompatActivity {

    ActivityPatientEntryBinding binding;

    boolean processClick=true;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_patient_entry);



        binding.btnNewPatient.setOnClickListener(view -> {


            Intent i = new Intent(PatientEntryActivity.this,PersonalDetailsActivity.class);
            startActivity(i);


        });

        binding.existingBtn.setOnClickListener(view -> {

            Intent i = new Intent(PatientEntryActivity.this, ExistingPatientActivity.class);
            startActivity(i);


        });

       binding.logout.setOnClickListener(view ->
              finishAffinity());




    }

    @Override
    public void onBackPressed() {
      finishAffinity();

    }




}

