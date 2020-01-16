package com.agatsa.testsdknew.ui;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.agatsa.testsdknew.Models.GlucoseModel;
import com.agatsa.testsdknew.Models.PatientModel;
import com.agatsa.testsdknew.R;
import com.agatsa.testsdknew.customviews.DialogUtil;
import com.agatsa.testsdknew.databinding.ActivityDiabetesBinding;

import br.com.ilhasoft.support.validation.Validator;

public class DiabetesActivity extends AppCompatActivity {
    SharedPreferences pref;
    String ptno = "";
    PatientModel newPatient = new PatientModel();
    LabDB labDB;
    ActivityDiabetesBinding binding;
    Validator validator;
    GlucoseModel glucoseModel=new GlucoseModel();
    private ProgressDialog dialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_diabetes);
        validator = new Validator(binding);
        validator.enableFormValidationMode();
        pref = this.getSharedPreferences("sunyahealth", Context.MODE_PRIVATE);
        ptno = pref.getString("PTNO", "");
        newPatient = getIntent().getParcelableExtra("patient");
        Log.d("diabetespt",ptno);

        labDB = new LabDB(getApplicationContext());
//        vitalSign = labDB.getLastVitalSign(ptno);
        dialog = new ProgressDialog(this);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setCancelable(false);


//        if(glucoseModel != null) {
//          binding.txtglucose.setText(binding.txtglucose.toString());
//
//
//        }

        binding.btnsaveglucose.setOnClickListener(v -> {
            if(validator.validate()){
               new SaveData().execute();
               navigatetonextactivity();


            }

        });

    }

    public void navigatetonextactivity(){
        Intent intent = new Intent(DiabetesActivity.this,TestListActivity.class);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        DialogUtil.getOKCancelDialog(this, "", "Do you want to discard the  diabetes test of " + newPatient.getPtName(), "Yes","No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Intent intent = new Intent(DiabetesActivity.this,TestListActivity.class);
                startActivity(intent);

            }
        });
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
            glucoseModel.setPt_no(ptno);
            Log.wtf("setptno",ptno);
            glucoseModel.setPtGlucose((getEdittextValue(binding.txtglucose)));
            String last_vitalsign_row_id = db.SaveGlucoseSign(glucoseModel);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
                return 3;
            }
            glucoseModel.setRow_id(last_vitalsign_row_id);
            return 1;
        }

        @Override
        protected void onPostExecute(Integer s) {
            super.onPostExecute(s);
            if (s == 2) {
                if (dialog.isShowing())
                    dialog.dismiss();
                Toast.makeText(getApplicationContext(), "Already saved " + newPatient.getPtNo() + " V " + glucoseModel.getRow_id(), Toast.LENGTH_LONG).show();

            } else if (s == 3) {
                if (dialog.isShowing())
                    dialog.dismiss();
                Toast.makeText(getApplicationContext(), "Exception catched " + newPatient.getPtNo() + " V " + glucoseModel.getRow_id(), Toast.LENGTH_LONG).show();

            } else {
                if (dialog.isShowing())
                    dialog.dismiss();
                Toast.makeText(getApplicationContext(), "Patient Saved " + newPatient.getPtNo() + " V " + glucoseModel.getRow_id(), Toast.LENGTH_LONG).show();
            }
        }
    }

    public String getEdittextValue(EditText editText) {
        return editText.getText().toString();
    }
}
