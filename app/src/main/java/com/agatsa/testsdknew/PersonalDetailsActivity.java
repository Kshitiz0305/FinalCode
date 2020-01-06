package com.agatsa.testsdknew;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.databinding.DataBindingUtil;

import com.agatsa.testsdknew.Models.PatientModel;
import com.agatsa.testsdknew.Models.VitalSign;
import com.agatsa.testsdknew.databinding.ActivityNewPatientDetailBinding;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import br.com.ilhasoft.support.validation.Validator;


public class PersonalDetailsActivity extends AppCompatActivity {

    String TAG = "PATIENTDETAIL";
    String device_id = "", duid = "", suid = "";
    int pt_id = 0;
    SharedPreferences pref;
    Button btnSave;
    private ProgressDialog dialog;
    TextView txtPtno;
    PatientModel newPatient = new PatientModel();
    VitalSign vitalSign = new VitalSign();
    EditText txtPtName, txtPtAddress, txtPtContactNo, txtEmail,noofboys,noofgirls,drug_allergies,medicationmedicinename;
    EditText txtAge,disease,smokepcs,alcoholpegs;
    RadioButton optMale, optFemale, optOther,optyes,optno,optsmoking,optalcohol;
    RadioButton married, unmarried, divorced,widowed;
    private static int TAKE_PICTURE = 111;
    String mCurrentPhotoPath;
    Context thisContext;
    LabDB labDB;

    LinearLayout noofhildrenll;
    Calendar myCalendar;
    RadioGroup maritalstatusrg;
    RadioGroup currentmedicationrg;
    RadioGroup unhealthyhabitsrg;
    LinearLayout medicationll;
    LinearLayout smokell;
    LinearLayout alcoholll;
    ActivityNewPatientDetailBinding binding;

 Validator validator;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_new_patient_detail);
        validator = new Validator(binding);
        validator.enableFormValidationMode();

        pref = this.getSharedPreferences("sunyahealth", Context.MODE_PRIVATE);
        device_id = pref.getString("device_id", "");
        duid = getIntent().getStringExtra("duid");
        pt_id = pref.getInt("pt_id", 0);

        thisContext = PersonalDetailsActivity.this;
        labDB = new LabDB(getApplicationContext());
        dialog = new ProgressDialog(this);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setCancelable(false);
        noofhildrenll=findViewById(R.id.noofhildrenll);
        maritalstatusrg=findViewById(R.id.maritalstatusrg);
        smokell=findViewById(R.id.smokell);
        alcoholll=findViewById(R.id.alcoholll);

        txtPtno = findViewById(R.id.txtPatientNo);
        txtPtName = findViewById(R.id.txtPatientName);
        txtPtAddress = findViewById(R.id.txtAddress);
        txtPtContactNo = findViewById(R.id.txtContactNo);
        txtEmail = findViewById(R.id.txtemail);

        smokepcs=findViewById(R.id.smokepcs);
        alcoholpegs=findViewById(R.id.alcoholpegs);

        txtAge = findViewById(R.id.txtAge);
        optMale = findViewById(R.id.optMale);
        optFemale = findViewById(R.id.optFemale);
        optOther = findViewById(R.id.optOther);
        optyes= findViewById(R.id.yes);
        optno = findViewById(R.id.no);
        optsmoking= findViewById(R.id.optsmoking);
        optalcohol = findViewById(R.id.optalcohol);

        married=findViewById(R.id.married);
        unmarried=findViewById(R.id.unmarried);
        divorced=findViewById(R.id.divorced);
        widowed=findViewById(R.id.widowed);
        noofboys=findViewById(R.id.noofboys);
        noofgirls=findViewById(R.id.noofgirls);
        drug_allergies=findViewById(R.id.drug_allergies);
        currentmedicationrg=findViewById(R.id.currentmedicationrg);
        unhealthyhabitsrg=findViewById(R.id.unhealthyhabitsrg);
        medicationll=findViewById(R.id.medicationll);
        medicationmedicinename=findViewById(R.id.medicationmedicinename);
        disease=findViewById(R.id.diseases);


        unhealthyhabitsrg.setOnCheckedChangeListener((radioGroup, i) -> {
            switch (i){
                case R.id.optsmoking:
                    smokell.setVisibility(View.VISIBLE);
                    alcoholll.setVisibility(View.GONE);
                    break;

                case R.id.optalcohol:
                    alcoholll.setVisibility(View.VISIBLE);
                    smokell.setVisibility(View.GONE);
                    break;

            }

        });


        currentmedicationrg.setOnCheckedChangeListener((group, checkedId) -> {
            switch (checkedId) {
                case R.id.yes:
                    medicationll.setVisibility(View.VISIBLE);
                    break;

                case R.id.no:
                    medicationll.setVisibility(View.GONE);
                    break;

            }
        });




        maritalstatusrg.setOnCheckedChangeListener((group, checkedId) -> {
            switch (checkedId) {
                case R.id.married:
                    noofhildrenll.setVisibility(View.VISIBLE);


                case R.id.divorced:
                    noofhildrenll.setVisibility(View.VISIBLE);


                case R.id.widowed:
                    noofhildrenll.setVisibility(View.VISIBLE);


                    break;

                case R.id.unmarried:
                    noofhildrenll.setVisibility(View.GONE);

                    break;

            }

        });


        final LabDB db = new LabDB(getApplicationContext());
       newPatient = db.getPatient(device_id + duid, pt_id);

        if (newPatient != null) {
            txtPtno.setText(device_id + duid);
            txtPtName.setText(newPatient.getPtName());
            txtPtAddress.setText(newPatient.getPtAddress());
            txtPtContactNo.setText(newPatient.getPtContactNo());
            txtEmail.setText(newPatient.getPtEmail());
            txtAge.setText(newPatient.getPtAge());
            String gender = newPatient.getPtSex();
            if (gender.equals("Male")) {
                optMale.setChecked(true);
            } else if (gender.equals("Female")) {
                optFemale.setChecked(true);
            } else if (gender.equals("Other")) {
                optOther.setChecked(true);
            }





        }

        btnSave = findViewById(R.id.btnSave);
        btnSave.setOnClickListener(v -> {
            if (validator.validate()) {
                Toast.makeText(getApplicationContext(), "Saving Data", Toast.LENGTH_LONG).show();
                new savedata().execute();
                navigatenext();
            }
            else {

                Toast.makeText(getApplicationContext(), "Validation Error", Toast.LENGTH_LONG).show();

            }
        });

    }

    private void navigatenext() {
        Intent i = new Intent(PersonalDetailsActivity.this, PatientActivity.class);
        startActivity(i);

    }

    private void updateLabel(EditText editText) {
        String myFormat = "yyyy-MM-dd"; //In which you need put here
        @SuppressLint("SimpleDateFormat")
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat);

        editText.setText(sdf.format(myCalendar.getTime()));
    }

//    @Override
//    public void onValidationSuccess() {
//
//    }
//
//    @Override
//    public void onValidationError() {
//
//    }

    @SuppressLint("StaticFieldLeak")
    private class savedata extends AsyncTask<String, Void, Integer> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog.setMessage("Saving Data");
            dialog.show();
        }

        @Override
        protected Integer doInBackground(String... strings) {
//            newPatient = new PatientModel();
            newPatient.setUser_id(duid);
            newPatient.setPtName(getEdittextValue(txtPtName));
            newPatient.setPtAddress(getEdittextValue(txtPtAddress));
            newPatient.setPtContactNo(getEdittextValue(txtPtContactNo));
            newPatient.setPtEmail(getEdittextValue(txtEmail));
            newPatient.setPtAge(getEdittextValue(txtAge));
            String sex = "Male";
            if (getRadioButtonValue(optFemale)) {
                sex = "Female";
                System.out.println("Female Selected");
            } else if (getRadioButtonValue(optOther))
                sex = "Other";
            newPatient.setPtSex(sex);

            String maritalstatus = "Unmarried";
            if (getRadioButtonValue(married)) {
                maritalstatus = "Married";
            } else if (getRadioButtonValue(divorced)){
                maritalstatus = "Divorced";

            }else if(getRadioButtonValue(widowed)){
                maritalstatus="Widowed";
            }
            newPatient.setPtmaritalstatus(maritalstatus);
            if(noofboys.getText().toString().isEmpty()){
                newPatient.setPtnoofboys("0");

            }else{
                newPatient.setPtnoofboys(getEdittextValue(noofboys));
            }

            if(noofgirls.getText().toString().isEmpty()){
                newPatient.setPtnoofgirls("0");

            }else{
                newPatient.setPtnoofgirls(getEdittextValue(noofgirls));

            }
            newPatient.setPtnoofboys(getEdittextValue(noofboys));

            if(drug_allergies.getText().toString().isEmpty()){
                newPatient.setPtdrugallergies("No");

            }else{
                newPatient.setPtdrugallergies(getEdittextValue(drug_allergies));

            }




           String medication;
            if (getRadioButtonValue(optyes)) {
                medication = "Yes";

            }else{
                medication="No";
            }
            newPatient.setPtmedication(medication);
            if(medicationmedicinename.getText().toString().isEmpty()){
                newPatient.setPtmedicationmedicinename("No");
            }else{
                newPatient.setPtmedicationmedicinename(getEdittextValue(medicationmedicinename));


            }

            if(disease.getText().toString().isEmpty()){
                newPatient.setPtdiseases("No");
            }else{
                newPatient.setPtdiseases(getEdittextValue(disease));
            }

            if(smokepcs.getText().toString().isEmpty()){
                newPatient.setPtsmoking("0");
            }else{
                newPatient.setPtsmoking(getEdittextValue(smokepcs));
            }

            if(alcoholpegs.getText().toString().isEmpty()){
                newPatient.setPtalcohol("0");
            }else{
                newPatient.setPtalcohol(getEdittextValue(alcoholpegs));
            }









            // Save Patient Detail
            LabDB db = new LabDB(getApplicationContext());
            if (db.getRowCount("pt_details", "") == 100) {
                return 2;
            } else {
                newPatient.setPtNo(device_id + duid);
                pt_id = db.SavePatient(newPatient);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    return 3;
                }
                String saved_id = String.valueOf(pt_id);
                newPatient.setId(pt_id);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    return 3;
                }
            }
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
                txtPtno.setText(String.valueOf(newPatient.getPtNo()));
                pref = thisContext.getSharedPreferences("sunyahealth", MODE_PRIVATE);
                SharedPreferences.Editor editor = pref.edit();
                editor.putInt("PTNO", pt_id);
                editor.apply();
                Toast.makeText(getApplicationContext(), "Patient Saved " + newPatient.getPtNo() + " V " + vitalSign.getRow_id(), Toast.LENGTH_LONG).show();
            }
        }
    }

    public String getEdittextValue(EditText editText) {
        String val = editText.getText().toString().trim();
        if (val != null && !val.equals("null")) {
            return val;
        } else {
            return "";
        }
    }

    public boolean getRadioButtonValue(RadioButton radioButton) {
        return radioButton.isChecked();
    }

//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if (requestCode == TAKE_PICTURE && resultCode == Activity.RESULT_OK) {
//            if (mCurrentPhotoPath != null) {
//                int ptno = Integer.parseInt(txtPtno.getText().toString());
//                Intent urine_test_comfirm = new Intent(PersonalDetailsActivity.this, UrineConfirmActivity.class);
//                urine_test_comfirm.putExtra("PTNO", ptno);
//                urine_test_comfirm.putExtra("photopath", mCurrentPhotoPath);
//                startActivity(urine_test_comfirm);
//            } else {
//                Toast.makeText(PersonalDetailsActivity.this, "Couldn't Take photo,Please Try Again", Toast.LENGTH_LONG).show();
//            }
//        }
//    }

    public boolean isCameraPermissionGranted() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.CAMERA)
                    == PackageManager.PERMISSION_GRANTED) {
                Log.v(TAG, "Permission is granted");
                return true;
            } else {

                Log.v(TAG, "Permission is revoked");
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA, Manifest.permission.CAMERA}, 1);
                return false;
            }
        } else { //permission is automatically granted on sdk<23 upon installation
            Log.v(TAG, "Permission is granted");
            return true;
        }
    }

    public boolean isStoragePermissionGranted() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED) {
                Log.v(TAG, "Permission is granted");
                return true;
            } else {

                Log.v(TAG, "Permission is revoked");
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                return false;
            }
        } else { //permission is automatically granted on sdk<23 upon installation
            Log.v(TAG, "Permission is granted");
            return true;
        }
    }

    public boolean isReadStoragePermissionGranted() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED) {
                Log.v(TAG, "Permission is granted");
                return true;
            } else {

                Log.v(TAG, "Permission is revoked");
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
                return false;
            }
        } else { //permission is automatically granted on sdk<23 upon installation
            Log.v(TAG, "Permission is granted");
            return true;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            Log.v(TAG, "Permission: " + permissions[0] + "was " + grantResults[0]);
            //resume tasks needing this permission
            Map<String, Integer> perms = new HashMap<String, Integer>();
            for (int i = 0; i < permissions.length; i++)
                perms.put(permissions[i], grantResults[i]);
            if (perms.get(Manifest.permission.ACCESS_NETWORK_STATE) == PackageManager.PERMISSION_GRANTED
                    && perms.get(Manifest.permission.INTERNET) == PackageManager.PERMISSION_GRANTED
                    && perms.get(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED
                    && perms.get(Manifest.permission.READ_PHONE_STATE) == PackageManager.PERMISSION_GRANTED
                    && perms.get(Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    if (perms.get(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                        // All Permissions Granted
                        Toast.makeText(this, "All permission accepted", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    // All Permissions Granted
                    Toast.makeText(this, "All permission accepted", Toast.LENGTH_SHORT).show();
                }
            }
        }

    }

    /**
     * Create a File for saving an image or video
     */
    private File getOutputMediaFile() {

        File mediaStorageDir = new File(String.valueOf(getApplicationContext().getExternalFilesDir("pt_photos")));
        // Create the storage directory if it does not exist
        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                Log.d("sunyaHealth", "failed to create directory");
                return null;
            }
        }

        // Create a media file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        File mediaFile;
        mediaFile = new File(mediaStorageDir.getPath() + File.separator +
                "IMG_" + txtPtno.getText().toString() + ".jpg");
        return mediaFile;
    }

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "URINE_" + txtPtno.getText().toString() + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = image.getAbsolutePath();
        return image;
    }

    private boolean datavalidation() {
        boolean result = true;
        String email = txtEmail.getText().toString();
        if (txtPtName.getText().toString().equals("")
                || txtPtAddress.getText().toString().equals("")
                || txtAge.getText().toString().equals("")
                || (!optMale.isChecked() && !optFemale.isChecked() && !optOther.isChecked())) {
            Toast.makeText(getApplicationContext(), "Please Fill up data", Toast.LENGTH_LONG).show();
            result = false;
            return result;
        }

        if (Integer.parseInt(txtAge.getText().toString()) > 150) {
            Toast.makeText(thisContext, "Invalid Age", Toast.LENGTH_LONG).show();
            return false;
        }

        return result;
    }

    @Override
    public void onBackPressed() {
//        super.onBackPressed();
        Intent i = new Intent(PersonalDetailsActivity.this, PatientActivity.class);
        i.putExtra("PTNO", pt_id);
        i.putExtra("duid", duid);
        setResult(-1, i);
        finish();
    }
}