package com.agatsa.testsdknew.ui;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
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
import com.agatsa.testsdknew.databinding.ActivityNoDiabetesBinding;

import java.util.Calendar;
import java.util.Date;

import br.com.ilhasoft.support.validation.Validator;

public class NodaibetesActivity extends AppCompatActivity {
    ActivityNoDiabetesBinding binding;
    Validator validator;
    SharedPreferences pref;
    String ptno = "";
    PatientModel newPatient = new PatientModel();
    LabDB labDB;
    ProgressDialog dialog;




    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_no_diabetes);
        validator = new Validator(binding);
        validator.enableFormValidationMode();
        pref = this.getSharedPreferences("sunyahealth", Context.MODE_PRIVATE);
        ptno = pref.getString("PTNO", "");
        newPatient = getIntent().getParcelableExtra("patient");
        dialog=new ProgressDialog(this);


        labDB = new LabDB(getApplicationContext());
        labDB=new LabDB(this);




        binding.buttonsave.setOnClickListener(v -> {
            if(validator.validate()){

                new SaveData().execute();



            }

        });











    }







    @Override
    public void onBackPressed() {
        DialogUtil.getOKCancelDialog(this, "", "Do you want to discard the  diabetes test of " + newPatient.getPtName() + "?", "Yes","No", (dialogInterface, i) ->
                NodaibetesActivity.super.onBackPressed());
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
            Date currentTime = Calendar.getInstance().getTime();


            GlucoseModel glucoseModel=new GlucoseModel();
            glucoseModel.setPt_no(ptno);
            glucoseModel.setPtGlucose(binding.txtglucoseditext.getText().toString());
            glucoseModel.setPttesttype(getResources().getString(R.string.fast_reading));
            glucoseModel.setPtmealtype("Nil");
            glucoseModel.setPtlatestmealtime("Nil");
            glucoseModel.setPttimetaken(currentTime.toString());
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
                Toast.makeText(getApplicationContext(), "Already saved " , Toast.LENGTH_LONG).show();

            } else if (s == 3) {


                if (dialog.isShowing())
                    dialog.dismiss();
                Toast.makeText(getApplicationContext(), "Exception catched " , Toast.LENGTH_LONG).show();

            } else {
                if (dialog.isShowing())
                    dialog.dismiss();

                pref.edit().putInt("DF",1).apply();
                Log.d("vitaltestflag",String.valueOf(pref.getInt("DF",0)));

                NodaibetesActivity.super.onBackPressed();
                Toast.makeText(getApplicationContext(), "Diabetes Test Saved " , Toast.LENGTH_LONG).show();
            }
        }
    }

    public String getEdittextValue(EditText editText) {
        return editText.getText().toString();
    }






}

