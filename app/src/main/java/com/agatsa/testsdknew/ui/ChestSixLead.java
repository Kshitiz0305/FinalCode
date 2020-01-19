package com.agatsa.testsdknew.ui;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.databinding.DataBindingUtil;

import com.afollestad.materialdialogs.MaterialDialog;
import com.agatsa.sanketlife.callbacks.PdfCallback;
import com.agatsa.sanketlife.callbacks.ResponseCallback;
import com.agatsa.sanketlife.callbacks.SaveEcgCallBack;
import com.agatsa.sanketlife.development.EcgConfig;
import com.agatsa.sanketlife.development.Errors;
import com.agatsa.sanketlife.development.InitiateEcg;
import com.agatsa.sanketlife.development.Success;
import com.agatsa.sanketlife.development.UserDetails;
import com.agatsa.sanketlife.models.EcgTypes;
import com.agatsa.testsdknew.BuildConfig;
import com.agatsa.testsdknew.Models.ECGReport;
import com.agatsa.testsdknew.Models.PatientModel;
import com.agatsa.testsdknew.R;
import com.agatsa.testsdknew.customviews.DialogUtil;
import com.agatsa.testsdknew.databinding.ActivityChestLeadBinding;
import com.agatsa.testsdknew.ui.adapters.PatientDetailAdapter;
import com.ontbee.legacyforks.cn.pedant.SweetAlert.SweetAlertDialog;

import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;

import io.reactivex.android.schedulers.AndroidSchedulers;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import pl.droidsonroids.gif.GifImageView;

public class ChestSixLead extends AppCompatActivity {
    private static final String CLIENT_ID = "5a3b4c16b4a56b000132f5d5b4580266565440bda51dcb4122d39844";
    private static final String SECRET_ID = "5a3b4c16b4a56b000132f5d5746be305d56c49e49cc88b12ccb05d71";
    MaterialDialog progressDialog;
    private final CompositeDisposable mDisposable = new CompositeDisposable();
    TextView description;
    LinearLayout txtvone, txtvtwo,txtvthree,txtvfour,txtvfive,txtvsix;
    LinearLayout txtvoneagain, txtvtwoagain,txtvthreeagain,txtvfouragain,txtvfiveagain,txtvsixagain,sixleadagain;
    private Context mContext;
    String ptno = "";
    SharedPreferences pref;
    ECGReport ecgReport=new ECGReport();
    SweetAlertDialog pDialog;
    public   static  int leadIndex = 0,x=0;
    public   static  boolean again =false;
    GifImageView gifImageView;
PatientModel patientModel;
    static String pdfUrl="";
    ArrayList<String>  buttoncollectionshide =  new ArrayList<String>(Arrays.asList("txtvone",
            "txtvoneagain",
            "txtvtwo",
            "txtvtwoagain",
            "txtvthree",
            "txtvthreeagain",
            "txtvfour",
            "txtvfouragain",
            "txtvfive",
            "txtvfiveagain",
            "txtvsix",
            "txtvsixagain",
            "sixleadagain",
            "btnSavelimbreport",
            "btnviewreport",
            "ll_complete"));



    ActivityChestLeadBinding binding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this,R.layout.activity_chest_lead);

        pref = this.getSharedPreferences("sunyahealth", Context.MODE_PRIVATE);
        ptno = pref.getString("PTNO","");
//        labdb = new LabDB(getApplicationContext());
//        ecgReport=labdb.getLastEcgSign(ptno);
        mContext = getApplicationContext();
        patientModel = getIntent().getParcelableExtra("patient");
        hideAndSeek(buttoncollectionshide,true);
        ArrayList<String> buttoncollectionsshowstart=new ArrayList<String>(Arrays.asList("txtvone"));
        hideAndSeek(buttoncollectionsshowstart,false);
//         hideshow is to be deleted

        binding.btRprt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("rantest","viewing pdf");

                if(!pdfUrl.equals(""))
                {
                    Log.d("rantest","url available");
                    File file = new File(pdfUrl);
                    Intent intent = new Intent(Intent.ACTION_VIEW);

                    // set leadIndex to give temporary permission to external app to use your FileProvider
                    intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);

                    // generate URI, I defined authority as the application ID in the Manifest, the last param is file I want to open
                    Uri photoURI = FileProvider.getUriForFile(ChestSixLead.this,
                            BuildConfig.APPLICATION_ID + ".provider",
                            file);
                    // I am opening a PDF file so I give it a valid MIME type
                    intent.setDataAndType(photoURI, "application/pdf");

                    // validate that the device can open your File!
                    startActivity(intent);




                }




            }
        });
        binding.btsavePdfReport.setOnClickListener(v ->{


            Log.d("rantest","Clicked on save");
            hideAndSeek(buttoncollectionshide,true);

// to be changed
            ArrayList<String> buttoncollectionsshow21 = new ArrayList<String>(Arrays.asList("sixleadagain","ll_complete"));
            hideAndSeek(buttoncollectionsshow21, false);

            createPDF();
        });
        binding.sixleadagain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Log.d("rantest","Clicked on six again");
                showDynamicimage("gif_lead1");
                showDynamicDescription("ecginfo");
                hideAndSeek(buttoncollectionshide,true);
                ArrayList<String> buttoncollectionsshow0=new ArrayList<String>(Arrays.asList("txtvone"));
                hideAndSeek(buttoncollectionsshow0,false);
            }
        });


        initViews();
        initOnClickListener();
    }
    public void hideAndSeek(ArrayList<String> idsarray,boolean ishide){
        try{
            if(ishide){
                for(String selectedString:idsarray){

                    LinearLayout linearLayout = findViewById(getResourceId(selectedString,"id",getPackageName()));
                    linearLayout.setVisibility(View.GONE);

                }





            }
            else {

                for(String selectedString:idsarray){

                    LinearLayout linearLayout = findViewById(getResourceId(selectedString,"id",getPackageName()));
                    linearLayout.setVisibility(View.VISIBLE);

                }


            }

        }
        catch (Exception e){
            Log.d("rantest",e.getLocalizedMessage());



        }


    }


    private void initViews() {

        txtvone = findViewById(R.id.txtvone);
        txtvtwo = findViewById(R.id.txtvtwo);
        txtvthree = findViewById(R.id.txtvthree);
        txtvfour = findViewById(R.id.txtvfour);
        txtvfive = findViewById(R.id.txtvfive);
        txtvsix = findViewById(R.id.txtvsix);
        description = findViewById(R.id.tv_description);
//        btnSavechestreport = findViewById(R.id.btnSavechestreport);
//        btnchestHistory = findViewById(R.id.btnchestHistory);
        txtvoneagain=findViewById(R.id.txtvoneagain);
        txtvtwoagain=findViewById(R.id.txtvtwoagain);
        txtvthreeagain=findViewById(R.id.txtvthreeagain);
        txtvfouragain=findViewById(R.id.txtvfouragain);
        txtvfiveagain=findViewById(R.id.txtvfiveagain);
        txtvsixagain=findViewById(R.id.txtvsixagain);
        sixleadagain=findViewById(R.id.sixleadagain);





    }


    private void initOnClickListener() {
        binding.btComplete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ChestSixLead.this.onBackPressed();

            }
        });

       txtvone.setOnClickListener(v -> {
           leadIndex=1;
           again=false;

           getReadingForECG(2);});

       txtvoneagain.setOnClickListener(v -> {
           leadIndex=1;
           again=true;

           getReadingForECG(2);

       });

        txtvtwo.setOnClickListener(v ->{

            leadIndex=2;
            again=false;
            getReadingForECG(3);});

        txtvtwoagain.setOnClickListener(v ->{

            leadIndex=2;
            again=true;
            getReadingForECG(3);});

        txtvthree.setOnClickListener(v ->{

            leadIndex=3;
            again=false;
            getReadingForECG(4);});
        txtvthreeagain.setOnClickListener(v ->{

            leadIndex=3;
            again=true;
            getReadingForECG(4);});

        txtvfour.setOnClickListener(v ->{

            leadIndex=4;
            again=false;
            getReadingForECG(5);});
        txtvfouragain.setOnClickListener(v ->{

            leadIndex=4;
            again=true;
            getReadingForECG(5);});
        txtvfive.setOnClickListener(v ->{

            leadIndex=5;
            again=false;
            getReadingForECG(6);});

        txtvfiveagain.setOnClickListener(v ->{

            leadIndex=5;
            again=true;
            getReadingForECG(6);});
        txtvsix.setOnClickListener(v ->{

            leadIndex=6;
            again=false;
            getReadingForECG(7);});
        txtvsixagain.setOnClickListener(v ->{

            leadIndex=6;
            again=true;
            getReadingForECG(7);});


//        txtvtwoagain.setOnClickListener(v -> getReadingForECG(3));
//
//        txtvthree.setOnClickListener(v -> getReadingForECG(4));
//
//        txtvthreeagain.setOnClickListener(v -> getReadingForECG(4));
//
//        txtvfour.setOnClickListener(v -> getReadingForECG(5));
//
//        txtvfouragain.setOnClickListener(v -> getReadingForECG(5));
//
//        txtvfive.setOnClickListener(v -> getReadingForECG(6));
//
//        txtvfiveagain.setOnClickListener(v -> getReadingForECG(6));
//
//        txtvsix.setOnClickListener(v -> getReadingForECG(7));
//        txtvsixagain.setOnClickListener(v -> getReadingForECG(7));


//         this is to be seen null

//
//        btnchestHistory.setOnClickListener(view -> {
//            Intent intent = new Intent(getApplicationContext(), HistoryActivity.class);
//            intent.putExtra("type", EcgTypes.NORMAL_ECG);
//            startActivity(intent);
//        });


        pDialog = new SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE);
        pDialog.getProgressHelper().setBarColor(ContextCompat.getColor(getApplicationContext(), R.color.colorAccent));
        pDialog.setTitleText("Registering");
        pDialog.setCancelable(false);
    }

//

    public void getReadingForECG(int count) {

        InitiateEcg initiateEcg = new InitiateEcg();
        initiateEcg.takeEcg(mContext, SECRET_ID, count, new ResponseCallback() {
            @Override
            public void onSuccess(Success sucess) {
                Log.e("Reading Success:", sucess.getSuccessMsg());
                Toast.makeText(mContext, sucess.getSuccessMsg(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(Errors errors) {
                Log.e("Reading failure:", errors.getErrorMsg());
            }
        });

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

    public void showProgress(String message){
        progressDialog = DialogUtil.showProgressDialog(this, "", message);

    }
    public void hideProgress(){
        if(progressDialog==null) return;
        if(progressDialog.isShowing()) progressDialog.dismiss();
    }


    public void createPDF() {
        showProgress("Generating pdf");
        InitiateEcg initiateEcg = new InitiateEcg();
        initiateEcg.saveEcgData(mContext, "test", new SaveEcgCallBack() {
            @Override
            public void onSuccess(Success success, EcgConfig ecgConfig) {
                Toast.makeText(mContext,success.getSuccessMsg(), Toast.LENGTH_SHORT).show();
                setMobileDataEnabled(ChestSixLead.this,false);
                makePDF(ecgConfig);


        }
            @Override
            public void onError(Errors error) {
                setMobileDataEnabled(ChestSixLead.this,false);
                Toast.makeText(mContext, error.getErrorMsg(), Toast.LENGTH_SHORT).show();
            }
        });
    }




    @Override
    public void onBackPressed() {

        DialogUtil.getOKCancelDialog(this, "Warning", "Do you want to complete Chest Six Lead Test?", "Yes", "No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                ChestSixLead.super.onBackPressed();




            }
        }, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {


            }
        });




    }

    public void makePDF(EcgConfig ecgConfig) {
        InitiateEcg initiateEcg = new InitiateEcg();
        if(patientModel==null)
            DialogUtil.getOKDialog(this,"Error","Error while loading","OK");
        else {
        initiateEcg.makeEcgReport(mContext, new UserDetails(patientModel.getPtName(), patientModel.getPtAge(), patientModel.getPtSex()), true, SECRET_ID, ecgConfig, new PdfCallback() {
            @Override
            public void onPdfAvailable(EcgConfig ecgConfig) {
                Log.d("rantest", ecgConfig.getFileUrl());
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
                ecgReport.setEcgType("CSL");

                ecgReport.setFilepath(ecgConfig.getFileUrl());
                mDisposable.add(db.updateEcgObserVable(ecgReport)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(ecgid -> {

                                    if (ecgid != null) {

                                        if (!ecgid.equals("")) {

                                            pref.edit().putInt("CSLF", 1 ).apply();

                                            DialogUtil.getOKDialog(ChestSixLead.this, "", "Report Saved Successfully", "ok");

                                        } else {


                                            DialogUtil.getOKDialog(ChestSixLead.this, "", "Error While saving", "ok");
                                        }


                                    } else {



                                        DialogUtil.getOKDialog(ChestSixLead.this, "", "Error While saving", "ok");
                                    }
                                },
                                throwable -> Log.e("rantest", "Unable to get username", throwable)));


                pdfUrl = ecgConfig.getFileUrl();
                Toast.makeText(mContext, "Pdf Generated Successfully", Toast.LENGTH_SHORT).show();
                setMobileDataEnabled(ChestSixLead.this,true);
                hideProgress();



            }

            @Override
            public void onError(Errors errors) {
                hideProgress();
                setMobileDataEnabled(ChestSixLead.this,true);
                Toast.makeText(mContext, errors.getErrorMsg(), Toast.LENGTH_SHORT).show();
            }
        });}
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


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Intent intent = new Intent(ChestSixLead.this, EcgOptionsActivity.class);
                startActivity(intent);
                finish();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

//    @Override
//    public void onBackPressed() {
//        super.onBackPressed();
//        Intent intent = new Intent(ChestSixLead.this, EcgOptionsActivity.class);
//        startActivity(intent);
//
//    }

    @Override
    protected void onResume() {

        buttoncollectionshide =  new ArrayList<String>(Arrays.asList("txtvone",
                "txtvoneagain",
                "txtvtwo",
                "txtvtwoagain",
                "txtvthree",
                "txtvthreeagain",
                "txtvfour",
                "txtvfouragain",
                "txtvfive",
                "txtvfiveagain",
                "txtvsix",
                "txtvsixagain",
                "sixleadagain",
                "btnSavelimbreport",
                "btnviewreport",
                "ll_complete"));
//
        if(leadIndex ==0&&again){
//
            again =false;
            showDynamicimage("gif_lead1");
            showDynamicDescription("ecginfo");
            hideAndSeek(buttoncollectionshide,true);
            ArrayList<String> buttoncollectionsshow0=new ArrayList<String>(Arrays.asList("txtvone"));
            hideAndSeek(buttoncollectionsshow0,false);
        }
        else if(leadIndex ==1)
        {

            x++;
            if(!again&&x==2){
                leadIndex =0;
                x=0;
                showDynamicimage("gif_lead2");
                showDynamicDescription("ecginfo");
                hideAndSeek(buttoncollectionshide,true);
                ArrayList<String> buttoncollectionsshow11=new ArrayList<String>(Arrays.asList("txtvoneagain","txtvtwo"));
                hideAndSeek(buttoncollectionsshow11,false);

            }

            else if(again&&x==2) {
                leadIndex =0;
                x=0;
                again=false;
                showDynamicimage("gif_lead2");
                showDynamicDescription("ecginfo");
                hideAndSeek(buttoncollectionshide,true);
                ArrayList<String> buttoncollectionsshow11=new ArrayList<String>(Arrays.asList("txtvoneagain","txtvtwo"));
                hideAndSeek(buttoncollectionsshow11,false);

            }



        }
        else if(leadIndex ==2){
            x++;
            if(!again&&x==2){
//                 this is as lead one pressed and performed test for the fir5st time
                leadIndex =0;
                x=0;
                showDynamicimage("gif_lead2");
                showDynamicDescription("ecginfo");
                hideAndSeek(buttoncollectionshide,true);
                ArrayList<String> buttoncollectionsshow21=new ArrayList<String>(Arrays.asList("txtvtwoagain","txtvthree"));
                hideAndSeek(buttoncollectionsshow21,false);

            }

            else if(again&&x==2) {
                leadIndex =0;
                x=0;
                again=false;
                showDynamicimage("gif_lead2");
                showDynamicDescription("ecginfo");
                hideAndSeek(buttoncollectionshide,true);
                ArrayList<String> buttoncollectionsshow22=new ArrayList<String>(Arrays.asList("txtvtwoagain","txtvthree"));
                hideAndSeek(buttoncollectionsshow22,false);

            }





        }

        else if(leadIndex ==3) {
            x++;
            if (!again && x == 2) {
//                 this is as lead one pressed and performed test for the fir5st time
                leadIndex = 0;
                x = 0;
                showDynamicimage("gif_lead2");
                showDynamicDescription("ecginfo");
                hideAndSeek(buttoncollectionshide, true);
                ArrayList<String> buttoncollectionsshow21 = new ArrayList<String>(Arrays.asList("txtvthreeagain", "txtvfour"));
                hideAndSeek(buttoncollectionsshow21, false);

            } else if (again && x == 2) {
                leadIndex = 0;
                x = 0;
                again = false;
                showDynamicimage("gif_lead2");
                showDynamicDescription("ecginfo");
                hideAndSeek(buttoncollectionshide, true);
                ArrayList<String> buttoncollectionsshow22 = new ArrayList<String>(Arrays.asList("txtvthreeagain", "txtvfour"));
                hideAndSeek(buttoncollectionsshow22, false);

            }
        }
        else if(leadIndex ==4) {
            x++;
            if (!again && x == 2) {
//                 this is as lead one pressed and performed test for the fir5st time
                leadIndex = 0;
                x = 0;
                showDynamicimage("gif_lead2");
                showDynamicDescription("ecginfo");
                hideAndSeek(buttoncollectionshide, true);
                ArrayList<String> buttoncollectionsshow21 = new ArrayList<String>(Arrays.asList("txtvfouragain", "txtvfive"));
                hideAndSeek(buttoncollectionsshow21, false);

            } else if (again && x == 2) {
                leadIndex = 0;
                x = 0;
                again = false;
                showDynamicimage("gif_lead2");
                showDynamicDescription("ecginfo");
                hideAndSeek(buttoncollectionshide, true);
                ArrayList<String> buttoncollectionsshow22 = new ArrayList<String>(Arrays.asList("txtvfouragain", "txtvfive"));
                hideAndSeek(buttoncollectionsshow22, false);

            }
        }


        else if(leadIndex ==5) {
            x++;
            if (!again && x == 2) {
//                 this is as lead one pressed and performed test for the fir5st time
                leadIndex = 0;
                x = 0;
                showDynamicimage("gif_lead2");
                showDynamicDescription("ecginfo");
                hideAndSeek(buttoncollectionshide, true);
                ArrayList<String> buttoncollectionsshow21 = new ArrayList<String>(Arrays.asList("txtvfiveagain", "txtvsix"));
                hideAndSeek(buttoncollectionsshow21, false);

            } else if (again && x == 2) {
                leadIndex = 0;
                x = 0;
                again = false;
                showDynamicimage("gif_lead2");
                showDynamicDescription("ecginfo");
                hideAndSeek(buttoncollectionshide, true);
                ArrayList<String> buttoncollectionsshow22 = new ArrayList<String>(Arrays.asList("txtvfiveagain", "txtvsix"));
                hideAndSeek(buttoncollectionsshow22, false);

            }
        }

//        "sixleadagain",
//                "btnSavelimbreport",
//                "btnviewreport",
//                "ll_complete"));

        else if(leadIndex ==6) {
            x++;
            if (!again && x == 2) {
//                 this is as lead one pressed and performed test for the fir5st time
                leadIndex = 0;
                x = 0;
                showDynamicimage("gif_lead2");
                showDynamicDescription("ecginfo");
                hideAndSeek(buttoncollectionshide, true);
                ArrayList<String> buttoncollectionsshow21 = new ArrayList<String>(Arrays.asList("txtvsixagain","btnSavelimbreport"));
                hideAndSeek(buttoncollectionsshow21, false);

            } else if (again && x == 2) {
                leadIndex = 0;
                x = 0;
                again = false;
                showDynamicimage("gif_lead2");
                showDynamicDescription("ecginfo");
                hideAndSeek(buttoncollectionshide, true);
                ArrayList<String> buttoncollectionsshow22 = new ArrayList<String>(Arrays.asList("txtvsixagain","btnSavelimbreport"));
                hideAndSeek(buttoncollectionsshow22, false);

            }
        }


        else if(leadIndex ==7) {
            x++;
            if (!again && x == 2) {
//                 this is as lead one pressed and performed test for the fir5st time
                leadIndex = 0;
                x = 0;
                showDynamicimage("gif_lead2");
                showDynamicDescription("ecginfo");
                hideAndSeek(buttoncollectionshide, true);
                ArrayList<String> buttoncollectionsshow21 = new ArrayList<String>(Arrays.asList("sixleadagain", "btnviewreport","ll_complete"));
                hideAndSeek(buttoncollectionsshow21, false);

            } else if (again && x == 2) {
                leadIndex = 0;
                x = 0;
                again = false;
                showDynamicimage("gif_lead2");
                showDynamicDescription("ecginfo");
                hideAndSeek(buttoncollectionshide, true);
                ArrayList<String> buttoncollectionsshow22 = new ArrayList<String>(Arrays.asList("sixleadagain", "btnviewreport","ll_complete"));
                hideAndSeek(buttoncollectionsshow22, false);

            }
        }

//        binding.btRprt.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                if(!pdfUrl.equals(""))
//                {
//                    File file = new File(pdfUrl);
//                    Intent intent = new Intent(Intent.ACTION_VIEW);
//
//                    // set leadIndex to give temporary permission to external app to use your FileProvider
//                    intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
//
//                    // generate URI, I defined authority as the application ID in the Manifest, the last param is file I want to open
//                    Uri photoURI = FileProvider.getUriForFile(ChestSixLead.this,
//                            BuildConfig.APPLICATION_ID + ".provider",
//                            file);
//                    // I am opening a PDF file so I give it a valid MIME type
//                    intent.setDataAndType(photoURI, "application/pdf");
//
//                    // validate that the device can open your File!
//                    startActivity(intent);
//
//
//
//
//                }
//
//
//
//
//            }
//        });
        binding.btsavePdfReport.setOnClickListener(v ->{


            Log.d("rantest","Clicked on save");
            hideAndSeek(buttoncollectionshide,true);

// to be changed
            ArrayList<String> buttoncollectionsshow21 = new ArrayList<String>(Arrays.asList("sixleadagain", "btnviewreport","ll_complete"));
            hideAndSeek(buttoncollectionsshow21, false);

            createPDF();
        });

        super.onResume();
    }

    public void showDynamicDescription(String ecginfo){
        description.setText(getResourceId(ecginfo,"string",getPackageName()));
    }

    public void showDynamicimage(String imagesrcs){
        try{

            binding.gifHolder.setImageResource((getResourceId(imagesrcs,"drawable",getPackageName())));}
        catch (Exception e){


            Log.d("rantest",e.getLocalizedMessage());
        }

    }

}
