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
            vitalSign.setWeight(getEdittextValue(txtWeight));
            vitalSign.setHeight(getEdittextValue(txtHeight));
            double temp = Double.parseDouble(getEdittextValue(txtTemp));
            String TEMP = String.format("%.2f", temp);
            if (temp < 97) {
                TEMP += "(Low Body Temperature)";
            } else if (temp > 97 || temp < 100) {
                TEMP += "(Normal)";
            } else {
                TEMP += "(Fever)";
            }
            vitalSign.setTempt(TEMP);

            double pulse = Double.parseDouble(String.valueOf(getEdittextValue(txtPulse)));
            String PULSE = String.format("%.2f", pulse);
            if (pulse < 90) {
                PULSE += "(Clinical Emergency)";
            } else {
                PULSE += "(Normal)";

            }
            vitalSign.setPulse(PULSE);

            double heightinmeter = (Double.parseDouble(getEdittextValue(txtHeight)) * 0.0254);
            double m2 = heightinmeter * heightinmeter;
            double weight = Double.parseDouble(getEdittextValue(txtWeight));
            double bmi = weight / m2;
            String BMI = String.format("%.2f", bmi);
            if (bmi < 18.5) {
                BMI += "(Under Weight Range)";
            } else if (bmi < 24.9) {
                BMI += "(Healthy Weight Range)";
            } else if (bmi < 29.9) {
                BMI += "(Over Weight Range)";
            } else if (bmi < 39.9) {
                BMI += "(Obese Range)";
            }
            vitalSign.setBmi(BMI);

            vitalSign.setSto2(txtSTO2.getText().toString());

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



    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(grantResults[0]== PackageManager.PERMISSION_GRANTED){
            Log.v(TAG,"Permission: "+permissions[0]+ "was "+grantResults[0]);
            //resume tasks needing this permission
//            exportDB();
        }
    }


}
