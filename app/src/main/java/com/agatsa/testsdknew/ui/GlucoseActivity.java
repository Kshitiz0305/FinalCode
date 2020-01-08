//package com.agatsa.testsdknew;
//
//import android.annotation.SuppressLint;
//import android.app.ProgressDialog;
//import android.content.Context;
//import android.content.Intent;
//import android.content.SharedPreferences;
//import android.os.AsyncTask;
//import android.os.Bundle;
//import android.widget.Button;
//import android.widget.EditText;
//import android.widget.Toast;
//
//import androidx.appcompat.app.AppCompatActivity;
//
//import com.agatsa.testsdknew.Models.GlucoseModel;
//
//public class GlucoseActivity extends AppCompatActivity  {
//    int ptno = 0;
//    SharedPreferences pref;
//    Button btnSave;
//    private ProgressDialog dialog;
//   GlucoseModel glucoseModel;
//    EditText txtglucose;
//    LabDB labDB;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_new_glucose);
//        pref = this.getSharedPreferences("sunyahealth", Context.MODE_PRIVATE);
//        ptno = pref.getInt("pt_id", 0);
//
//        labDB = new LabDB(getApplicationContext());
//        dialog = new ProgressDialog(this);
//        dialog.setCanceledOnTouchOutside(false);
//        dialog.setCancelable(false);
//
//        txtglucose = findViewById(R.id.txtglucose);
//
//
//        if(glucoseModel != null) {
//            txtglucose.setText(String.valueOf(glucoseModel.getPtGlucose()));
//
//        }
//
//        btnSave = findViewById(R.id.btnsave);
//        btnSave.setOnClickListener(v -> {
//
//            Toast.makeText(getApplicationContext(), "Saving Data", Toast.LENGTH_LONG).show();
//            new SaveData().execute();
//            navigatenext();
//
//        });
//    }
//
//    private void navigatenext() {
//        Intent i = new Intent(GlucoseActivity.this, PatientActivity.class);
//        startActivity(i);
//
//    }
//
//
//
//
//    @SuppressLint("StaticFieldLeak")
//    private class SaveData extends AsyncTask<String, Void, Integer> {
//        @Override
//        protected void onPreExecute() {
//            super.onPreExecute();
//            dialog.setMessage("Saving Data");
//            dialog.show();
//        }
//
//        @Override
//        protected Integer doInBackground(String... strings) {
////            newPatient = new PatientModel();
//            LabDB db = new LabDB(getApplicationContext());
//            try {
//                Thread.sleep(1000);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//                return 3;
//            }
//            // Save Vital Sign
//            glucoseModel.setPt_no(ptno);
//            glucoseModel.setPtGlucose((float) Double.parseDouble(getEdittextValue(txtglucose)));
//            int last_vitalsign_row_id = db.SaveGlucoseSign(glucoseModel);
//            try {
//                Thread.sleep(1000);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//                return 3;
//            }
//            glucoseModel.setRow_id(last_vitalsign_row_id);
//            return 1;
//        }
//
//        @Override
//        protected void onPostExecute(Integer s) {
//            super.onPostExecute(s);
//            if (s == 2) {
//                if (dialog.isShowing())
//                    dialog.dismiss();
////                Toast.makeText(getApplicationContext(), "Already saved " + glucoseModel.getPtNo() + " V " + vitalSign.getRow_id(), Toast.LENGTH_LONG).show();
//
//            } else if (s == 3) {
//                if (dialog.isShowing())
//                    dialog.dismiss();
////                Toast.makeText(getApplicationContext(), "Exception catched " + newPatient.getPtNo() + " V " + vitalSign.getRow_id(), Toast.LENGTH_LONG).show();
//
//            } else {
//                if (dialog.isShowing())
//                    dialog.dismiss();
////                Toast.makeText(getApplicationContext(), "Patient Saved " + newPatient.getPtNo() + " V " + vitalSign.getRow_id(), Toast.LENGTH_LONG).show();
//            }
//        }
//    }
//
//    public String getEdittextValue(EditText editText) {
//        return editText.getText().toString();
//    }
//
//}
