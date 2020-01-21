package com.agatsa.testsdknew.ui;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.IntentCompat;
import androidx.databinding.DataBindingUtil;

import com.agatsa.testsdknew.Models.PatientModel;
import com.agatsa.testsdknew.R;
import com.agatsa.testsdknew.customviews.DialogUtil;
import com.agatsa.testsdknew.databinding.TestActionBinding;

public class TestActivity extends AppCompatActivity {

    TestActionBinding binding;
    PatientModel patientModel;
    String ptid;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.test_action);
         patientModel = getIntent().getParcelableExtra("patient");
         ptid = getIntent().getStringExtra("ptid");

if(patientModel!=null){

    binding.patientName.setText(patientModel.getPtName());

}

binding.btnNewPatient.setOnClickListener(view -> {

    Intent i = new Intent(TestActivity.this,LandingActivity.class);
    startActivity(i);


});


        binding.btnTest.setOnClickListener(view -> {
            Intent intent = new Intent(TestActivity.this,TestListActivity.class);
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
                Intent intent = new Intent(TestActivity.this, LandingActivity.class);
                startActivity(intent);
                finishAffinity();

            });
        }

    }




}

