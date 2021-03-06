package com.agatsa.testsdknew.ui.Diabetes;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.agatsa.testsdknew.LabInstanceDB;
import com.agatsa.testsdknew.Models.GlucoseModel;
import com.agatsa.testsdknew.Models.PatientModel;
import com.agatsa.testsdknew.R;
import com.agatsa.testsdknew.customviews.DialogUtil;
import com.agatsa.testsdknew.databinding.ActivityNewDiabetesBinding;
import com.agatsa.testsdknew.ui.LabDB;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import br.com.ilhasoft.support.validation.Validator;

public class NewDaibetesActivity extends AppCompatActivity implements DiabetesView {
    SharedPreferences pref;
    String ptno = "";
    PatientModel newPatient = new PatientModel();
    LabDB labDB;
    ActivityNewDiabetesBinding binding;
    Validator validator;
    GlucoseModel glucoseModel=new GlucoseModel();
    private ProgressDialog dialog;
    RadioButton Breakfast, Lunch, Supper,Snack;
    String Meal="";
    String datentime="";
    String timepickertxt="";
    String datepickertxt="";
    String am_pm="";
    String currentDateandTime="";
    long different;
    double value;
    String testype="";
    String Random="";
    DatePickerDialog picker;
    DiabetesPresenter diabetesPresenter;
    String patient_id;
    LabInstanceDB labInstanceDB;
    Integer diabeteslevel;



    @SuppressLint("DefaultLocale")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_new_diabetes);
        validator = new Validator(binding);
        validator.enableFormValidationMode();
        pref = this.getSharedPreferences("sunyahealth", Context.MODE_PRIVATE);
        ptno = pref.getString("PTNO", "");
        labInstanceDB=new LabInstanceDB(this);
        newPatient = getIntent().getParcelableExtra("patient");
        diabetesPresenter=new DiabetesPresenter(this,this);
        Breakfast=findViewById(R.id.breakfast);
        Lunch=findViewById(R.id.lunch);
        Supper=findViewById(R.id.supper);
        Snack=findViewById(R.id.Snack);
        labDB = new LabDB(getApplicationContext());
        dialog = new ProgressDialog(this);
        dialog.setCanceledOnTouchOutside(false);
        labDB=new LabDB(this);
        patient_id=getIntent().getStringExtra("patient_id");

        dialog.setCancelable(false);

        SimpleDateFormat sdf = new SimpleDateFormat( "yyyy:MM:dd" );
        binding.datepicker.setText( sdf.format( new Date() ));


        binding.btnsave.setOnClickListener(v -> {

            if(datentime.equals("") && testype.equals("")){
                Toast.makeText(this,"Enter time to Continue",Toast.LENGTH_LONG).show();
            }else{
                if(validator.validate()){
                    new SaveData().execute();

                }

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
            binding.cardviewyes.setVisibility(View.GONE);
            Intent i=new Intent(this,NodaibetesActivity.class);
            i.putExtra("PTNO", ptno);
            i.putExtra("patient",newPatient);
            startActivity(i);


        });

        binding.buttonyes.setOnClickListener(v -> {
            binding.cardviewyes.setVisibility(View.VISIBLE);
        });

        binding.datepicker.setOnClickListener(v -> {

            final Calendar cldr = Calendar.getInstance();
            int day = cldr.get(Calendar.DAY_OF_MONTH);
            int month = cldr.get(Calendar.MONTH);
            int year = cldr.get(Calendar.YEAR);
            // date picker dialog
            picker = new DatePickerDialog(NewDaibetesActivity.this,
                    (view, year1, monthOfYear, dayOfMonth) -> binding.datepicker.setText(year1 + ":" + (monthOfYear + 1) + ":" +day ), year, month, day);
            picker.show();


        });



        binding.timepicker.setOnClickListener((View v) -> {
            Calendar mcurrentTime = Calendar.getInstance();
            int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
            int minute = mcurrentTime.get(Calendar.MINUTE);


            TimePickerDialog mTimePicker;

            mTimePicker = new TimePickerDialog(NewDaibetesActivity.this, (timePicker, selectedHour, selectedMinute) -> {


                if (selectedHour > 12)
                {
                    selectedHour = selectedHour - 12;
                    am_pm = "pm";
                } else {
                    am_pm = "am";
                }

                binding.timepicker.setText( selectedHour + ":" + selectedMinute+am_pm );

                datepickertxt=binding.datepicker.getText().toString();
                timepickertxt=binding.timepicker.getText().toString();
                datentime=datepickertxt+" "+timepickertxt;
                Log.d("datentime",datentime);
                Date date = Calendar.getInstance().getTime();
                DateFormat dateFormat = new SimpleDateFormat("yyyy:MM:dd hh:mm");
                currentDateandTime = dateFormat.format(date);
                Log.d("difference",currentDateandTime);
                try {
                    Date date1 = dateFormat.parse(currentDateandTime);
                    Date date2 = dateFormat.parse(datentime);

                    different=date1.getTime()- date2.getTime();
                    Log.d("difference : " , String.valueOf(date1));
                    Log.d("difference : ", String.valueOf(date2));
                    Log.d("difference : " , String.valueOf(different));

                    if(different<7200000){
                        binding.glucosetesttype.setText("Random Glucose Test");
                    }else if(different>=720000  && different<10800000){
                        binding.glucosetesttype.setText("Post Prandial Glucose Test");


                    }else if(different>=10800000){
                        binding.glucosetesttype.setText("Random Glucose Test");

                    }


                } catch (ParseException e) {
                    e.printStackTrace();
                }


            }, hour, minute, false);
            mTimePicker.setTitle("Select Time");
            mTimePicker.show();



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
            LabInstanceDB labInstanceDB=new LabInstanceDB(getApplicationContext());
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
                return 3;
            }


            Meal = "Breakfast";
            if (getRadioButtonValue(Lunch)) {
                Meal = "Lunch";
            } else if (getRadioButtonValue(Supper)) {
                Meal = "Supper";

            } else if (getRadioButtonValue(Snack)) {
                Meal = "Snack";
            }

            testype = binding.glucosetesttype.getText().toString();
            value= Double.parseDouble(getEdittextValue(binding.txtGlucoselevel));
            glucoseModel.setPt_no(ptno);

            glucoseModel.setPttimetaken(currentDateandTime);
            glucoseModel.setPttesttype(testype);
            if (testype.equals("Post Prandial Glucose Test")) {
                 Random = String.format("%.2f", value);
                if (value <= 180) {
                    Random += "(Normal)";
                } else if (value > 180) {
                    Random += "(Prediabetes)";

                }
                glucoseModel.setPtGlucose(Random+"mg/dL");


            } else if (testype.equals("Random Glucose Test")) {
                 Random = String.format("%.2f", value);

                if (value <= 140 && value > 0) {
                    Random += "(Normal)";
                } else if (value > 140 && value <= 200) {
                    Random += "(Prediabetes)";

                } else if (value > 200) {
                    Random += "(Diabetes)";


                }
                glucoseModel.setPtGlucose(Random+"mg/dL");



            } else {
                 Random = String.format("%.2f", value);
                if (value <= 100 && value > 0) {
                    Random += "(Normal)";
                } else if (value > 100 && value <= 125) {
                    Random += "(Prediabetes)";


                } else if (value > 125) {
                    Random += "(Diabetes)";

                }
                glucoseModel.setPtGlucose(Random+"mg/dL");


            }



               Log.d("random",Random);

                glucoseModel.setPtlatestmealtime(datentime);
                glucoseModel.setPtmealtype(Meal);
                String last_vitalsign_row_id = db.SaveGlucoseSign(glucoseModel);
                String last_vitalsign_row_idd = labInstanceDB.SaveGlucoseSign(glucoseModel);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    return 3;
                }
                glucoseModel.setRow_id(last_vitalsign_row_id);
                glucoseModel.setRow_id(last_vitalsign_row_idd);
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
