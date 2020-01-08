package com.agatsa.testsdknew.ui;

import android.app.Activity;
import android.content.Context;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.agatsa.testsdknew.R;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class PatientActivity extends AppCompatActivity {
    LinearLayout llNewPatient;
    LinearLayout llecg;
    LinearLayout llprintreport;
    LinearLayout VitalTest;
    Button diabetesbtn;
    String server_url = "";
    int pt_id = 0;
    String device_id = "", duid = "", suid = "";
    SharedPreferences pref;
    public static final int PICK_FILE = 101;
    LabDB db;
    boolean isButtonClicked = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient);
        db = new LabDB(getApplicationContext());
        pref = this.getSharedPreferences("sunyahealth", Context.MODE_PRIVATE);
        device_id = pref.getString("device_id", "");
//        pt_id = pref.getInt("pt_id", 0);
//        duid = getIntent().getStringExtra("duid");
//        if (pt_id == 0) {
//            duid = getIntent().getStringExtra("duid");
//        } else {
//            duid = db.getUserIdFromPtno("pt_details", pt_id);
//        }

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Dashboard");
        setSupportActionBar(toolbar);
        VitalTest=findViewById(R.id.VitalTest);
        llprintreport=findViewById(R.id.printreport);
        llNewPatient = findViewById(R.id.btnNewPatient);
        diabetesbtn=findViewById(R.id.diabetesbtn);
        llecg = findViewById(R.id.llecg);
        llNewPatient.setOnClickListener(v -> {
            Intent patientIntent = new Intent(getApplicationContext(), PersonalDetailsActivity.class);
            patientIntent.putExtra("duid", duid);
            patientIntent.putExtra("PTNO", pt_id);
            startActivity(patientIntent);
        });

        llecg.setOnClickListener(view -> {
//            if (pt_id == 0) {
////                ToastUtils.maketoast(PatientActivity.this,"Please save personal details");
//                Toast.makeText(this, "Please save personal details", Toast.LENGTH_SHORT).show();
//
//
//            }else{
                Intent i = new Intent(PatientActivity.this, RegisterDeviceActivity.class);
                i.putExtra("PTNO", pt_id);
                startActivity(i);

//            }



        });

//        diabetesbtn.setOnClickListener(view -> {
//            Intent i=new Intent(this,GlucoseActivity.class);
//            i.putExtra("PTNO", pt_id);
//            startActivity(i);
//        });

        VitalTest.setOnClickListener(view -> {
//            if (pt_id == 0) {
//                Toast.makeText(this, "Please save personal details", Toast.LENGTH_SHORT).show();
////                ToastUtils.maketoast(PatientActivity.this, "Please save personal details");
//
//            }else{
                Intent i = new Intent(this, VitalSignActivity.class);
                i.putExtra("PTNO", pt_id);
                startActivity(i);

//            }





        });

        llprintreport.setOnClickListener(view -> {
//            if (pt_id == 0) {
////                ToastUtils.maketoast(PatientActivity.this, "Please save personal details");
//                Toast.makeText(this, "Please save personal details", Toast.LENGTH_SHORT).show();
//
//
//            }else{
                new LabDB(getApplicationContext()).exportDB("sunya_health_db", getApplicationContext());
                Intent reportintent = new Intent(getApplicationContext(), PrintReport.class);
                reportintent.putExtra("PTNO", pt_id);
                reportintent.putExtra("duid", duid);
                startActivity(reportintent);

//            }



        });
    }

    @Override
    public void onBackPressed() {
        if (pt_id > 0 && (db.getRowCount("vital_sign", " WHERE ptno=" + pt_id) <= 0))
        {
            AlertDialog.Builder alertDialog = new AlertDialog.Builder(
                    this);
            alertDialog.setTitle("Alert!!");
            alertDialog.setMessage("All tests are not filled. Do you wish to go back to Home page?\n\n" +
                    "Note: If all tests are not filled going back will delete all the filled data for Patient ID " + duid);
            alertDialog.setCancelable(false);
            alertDialog.setPositiveButton("Yes",
                    (dialogInterface, i) -> {
                        db.deleteData("pt_details", "id="+pt_id);
                        db.deleteData("vital_sign", "ptno='"+pt_id +"'");
                        finish();

                    });
            alertDialog.setNegativeButton("No",
                    (dialog, which) -> dialog.dismiss());
            alertDialog.show();
        } else {
//            Intent i = new Intent(PatientActivity.this, PatientActivity.class);
//            startActivity(i);
//            finish();
        }

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_FILE && resultCode == Activity.RESULT_OK) {
            Uri uri = data.getData();
            String fileContent = readTextFile(uri);
            Log.d("Selected file content ", fileContent);
            // Replace data
            LabDB db = new LabDB(this);
            db.setUrineData(fileContent);
        }
    }

    private String readTextFile(Uri uri) {
        BufferedReader reader = null;
        StringBuilder builder = new StringBuilder();
        try {
            reader = new BufferedReader(new InputStreamReader(getContentResolver().openInputStream(uri)));

            String line = "";
            while ((line = reader.readLine()) != null) {
                builder.append(line);
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return builder.toString();
    }
}
