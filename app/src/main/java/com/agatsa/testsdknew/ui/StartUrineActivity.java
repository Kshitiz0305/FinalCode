package com.agatsa.testsdknew.ui;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.agatsa.testsdknew.Models.PatientModel;
import com.agatsa.testsdknew.R;
import com.agatsa.testsdknew.databinding.ActivityStartUrineBinding;

import javax.xml.validation.Validator;

public class StartUrineActivity extends AppCompatActivity {

    ActivityStartUrineBinding binding;
    String ptno = " ";
    SharedPreferences pref;
    PatientModel patientModel = new PatientModel();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pref = this.getSharedPreferences("sunyahealth", Context.MODE_PRIVATE);
        ptno = pref.getString("PTNO", "");
        patientModel = getIntent().getParcelableExtra("patient");
        binding = DataBindingUtil.setContentView(this, R.layout.activity_start_urine);

        binding.elevenparam.setOnClickListener(view -> {
            Intent i = new Intent(this, CapturedImageActivity.class);
            i.putExtra("PTNO", ptno);
            i.putExtra("patient", patientModel);
            startActivity(i);


        });


        binding.twoparam.setOnClickListener(view -> {
            Toast.makeText(this, "Coming Soon", Toast.LENGTH_SHORT).show();

        });

        binding.uricAcid.setOnClickListener(view -> {
            Toast.makeText(this, "Coming Soon", Toast.LENGTH_SHORT).show();

        });


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        StartUrineActivity.super.onBackPressed();
    }

}
