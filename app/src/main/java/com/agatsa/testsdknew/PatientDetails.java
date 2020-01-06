//package com.agatsa.testsdknew;
//
//import android.Manifest;
//import android.annotation.SuppressLint;
//import android.app.ProgressDialog;
//import android.content.Context;
//import android.content.pm.PackageManager;
//import android.os.AsyncTask;
//import android.os.Build;
//import android.os.Bundle;
//import android.os.Environment;
//import android.util.Log;
//import android.widget.Button;
//import android.widget.EditText;
//import android.widget.RadioButton;
//import android.widget.TextView;
//import android.widget.Toast;
//
//
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.core.app.ActivityCompat;
//
//import com.agatsa.testsdknew.Models.PatientModel;
//import com.agatsa.testsdknew.Models.VitalSign;
//
//import java.io.File;
//import java.io.IOException;
//import java.text.SimpleDateFormat;
//import java.util.Date;
//
//public class PatientDetails extends AppCompatActivity {
//    String TAG = "PATIENTDETAIL";
//    Button btnSave,btnPrintReport;
//    private ProgressDialog dialog;
////    Button btnBloodTest;
////    Button btnUrineTest;
//    TextView txtPtno;
//    PatientModel newPatient = new PatientModel();
//    VitalSign vitalSign = new VitalSign();
//    EditText txtPtName,txtPtAddress,txtPtContactNo,txtEmail;
//    EditText txtAge,txtWeight,txtHeight,txtTemp,txtPulse,txtBPS,txtBPD,txtSTO2;
//    RadioButton optMale,optFemale,optOther;
//    private  static  int TAKE_PICTURE = 111;
//    String mCurrentPhotoPath;
//    Context thisContext;
//    int ptid=0;
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_new_patient_detail);
//        thisContext = PatientDetails.this;
//        dialog = new ProgressDialog(this);
//        dialog.setCanceledOnTouchOutside(false);
//        dialog.setCancelable(false);
//
//        txtPtno = findViewById(R.id.txtPatientNo);
//        txtPtName = findViewById(R.id.txtPatientName);
//        txtPtAddress = findViewById(R.id.txtAddress);
//        txtPtContactNo = findViewById(R.id.txtContactNo);
//        txtEmail = findViewById(R.id.txtemail);
//
//        txtAge = findViewById(R.id.txtAge);
//        optMale = findViewById(R.id.optMale);
//        optFemale = findViewById(R.id.optFemale);
//        optOther = findViewById(R.id.optOther);
//
//        txtTemp = findViewById(R.id.txtTemp);
//        txtWeight = findViewById(R.id.txtWeight);
//        txtHeight = findViewById(R.id.txtHeight);
//        txtPulse = findViewById(R.id.txtPulse);
//        txtBPS = findViewById(R.id.txtBPSys);
//        txtBPD = findViewById(R.id.txtBPDias);
//        txtSTO2 = findViewById(R.id.txtSto2);
//
//        isStoragePermissionGranted();
////        isCameraPermissionGranted();
//        btnSave = findViewById(R.id.btnSave);
//        btnSave.setOnClickListener(v -> {
//            if(datavalidation()){
//                Toast.makeText(getApplicationContext(),"Saving Data",Toast.LENGTH_LONG).show();
//                new savedata().execute();
//            }
//        });
////        btnBloodTest = findViewById(R.id.btnBloodTest);
////        btnBloodTest.setOnClickListener(new View.OnClickListener() {
////            @Override
////            public void onClick(View v) {
////                if(txtPtno.getText().toString().equals("")){
////                    Toast.makeText(getApplicationContext(),"Please Save The Record to continue",Toast.LENGTH_LONG).show();
////                    return;
////                }
////                int ptno = Integer.parseInt(txtPtno.getText().toString());
////                Intent bloodtestintent = new Intent(getApplicationContext(),BloodTest.class);
////                bloodtestintent.putExtra("PTNO",ptno);
////                startActivity(bloodtestintent);
////            }
////        });
////        btnUrineTest = findViewById(R.id.btnUrineTest);
////        btnUrineTest.setOnClickListener(new View.OnClickListener() {
////            @Override
////            public void onClick(View v) {
////                if(txtPtno.getText().toString().equals("")){
////                    // Show Message to Save Record
////                    Toast.makeText(getApplicationContext(),"Please Save The Record to continue",Toast.LENGTH_LONG).show();
////                    return;
////                }
////
////                Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
////
////                if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
////                    // Create the File where the photo should go
////                    File photoFile = null;
////                    try {
////                        photoFile = createImageFile();
////                    } catch (IOException e) {
////                        e.printStackTrace();
////                    }
////                    // Continue only if the File was successfully created
////                    if (photoFile != null) {
//////                        Uri photoURI  = Uri.fromFile(photoFile);
////                        Uri photoURI  = FileProvider.getUriForFile(PatientDetails.this,
////                                "com.sunyahealth.fileprovider",
////                                photoFile);
////
////                        takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
////                        startActivityForResult(takePictureIntent, TAKE_PICTURE);
////                    }
////                }
////            }
////        });
////        btnPrintReport = findViewById(R.id.btnPrintReport);
////        btnPrintReport.setOnClickListener(v -> {
////            if(txtPtno.getText().toString().equals("")){
////                Toast.makeText(getApplicationContext(),"Please Save The Record to continue",Toast.LENGTH_LONG).show();
////                return;
////            }
////            int ptno = Integer.parseInt(txtPtno.getText().toString());
////            Intent reportintent = new Intent(getApplicationContext(),PrintReport.class);
////            reportintent.putExtra("PTNO",ptno);
////            startActivity(reportintent);
////
////        });
//
//    }
//    private class savedata extends AsyncTask<String,Void,Integer>{
//        @Override
//        protected void onPreExecute() {
//            super.onPreExecute();
//            dialog.setMessage("Saving Data");
//            dialog.show();
//        }
//
//        @SuppressLint("WrongThread")
//        @Override
//        protected Integer doInBackground(String... strings) {
////            newPatient = new PatientModel();
//            newPatient.setPtName(txtPtName.getText().toString());
//            newPatient.setPtAddress(txtPtAddress.getText().toString());
//            newPatient.setPtContactNo(txtPtContactNo.getText().toString());
//            newPatient.setPtEmail(txtEmail.getText().toString());
//            newPatient.setPtAge(txtAge.getText().toString());
//            String sex="Male";
//            if(optFemale.isChecked()){
//                sex="Female";
//                System.out.println("Female Selected");
//            }
//            else if(optOther.isChecked())
//                sex="Other";
//            newPatient.setPtSex(sex);
//            // Save Patient Detail
//            LabDB db = new LabDB(getApplicationContext());
//            if (db.getRowCount("pt_details", "") == 100) {
//                return 2;
//            } else {
//                ptid = db.SavePatient(newPatient);
//                try {
//                    Thread.sleep(1000);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                    return 3;
//                }
//                String saved_id = String.valueOf(ptid);
//                newPatient.setPtNo(ptid);
//                try {
//                    Thread.sleep(1000);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                    return 3;
//                }
//            }
//            return 1;
//
//
//
//
//        }
//
//        @Override
//        protected void onPostExecute(Integer s) {
//            super.onPostExecute(s);
//            if(dialog.isShowing())
//                dialog.dismiss();
//            txtPtno.setText(String.valueOf(newPatient.getPtNo()));
//            Toast.makeText(getApplicationContext(),"Patient Saved " + newPatient.getPtNo(),Toast.LENGTH_LONG).show();
//        }
//    }
//
////    @Override
////    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
////        super.onActivityResult(requestCode, resultCode, data);
////        if(requestCode==TAKE_PICTURE && resultCode == Activity.RESULT_OK){
////            if(mCurrentPhotoPath!=null) {
////                int ptno = Integer.parseInt(txtPtno.getText().toString());
////                Intent urine_test_comfirm = new Intent(PatientDetails.this, UrineConfirmActivity.class);
////                urine_test_comfirm.putExtra("PTNO", ptno);
////                urine_test_comfirm.putExtra("photopath", mCurrentPhotoPath);
////                startActivity(urine_test_comfirm);
////            }else {
////                Toast.makeText(PatientDetails.this,"Couldn't Take photo,Please Try Again",Toast.LENGTH_LONG).show();
////            }
////        }
////    }
////    public  boolean isCameraPermissionGranted() {
////        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
////            if (checkSelfPermission(Manifest.permission.CAMERA)
////                    == PackageManager.PERMISSION_GRANTED) {
////                Log.v(TAG,"Permission is granted");
////                return true;
////            } else {
////
////                Log.v(TAG,"Permission is revoked");
////                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA,Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
////                return false;
////            }
////        }
////        else { //permission is automatically granted on sdk<23 upon installation
////            Log.v(TAG,"Permission is granted");
////            return true;
////        }
////    }
//
//    public  boolean isStoragePermissionGranted() {
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//            if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
//                    == PackageManager.PERMISSION_GRANTED) {
//                Log.v(TAG,"Permission is granted");
//                return true;
//            } else {
//
//                Log.v(TAG,"Permission is revoked");
//                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
//                return false;
//            }
//        }
//        else { //permission is automatically granted on sdk<23 upon installation
//            Log.v(TAG,"Permission is granted");
//            return true;
//        }
//    }
//    @Override
//    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//        if(grantResults[0]== PackageManager.PERMISSION_GRANTED){
//            Log.v(TAG,"Permission: "+permissions[0]+ "was "+grantResults[0]);
//            //resume tasks needing this permission
//        }
//
//    }
//
//    /** Create a File for saving an image or video */
//    private File getOutputMediaFile(){
//
//        File mediaStorageDir = new File(String.valueOf(getApplicationContext().getExternalFilesDir("pt_photos")));
//        // Create the storage directory if it does not exist
//        if (! mediaStorageDir.exists()){
//            if (! mediaStorageDir.mkdirs()){
//                Log.d("sunyaHealth", "failed to create directory");
//                return null;
//            }
//        }
//
//        // Create a media file name
//        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
//        File mediaFile;
//            mediaFile = new File(mediaStorageDir.getPath() + File.separator +
//                    "IMG_"+ txtPtno.getText().toString() + ".jpg");
//        return mediaFile;
//    }
//    private File createImageFile() throws IOException {
//        // Create an image file name
//        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
//        String imageFileName = "URINE_" + txtPtno.getText().toString() + "_";
//        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
//        File image = File.createTempFile(
//                imageFileName,  /* prefix */
//                ".jpg",         /* suffix */
//                storageDir      /* directory */
//        );
//
//        // Save a file: path for use with ACTION_VIEW intents
//        mCurrentPhotoPath = image.getAbsolutePath();
//        return image;
//    }
//    private boolean datavalidation(){
//        boolean result = true;
//        String email = txtEmail.getText().toString();
//        if(txtPtName.getText().toString().equals("")
//                || txtPtAddress.getText().toString().equals("")
//                || txtPtContactNo.getText().toString().equals("")
//                || txtAge.getText().toString().equals("")
//                || (!optMale.isChecked() && !optFemale.isChecked() && !optOther.isChecked())) {
//            Toast.makeText(getApplicationContext(), "Please Fill up data", Toast.LENGTH_LONG).show();
//            result = false;
//            return result;
//        }
//        if(txtPtContactNo.getText().length()!=10){
//            Toast.makeText(thisContext,"Invalid Cell Number",Toast.LENGTH_LONG).show();
//            result = false;
//            return result;
//        }
//        if(!email.equals("")){
//            if(email.startsWith("@") || !email.contains("@") || email.endsWith("@")){
//                Toast.makeText(thisContext,"Invalid email Address",Toast.LENGTH_LONG).show();
//                result = false;
//                return result;
//            }
//        }
//        if(Integer.parseInt(txtAge.getText().toString())>150){
//            Toast.makeText(thisContext,"Invalid Age",Toast.LENGTH_LONG).show();
//            return false;
//        }
//        return result;
//    }
//}
