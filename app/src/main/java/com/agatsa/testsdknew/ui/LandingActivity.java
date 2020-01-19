package com.agatsa.testsdknew.ui;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.agatsa.testsdknew.R;
import com.agatsa.testsdknew.databinding.ActivityActionBinding;

public class LandingActivity extends AppCompatActivity {

    ActivityActionBinding binding;

    boolean processClick=true;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_action);



        binding.btnNewPatient.setOnClickListener(view -> {


            Intent i = new Intent(LandingActivity.this,PersonalDetailsActivity.class);
            startActivity(i);


        });

        binding.existingBtn.setOnClickListener(view -> {

            Intent i = new Intent(LandingActivity.this,SearchActivity.class);
            startActivity(i);


        });

binding.logout.setOnClickListener(view -> finish());





    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();

    }




}

