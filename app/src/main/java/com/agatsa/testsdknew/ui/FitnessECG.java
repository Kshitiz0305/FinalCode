package com.agatsa.testsdknew.ui;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.net.wifi.WifiManager;
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
import androidx.core.content.FileProvider;
import androidx.databinding.DataBindingUtil;

import com.afollestad.materialdialogs.MaterialDialog;
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
import com.agatsa.testsdknew.BuildConfig;
import com.agatsa.testsdknew.Models.ECGReport;
import com.agatsa.testsdknew.Models.PatientModel;
import com.agatsa.testsdknew.R;
import com.agatsa.testsdknew.customviews.DialogUtil;
import com.agatsa.testsdknew.databinding.ActivityFitnessEcgBinding;
import com.ontbee.legacyforks.cn.pedant.SweetAlert.SweetAlertDialog;

import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class FitnessECG extends AppCompatActivity implements ResponseCallback {
    private static final String CLIENT_ID = "5a3b4c16b4a56b000132f5d5b4580266565440bda51dcb4122d39844";
    private static final String SECRET_ID = "5a3b4c16b4a56b000132f5d5746be305d56c49e49cc88b12ccb05d71";
    private Button fitnessback,fitnessbtnSavereport;

    LinearLayout fitnesstxtLeadOne,fitnessbtnsavell,fitnesstxttakeagain,fitnessviewreportll;
    private Context mContext;
    String ptno = "";
    SharedPreferences pref;
    ECGReport ecgReport=new ECGReport();

    Button bt_view;

   public static int state=0;
    InitiateEcg initiateEcg;
    static  String pdfurl="";
    MaterialDialog progressDialog;

 ActivityFitnessEcgBinding binding;
 final CompositeDisposable compositeDisposable =  new CompositeDisposable();
 PatientModel patientModel;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_fitness_ecg);

        pref = this.getSharedPreferences("sunyahealth", Context.MODE_PRIVATE);
        ptno = pref.getString("PTNO", "");
        patientModel = getIntent().getParcelableExtra("patient");
        mContext = getApplicationContext();



        initiateEcg = new InitiateEcg();
        initViews();
        if(state==0)
        {   fitnesstxtLeadOne.setVisibility(View.VISIBLE);
            binding.btSave.setVisibility(View.GONE);
            binding.btView.setVisibility(View.GONE);
            binding.btnBack.setVisibility(View.GONE);

        }



//        if(state%3==1){
//            state=state+1;
//        }
//        else if(state%3==2){
//            fitnessbtnsavell.setVisibility(View.VISIBLE);
//            fitnesstxttakeagain.setVisibility(View.VISIBLE);
//            fitnesstxtLeadOne.setVisibility(View.GONE);
//            state=state+1;
//        }

//        if(state==0)
//        {  binding.btnsavell.setVisibility(View.GONE);
//            binding.txtLeadOne.setVisibility(View.VISIBLE);}
//
//        initViews();
//        if(state%3==1){
//            Log.d("rantest","equals1");
//            state=state+1;
//        }
//        else if(state%3==2){
//            Log.d("rantest","equals2");
//            btnsavell.setVisibility(View.VISIBLE);
//            txttakeagain.setVisibility(View.VISIBLE);
//            LeadOne.setVisibility(View.GONE);
//            state=state+1;
//        }






        initOnClickListener();
    }


    @Override
    public void onBackPressed() {

        DialogUtil.getOKCancelDialog(this, "Warning", "Do you want to complete Chest Six Lead Test?", "Yes", "No", (dialogInterface, i) -> FitnessECG.super.onBackPressed(), (dialogInterface, i) -> {


        });




    }

    private void initViews() {
        fitnessbtnSavereport = findViewById(R.id.bt_save);
        fitnesstxtLeadOne = findViewById(R.id.fitnesstxtLeadOne);
        fitnesstxttakeagain=findViewById(R.id.fitnesstxttakeagain);
        fitnessbtnsavell=findViewById(R.id.fitnessbtnsavell);
        fitnessviewreportll=findViewById(R.id.fitnessviewreportll);
        bt_view=findViewById(R.id.bt_view);





    }

    private void initOnClickListener() {


        fitnesstxtLeadOne.setOnClickListener(v -> {
            state=state+1;
            new Handler().postDelayed(() -> FitnessECG.this.getlongReadingForECG(1, 60), 2000);
        });

        fitnessbtnSavereport.setOnClickListener(v -> {

//                state=state+1;
            saveLongEcg();
        });

        bt_view.setOnClickListener(view -> {
            Log.d("rantest","viewing pdf");

            if(!pdfurl.equals(""))
            {
                state=0;
                Log.d("rantest","url available");
                File file = new File(pdfurl);
                Intent intent = new Intent(Intent.ACTION_VIEW);

                // set leadIndex to give temporary permission to external app to use your FileProvider
                intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);

                // generate URI, I defined authority as the application ID in the Manifest, the last param is file I want to open
                Uri photoURI = FileProvider.getUriForFile(FitnessECG.this,
                        BuildConfig.APPLICATION_ID + ".provider",
                        file);
                // I am opening a PDF file so I give it a valid MIME type
                intent.setDataAndType(photoURI, "application/pdf");

                // validate that the device can open your File!
                startActivity(intent);


            }
        });


        binding.btnBack.setOnClickListener(view -> FitnessECG.this.onBackPressed());



        fitnesstxttakeagain.setOnClickListener(v -> new Handler().postDelayed(() -> getlongReadingForECG(1, 60), 2000));
//        fitnesstxttakeagain.setOnClickListener(v -> {
//            initiateEcg.takeEcg(mContext, SECRET_ID, 1,this);
//
//
//        });






    }

    public void showProgress(String message){
        progressDialog = DialogUtil.showProgressDialog(this, "", message);

    }
    public void hideProgress(){
        if(progressDialog==null) return;
        if(progressDialog.isShowing()) progressDialog.dismiss();
    }

    private void setMobileDataEnabled(Context context, boolean enabled) {

        try{
            final ConnectivityManager conman = (ConnectivityManager)  context.getSystemService(Context.CONNECTIVITY_SERVICE);
            final Class conmanClass = Class.forName(conman.getClass().getName());
            final Field iConnectivityManagerField = conmanClass.getDeclaredField("mService");
            iConnectivityManagerField.setAccessible(true);
            final Object iConnectivityManager = iConnectivityManagerField.get(conman);
            final Class iConnectivityManagerClass = Class.forName(iConnectivityManager.getClass().getName());
            final Method setMobileDataEnabledMethod = iConnectivityManagerClass.getDeclaredMethod("setMobileDataEnabled", Boolean.TYPE);
            setMobileDataEnabledMethod.setAccessible(true);
            setMobileDataEnabledMethod.invoke(iConnectivityManager, enabled);

        }
        catch (Exception e){

            Log.d("rantest",e.getLocalizedMessage());
        }
        try{

            WifiManager wifiManager = (WifiManager)this.getApplicationContext().getSystemService(Context.WIFI_SERVICE);
            wifiManager.setWifiEnabled(enabled);

        }
        catch (Exception e ){
            Log.d("rantest",e.getLocalizedMessage());




        }
    }






    public void getlongReadingForECG(int leadCount, int time) {
        InitiateEcg initiateEcg = new InitiateEcg();
        initiateEcg.takeLongEcg(mContext, SECRET_ID, leadCount, time, new ResponseCallback() {
            @Override
            public void onSuccess(Success sucess) {
                Log.e("Reading Success:", sucess.getSuccessMsg());
                Toast.makeText(mContext, sucess.getSuccessMsg(), Toast.LENGTH_SHORT).show();
//                new Handler().postDelayed(() -> generateLongECGReportAndSave(), 5000);

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
            state++;
            binding.btnBack.setVisibility(View.GONE);
            fitnesstxttakeagain.setVisibility(View.VISIBLE);
            fitnesstxtLeadOne.setVisibility(View.GONE);
            binding.btView.setVisibility(View.GONE);
            binding.btSave.setVisibility(View.VISIBLE);



        }
//        if(state!=0){
//            Log.d("rantest","onresume");
//            btnsavell.setVisibility(View.VISIBLE);
//            txttakeagain.setVisibility(View.VISIBLE);
//            LeadOne.setVisibility(View.GONE);
//
//
//
//        }


    }

    private void generateLongECGReportAndSave() {

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

                        Log.e("path", longEcgConfig.getFileUrl());
                        String filePath = longEcgConfig.getFileUrl();

                        hideProgress();
                        setMobileDataEnabled(FitnessECG.this,true);

//                        Intent intent = new Intent(fitnessECG.this, PdfViewActivity.class);
//                        intent.putExtra("fileUrl", filePath);
//                        startActivity(intent);
                    }

                    @Override
                    public void onError(Errors errors) {
                        setMobileDataEnabled(FitnessECG.this,true);
                        hideProgress();
                        Log.e("Generate Report fn", "on Error of PDF " + errors.getErrorMsg());

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
//                pDialog.dismissWithAnimation();
                String errorMsg = errors.getErrorMsg();
                Toast.makeText(FitnessECG.this, errorMsg, Toast.LENGTH_SHORT).show();

            }
        });
    }

    public void saveLongEcg() {
        InitiateEcg initiateEcg = new InitiateEcg();
        initiateEcg.saveLongEcgData(mContext, "test", new SaveLongEcgCallBack() {
            @Override
            public void onSuccess(Success success, LongEcgConfig ecgConfig) {

                setMobileDataEnabled(FitnessECG.this,false);
                 showProgress("Generating Report");
                createLongPDF(ecgConfig);
            }

            @Override
            public void onError(Errors error) {
                setMobileDataEnabled(FitnessECG.this,true);
                Toast.makeText(mContext, error.getErrorMsg(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void createLongPDF(LongEcgConfig longEcgConfig) {

        InitiateEcg initiateEcg = new InitiateEcg();
        initiateEcg.makeLongEcgReport(mContext, new UserDetails(patientModel.getPtName(), patientModel.getPtAge(), patientModel.getPtSex()), true, SECRET_ID, longEcgConfig, new LongPdfCallBack() {
            @Override
            public void onPdfAvailable(LongEcgConfig ecgConfig) {
                Log.e("path", ecgConfig.getFileUrl());
                String filePath = ecgConfig.getFileUrl();
                pdfurl =filePath;
                Toast.makeText(mContext, "Pdf Generated", Toast.LENGTH_SHORT).show();

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
                ecgReport.setEcgType("LSL");
                ecgReport.setFilepath(ecgConfig.getFileUrl());
                compositeDisposable.add(db.updateEcgObserVable(ecgReport)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(ecgid -> {

                                    if (ecgid != null) {

                                        if (!ecgid.equals("")) {

                                            pref.edit().putInt("LSLF", 1 ).apply();

                                            DialogUtil.getOKDialog(FitnessECG.this, "", "Report Saved Successfully", "ok");

                                        } else {


                                            DialogUtil.getOKDialog(FitnessECG.this, "", "Error While saving", "ok");
                                        }


                                    } else {



                                        DialogUtil.getOKDialog(FitnessECG.this, "", "Error While saving", "ok");
                                    }
                                },
                                throwable -> {

                                    Log.e("rantest", "Unable to get username", throwable);


                                }));
                hideProgress();
                binding.btSave.setVisibility(View.GONE);
                binding.btnBack.setVisibility(View.VISIBLE);
                fitnesstxttakeagain.setVisibility(View.VISIBLE);
                fitnesstxtLeadOne.setVisibility(View.GONE);
                binding.btView.setVisibility(View.VISIBLE);
                setMobileDataEnabled(FitnessECG.this,true);


            }

            @Override
            public void onError(Errors errors) {
                setMobileDataEnabled(FitnessECG.this,true);
                hideProgress();
                Log.e("Create PDF Error", errors.getErrorMsg());
                binding.btSave.setVisibility(View.VISIBLE);
                binding.btnBack.setVisibility(View.GONE);
                fitnesstxttakeagain.setVisibility(View.VISIBLE);
                fitnesstxtLeadOne.setVisibility(View.GONE);
                binding.btView.setVisibility(View.GONE);
                Toast.makeText(mContext, errors.getErrorMsg(), Toast.LENGTH_SHORT).show();
            }
        });
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
    public void onError(Errors errors) {

    }

    @Override
    public void onSuccess(Success success) {
        Toast.makeText(mContext, success.getSuccessMsg(), Toast.LENGTH_SHORT).show();


    }
}
