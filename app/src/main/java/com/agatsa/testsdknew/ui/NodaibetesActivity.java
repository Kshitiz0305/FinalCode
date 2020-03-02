package com.agatsa.testsdknew.ui;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.agatsa.testsdknew.Models.GlucoseModel;
import com.agatsa.testsdknew.Models.PatientModel;
import com.agatsa.testsdknew.R;
import com.agatsa.testsdknew.customviews.DialogUtil;
import com.agatsa.testsdknew.databinding.ActivityNoDiabetesBinding;


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

        binding.nodiabeteshelp.setOnClickListener(v -> {
            final AlertDialog.Builder alert = new AlertDialog.Builder(NodaibetesActivity.this);
            View mView = getLayoutInflater().inflate(R.layout.help_dailog,null);
            Button btn_okay = (Button)mView.findViewById(R.id.btn_okay);
            TextView diabetestxt=(TextView)mView.findViewById(R.id.infotxt);
            diabetestxt.setText(getResources().getString(R.string.diabetesinfo));
            alert.setView(mView);
            final AlertDialog alertDialog = alert.create();
            alertDialog.setCanceledOnTouchOutside(false);

            btn_okay.setOnClickListener(v1-> {
                alertDialog.dismiss();
            });
            alertDialog.show();

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



            GlucoseModel glucoseModel=new GlucoseModel();
            glucoseModel.setPt_no(ptno);
            double value= Double.parseDouble(binding.txtglucoseditext.getText().toString());
             String Random = String.format("%.2f", value);
            if (value <= 100 && value > 0) {
                Random += "(Normal)";
            } else if (value > 100 && value <= 125) {
                Random += "(Prediabetes)";


            } else if (value > 125) {
                Random += "(Diabetes)";

            }
            glucoseModel.setPtGlucose(Random+"mg/dL");

            glucoseModel.setPttesttype(getResources().getString(R.string.fast_reading));
            glucoseModel.setPtmealtype("Nil");
            glucoseModel.setPtlatestmealtime("Nil");
            glucoseModel.setPttimetaken("Nil");
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

