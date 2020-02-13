package com.agatsa.testsdknew.ui;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.databinding.DataBindingUtil;

import com.agatsa.testsdknew.Models.GlucoseModel;
import com.agatsa.testsdknew.Models.PatientModel;
import com.agatsa.testsdknew.R;
import com.agatsa.testsdknew.customviews.DialogUtil;
import com.agatsa.testsdknew.databinding.ActivityDiabetesBinding;
import com.agatsa.testsdknew.utils.CSVWriter;

import java.io.File;
import java.io.FileWriter;

import br.com.ilhasoft.support.validation.Validator;

public class DiabetesActivity extends AppCompatActivity {
    SharedPreferences pref;
    String ptno = "";
    PatientModel newPatient = new PatientModel();
    LabDB labDB;
    String TAG = "Diabetes";
    String ptname;
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

        labDB = new LabDB(getApplicationContext());
        dialog = new ProgressDialog(this);
        dialog.setCanceledOnTouchOutside(false);
        labDB=new LabDB(this);
        dialog.setCancelable(false);


//        if(glucoseModel != null) {
//          binding.txtglucose.setText(binding.txtglucose.toString());
//
//
//        }

        binding.btnsaveglucose.setOnClickListener(v -> {
            if(validator.validate()){
               new SaveData().execute();



            }

        });

        binding.help.setOnClickListener(view -> {

            final AlertDialog.Builder alert = new AlertDialog.Builder(DiabetesActivity.this);
            View mView = getLayoutInflater().inflate(R.layout.help_dailog,null);
            Button btn_okay = (Button)mView.findViewById(R.id.btn_okay);
            TextView diabetestxt=(TextView)mView.findViewById(R.id.infotxt);
            diabetestxt.setText(getResources().getString(R.string.diabetesinfo));
            alert.setView(mView);
            final AlertDialog alertDialog = alert.create();
            alertDialog.setCanceledOnTouchOutside(false);

            btn_okay.setOnClickListener(v -> {
                alertDialog.dismiss();
            });
            alertDialog.show();


        });

    }



    @Override
    public void onBackPressed() {
        DialogUtil.getOKCancelDialog(this, "", "Do you want to discard the  diabetes test of " + newPatient.getPtName() + "?", "Yes","No", (dialogInterface, i) ->
        DiabetesActivity.super.onBackPressed());
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

                DiabetesActivity.super.onBackPressed();
               Toast.makeText(getApplicationContext(), "Diabetes Test Saved " , Toast.LENGTH_LONG).show();
            }
        }
    }

    public String getEdittextValue(EditText editText) {
        return editText.getText().toString();
    }



//    private void exportDiabetesDB() {
//
//        LabDB labDB = new LabDB(this);
//        File exportDir = new File(Environment.getExternalStorageDirectory(), "/CSV/");
//        if (!exportDir.exists())
//        {
//            exportDir.mkdirs();
//        }
//
//        File file = new File(exportDir, "Diabetes.csv");
//        try
//        {
//            file.createNewFile();
//            CSVWriter csvWrite = new CSVWriter(new FileWriter(file));
//            SQLiteDatabase db = labDB.getReadableDatabase();
//            Cursor curCSV = db.rawQuery("SELECT * FROM diabetes_test",null);
//            csvWrite.writeNext(curCSV.getColumnNames());
//            while(curCSV.moveToNext())
//            {
//                //Which column you want to exprort
//                String arrStr[] ={curCSV.getString(0),curCSV.getString(1),
//                        curCSV.getString(2),curCSV.getString(3),
//                        curCSV.getString(4),curCSV.getString(5),
//                        curCSV.getString(6),curCSV.getString(7)};
//                csvWrite.writeNext(arrStr);
//            }
//            csvWrite.close();
//            curCSV.close();
//        }
//        catch(Exception sqlEx)
//        {
//            Log.e("MainActivity", sqlEx.getMessage(), sqlEx);
//        }
//
//
//    }

}
