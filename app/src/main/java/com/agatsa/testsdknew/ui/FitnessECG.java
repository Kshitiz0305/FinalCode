package com.agatsa.testsdknew.ui;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;

import com.agatsa.sanketlife.callbacks.LongPdfCallBack;
import com.agatsa.sanketlife.callbacks.PdfCallback;
import com.agatsa.sanketlife.callbacks.ResponseCallback;
import com.agatsa.sanketlife.callbacks.SaveEcgCallBack;
import com.agatsa.sanketlife.callbacks.SaveLongEcgCallBack;
import com.agatsa.sanketlife.development.EcgConfig;
import com.agatsa.sanketlife.development.Errors;
import com.agatsa.sanketlife.development.InitiateEcg;
import com.agatsa.sanketlife.development.LongEcgConfig;
import com.agatsa.sanketlife.development.Success;
import com.agatsa.sanketlife.development.UserDetails;
import com.agatsa.sanketlife.models.EcgTypes;
import com.agatsa.testsdknew.Models.ECGReport;
import com.agatsa.testsdknew.R;
import com.ontbee.legacyforks.cn.pedant.SweetAlert.SweetAlertDialog;

public class FitnessECG extends AppCompatActivity implements ResponseCallback {
    private static final String CLIENT_ID = "5a3b4c16b4a56b000132f5d5b4580266565440bda51dcb4122d39844";
    private static final String SECRET_ID = "5a3b4c16b4a56b000132f5d5746be305d56c49e49cc88b12ccb05d71";
    private Button fitnessback,fitnessbtnSavereport;

    LinearLayout fitnesstxtLeadOne,fitnessbtnsavell,fitnesstxttakeagain,fitnessviewreportll;
    private Context mContext;
    int ptno = 0;
    SharedPreferences pref;
    ECGReport ecgReport=new ECGReport();
    SweetAlertDialog pDialog;
    Toolbar toolbar;
    static int state=0;
    InitiateEcg initiateEcg;;






    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fitness_ecg);
        pref = this.getSharedPreferences("sunyahealth", Context.MODE_PRIVATE);
        ptno = pref.getInt("pt_id", 0);
//        labdb = new LabDB(getApplicationContext());
//        ecgReport=labdb.getLastEcgSign(ptno);
        mContext = getApplicationContext();
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Fitness Test");
        initiateEcg = new InitiateEcg();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        initViews();
        if(state%3==1){
            state=state+1;
        }
        else if(state%3==2){
            fitnessbtnsavell.setVisibility(View.VISIBLE);
            fitnesstxttakeagain.setVisibility(View.VISIBLE);
            fitnesstxtLeadOne.setVisibility(View.GONE);
            state=state+1;
        }

        initOnClickListener();
    }


    private void initViews() {
        fitnessbtnSavereport = findViewById(R.id.fitnessbtnSavereport);
        fitnesstxtLeadOne = findViewById(R.id.fitnesstxtLeadOne);
        fitnesstxttakeagain=findViewById(R.id.fitnesstxttakeagain);
        fitnessbtnsavell=findViewById(R.id.fitnessbtnsavell);
        fitnessviewreportll=findViewById(R.id.fitnessviewreportll);
        fitnessback=findViewById(R.id.fitnessback);



    }

    private void initOnClickListener() {


        fitnesstxtLeadOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                state=state+1;
                new Handler().postDelayed(() -> FitnessECG.this.getlongReadingForECG(1, 60), 2000);
            }
        });

        fitnessbtnSavereport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                state=state+1;
                saveLongEcg();
            }
        });

        fitnessback.setOnClickListener(v -> {
            Intent i=new Intent(this,EcgOptionsActivity.class);
            startActivity(i);
        });

        fitnesstxttakeagain.setOnClickListener(v -> new Handler().postDelayed(() -> getlongReadingForECG(1, 60), 2000));
//        fitnesstxttakeagain.setOnClickListener(v -> {
//            initiateEcg.takeEcg(mContext, SECRET_ID, 1,this);
//
//
//        });





        pDialog = new SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE);
        pDialog.getProgressHelper().setBarColor(ContextCompat.getColor(getApplicationContext(), R.color.colorAccent));
        pDialog.setTitleText("Registering");
        pDialog.setCancelable(false);
    }



    public void getlongReadingForECG(int leadCount, int time) {
        InitiateEcg initiateEcg = new InitiateEcg();
        initiateEcg.takeLongEcg(mContext, SECRET_ID, leadCount, time, new ResponseCallback() {
            @Override
            public void onSuccess(Success sucess) {
                Log.e("Reading Success:", sucess.getSuccessMsg());
                Toast.makeText(mContext, sucess.getSuccessMsg(), Toast.LENGTH_SHORT).show();
                new Handler().postDelayed(() -> generateLongECGReportAndSave(), 5000);

            }

            @Override
            public void onError(Errors errors) {
                Log.e("Reading failure:", errors.getErrorMsg());
                Toast.makeText(mContext, errors.getErrorMsg(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    public void onResume(){
        super.onResume();
        if(state!=0){
            fitnessbtnsavell.setVisibility(View.VISIBLE);
            fitnesstxttakeagain.setVisibility(View.VISIBLE);
            fitnesstxtLeadOne.setVisibility(View.GONE);



        }


    }

    private void generateLongECGReportAndSave() {
        pDialog.setTitleText("Generating Report");
        pDialog.show();
        final InitiateEcg initiateEcg = new InitiateEcg();
        initiateEcg.saveLongEcgData(FitnessECG.this, "test", new SaveLongEcgCallBack() {
            @Override
            public void onSuccess(Success sucess, LongEcgConfig longEcgConfig) {
                Log.e("Generate Report fn", "onSuccess: " + "saved data");
                UserDetails userDetails = new UserDetails("Vikas", "24", "Male");

                initiateEcg.makeLongEcgReport(FitnessECG.this, userDetails, false, SECRET_ID, longEcgConfig, new LongPdfCallBack() {
                    @Override
                    public void onPdfAvailable(LongEcgConfig longEcgConfig) {
                        Log.e("Generate Report fn", "on PDF Availanble ");
                        pDialog.dismiss();
                        Log.e("path", longEcgConfig.getFileUrl());
                        String filePath = longEcgConfig.getFileUrl();
                        Intent intent = new Intent(FitnessECG.this, PdfViewActivity.class);
                        intent.putExtra("fileUrl", filePath);
                        startActivity(intent);
                    }

                    @Override
                    public void onError(Errors errors) {
                        Log.e("Generate Report fn", "on Error of PDF " + errors.getErrorMsg());
                        pDialog.dismiss();
                        String errorMsg = errors.getErrorMsg();
                        if (errorMsg.contains("Quota exceeded")) {
                            Toast.makeText(FitnessECG.this, errorMsg, Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(FitnessECG.this, errorMsg, Toast.LENGTH_SHORT).show();

                        }
                    }
                });
            }

            @Override
            public void onError(Errors errors) {
                Log.e("Generate Report fn", "on Error " + errors.getErrorMsg());

//                testComplete();
                pDialog.dismissWithAnimation();
                String errorMsg = errors.getErrorMsg();
                Toast.makeText(FitnessECG.this, errorMsg, Toast.LENGTH_SHORT).show();

            }
        });
    }

    public void saveLongEcg() {
        InitiateEcg initiateEcg = new InitiateEcg();
        initiateEcg.saveLongEcgData(mContext, "test", new SaveLongEcgCallBack() {
            @Override
            public void onSuccess(Success success, LongEcgConfig longEcgConfig) {
//                heartratevalue=String.valueOf(longEcgConfig.getHeartRate());
//                LabDB db = new LabDB(getApplicationContext());
//                longECGReport.setPt_no(ptno);
//                longECGReport.setHeartrate(longEcgConfig.getHeartRate());
//                longECGReport.setPr((longEcgConfig.getpR()));
//                longECGReport.setQt(longEcgConfig.getqT());
//                longECGReport.setQtc(longEcgConfig.getqTc());
//                longECGReport.setQrs(longEcgConfig.getqRs());
//                longECGReport.setSdnn(longEcgConfig.getSdnn());
//                longECGReport.setRmssd(longEcgConfig.getRmssd());
//                longECGReport.setMrr(longEcgConfig.getmRR());
//                longECGReport.setFinding(longEcgConfig.getFinding());
//                int last_long_ecgsign_row_id = db.SavelongleadECGSign(longECGReport);
//                try {
//                    Thread.sleep(1000);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//                ecgReport.setRow_id(last_long_ecgsign_row_id);
                Toast.makeText(mContext, success.getSuccessMsg(), Toast.LENGTH_SHORT).show();
                createLongPDF(longEcgConfig);
            }

            @Override
            public void onError(Errors error) {
                Toast.makeText(mContext, error.getErrorMsg(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void createLongPDF(LongEcgConfig longEcgConfig) {
        InitiateEcg initiateEcg = new InitiateEcg();
        initiateEcg.makeLongEcgReport(mContext, new UserDetails("Vikas", "24", "Male"), true, SECRET_ID, longEcgConfig, new LongPdfCallBack() {
            @Override
            public void onPdfAvailable(LongEcgConfig longEcgConfig) {
                Log.e("path", longEcgConfig.getFileUrl());
                String filePath = longEcgConfig.getFileUrl();
                Intent intent = new Intent(FitnessECG.this, PdfViewActivity.class);
                intent.putExtra("fileUrl", filePath);
                startActivity(intent);
            }

            @Override
            public void onError(Errors errors) {
                Log.e("Create PDF Error", errors.getErrorMsg());
                Toast.makeText(mContext, errors.getErrorMsg(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void hideSoftKeyboard() {
        View view = this.getCurrentFocus();

        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            assert imm != null;
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }





    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Intent intent = new Intent(FitnessECG.this, EcgOptionsActivity.class);
                startActivity(intent);
                finish();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(FitnessECG.this, EcgOptionsActivity.class);
        startActivity(intent);

    }


    @Override
    public void onError(Errors errors) {

    }

    @Override
    public void onSuccess(Success success) {
        Toast.makeText(mContext, success.getSuccessMsg(), Toast.LENGTH_SHORT).show();
//         Log.d("ktest","came in success");
        fitnessbtnSavereport.setVisibility(View.VISIBLE);
        fitnesstxttakeagain.setVisibility(View.VISIBLE);
        fitnesstxtLeadOne.setVisibility(View.GONE);

    }
}
