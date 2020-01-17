package com.agatsa.testsdknew.ui;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
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
    startActivity(i);

   });

   binding.diabetesTest.setOnClickListener(v -> {
    Intent i = new Intent(TestListActivity.this, DiabetesActivity.class);
    i.putExtra("PTNO", pt_id);
    i.putExtra("patient",patientModel);
    startActivity(i);


    });






    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(TestListActivity.this, TestActivity.class);
        startActivity(intent);

    }




}

