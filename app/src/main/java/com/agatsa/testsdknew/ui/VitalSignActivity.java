package com.agatsa.testsdknew.ui;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.databinding.DataBindingUtil;
import com.agatsa.testsdknew.Models.PatientModel;
import com.agatsa.testsdknew.Models.VitalSign;
import com.agatsa.testsdknew.R;
import com.agatsa.testsdknew.customviews.DialogUtil;
import com.agatsa.testsdknew.databinding.ActivityVitalTestBinding;
import com.agatsa.testsdknew.utils.CSVWriter;
import java.io.File;
import java.io.FileWriter;
import br.com.ilhasoft.support.validation.Validator;

public class VitalSignActivity extends AppCompatActivity  {
    String TAG = "PATIENTDETAIL";
    String device_id = "", duid = "", suid = "";
    String ptno = " ";
    SharedPreferences pref;
    Button btnSave;
    private ProgressDialog dialog;
    PatientModel newPatient;
    VitalSign vitalSign = new VitalSign();
    EditText txtWeight, txtHeight, txtTemp, txtPulse,txtSTO2;
    LabDB labDB;
    Validator validator;
    ActivityVitalTestBinding binding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_vital_test);
        validator = new Validator(binding);
        validator.enableFormValidationMode();
        pref = this.getSharedPreferences("sunyahealth", Context.MODE_PRIVATE);
        ptno = pref.getString("PTNO", "");
        newPatient = getIntent().getParcelableExtra("patient");
        Log.d("rantestvitalpt",ptno);



        labDB = new LabDB(getApplicationContext());
        dialog = new ProgressDialog(this);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setCancelable(false);

        txtWeight = findViewById(R.id.txtWeight);
        txtHeight = findViewById(R.id.txtHeight);
        txtTemp = findViewById(R.id.txtTemp);
        txtPulse = findViewById(R.id.txtPulse);
        txtSTO2 = findViewById(R.id.txtSto2);




        btnSave = findViewById(R.id.btnsave);
        btnSave.setOnClickListener(v -> {
            if(validator.validate()){
                if(Double.parseDouble(txtWeight.getText().toString())> 200){
                    Toast.makeText(this,"Invalid Weight",Toast.LENGTH_LONG).show();

                }else  if(Double.parseDouble(txtHeight.getText().toString())> 100){
                    Toast.makeText(this,"Invalid Height",Toast.LENGTH_LONG).show();

                }else if(Double.parseDouble(txtTemp.getText().toString())<90 || Double.parseDouble(txtTemp.getText().toString())>108){
                    Toast.makeText(this,"Invalid Temprature",Toast.LENGTH_LONG).show();

                }else if(Double.parseDouble(txtPulse.getText().toString())<40 || Double.parseDouble(txtPulse.getText().toString())>150 ){
                    Toast.makeText(this,"Invalid Pulse",Toast.LENGTH_LONG).show();

                }else if(Double.parseDouble(txtSTO2.getText().toString())<40 ||Double.parseDouble(txtSTO2.getText().toString())>150){
                    Toast.makeText(this,"Invalid STO2",Toast.LENGTH_LONG).show();


                }else{
                    new SaveData().execute();




                }


            }



        });
    }

    @Override
    public void onBackPressed() {
        DialogUtil.getOKCancelDialog(this, "", "Do you want to discard the  vital test of " + newPatient.getPtName(), "Yes","No", (dialogInterface, i) -> {

                VitalSignActivity.super.onBackPressed();


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
            vitalSign.setPt_no(ptno);
            vitalSign.setWeight(Double.parseDouble((getEdittextValue(txtWeight))));
            vitalSign.setHeight(Double.parseDouble((getEdittextValue(txtHeight))));
            vitalSign.setTempt(Double.parseDouble((getEdittextValue(txtTemp))));
            vitalSign.setPulse(Double.parseDouble((getEdittextValue(txtPulse))));
            vitalSign.setSto2(Double.parseDouble((getEdittextValue(txtSTO2))));

            String last_vitalsign_row_id = db.SaveVitalSign(vitalSign);

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
                Toast.makeText(getApplicationContext(), "Already saved " , Toast.LENGTH_LONG).show();

            } else if (s == 3) {
                if (dialog.isShowing())
                    dialog.dismiss();
                Toast.makeText(getApplicationContext(), "Exception catched " , Toast.LENGTH_LONG).show();

            } else {
                if (dialog.isShowing())
                    dialog.dismiss();
                pref.edit().putInt("VTF",1).apply();
                Log.d("vitaltestflag",String.valueOf(pref.getInt("VTF",0)));
                VitalSignActivity.super.onBackPressed();
                Toast.makeText(getApplicationContext(), "Vital Test  Saved ", Toast.LENGTH_LONG).show();
            }

        }
    }

    public String getEdittextValue(EditText editText) {
        return editText.getText().toString();
    }

    public  boolean isStoragePermissionGranted() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED) {
                Log.v(TAG,"Permission is granted");
                return true;
            } else {

                Log.v(TAG,"Permission is revoked");
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                return false;
            }
        }
        else { //permission is automatically granted on sdk<23 upon installation
            Log.v(TAG,"Permission is granted");
            return true;
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(grantResults[0]== PackageManager.PERMISSION_GRANTED){
            Log.v(TAG,"Permission: "+permissions[0]+ "was "+grantResults[0]);
            //resume tasks needing this permission
//            exportDB();
        }
    }

    private void exportVitalsigndbDB() {

        LabDB dbhelper = new LabDB(getApplicationContext());
        File exportDir = new File(Environment.getExternalStorageDirectory(), "/CSV/");
        if (!exportDir.exists())
        {
            exportDir.mkdirs();
        }

        File file = new File(exportDir, "VitalSign.csv");
        try
        {
            file.createNewFile();
            CSVWriter csvWrite = new CSVWriter(new FileWriter(file));
            SQLiteDatabase db = dbhelper.getReadableDatabase();
            Cursor curCSV = db.rawQuery("SELECT * FROM vital_sign",null);
            csvWrite.writeNext(curCSV.getColumnNames());
            while(curCSV.moveToNext())
            {
                //Which column you want to exprort
                String arrStr[] ={curCSV.getString(0),curCSV.getString(1),
                        curCSV.getString(2),curCSV.getString(3),
                        curCSV.getString(4),curCSV.getString(5),
                        curCSV.getString(6),curCSV.getString(7),
                        curCSV.getString(8),curCSV.getString(9),
                        curCSV.getString(10),curCSV.getString(11)};
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
