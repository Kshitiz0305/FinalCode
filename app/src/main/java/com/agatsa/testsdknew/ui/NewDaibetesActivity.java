package com.agatsa.testsdknew.ui;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
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
import com.agatsa.testsdknew.databinding.ActivityNewDiabetesBinding;


import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import br.com.ilhasoft.support.validation.Validator;

public class NewDaibetesActivity extends AppCompatActivity {
    SharedPreferences pref;
    String ptno = "";
    PatientModel newPatient = new PatientModel();
    LabDB labDB;
    String TAG = "Diabetes";
    String ptname;
    ActivityNewDiabetesBinding binding;
    Validator validator;
    GlucoseModel glucoseModel=new GlucoseModel();
    private ProgressDialog dialog;
    TimePickerDialog picker;
    RadioButton Breakfast, Lunch, Supper,Snack;
    String testtype="Post Prandial Glucose Test";
    String txttimingmeal="";
    String Meal="";





    @SuppressLint("DefaultLocale")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_new_diabetes);
        validator = new Validator(binding);
        validator.enableFormValidationMode();
        pref = this.getSharedPreferences("sunyahealth", Context.MODE_PRIVATE);
        ptno = pref.getString("PTNO", "");
        newPatient = getIntent().getParcelableExtra("patient");
        Breakfast=findViewById(R.id.breakfast);
        Lunch=findViewById(R.id.lunch);
        Supper=findViewById(R.id.supper);
        Snack=findViewById(R.id.Snack);

        labDB = new LabDB(getApplicationContext());
        dialog = new ProgressDialog(this);
        dialog.setCanceledOnTouchOutside(false);
        labDB=new LabDB(this);
        dialog.setCancelable(false);


        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add("Post Prandial Glucose Test");
        arrayList.add("Random Glucose Test");
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this,  android.R.layout.simple_spinner_item, arrayList);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.glucosetesttype.setAdapter(arrayAdapter);
        binding.btnsave.setOnClickListener(v -> {



            if(validator.validate()){

                new SaveData().execute();




            }

        });


        binding.help.setOnClickListener(view -> {

            final AlertDialog.Builder alert = new AlertDialog.Builder(NewDaibetesActivity.this);
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

        binding.buttonno.setOnClickListener(v -> {
//            binding.cardviewyes.setVisibility(View.GONE);
//            AlertDialog.Builder alert = new AlertDialog.Builder(DiabetesActivity.this);
//            View mView = getLayoutInflater().inflate(R.layout.activity_no_diabetes,null);
//            Button btn_save = (Button)mView.findViewById(R.id.buttonsave);
//            Button btn_cancel = (Button)mView.findViewById(R.id.buttoncancel);
////            EditText  editText = (EditText)mView.findViewById(R.id.txtglucoseditext);
//            final EditText editText1=(EditText)(mView.findViewById(R.id.txtglucoseditext));
//            txtglucose=editText1.getText().toString();
//            Log.d("glucosedailog", txtglucose);
//            Log.d("glucosedailog", txtglucose);
//            alert.setView(mView);
//           AlertDialog alertDialog = alert.create();
//            alertDialog.setCanceledOnTouchOutside(false);
//
//            btn_save.setOnClickListener(v1 -> {
//                if(editText1.length()==0 || editText1.getText().toString().isEmpty()){
//                    Toast.makeText(this, "Glucose Level Cannot Be Empty", Toast.LENGTH_SHORT).show();
//
//
//
//                }else{
//                    new SaveData().execute();
//
//                }
//
//            });
//
//            btn_cancel.setOnClickListener(v12 -> {
//                alertDialog.dismiss();
//
//            });
//            alertDialog.show();
            Intent i=new Intent(this,NodaibetesActivity.class);
            i.putExtra("PTNO", ptno);
            i.putExtra("patient",newPatient);
            startActivity(i);


        });

        binding.buttonyes.setOnClickListener(v -> {
            binding.cardviewyes.setVisibility(View.VISIBLE);


        });



        binding.txttiming.setOnClickListener((View v) -> {
            final Calendar cldr = Calendar.getInstance();

            int hour = cldr.get(Calendar.HOUR_OF_DAY);
            int minutes = cldr.get(Calendar.MINUTE);
            String format;
            if (hour == 0) {
                hour += 12;
                format = "AM";
            } else if (hour == 12) {
                format = "PM";
            } else if (hour > 12) {
                hour -= 12;
                format = "PM";
            } else {
                format = "AM";
            }

            String finalFormat = format;



            picker = new TimePickerDialog(NewDaibetesActivity.this,
                    (tp, sHour, sMinute) ->
                            binding.txttiming.setText(sHour + ":" + sMinute+" "+ finalFormat), hour, minutes, true);

            picker.show();



            binding.glucosetesttype.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    if(binding.glucosetesttype.getSelectedItem()==null){
                        Toast.makeText(NewDaibetesActivity.this, "Select One Option", Toast.LENGTH_SHORT).show();

                    }else{
                        testtype=binding.glucosetesttype.getSelectedItem().toString();

                    }
















                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                    Toast.makeText(NewDaibetesActivity.this, "Nothing Selected", Toast.LENGTH_SHORT).show();

                }
            });








        });









    }

    public boolean getRadioButtonValue(RadioButton radioButton) {
        return radioButton.isChecked();
    }






    @Override
    public void onBackPressed() {
        DialogUtil.getOKCancelDialog(this, "", "Do you want to discard the  diabetes test of " + newPatient.getPtName() + "?", "Yes","No", (dialogInterface, i) ->
                NewDaibetesActivity.super.onBackPressed());
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

            Meal="Breakfast";
            if (getRadioButtonValue(Lunch)) {
                Meal = "Lunch";
            } else if (getRadioButtonValue(Supper)){
                Meal = "Supper";

            }else if(getRadioButtonValue(Snack)){
                Meal="Snack";
            }


            txttimingmeal=binding.txttiming.getText().toString();

            Date currentTime = Calendar.getInstance().getTime();
            glucoseModel.setPt_no(ptno);
            glucoseModel.setPtGlucose(getEdittextValue(binding.txtGlucoselevel));
            glucoseModel.setPttimetaken(currentTime.toString());

            glucoseModel.setPttesttype(testtype);


            glucoseModel.setPtlatestmealtime(txttimingmeal);
            glucoseModel.setPtmealtype(Meal);




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

                NewDaibetesActivity.super.onBackPressed();
                Toast.makeText(getApplicationContext(), "Diabetes Test Saved " , Toast.LENGTH_LONG).show();
            }
        }
    }

    public String getEdittextValue(EditText editText) {
        return editText.getText().toString();
    }









}
