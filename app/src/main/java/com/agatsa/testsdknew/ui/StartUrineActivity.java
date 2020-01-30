package com.agatsa.testsdknew.ui;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import com.agatsa.testsdknew.Models.PatientModel;
import com.agatsa.testsdknew.R;
import com.agatsa.testsdknew.databinding.ActivityStartUrineBinding;

public class StartUrineActivity extends AppCompatActivity {

   ActivityStartUrineBinding binding;
    String ptno = " ";
    SharedPreferences pref;
    PatientModel patientModel = new PatientModel();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_start_urine);
        pref = this.getSharedPreferences("sunyahealth", Context.MODE_PRIVATE);
        ptno = pref.getString("PTNO", "");
        patientModel = getIntent().getParcelableExtra("patient");


        binding.elevenparam.setOnClickListener(view -> {
            Intent i = new Intent(this, UrineTestActivity.class);
            i.putExtra("PTNO", ptno);
            i.putExtra("patient", patientModel);
            startActivity(i);


        });


        binding.twoparam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(StartUrineActivity.this, "Coming Soon", Toast.LENGTH_SHORT).show();

            }
        });

        binding.uricAcid.setOnClickListener(view -> {
            Toast.makeText(this, "Coming Soon", Toast.LENGTH_SHORT).show();

        });

        binding.help.setOnClickListener(view -> {


                final androidx.appcompat.app.AlertDialog.Builder alert = new androidx.appcompat.app.AlertDialog.Builder(StartUrineActivity.this);
                View mView = getLayoutInflater().inflate(R.layout.help_dailog,null);
                Button btn_okay = (Button)mView.findViewById(R.id.btn_okay);
                 TextView diabetestxt=(TextView)mView.findViewById(R.id.infotxt);
                 diabetestxt.setText(getResources().getString(R.string.urineinfo));
                 alert.setView(mView);
                final androidx.appcompat.app.AlertDialog alertDialog = alert.create();
                alertDialog.setCanceledOnTouchOutside(false);

                btn_okay.setOnClickListener(v -> {
                    alertDialog.dismiss();
                });
                alertDialog.show();




        });


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        StartUrineActivity.super.onBackPressed();
    }

}
