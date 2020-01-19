package com.agatsa.testsdknew.ui;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.agatsa.testsdknew.Models.PatientModel;
import com.agatsa.testsdknew.R;
import com.agatsa.testsdknew.databinding.ActivityActionDashBinding;

public class TestListActivity extends AppCompatActivity {

    ActivityActionDashBinding binding;
    String pt_id;

    String patientname;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_action_dash);;
        PatientModel patientModel = getIntent().getParcelableExtra("patient");
        pt_id = getIntent().getStringExtra("ptid");



       if(patientModel!=null){

           binding.patientName.setText(patientModel.getPtName());

       }else{
           binding.patientName.setText(patientname);
       }

       binding.btnVItal.setOnClickListener(view -> {

    Intent i = new Intent(TestListActivity.this, VitalSignActivity.class);
    i.putExtra("PTNO", pt_id);
    i.putExtra("patient",patientModel);
    startActivity(i);


}
);
   binding.ecgTest.setOnClickListener(view -> {

    Intent i = new Intent(TestListActivity.this, EcgOptionsActivity.class);
    i.putExtra("PTNO", pt_id);
       i.putExtra("patient",patientModel);
    startActivity(i);

   });

   binding.diabetesTest.setOnClickListener(v -> {
    Intent i = new Intent(TestListActivity.this, DiabetesActivity.class);
    i.putExtra("PTNO", pt_id);
    i.putExtra("patient",patientModel);
    startActivity(i);


    });

   binding.tblUrineTest.setOnClickListener(view -> {
       Intent i = new Intent(TestListActivity.this, CapturedImageActivity.class);
       i.putExtra("PTNO", pt_id);
       i.putExtra("patient",patientModel);
       startActivity(i);

   });

        binding.btnPrint.setOnClickListener(view -> {
            Intent i = new Intent(TestListActivity.this, PrintReport.class);
            i.putExtra("PTNO", pt_id);
            i.putExtra("patient",patientModel);
            startActivity(i);

        });





    }






}

