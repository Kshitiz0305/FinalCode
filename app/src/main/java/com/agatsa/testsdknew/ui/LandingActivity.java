package com.agatsa.testsdknew.ui;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.agatsa.testsdknew.Models.PatientModel;
import com.agatsa.testsdknew.Models.VitalSign;
import com.agatsa.testsdknew.R;
import com.agatsa.testsdknew.databinding.ActivityActionBinding;

public class LandingActivity extends AppCompatActivity {

    ActivityActionBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_action);


binding.btnNewPatient.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {

        Intent i = new Intent(LandingActivity.this,PersonalDetailsActivity.class);
        startActivity(i);


    }
});

        binding.existingBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(LandingActivity.this,SearchActivity.class);
                startActivity(i);


            }
        });

binding.logout.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        finish();
    }
});





    }


}

