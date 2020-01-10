package com.agatsa.testsdknew.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.agatsa.testsdknew.Models.PatientModel;
import com.agatsa.testsdknew.R;
import com.agatsa.testsdknew.databinding.ActivityActionDashBinding;
import com.agatsa.testsdknew.databinding.TestActionBinding;

public class TestListActivity extends AppCompatActivity {

    ActivityActionDashBinding binding;
    int pt_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_action_dash);
        PatientModel patientModel = getIntent().getParcelableExtra("patient");
        pt_id = getIntent().getIntExtra("ptid",0);

if(patientModel!=null){

    binding.patientName.setText(patientModel.getPtName());

}

binding.btnVItal.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {

        Intent i = new Intent(TestListActivity.this, VitalSignActivity.class);
        i.putExtra("PTNO", pt_id);
        startActivity(i);


    }
}
);
binding.ecgTest.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {

        Intent i = new Intent(TestListActivity.this, ECGactivity.class);
        i.putExtra("PTNO", pt_id);
        startActivity(i);

    }
});
//        binding.ecgTest.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent i = new Intent(TestListActivity.this, ECGactivity.class);
//                i.putExtra("PTNO", pt_id);
//                startActivity(i);
//
//            }
//        });









    }


}

