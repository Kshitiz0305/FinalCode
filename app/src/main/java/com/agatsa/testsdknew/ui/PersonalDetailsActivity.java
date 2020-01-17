package com.agatsa.testsdknew.ui;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
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
import com.agatsa.testsdknew.Models.districtsplaces.DistrictPlaces;
import com.agatsa.testsdknew.Models.districtsplaces.Districts;
import com.agatsa.testsdknew.Models.districtsplaces.Places;
import com.agatsa.testsdknew.R;
import com.agatsa.testsdknew.customviews.DialogUtil;
import com.agatsa.testsdknew.databinding.ActivityNewPatientDetailBinding;
import com.agatsa.testsdknew.utils.StrictEncryption;
import com.google.gson.Gson;
import com.hornet.dateconverter.DateConverter;
import com.hornet.dateconverter.DatePicker.DatePickerDialog;
import com.hornet.dateconverter.Model;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import br.com.ilhasoft.support.validation.Validator;


public class PersonalDetailsActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener{

    String TAG = "PATIENTDETAIL";
    String device_id = "", duid = "", suid = "";
    String pt_id = "";
    SharedPreferences pref;
    Button btnSave;
    private ProgressDialog dialog;
    TextView txtPtno;
    PatientModel newPatient = new PatientModel();
    VitalSign vitalSign = new VitalSign();
    EditText txtPtName, txtPtAddress, txtPtContactNo, txtEmail,noofboys,noofgirls,drug_allergies,medicationmedicinename;
    EditText txtAge,disease,smokepcs,alcoholpegs;
    RadioButton optMale, optFemale, optOther,optyes,optno;
    RadioButton married, unmarried, divorced,widowed;
    private static int TAKE_PICTURE = 111;
    String mCurrentPhotoPath;
    Context thisContext;
    LabDB labDB;

    LinearLayout noofchildrenll;
    Calendar myCalendar;
    RadioGroup maritalstatusrg;
    RadioGroup currentmedicationrg;
    RadioGroup unhealthyhabitsrg;
    LinearLayout medicationll;
    LinearLayout smokell;
    LinearLayout alcoholll;
    int changedage=-102;
    ActivityNewPatientDetailBinding binding;


ArrayList<String> distictnames = new ArrayList<>();
ArrayList<String>  placesnames = new ArrayList<>();
 Validator validator;
 int recentid;

 DistrictPlaces districtPlaces ;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_new_patient_detail);
        validator = new Validator(binding);
        validator.enableFormValidationMode();
        pref = this.getSharedPreferences("sunyahealth", Context.MODE_PRIVATE);
        device_id = pref.getString("device_id", "");
        duid = getIntent().getStringExtra("duid");
        pt_id = pref.getString("PTNO", "");

        thisContext = PersonalDetailsActivity.this;
        labDB = new LabDB(getApplicationContext());
        dialog = new ProgressDialog(this);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setCancelable(false);
        noofchildrenll=findViewById(R.id.noofhildrenll);
        maritalstatusrg=findViewById(R.id.maritalstatusrg);
        smokell=findViewById(R.id.smokell);
        alcoholll=findViewById(R.id.alcoholll);
        txtPtno = findViewById(R.id.txtPatientNo);
        txtPtName = findViewById(R.id.txtPatientName);
        txtPtAddress = findViewById(R.id.wardNo);
        txtPtContactNo = findViewById(R.id.txtContactNo);
        txtEmail = findViewById(R.id.txtemail);

        smokepcs=findViewById(R.id.et_smoking);
        alcoholpegs=findViewById(R.id.et_alcohol);

        txtAge = findViewById(R.id.txtAge);
        optMale = findViewById(R.id.optMale);
        optFemale = findViewById(R.id.optFemale);
        optOther = findViewById(R.id.optOther);
        optyes= findViewById(R.id.yes);
        optno = findViewById(R.id.no);
//        optsmoking= findViewById(R.id.optsmoking);
//        optalcohol = findViewById(R.id.optalcohol);


        married=findViewById(R.id.married);
        unmarried=findViewById(R.id.unmarried);
        divorced=findViewById(R.id.divorced);
        widowed=findViewById(R.id.widowed);
        noofboys=findViewById(R.id.noofboys);
        noofgirls=findViewById(R.id.noofgirls);
        drug_allergies=findViewById(R.id.drug_allergies);
        currentmedicationrg=findViewById(R.id.currentmedicationrg);
//        unhealthyhabitsrg=findViewById(R.id.unhealthyhabitsrg);
        medicationll=findViewById(R.id.medicationll);
        medicationmedicinename=findViewById(R.id.medicationmedicinename);
        disease=findViewById(R.id.diseases);


//        unhealthyhabitsrg.setOnCheckedChangeListener((radioGroup, i) -> {
//            switch (i){
//                case R.id.optsmoking:
//                    smokell.setVisibility(View.VISIBLE);
//                    alcoholll.setVisibility(View.GONE);
//                    break;
//
//                case R.id.optalcohol:
//                    alcoholll.setVisibility(View.VISIBLE);
//                    smokell.setVisibility(View.GONE);
//                    break;
//
//            }
//
//        });


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

        String generatedjsonString="";

        try {
            generatedjsonString = getJsonStringfromFile("locationdetails", "raw", getPackageName());
        }
        catch (Exception e){



        }

        try{
            if(!generatedjsonString.equals("")){

                Gson gson = new Gson();
                 districtPlaces =gson.fromJson(generatedjsonString,DistrictPlaces.class);

                for(Districts district:districtPlaces.getDistricts()){
                    distictnames.add(district.getName());


                }

               if( distictnames.size()>0)
                binding.spDistrict.setItem(distictnames);


            }



        }
        catch (Exception e){
            DialogUtil.getOKDialog(this,"","Please edit the json file in resource properly","Ok");




        }

        binding.spDistrict.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {


                placesnames.clear();
                String selectedDistrict = distictnames.get(i);
                if(districtPlaces!=null)
                {
                    for (Districts district : districtPlaces.getDistricts()) {
                        if (district.getName().equals(selectedDistrict)) {
                            for(Places place:district.getPlaces()){
                                placesnames.add(place.getPlacename());

                            }




                            }

                        }



                }

                binding.spPlace.setItem(placesnames);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });



//        binding.countrySpinner.setItem(countryName);

//        mBinding.countrySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
//                selectedCountryIso = countryList.get(i).getIso1();
//                selectedCountryName = countryName.get(i);
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> adapterView) {
//
//            }
//        });

        binding.rgDobType.setOnCheckedChangeListener((radioGroup, i) -> {

            switch (i) {
                case R.id.dob:
                    binding.etDob.setVisibility(View.VISIBLE);
                    if(changedage!=-102){

                        binding.txtAge.setText(String.valueOf(changedage));
                    }
                    binding.txtAge.setEnabled(false);
                    break;

                case R.id.Age:
                    binding.etDob.setVisibility(View.GONE);
                    binding.txtAge.setEnabled(true);
                    break;

            }




        });

        binding.rgDrug.setOnCheckedChangeListener((radioGroup, i) -> {

            switch (i) {
                case R.id.yesdrug:
                  binding.drugAllergies.setVisibility(View.VISIBLE);

                    break;

                case R.id.nodrug:
                    binding.drugAllergies.setVisibility(View.GONE);
                    break;

            }




        });

        binding.rgAlcohol.setOnCheckedChangeListener((radioGroup, i) -> {

            switch (i) {
                case R.id.rb_yes_alcohol:
                    binding.etAlcohol.setVisibility(View.VISIBLE);

                    break;

                case R.id.rb_no_alcohol:
                    binding.etAlcohol.setVisibility(View.GONE);
                    break;

            }




        });


        binding.rgSmoking.setOnCheckedChangeListener((radioGroup, i) -> {

            switch (i) {
                case R.id.rb_yes_smoking:
                    binding.etSmoking.setVisibility(View.VISIBLE);

                    break;

                case R.id.rb_no_smoking:
                    binding.etSmoking.setVisibility(View.GONE);
                    break;

            }




        });
        binding.rgDisease
                .setOnCheckedChangeListener((radioGroup, i) -> {

                    switch (i) {
                        case R.id.yes_disease:
                            binding.diseases.setVisibility(View.VISIBLE);

                            break;

                        case R.id.nodisease:
                            binding.diseases.setVisibility(View.GONE);
                            break;

                    }




                });


//     binding.etDob.setOnTouchListener(new View.OnTouchListener() {
//         @Override
//         public boolean onTouch(View view, MotionEvent motionEvent) {
//             binding.etDob.setEnabled(false);
//             Calendar cal = Calendar.getInstance();
//             int dayOfMonthEnglish = cal.get(Calendar.DAY_OF_MONTH);
//             int monthEnglish = cal.get(Calendar.MONTH)+1;
//             int yearEnglish = cal.get(Calendar.YEAR);
//             Model m=null;
//             Log.d("rantestdate","todays date"+dayOfMonthEnglish+monthEnglish+yearEnglish);
//             DateConverter dateConverter = new DateConverter();
//             try {
//                 m = dateConverter.getNepaliDate(yearEnglish,monthEnglish,dayOfMonthEnglish);
//             }
//             catch (Exception e){
//
//                 Log.d("rantestexception",e.getLocalizedMessage());
//
//
//             }
//             if(m!=null)
//             {
//                 DatePickerDialog dpd = DatePickerDialog.newInstance(PersonalDetailsActivity.this,m.getYear(),m.getMonth(),m.getDay());
//                 dpd.show(getSupportFragmentManager(), "Datepickerdialog");
//
//
//             }
//             else {
//
//                 Toast.makeText(getApplicationContext(), "Please set your local time correctly", Toast.LENGTH_LONG).show();
//
//
//
//             }
//
//
//             return false;
//         }
//     });
        binding.etDob.setOnClickListener(view -> {
            Calendar cal = Calendar.getInstance();
            int dayOfMonthEnglish = cal.get(Calendar.DAY_OF_MONTH);
            int monthEnglish = cal.get(Calendar.MONTH)+1;
            int yearEnglish = cal.get(Calendar.YEAR);
            Model m=null;
            Log.d("rantestdate","todays date"+dayOfMonthEnglish+monthEnglish+yearEnglish);
            DateConverter dateConverter = new DateConverter();
            try {
                m = dateConverter.getNepaliDate(yearEnglish,monthEnglish,dayOfMonthEnglish);
            }
            catch (Exception e){

                Log.d("rantestexception",e.getLocalizedMessage());


            }
            if(m!=null)
            {
                DatePickerDialog dpd = DatePickerDialog.newInstance(PersonalDetailsActivity.this,m.getYear(),m.getMonth(),m.getDay());
            dpd.show(getSupportFragmentManager(), "Datepickerdialog");


                }
            else {

                Toast.makeText(getApplicationContext(), "Please set your local time correctly", Toast.LENGTH_LONG).show();



            }

        }
        );

        maritalstatusrg.setOnCheckedChangeListener((group, checkedId) -> {
            switch (checkedId) {
                case R.id.married:
                    noofchildrenll.setVisibility(View.VISIBLE);


                case R.id.divorced:
                    noofchildrenll.setVisibility(View.VISIBLE);


                case R.id.widowed:
                    noofchildrenll.setVisibility(View.VISIBLE);


                    break;

                case R.id.unmarried:
                    noofchildrenll.setVisibility(View.GONE);

                    break;

            }

        });
//        this block is commented we dont need to fetch previous recent data
// this logic should be maintained clean
// getting the previous db  while checking the sharedpreferences value in certain key
//        final LabDB db = new LabDB(getApplicationContext());
//       newPatient = db.getPatient(device_id + duid, pt_id);
//
//        if (newPatient != null) {
//            txtPtno.setText(device_id + duid);
//            txtPtName.setText(newPatient.getPtName());
//            txtPtAddress.setText(newPatient.getPtAddress());
//            txtPtContactNo.setText(newPatient.getPtContactNo());
//            txtEmail.setText(newPatient.getPtEmail());
//            txtAge.setText(newPatient.getPtAge());
//            String gender = newPatient.getPtSex();
//            if (gender.equals("Male")) {
//                optMale.setChecked(true);
//            } else if (gender.equals("Female")) {
//                optFemale.setChecked(true);
//            } else if (gender.equals("Other")) {
//                optOther.setChecked(true);
//            }
//
//
//
//
//
//        }

        btnSave = findViewById(R.id.btnSave);
        btnSave.setOnClickListener(v -> {
            if (validator.validate()) {



                if(binding.spDistrict.getSelectedItemId()==-1){
                    Toast.makeText(getApplicationContext(), "Validation Error in District", Toast.LENGTH_LONG).show();

                }else {
                    if(binding.spPlace.getSelectedItemId()==-1){

                        Toast.makeText(getApplicationContext(), "Validation Error in Municipality/VDC", Toast.LENGTH_LONG).show();


                    }
                    else {

                        String sex = "Male";
                        if (getRadioButtonValue(optFemale)) {
                            sex = "Female";
                            System.out.println("Female Selected");
                        } else if (getRadioButtonValue(optOther))
                            sex = "Other";

                        try{
                            String encrypteddata = StrictEncryption.encrypt(txtPtName.getText().toString()+","+binding.spDistrict.getSelectedItem().toString()+" "+binding.spPlace.getSelectedItem().toString()+" "+binding.wardNo.getText().toString()+","+sex);
                            Log.d("rantest",  encrypteddata);
                            Log.d("rantest",  txtPtName.getText().toString()+","+binding.spDistrict.getSelectedItem().toString()+" "+binding.spPlace.getSelectedItem().toString()+" "+binding.wardNo.getText().toString()+","+sex);
                            Toast.makeText(getApplicationContext(), "Saving Data", Toast.LENGTH_LONG).show();
                            newPatient.setId(encrypteddata);
                            new savedata().execute();


                        }
                        catch (Exception e){
                            Toast.makeText(getApplicationContext(), "Something went wrong while encrypting ", Toast.LENGTH_LONG).show();


                        }





                    }

                }

//                navigatenext();
            }
            else {

                Toast.makeText(getApplicationContext(), "Validation Error", Toast.LENGTH_LONG).show();

            }
        });

    }




    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {


        DateConverter dc=new DateConverter();
        try {
        Model m = dc.getEnglishDate(year, monthOfYear, dayOfMonth);
        Log.d("rantestdobeng",String.valueOf(m.getYear())+"/"+String.valueOf(m.getMonth())+"/"+String.valueOf(m.getDay()));
        binding.etDob.setTag(String.valueOf(m.getYear())+"/"+String.valueOf(m.getMonth())+"/"+String.valueOf(m.getDay()));
        binding.etDob.setText(String.valueOf(year)+"/"+String.valueOf(monthOfYear)+"/"+String.valueOf(dayOfMonth));
        binding.txtAge.setEnabled(false);
        changedage= getAge(m.getYear(),m.getMonth(),m.getDay());
        binding.txtAge.setText(String.valueOf(getAge(m.getYear(),m.getMonth(),m.getDay())));

        }
catch (Exception e){
            Log.d("rantestex",e.getLocalizedMessage());


}




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
            newPatient.setPtAddress(binding.spDistrict.getSelectedItem().toString()+ " "+binding.spPlace.getSelectedItem().toString()+" "+binding.wardNo.getText().toString());
            if (txtPtContactNo.getText().toString().equals("")) {
                newPatient.setPtContactNo("nil");

            }else{
                newPatient.setPtContactNo(getEdittextValue(txtPtContactNo));

            }
//            newPatient.setPtContactNo(contactData);

            if(txtEmail.getText().toString().equals(""))
                newPatient.setPtEmail("nil");
                else
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
            if(noofboys.getText().toString().equals("")){
                newPatient.setPtnoofboys("0");

            }else{
                newPatient.setPtnoofboys(getEdittextValue(noofboys));
            }

            if(noofgirls.getText().toString().isEmpty()){
                newPatient.setPtnoofgirls("0");

            }else{
                newPatient.setPtnoofgirls(getEdittextValue(noofgirls));

            }
//            newPatient.setPtnoofboys(getEdittextValue(noofboys));

            if(drug_allergies.getVisibility()==View.GONE){
                newPatient.setPtdrugallergies("nil");

            }else{
                if(drug_allergies.getText().toString().equals("")){
                    newPatient.setPtdrugallergies("nil");
                }else{
                    newPatient.setPtdrugallergies(getEdittextValue(drug_allergies));

                }

            }


          if(binding.etDob.getVisibility()==View.VISIBLE) {

              if(binding.etDob.getTag()==null)
                  newPatient.setPtDob("nil");
              else
              newPatient.setPtDob((String) binding.etDob.getTag());
          }
          else
              newPatient.setPtDob("nil");



//           String medication;
//            if (getRadioButtonValue(optyes)) {
//                    medication = medicationmedicinename.getText().toString();
//
//            }else{
//                medication="nil";
//            }
//            newPatient.setPtmedication(medication);

            if(medicationmedicinename.getVisibility()==View.VISIBLE)
            {
                if(medicationmedicinename.getText().toString().equals("")){
                newPatient.setPtmedicationmedicinename("nil");
            }
                else{
                newPatient.setPtmedicationmedicinename(getEdittextValue(medicationmedicinename));
            }

            }
            else {
                newPatient.setPtmedicationmedicinename("nil");

            }

            if(disease.getVisibility()==View.GONE){
                newPatient.setPtdiseases("nil");
            }else{
                if(disease.getText().toString().equals("")){
                    newPatient.setPtdiseases("nil");

                }else{
                    newPatient.setPtdiseases(getEdittextValue(disease));
                }
            }


              if(smokepcs.getVisibility()==View.VISIBLE)
              { if(smokepcs.getText().toString().equals("")){
                newPatient.setPtsmoking("nil");
            }else{
                newPatient.setPtsmoking(getEdittextValue(smokepcs));

            }}
              else {
                  newPatient.setPtsmoking("nil");

              }


              if(alcoholpegs.getVisibility()==View.VISIBLE)
              {
                  if(alcoholpegs.getText().toString().equals("")){
                newPatient.setPtalcohol("nil");
            }else{
                newPatient.setPtalcohol(getEdittextValue(alcoholpegs));
            }
              }
              else {

                  newPatient.setPtalcohol("nil");
              }









            // Save Patient Detail
            LabDB db = new LabDB(getApplicationContext());
            if (db.getRowCount("pt_details", "") == 100) {
                return 2;
            }
            else {
//                newPatient.setPtNo(device_id + duid);
                pt_id = db.SavePatient(newPatient);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    return 3;
                }
                String saved_id = String.valueOf(pt_id);
//                newPatient.setId(pt_id);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    return 3;
                }

            Log.d("rantest",newPatient.getPtContactNo());
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
                editor.putString("PTNO", pt_id);
                editor.apply();
                Toast.makeText(getApplicationContext(), "Patient Saved " + newPatient.getPtNo() + " V " + vitalSign.getRow_id(), Toast.LENGTH_LONG).show();
                Intent intent = new Intent(PersonalDetailsActivity.this,TestActivity.class);
                intent.putExtra("patient",newPatient);
                intent.putExtra("ptid",pt_id);
                startActivity(intent);

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



    @Override
    public void onBackPressed() {

        DialogUtil.getOKCancelDialog(this, "Warning", " Do you want to save data?", "Yes", "No", (dialogInterface, i) -> {
            if (validator.validate()) {
                Toast.makeText(getApplicationContext(), "Saving Data", Toast.LENGTH_LONG).show();
                new savedata().execute();
//                    navigatenext();
            }
            else {

                Toast.makeText(getApplicationContext(), "Validation Error", Toast.LENGTH_LONG).show();

            }


        }, (dialogInterface, i) -> {

            Intent intent = new Intent(PersonalDetailsActivity.this, PatientActivity.class);
            intent.putExtra("PTNO", pt_id);
            intent.putExtra("duid", duid);
            setResult(-1, intent);
            finish();

        });
//        super.onBackPressed();

    }


    public int getAge(int year, int month, int day) {
        //calculating age from dob
        Calendar dob = Calendar.getInstance();
        Calendar today = Calendar.getInstance();
        dob.set(year, month, day);
        int age = today.get(Calendar.YEAR) - dob.get(Calendar.YEAR);
        if (today.get(Calendar.DAY_OF_YEAR) < dob.get(Calendar.DAY_OF_YEAR)) {
            age--;
    }
        return age;
    }


    public String getJsonStringfromFile(String jsonFileName, String resources, String packagename) {

        InputStream is = getResources().openRawResource(getResourceId(jsonFileName,resources,packagename));
        Writer writer = new StringWriter();
        char[] buffer = new char[1024];
        try {
            Reader reader;
            reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
            int n;
            while ((n = reader.read(buffer)) != -1) {
                writer.write(buffer, 0, n);
            }

            is.close();
          }
        catch (Exception e){



        }


        finally {

        }

        String jsonString = writer.toString();
      return  jsonString;
    }
    public  int getResourceId(String pVariableName, String pResourcename, String pPackageName)
    {
        try {
            Log.d("rantestid",String.valueOf(getResources().getIdentifier(pVariableName, pResourcename, pPackageName)));
            return getResources().getIdentifier(pVariableName, pResourcename, pPackageName);
        } catch (Exception e) {
            Log.d("rantest",e.getLocalizedMessage());
            e.printStackTrace();
            return -1;
        }

    }


}