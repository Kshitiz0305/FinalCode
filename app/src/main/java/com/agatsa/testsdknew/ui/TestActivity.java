package com.agatsa.testsdknew.ui;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.agatsa.testsdknew.Models.PatientModel;
import com.agatsa.testsdknew.R;
import com.agatsa.testsdknew.customviews.DialogUtil;
import com.agatsa.testsdknew.databinding.ActivityActionBinding;
import com.agatsa.testsdknew.databinding.TestActionBinding;

public class TestActivity extends AppCompatActivity {

    TestActionBinding binding;
    PatientModel patientModel;
    int ptid;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.test_action);
         patientModel = getIntent().getParcelableExtra("patient");
         ptid = getIntent().getIntExtra("ptid",0);

if(patientModel!=null){

    binding.patientName.setText(patientModel.getPtName());

}

binding.btnNewPatient.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {

        Intent i = new Intent(TestActivity.this,LandingActivity.class);
        startActivity(i);


    }
});


        binding.btnTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TestActivity.this,TestListActivity.class);
                intent.putExtra("ptid",ptid);
                intent.putExtra("patient",patientModel);
                startActivity(intent);


            }
        });
        binding.logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                android.os.Process.killProcess(android.os.Process.myPid());
                System.exit(0);

            }
        });


    }
    @Override
    public void onBackPressed() {

        DialogUtil.getOKCancelDialog(this, "", "Do you want to discard the test of " + patientModel.getPtName(), "Yes","No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Intent intent = new Intent(TestActivity.this,LandingActivity.class);
                startActivity(intent);

            }
        });
    }


}

