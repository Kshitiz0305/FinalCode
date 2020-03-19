package com.agatsa.testsdknew.ui.PatientEntry;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
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
import android.widget.ArrayAdapter;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.databinding.DataBindingUtil;
import com.agatsa.testsdknew.Models.BloodPressureModel;
import com.agatsa.testsdknew.Models.ECGReport;
import com.agatsa.testsdknew.Models.GlucoseModel;
import com.agatsa.testsdknew.Models.PatientModel;
import com.agatsa.testsdknew.Models.UrineReport;
import com.agatsa.testsdknew.Models.VitalSign;
import com.agatsa.testsdknew.R;
import com.agatsa.testsdknew.databinding.ActivityPatientEntryBinding;
import com.agatsa.testsdknew.ui.ExistingPatientActivity;
import com.agatsa.testsdknew.ui.LabDB;
import com.agatsa.testsdknew.ui.Personaldetails.PersonalDetailsActivity;
import com.agatsa.testsdknew.utils.CSVWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.nio.channels.FileChannel;
import java.text.SimpleDateFormat;
import java.util.Date;



public class PatientEntryActivity extends AppCompatActivity implements PatientEntryView  {

    private static final String TAG ="" ;
    ActivityPatientEntryBinding binding;
    LabDB labDB;
    String currentDateandTime;
   ProgressDialog dialog;
   String patient_id="";
   VitalSign vitalSign;
    SharedPreferences pref;
    ECGReport ecgReport;
    String pt_no="";
    UrineReport urineReport;
   PatientEntryPresenter patientEntryPresenter;
   BloodPressureModel bloodPressureModel;
   PatientModel patientModel;
   GlucoseModel glucoseModel;
   String token="d2c897d823c8816180c28d1eb5849b8aa1160dde";





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_patient_entry);
        labDB=new LabDB(this);
         pref = this.getSharedPreferences("sunyahealth", Context.MODE_PRIVATE);
        pt_no =pref.getString("PTNO","");
        dialog=new ProgressDialog(this);
        patientEntryPresenter=new PatientEntryPresenter(this,this);
        patient_id=pref.getString("Patient_id","");
        patientModel=getIntent().getParcelableExtra("patient");




        binding.btnNewPatient.setOnClickListener(view -> {
            Intent i = new Intent(PatientEntryActivity.this, PersonalDetailsActivity.class);
            startActivity(i);
        });

        binding.existingBtn.setOnClickListener(view -> {

            Intent i = new Intent(PatientEntryActivity.this, ExistingPatientActivity.class);

            startActivity(i);
        });

       binding.logout.setOnClickListener(view -> {
           finishAffinity();


       });

       binding.settings.setOnClickListener(v -> {
           showOptions();
       });


    }

    public void showOptions() {
        AlertDialog.Builder builderSingle = new AlertDialog.Builder(PatientEntryActivity.this);

        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
                PatientEntryActivity.this, android.R.layout.simple_list_item_1);
        arrayAdapter.add("Export");
        arrayAdapter.add("Sync");

        builderSingle.setNegativeButton("Close", (dialog, which) -> dialog.dismiss());

        builderSingle.setAdapter(arrayAdapter, (dialog, which) -> {
            String strName = arrayAdapter.getItem(which);
            if(strName == null)
                return;
            if ("Export".equals(strName)) {
//                new SyncData().execute();
                new SaveData().execute();

            }else if("Sync".equals((strName))){
                 new SyncData().execute();



            }

        });
        builderSingle.show();
    }


    public void exportDatabse(String databaseName) {
        try {
           File sd = Environment.getExternalStorageDirectory();
           File data = Environment.getDataDirectory();
            if (sd.canWrite()) {
                String currentDBPath = "//data//"+getPackageName()+"//databases//"+databaseName+"";
                String backupDBPath = "/sunyahealth/sunyahealth.sqlite"+currentDateandTime;
               File currentDB = new File(data, currentDBPath);
               File backupDB = new File(sd, backupDBPath);

                if (currentDB.exists()) {
                    FileChannel src = new FileInputStream(currentDB).getChannel();
                    FileChannel dst = new FileOutputStream(backupDB).getChannel();
                    dst.transferFrom(src, 0, src.size());
                   src.close();
                    dst.close();


               }
            }
        } catch (Exception e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();

        }
    }

    @Override
    public void onBackPressed() {
      finishAffinity();

    }

    private void exportpersonaldetailsDB() {



        File exportDir = new File(Environment.getExternalStorageDirectory().toString(), "/sunyahealth/CSV/"+currentDateandTime);
        if (!exportDir.exists())
        {
            exportDir.mkdirs();
        }

        File file = new File(exportDir, "Patientdetails.csv");
        try
        {
            file.createNewFile();
            CSVWriter csvWrite = new CSVWriter(new FileWriter(file));
            SQLiteDatabase db = labDB.getReadableDatabase();
            Cursor curCSV = db.rawQuery("SELECT * FROM pt_details",null);
            csvWrite.writeNext(curCSV.getColumnNames());
            while(curCSV.moveToNext())
            {
                //Which column you want to exprort
                String arrStr[] ={curCSV.getString(0),curCSV.getString(1),
                        curCSV.getString(2),curCSV.getString(3),
                        curCSV.getString(4),curCSV.getString(5),
                        curCSV.getString(6),curCSV.getString(7),
                        curCSV.getString(8),curCSV.getString(9),
                        curCSV.getString(10),curCSV.getString(11),
                        curCSV.getString(12),curCSV.getString(13),
                        curCSV.getString(14),curCSV.getString(15),
                        curCSV.getString(16),curCSV.getString(17),
                        curCSV.getString(18),curCSV.getString(19),
                        curCSV.getString(20)};
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
    private void exportVitalsignDB() {


        File exportDir = new File(Environment.getExternalStorageDirectory().toString(), "/sunyahealth/CSV/"+currentDateandTime);
        if (!exportDir.exists())
        {
            exportDir.mkdirs();
        }

        File file = new File(exportDir, "VitalSign.csv");
        try
        {
            file.createNewFile();
            CSVWriter csvVitalWrite = new CSVWriter(new FileWriter(file));
            SQLiteDatabase db = labDB.getReadableDatabase();
            Cursor curVitalCSV = db.rawQuery("SELECT * FROM  vital_sign",null);
            csvVitalWrite.writeNext(curVitalCSV.getColumnNames());
            while(curVitalCSV.moveToNext())
            {
                //Which column you want to export
                String vitalstr[] ={curVitalCSV.getString(0),curVitalCSV.getString(1),
                        curVitalCSV.getString(2),curVitalCSV.getString(3),
                        curVitalCSV.getString(4),curVitalCSV.getString(5),
                        curVitalCSV.getString(6),curVitalCSV.getString(7),
                        curVitalCSV.getString(8),curVitalCSV.getString(9)};
                csvVitalWrite.writeNext(vitalstr);
            }
            csvVitalWrite.close();
            curVitalCSV.close();
        }
        catch(Exception sqlEx)
        {
            Log.e("Vitalactivity", sqlEx.getMessage(), sqlEx);
        }


    }
    private void exportDiabetesDB() {

        LabDB labDB = new LabDB(this);
        File exportDir = new File(Environment.getExternalStorageDirectory().toString(), "/sunyahealth/CSV/"+currentDateandTime);
        if (!exportDir.exists())
        {
            exportDir.mkdirs();
        }

        File file = new File(exportDir, "Diabetes.csv");
        try
        {
            file.createNewFile();
            CSVWriter csvdiabtesWrite = new CSVWriter(new FileWriter(file));
            SQLiteDatabase db = labDB.getReadableDatabase();
            Cursor curdiabetesCSV = db.rawQuery("SELECT * FROM  diabetes_test",null);
            csvdiabtesWrite.writeNext(curdiabetesCSV.getColumnNames());
            while(curdiabetesCSV.moveToNext())
            {
                //Which column you want to exprort
                String diabetesStr[] ={curdiabetesCSV.getString(0),curdiabetesCSV.getString(1),
                        curdiabetesCSV.getString(2),curdiabetesCSV.getString(3),
                        curdiabetesCSV.getString(4)};
                csvdiabtesWrite.writeNext(diabetesStr);
            }
            csvdiabtesWrite.close();
            curdiabetesCSV.close();
        }
        catch(Exception sqlEx)
        {
            Log.e("MainActivity", sqlEx.getMessage(), sqlEx);
        }


    }
    private void exportbloodpressureDB() {


        LabDB dbhelper = new LabDB(getApplicationContext());
        File exportDir = new File(Environment.getExternalStorageDirectory().toString(), "/sunyahealth/CSV/"+currentDateandTime);
        if (!exportDir.exists())
        {
            exportDir.mkdirs();
        }

        File file = new File(exportDir, "Bloodpressure.csv");
        try
        {
            file.createNewFile();
            CSVWriter csvbloodpressureWrite = new CSVWriter(new FileWriter(file));
            SQLiteDatabase db = dbhelper.getReadableDatabase();
            Cursor curbloodpressureCSV = db.rawQuery("SELECT * FROM blood_pressure_test",null);
            csvbloodpressureWrite.writeNext(curbloodpressureCSV.getColumnNames());
            while(curbloodpressureCSV.moveToNext())
            {
                //Which column you want to exprort
                String bloodpressureStr[] ={curbloodpressureCSV.getString(0),curbloodpressureCSV.getString(1),
                        curbloodpressureCSV.getString(2),curbloodpressureCSV.getString(3),
                        curbloodpressureCSV.getString(4)};
                csvbloodpressureWrite.writeNext(bloodpressureStr);
            }
            csvbloodpressureWrite.close();
            curbloodpressureCSV.close();
        }
        catch(Exception sqlEx)
        {
            Log.e("MainActivity", sqlEx.getMessage(), sqlEx);
        }


    }
    private void exportECGDB() {

        LabDB labDB = new LabDB(this);
        File exportDir = new File(Environment.getExternalStorageDirectory().toString(), "/sunyahealth/CSV/"+currentDateandTime);
        if (!exportDir.exists())
        {
            exportDir.mkdirs();
        }

        File file = new File(exportDir, "ECG.csv");
        try
        {
            file.createNewFile();
            CSVWriter csveecgWrite = new CSVWriter(new FileWriter(file));
            SQLiteDatabase db = labDB.getReadableDatabase();
            Cursor curecgCSV = db.rawQuery("SELECT * FROM  ptEcg",null);
            csveecgWrite.writeNext(curecgCSV.getColumnNames());
            while(curecgCSV.moveToNext())
            {
                //Which column you want to exprort
                String diabetesStr[] ={curecgCSV.getString(0),curecgCSV.getString(1),
                        curecgCSV.getString(2),curecgCSV.getString(3),
                        curecgCSV.getString(4),curecgCSV.getString(5),
                        curecgCSV.getString(6),curecgCSV.getString(7),
                        curecgCSV.getString(8),curecgCSV.getString(9),
                        curecgCSV.getString(10),curecgCSV.getString(11),
                        curecgCSV.getString(12),curecgCSV.getString(13),
                        curecgCSV.getString(14)};
                csveecgWrite.writeNext(diabetesStr);
            }
            csveecgWrite.close();
            curecgCSV.close();
        }
        catch(Exception sqlEx)
        {
            Log.e("MainActivity", sqlEx.getMessage(), sqlEx);
        }


    }
    private void exportelevenparameterUrineDB() {

        LabDB labDB = new LabDB(this);
        File exportDir = new File(Environment.getExternalStorageDirectory().toString(), "/sunyahealth/CSV/"+currentDateandTime);
        if (!exportDir.exists())
        {
            exportDir.mkdirs();
        }

        File file = new File(exportDir, "Urine.csv");
        try
        {
            file.createNewFile();
            CSVWriter csveurineWrite = new CSVWriter(new FileWriter(file));
            SQLiteDatabase db = labDB.getReadableDatabase();
            Cursor cururineCSV = db.rawQuery("SELECT * FROM  urine_test",null);
            csveurineWrite.writeNext(cururineCSV.getColumnNames());
            while(cururineCSV.moveToNext())
            {
                //Which column you want to exprort
                String diabetesStr[] ={cururineCSV.getString(0),cururineCSV.getString(1),
                        cururineCSV.getString(2),cururineCSV.getString(3),
                        cururineCSV.getString(4),cururineCSV.getString(5),
                        cururineCSV.getString(6),cururineCSV.getString(7),
                        cururineCSV.getString(8),cururineCSV.getString(9),
                        cururineCSV.getString(10),cururineCSV.getString(11),
                        cururineCSV.getString(12),cururineCSV.getString(13),
                        cururineCSV.getString(14)};
                csveurineWrite.writeNext(diabetesStr);
            }
            csveurineWrite.close();
            cururineCSV.close();
        }
        catch(Exception sqlEx)
        {
            Log.e("MainActivity", sqlEx.getMessage(), sqlEx);
        }


    }
    private void exporttwoparameterUrineDB() {

        LabDB labDB = new LabDB(this);
        File exportDir = new File(Environment.getExternalStorageDirectory().toString(), "/sunyahealth/CSV/"+currentDateandTime);
        if (!exportDir.exists())
        {
            exportDir.mkdirs();
        }

        File file = new File(exportDir, "TwoParameterUrine.csv");
        try
        {
            file.createNewFile();
            CSVWriter csveurineWrite = new CSVWriter(new FileWriter(file));
            SQLiteDatabase db = labDB.getReadableDatabase();
            Cursor curtwoparameterurineCSV = db.rawQuery("SELECT * FROM  two_parameter_urine_test",null);
            csveurineWrite.writeNext(curtwoparameterurineCSV.getColumnNames());
            while(curtwoparameterurineCSV.moveToNext())
            {
                //Which column you want to exprort
                String diabetesStr[] ={curtwoparameterurineCSV.getString(0),curtwoparameterurineCSV.getString(1),
                        curtwoparameterurineCSV.getString(2),curtwoparameterurineCSV.getString(3),
                        curtwoparameterurineCSV.getString(4),curtwoparameterurineCSV.getString(5),
                        curtwoparameterurineCSV.getString(6)
                };
                csveurineWrite.writeNext(diabetesStr);
            }
            csveurineWrite.close();
            curtwoparameterurineCSV.close();
        }
        catch(Exception sqlEx)
        {
            Log.e("MainActivity", sqlEx.getMessage(), sqlEx);
        }


    }
    private void exporturicacid() {

        LabDB labDB = new LabDB(this);
        File exportDir = new File(Environment.getExternalStorageDirectory().toString(), "/sunyahealth/CSV/"+currentDateandTime);
        if (!exportDir.exists())
        {
            exportDir.mkdirs();
        }

        File file = new File(exportDir, "UricAcid.csv");
        try
        {
            file.createNewFile();
            CSVWriter csveurineWrite = new CSVWriter(new FileWriter(file));
            SQLiteDatabase db = labDB.getReadableDatabase();
            Cursor cururicacidCSV = db.rawQuery("SELECT * FROM  uric_acid",null);
            csveurineWrite.writeNext(cururicacidCSV.getColumnNames());
            while(cururicacidCSV.moveToNext())
            {
                //Which column you want to exprort
                String diabetesStr[] ={cururicacidCSV.getString(0),cururicacidCSV.getString(1),
                        cururicacidCSV.getString(2),cururicacidCSV.getString(3),
                        cururicacidCSV.getString(4),cururicacidCSV.getString(5),
                        cururicacidCSV.getString(6)
                };
                csveurineWrite.writeNext(diabetesStr);
            }
            csveurineWrite.close();
            cururicacidCSV.close();
        }
        catch(Exception sqlEx)
        {
            Log.e("MainActivity", sqlEx.getMessage(), sqlEx);
        }


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
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
                return 3;
            }
            if(isStoragePermissionGranted()){
                exportpersonaldetailsDB();
                exportVitalsignDB();
                exportbloodpressureDB();
                exportDiabetesDB();
                exportECGDB();
                exportelevenparameterUrineDB();
                exporttwoparameterUrineDB();
                exporturicacid();
                exportDatabse("Test1.db");
               copyfile();


            }


            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
                return 3;
            }


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
//                pref.edit().putInt("VTF",1).apply();
//                Log.d("vitaltestflag",String.valueOf(pref.getInt("VTF",0)));
//                VitalSignActivity.super.onBackPressed();
                Toast.makeText(getApplicationContext(), "Database Exported", Toast.LENGTH_LONG).show();
            }

        }
    }

    @SuppressLint("StaticFieldLeak")
    private class SyncData extends AsyncTask<String, Void, Integer> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog.setMessage("Saving Data");
            dialog.show();
        }

        @Override
        protected Integer doInBackground(String... strings) {
//
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
                return 3;
            }

            SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ");
             String formatteddate=f.format(new Date());
            vitalSign = labDB.getLastVitalSign(pt_no);
            bloodPressureModel=labDB.getbloodpressuresign(pt_no);
            glucoseModel=labDB.getDiabetesSign(pt_no);
            patientModel=labDB.getPatientmodel(pt_no);
//            ecgReport= labDB.getlastecg(pt_no);
//            urineReport=labDB.getLastUrineReport(pt_no);



            String temp = vitalSign.getTempt();
            String diabeteslevel = glucoseModel.getPtGlucose();
            String pulse = vitalSign.getPulse();
            temp = temp.replaceAll("[^0-9]", "");
           temp= temp.substring(0, temp.length() - 2);
//
            diabeteslevel = diabeteslevel.replaceAll("[^0-9]", "");
            diabeteslevel= diabeteslevel.substring(0, diabeteslevel.length() - 2);

//
//            // Convert str into StringBuffer as Strings
//            // are immutable.
           pulse = pulse.replaceAll("[^0-9]", "");

           Log.d("patientmodel", String.valueOf(patientModel));
            pulse= pulse.substring(0, pulse.length() - 2);

          patientEntryPresenter.login();

            patientEntryPresenter.vitalsign(patient_id, vitalSign.getWeight(), vitalSign.getHeight(), Integer.parseInt(temp),Integer.parseInt(pulse), vitalSign.getSto2(), bloodPressureModel.getSystolic(), bloodPressureModel.getDiastolic(), formatteddate, formatteddate);
           patientEntryPresenter.bloodpressure(patient_id,bloodPressureModel.getSystolic(),bloodPressureModel.getDiastolic(),formatteddate,formatteddate);
            patientEntryPresenter.diabetes(patient_id, diabeteslevel,formatteddate,formatteddate);
            patientEntryPresenter.medicalhistory(patient_id,patientModel.getPtdrugallergies(),patientModel.getPtdiseases(),"Typhoid",patientModel.getPtmedicationmedicinename(),false,false,false,formatteddate,patientModel.getPtCity());
            patientEntryPresenter.ecg(patient_id,formatteddate, 97,96,98,98, 90,90,98, 99,0);
            patientEntryPresenter.urinereport(patient_id,70, 80,90,97,92, 89,86, 87,89,90,98,"color_test","/storage/emulated",formatteddate,formatteddate);




            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
                return 3;
            }


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
//                pref.edit().putInt("VTF",1).apply();
//                Log.d("vitaltestflag",String.valueOf(pref.getInt("VTF",0)));
//                VitalSignActivity.super.onBackPressed();
//                Toast.makeText(getApplicationContext(), "Database Exported", Toast.LENGTH_LONG).show();
            }

        }
    }


    public  boolean isStoragePermissionGranted() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED) {
                Log.v(TAG,"Permission is granted");
                return true;
            } else {

                Log.v(TAG,"Permission is revoked");
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
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
            exportpersonaldetailsDB();
            exportVitalsignDB();
            exportDiabetesDB();
            exportECGDB();
            exportbloodpressureDB();
            exportelevenparameterUrineDB();
            exporttwoparameterUrineDB();
            exporturicacid();
            exportDatabse("Test1.db");
            copyfile();
        }
    }

    public void copyfile(){

        File SourceFile = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM).getAbsolutePath()+"/sanket");

        File DestinationFile = new File(Environment.getExternalStorageDirectory(), "/sunyahealth/ECG/");

        if(SourceFile.renameTo(DestinationFile))
        {
            Log.v("Moving", "Moving file successful.");
        }
        else
        {
            Log.v("Moving", "Moving file failed.");
        }


    }





}

