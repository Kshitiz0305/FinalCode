package com.agatsa.testsdknew;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.agatsa.testsdknew.Models.PatientModel;
import com.agatsa.testsdknew.Models.VitalSign;

public class VitalSignActivity extends AppCompatActivity {
    String TAG = "PATIENTDETAIL";
    String device_id = "", duid = "", suid = "";
    int ptno = 0;
    SharedPreferences pref;
    Button btnSave;
    private ProgressDialog dialog;
    PatientModel newPatient = new PatientModel();
    VitalSign vitalSign = new VitalSign();
    EditText txtWeight, txtHeight, txtTemp, txtPulse, txtBPS, txtBPD, txtSTO2,txtglucose;
    Context thisContext;
    LabDB labDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vital_test);
        pref = this.getSharedPreferences("sunyahealth", Context.MODE_PRIVATE);
        ptno = pref.getInt("pt_id", 0);

        labDB = new LabDB(getApplicationContext());
        vitalSign = labDB.getLastVitalSign(ptno);
        dialog = new ProgressDialog(this);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setCancelable(false);

        txtWeight = findViewById(R.id.txtWeight);
        txtHeight = findViewById(R.id.txtHeight);
        txtTemp = findViewById(R.id.txtTemp);
        txtPulse = findViewById(R.id.txtPulse);
        txtSTO2 = findViewById(R.id.txtSto2);
        txtBPS = findViewById(R.id.txtBPSys);
        txtBPD = findViewById(R.id.txtBPDias);
        txtglucose=findViewById(R.id.txtglucose);

        if(vitalSign != null) {
            txtWeight.setText(String.valueOf(vitalSign.getWeight()));
            txtHeight.setText(String.valueOf(vitalSign.getHeight()));
            txtTemp.setText(String.valueOf(vitalSign.getTempt()));
            txtPulse.setText(String.valueOf(vitalSign.getPulse()));
            txtSTO2.setText(String.valueOf(vitalSign.getSto2()));
            txtBPS.setText(String.valueOf(vitalSign.getBps()));
            txtBPD.setText(String.valueOf(vitalSign.getBpd()));
            txtBPD.setText(String.valueOf(vitalSign.getGlucose()));

        }

        btnSave = findViewById(R.id.btnsave);
        btnSave.setOnClickListener(v -> {

                Toast.makeText(getApplicationContext(), "Saving Data", Toast.LENGTH_LONG).show();
                new SaveData().execute();
                navigatenext();

        });
    }

    private void navigatenext() {
        Intent i = new Intent(VitalSignActivity.this, PatientActivity.class);
        startActivity(i);

    }



    private boolean check_validation() {

        boolean result = true;
        if(Integer.parseInt(txtWeight.getText().toString())<2 || Integer.parseInt(txtWeight.getText().toString())>200 ){
            Toast.makeText(thisContext,"Invalid Weight",Toast.LENGTH_LONG).show();
            return  false;
        }
        if(Integer.parseInt(txtHeight.getText().toString())<2 || Integer.parseInt(txtHeight.getText().toString())>100 ){
            Toast.makeText(thisContext,"Invalid Height",Toast.LENGTH_LONG).show();
            return  false;
        }

        if(Integer.parseInt(txtTemp.getText().toString())<90 || Integer.parseInt(txtTemp.getText().toString())>108 ){
            Toast.makeText(thisContext,"Invalid Temprature",Toast.LENGTH_LONG).show();
            return false;
        }
        if(Integer.parseInt(txtPulse.getText().toString())<40 || Integer.parseInt(txtPulse.getText().toString())>150 ){
            Toast.makeText(thisContext,"Invalid Pulse",Toast.LENGTH_LONG).show();
            return false;
        }
        if(Integer.parseInt(txtSTO2.getText().toString())<40 || Integer.parseInt(txtSTO2.getText().toString())>150 ){
            Toast.makeText(thisContext,"Invalid STO2",Toast.LENGTH_LONG).show();
            return false;
        }
        if(Integer.parseInt(txtBPS.getText().toString())<20 || Integer.parseInt(txtBPS.getText().toString())>250 ){
            Toast.makeText(thisContext,"Invalid Blood Pressure (Systolic)",Toast.LENGTH_LONG).show();
            return  false;
        }
        if(Integer.parseInt(txtBPD.getText().toString())<20 || Integer.parseInt(txtBPD.getText().toString())>250 ){
            Toast.makeText(thisContext,"Invalid Blood Pressure (Dyostolic)",Toast.LENGTH_LONG).show();
            return false;
        }
        return result;
    }

    @SuppressLint("StaticFieldLeak")
    private class SaveData extends AsyncTask<String, Void, Integer> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog.setMessage("Saving Data");
            dialog.show();
        }

        @Override
        protected Integer doInBackground(String... strings) {
//
            LabDB db = new LabDB(getApplicationContext());
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
                return 3;
            }
            // Save Vital Sign
            vitalSign.setPt_no(ptno);
            vitalSign.setWeight((float) Double.parseDouble(getEdittextValue(txtWeight)));
            vitalSign.setHeight((float) Double.parseDouble(getEdittextValue(txtHeight)));
            vitalSign.setTempt((float) Double.parseDouble(getEdittextValue(txtTemp)));
            vitalSign.setPulse((float) Double.parseDouble(getEdittextValue(txtPulse)));
            vitalSign.setSto2((float) Double.parseDouble(getEdittextValue(txtSTO2)));
            vitalSign.setBpd((float) Double.parseDouble(getEdittextValue(txtBPD)));
            vitalSign.setBps((float) Double.parseDouble(getEdittextValue(txtBPS)));
            vitalSign.setGlucose((float) Double.parseDouble(getEdittextValue(txtglucose)));
            int last_vitalsign_row_id = db.SaveVitalSign(vitalSign);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
                return 3;
            }
            vitalSign.setRow_id(last_vitalsign_row_id);
            return 1;
        }

        @Override
        protected void onPostExecute(Integer s) {
            super.onPostExecute(s);
            if (s == 2) {
                if (dialog.isShowing())
                    dialog.dismiss();
                Toast.makeText(getApplicationContext(), "Already saved " + newPatient.getPtNo() + " V " + vitalSign.getRow_id(), Toast.LENGTH_LONG).show();

            } else if (s == 3) {
                if (dialog.isShowing())
                    dialog.dismiss();
                Toast.makeText(getApplicationContext(), "Exception catched " + newPatient.getPtNo() + " V " + vitalSign.getRow_id(), Toast.LENGTH_LONG).show();

            } else {
                if (dialog.isShowing())
                    dialog.dismiss();
                Toast.makeText(getApplicationContext(), "Patient Saved " + newPatient.getPtNo() + " V " + vitalSign.getRow_id(), Toast.LENGTH_LONG).show();
            }
        }
    }

    public String getEdittextValue(EditText editText) {
        return editText.getText().toString();
    }
}
