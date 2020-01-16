package com.agatsa.testsdknew.ui;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.databinding.DataBindingUtil;

import com.agatsa.sanketlife.callbacks.PdfCallback;
import com.agatsa.sanketlife.callbacks.ResponseCallback;
import com.agatsa.sanketlife.callbacks.SaveEcgCallBack;
import com.agatsa.sanketlife.development.EcgConfig;
import com.agatsa.sanketlife.development.Errors;
import com.agatsa.sanketlife.development.InitiateEcg;
import com.agatsa.sanketlife.development.Success;
import com.agatsa.sanketlife.development.UserDetails;
import com.agatsa.testsdknew.BuildConfig;
import com.agatsa.testsdknew.Models.ECGReport;
import com.agatsa.testsdknew.R;
import com.agatsa.testsdknew.databinding.ActivitySingleLeadBinding;
import com.ontbee.legacyforks.cn.pedant.SweetAlert.SweetAlertDialog;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public  class

 SingleLeadECG extends AppCompatActivity implements ResponseCallback {
    private static final String CLIENT_ID = "5a3b4c16b4a56b000132f5d5b4580266565440bda51dcb4122d39844";
    private static final String SECRET_ID = "5a3b4c16b4a56b000132f5d5746be305d56c49e49cc88b12ccb05d71";
    private Button btnsavereport,back;

    LinearLayout LeadOne,txttakeagain,btnsavell,viewreportll;
    private Context mContext;
    String  ptno = "";
    SharedPreferences pref;
    ECGReport ecgReport=new ECGReport();
    SweetAlertDialog pDialog;
    Toolbar toolbar;
        ActivitySingleLeadBinding binding;
    static int state=0;
   public static String pdfuri;

    InitiateEcg initiateEcg;
     private static final String[] REQUIRED_SDK_PERMISSIONS = new String[]{
             Manifest.permission.WRITE_EXTERNAL_STORAGE,
             Manifest.permission.READ_EXTERNAL_STORAGE,
            };

     private final static int REQUEST_CODE_ASK_PERMISSIONS = 1;






     @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_single_lead);
        checkPermissions();
        pref = this.getSharedPreferences("sunyahealth", Context.MODE_PRIVATE);
        ptno = pref.getString("PTNO","");




        initiateEcg = new InitiateEcg();
        initViews();
        if(state%3==1){
            Log.d("rantest","equals1");
            state=state+1;
        }
        else if(state%3==2){
            Log.d("rantest","equals2");
            btnsavell.setVisibility(View.VISIBLE);
            txttakeagain.setVisibility(View.VISIBLE);
            LeadOne.setVisibility(View.GONE);
            state=state+1;
        }




//        labdb = new LabDB(getApplicationContext());
//        ecgReport=labdb.getLastEcgSign(ptno);
        mContext = getApplicationContext();
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Single Lead");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

       binding.btViewreport.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               if(!pdfuri.equals(""))
               {
                   File file = new File(pdfuri);
               Intent intent = new Intent(Intent.ACTION_VIEW);

               // set leadIndex to give temporary permission to external app to use your FileProvider
               intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);

               // generate URI, I defined authority as the application ID in the Manifest, the last param is file I want to open
               Uri photoURI = FileProvider.getUriForFile(SingleLeadECG.this,
                       BuildConfig.APPLICATION_ID + ".provider",
                       file);
               // I am opening a PDF file so I give it a valid MIME type
               intent.setDataAndType(photoURI, "application/pdf");

               // validate that the device can open your File!
               startActivity(intent);




           }}
       });
        initOnClickListener();
    }




    private void initViews() {
        btnsavereport = findViewById(R.id.btnSavereport);
        LeadOne = findViewById(R.id.txtLeadOne);
        txttakeagain=findViewById(R.id.txttakeagain);
        btnsavell=findViewById(R.id.btnsavell);
        viewreportll=findViewById(R.id.viewreportll);
        back=findViewById(R.id.back);




    }

    protected void checkPermissions() {
        final List<String> missingPermissions = new ArrayList<String>();
        // check all required dynamic permissions
        for (final String permission : REQUIRED_SDK_PERMISSIONS) {
            final int result = ContextCompat.checkSelfPermission(this, permission);
            if (result != PackageManager.PERMISSION_GRANTED) {
                missingPermissions.add(permission);
            }
        }
        if (!missingPermissions.isEmpty()) {
            // request all missing permissions
            final String[] permissions = missingPermissions
                    .toArray(new String[missingPermissions.size()]);
            ActivityCompat.requestPermissions(this, permissions, REQUEST_CODE_ASK_PERMISSIONS);
        } else {
            final int[] grantResults = new int[REQUIRED_SDK_PERMISSIONS.length];
            Arrays.fill(grantResults, PackageManager.PERMISSION_GRANTED);
            onRequestPermissionsResult(REQUEST_CODE_ASK_PERMISSIONS, REQUIRED_SDK_PERMISSIONS,
                    grantResults);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[],
                                           @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CODE_ASK_PERMISSIONS:
                for (int index = permissions.length - 1; index >= 0; --index) {
                    if (grantResults[index] != PackageManager.PERMISSION_GRANTED) {
                        // exit the app if one permission is not granted
                        Toast.makeText(this, "Required permission '" + permissions[index]
                                + "' not granted, exiting", Toast.LENGTH_LONG).show();
                        finish();
                        return;
                    }
                }
                // all permissions were granted
//                initialize();
                break;
        }
    }


    public void onResume(){
        super.onResume();
        if(state!=0){
            Log.d("rantest","onresume");
            btnsavell.setVisibility(View.VISIBLE);
            txttakeagain.setVisibility(View.VISIBLE);
            LeadOne.setVisibility(View.GONE);



        }


    }

    private void initOnClickListener() {

        LeadOne.setOnClickListener(v -> {
            state=state+1;
          initiateEcg.takeEcg(mContext, SECRET_ID, 1,this);


//            btnsavell.setVisibility(View.VISIBLE);
//            txttakeagain.setVisibility(View.VISIBLE);
//            LeadOne.setVisibility(View.GONE);



        });

        back.setOnClickListener(v -> {
            Intent i=new Intent(this,EcgOptionsActivity.class);
            startActivity(i);
        });

        txttakeagain.setOnClickListener(v -> {
         initiateEcg.takeEcg(mContext, SECRET_ID, 1,this);


        });


      btnsavereport.setOnClickListener(v -> {
          createPDF();
          btnsavereport.setVisibility(View.GONE);
          viewreportll.setVisibility(View.VISIBLE);

      });

//        btncreatepdf.setOnClickListener(v -> {
//
//
//        });

//        btncreatepdf.setOnClickListener(v ->
//                createPDF());

//        btnHistory.setOnClickListener(view -> {
//            Intent intent = new Intent(getApplicationContext(), HistoryActivity.class);
//            intent.putExtra("type", EcgTypes.NORMAL_ECG);
//            startActivity(intent);
//        });


        pDialog = new SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE);
        pDialog.getProgressHelper().setBarColor(ContextCompat.getColor(getApplicationContext(), R.color.colorAccent));
        pDialog.setTitleText("Registering");
        pDialog.setCancelable(false);
    }


//    public void getReadingForECG(int count) {
//
//
//        InitiateEcg initiateEcg = new InitiateEcg();
//        initiateEcg.takeEcg(mContext, SECRET_ID, count, new ResponseCallback() {
//            @Override
//            public void onSuccess(Success sucess) {
//
//                Log.e("Reading Success:", sucess.getSuccessMsg());
//                Toast.makeText(mContext, sucess.getSuccessMsg(), Toast.LENGTH_SHORT).show();
//
//
//
//            }
//
//            @Override
//            public void onError(Errors errors) {
//                Log.e("Reading failure:", errors.getErrorMsg());
//            }
//        });
//
//    }


    public void createPDF() {
        InitiateEcg initiateEcg = new InitiateEcg();
        initiateEcg.saveEcgData(mContext, "test", new SaveEcgCallBack() {
            @Override
            public void onSuccess(Success success, EcgConfig ecgConfig) {
                LabDB db = new LabDB(getApplicationContext());
                ecgReport.setPt_no(ptno);
                ecgReport.setHeartrate(ecgConfig.getHeartRate());
                ecgReport.setPr((ecgConfig.getpR()));
                ecgReport.setQt(ecgConfig.getqT());
                ecgReport.setQtc(ecgConfig.getqTc());
                ecgReport.setQrs(ecgConfig.getqRs());
                ecgReport.setSdnn(ecgConfig.getSdnn());
                ecgReport.setRmssd(ecgConfig.getRmssd());
                ecgReport.setMrr(ecgConfig.getmRR());
                ecgReport.setFinding(ecgConfig.getFinding());
                String last_ecgsign_row_id = db.SaveSingleleadECGSign(ecgReport);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                ecgReport.setRow_id(last_ecgsign_row_id);
                makePDF(ecgConfig);
                Toast.makeText(mContext, success.getSuccessMsg(), Toast.LENGTH_SHORT).show();







            }

            @Override
            public void onError(Errors error) {
                Toast.makeText(mContext, error.getErrorMsg(), Toast.LENGTH_SHORT).show();
            }
        });
    }



    public void makePDF(EcgConfig ecgConfig) {
        InitiateEcg initiateEcg = new InitiateEcg();
        initiateEcg.makeEcgReport(mContext, new UserDetails("Vikas", "24", "Male"), true, SECRET_ID, ecgConfig, new PdfCallback() {
            @Override
            public void onPdfAvailable(EcgConfig ecgConfig) {

                Log.e("makepdfpath", ecgConfig.getFileUrl());
                String filePath = ecgConfig.getFileUrl();
                 pdfuri = ecgConfig.getFileUrl();
                 binding.viewreportll.setVisibility(View.VISIBLE);
                 binding.btViewreport.setVisibility(View.VISIBLE);


            }

            @Override
            public void onError(Errors errors) {
                Toast.makeText(mContext, errors.getErrorMsg(), Toast.LENGTH_SHORT).show();
            }
        });
    }



     @Override
     public boolean onOptionsItemSelected(MenuItem item) {
         switch (item.getItemId()) {
             case android.R.id.home:
                 Intent intent = new Intent(SingleLeadECG.this, EcgOptionsActivity.class);
                 startActivity(intent);
                 finish();
                 break;
         }

         return super.onOptionsItemSelected(item);
     }

     @Override
     public void onBackPressed() {
         super.onBackPressed();
         Intent intent = new Intent(SingleLeadECG.this, EcgOptionsActivity.class);
         startActivity(intent);

     }

     @Override
     public void onError(Errors errors) {
         Toast.makeText(mContext, errors.getErrorMsg(), Toast.LENGTH_SHORT).show();
         Log.d("ktest","came in failure");
//         btnsavell.setVisibility(View.VISIBLE);
//         txttakeagain.setVisibility(View.VISIBLE);
//         LeadOne.setVisibility(View.GONE);

     }

     @Override
     public void onSuccess(Success success) {
       this.recreate();
         Toast.makeText(mContext, success.getSuccessMsg(), Toast.LENGTH_SHORT).show();
//         Log.d("ktest","came in success");
//         btnsavell.setVisibility(View.VISIBLE);
//         txttakeagain.setVisibility(View.VISIBLE);
//         LeadOne.setVisibility(View.GONE);

     }

     @Override
     public void onDestroy(){
        super.onDestroy();

         Log.d("ktest","Seek and Destroy");

     }





 }
