package com.agatsa.testsdknew.ui;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.agatsa.testsdknew.Models.BloodPressureModel;
import com.agatsa.testsdknew.Models.PatientModel;
import com.agatsa.testsdknew.R;
import com.agatsa.testsdknew.customviews.DialogUtil;
import com.agatsa.testsdknew.databinding.ActivityBloodPressureBinding;
import com.agatsa.testsdknew.utils.CSVWriter;

import java.io.File;
import java.io.FileWriter;

import br.com.ilhasoft.support.validation.Validator;

public class BloodPressureActivity extends AppCompatActivity {

    ActivityBloodPressureBinding binding;
    Validator validator;

    EditText  BPS, BPD;

    SharedPreferences pref;
    LabDB labDB;
    ProgressDialog dialog;
    PatientModel newPatient;
    String ptno = " ";
   BloodPressureModel bloodPressureModel=new BloodPressureModel();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_blood_pressure);
        validator = new Validator(binding);
        validator.enableFormValidationMode();
        BPS = findViewById(R.id.BPSys);
        BPD = findViewById(R.id.BPDias);
        pref = this.getSharedPreferences("sunyahealth", Context.MODE_PRIVATE);
        ptno = pref.getString("PTNO", "");
        newPatient = getIntent().getParcelableExtra("patient");
        Log.d("bloodpressurelpt",ptno);
        labDB = new LabDB(getApplicationContext());
        dialog = new ProgressDialog(this);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setCancelable(false);

        binding.btnsavebloodpressure.setOnClickListener(v -> {
            if(validator.validate()) {
                if (Double.parseDouble(BPS.getText().toString()) < 20 || Double.parseDouble(BPS.getText().toString()) > 250) {
                    Toast.makeText(this, "Invalid Blood Pressure (Systolic)", Toast.LENGTH_LONG).show();


                } else if (Double.parseDouble(BPD.getText().toString()) < 20 || Double.parseDouble(BPD.getText().toString()) > 250) {
                    Toast.makeText(this, "Invalid Blood Pressure (Dyostolic)", Toast.LENGTH_LONG).show();


                } else {
                    new SaveData().execute();
//                    exportDB();


                }

            }


        });







    }

    @Override
    public void onBackPressed() {

        DialogUtil.getOKCancelDialog(this, "", "Do you want to discard the  blood pressure test of " + newPatient.getPtName(), "Yes","No", (dialogInterface, i) -> {
            BloodPressureActivity.super.onBackPressed();

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
            // Save Bloodpressure Sign
            bloodPressureModel.setPt_no(ptno);
            bloodPressureModel.setDiastolic(Double.parseDouble(BPD.getText().toString()));
            bloodPressureModel.setSystolic(Double.parseDouble(BPS.getText().toString()));
            String last_vitalsign_row_id = db.SaveBloodpressureSign(bloodPressureModel);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
                return 3;
            }
            bloodPressureModel.setRow_id(last_vitalsign_row_id);
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
//                pref.edit().putInt("BTF",1).apply();
//                Log.d("vitaltestflag",String.valueOf(pref.getInt("VTF",0)));
                BloodPressureActivity.super.onBackPressed();
                Toast.makeText(getApplicationContext(), "Blood Pressure Test Saved ", Toast.LENGTH_LONG).show();
            }

        }
    }

    private void exportbloodpressureDB() {

        LabDB dbhelper = new LabDB(getApplicationContext());
        File exportDir = new File(Environment.getExternalStorageDirectory(), "/CSV/");
        if (!exportDir.exists())
        {
            exportDir.mkdirs();
        }

        File file = new File(exportDir, "Bloodpressure.csv");
        try
        {
            file.createNewFile();
            CSVWriter csvWrite = new CSVWriter(new FileWriter(file));
            SQLiteDatabase db = dbhelper.getReadableDatabase();
            Cursor curCSV = db.rawQuery("SELECT * FROM blood_pressure_test",null);
            csvWrite.writeNext(curCSV.getColumnNames());
            while(curCSV.moveToNext())
            {
                //Which column you want to exprort
                String arrStr[] ={curCSV.getString(0),curCSV.getString(1),
                        curCSV.getString(2),curCSV.getString(3),
                        curCSV.getString(4),curCSV.getString(5),
                        curCSV.getString(6)};
                csvWrite.writeNext(arrStr);
            }
            csvWrite.close();
            curCSV.close();
        }
        catch(Exception sqlEx)
        {
            Log.e("MainActivity", sqlEx.getMessage(), sqlEx);
        }


    }



}
