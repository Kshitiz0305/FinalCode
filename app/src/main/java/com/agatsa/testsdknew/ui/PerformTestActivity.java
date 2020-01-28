package com.agatsa.testsdknew.ui;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.agatsa.testsdknew.Models.PatientModel;
import com.agatsa.testsdknew.R;
import com.agatsa.testsdknew.customviews.DialogUtil;
import com.agatsa.testsdknew.databinding.ActivityPerformTestBinding;


public class PerformTestActivity extends AppCompatActivity {

    ActivityPerformTestBinding binding;
    PatientModel patientModel;
    String ptid;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_perform_test);
         patientModel = getIntent().getParcelableExtra("patient");
         ptid = getIntent().getStringExtra("ptid");

if(patientModel!=null){

    binding.patientName.setText(patientModel.getPtName());

}

binding.btnNewPatient.setOnClickListener(view -> {

    Intent i = new Intent(PerformTestActivity.this, PatientEntryActivity.class);
    startActivity(i);


});


        binding.btnTest.setOnClickListener(view -> {
            Intent intent = new Intent(PerformTestActivity.this, PerformTestListActivity.class);
            intent.putExtra("ptid",ptid);
            intent.putExtra("patient",patientModel);
            startActivity(intent);


        });
        binding.logout.setOnClickListener(view -> {
            finishAffinity();


        });


    }
    @Override
    public void onBackPressed() {

        if (patientModel != null) {
            DialogUtil.getOKCancelDialog(this, "", "Do you want to discard the test of " + patientModel.getPtName(), "Yes", "No", (dialogInterface, i) -> {
                Intent intent = new Intent(PerformTestActivity.this, PatientEntryActivity.class);
                startActivity(intent);
                finishAffinity();

            });
        }

    }




}

