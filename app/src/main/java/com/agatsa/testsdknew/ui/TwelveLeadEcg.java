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
import com.agatsa.testsdknew.LabInstanceDB;
import com.agatsa.testsdknew.Models.ECGReport;
import com.agatsa.testsdknew.Models.PatientModel;
import com.agatsa.testsdknew.R;
import com.agatsa.testsdknew.customviews.DialogUtil;
import com.agatsa.testsdknew.databinding.ActivityTwelveLeadBinding;
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

public class TwelveLeadEcg extends AppCompatActivity {

    private static final String CLIENT_ID = "5a3b4c16b4a56b000132f5d5b4580266565440bda51dcb4122d39844";
    private static final String SECRET_ID = "5a3b4c16b4a56b000132f5d5746be305d56c49e49cc88b12ccb05d71";
    private Button btnSaveTwelveLeadRecord;

    LinearLayout txttwelveleadone, txttwelveleadtwo,txttwelvevone,txttwelvevtwo,txttwelvevthree,txttwelvevfour,txttwelvevfive,txttwelvevsix,completeTestll;
    LinearLayout txttwelveleadoneagain, txttwelveleadtwoagain,txttwelvevoneagain,txttwelvevtwoagain,txttwelvevthreeagain,txttwelvevfouragain,txttwelvevfiveagain,txttwelvevsixagain,txttwelveleadagain;
    private Context mContext;
    String ptno = "";
    SharedPreferences pref;
    MaterialDialog progressDialog;
    SweetAlertDialog pDialog;
    ECGReport ecgReport = new ECGReport();

    public   static  int leadIndex = 0,x=0;
    static String pdfurl = "";
    public   static  boolean again =false;

    final CompositeDisposable compositeDisposable = new CompositeDisposable();
    TextView description;
    ArrayList<String> buttoncollectionshide = new ArrayList<String>(Arrays.asList("txttwelveleadone","txttwelveleadoneagain","txttwelveleadtwo","txttwelveleadtwoagain","txttwelvevone","txttwelvevoneagain","txttwelvevtwo","txttwelvevtwoagain","txttwelvevthree","txttwelvevthreeagain","txttwelvevfour","txttwelvevfouragain","txttwelvevfive","txttwelvevfiveagain","txttwelvevsix","txttwelvevsixagain","txttwelveleadagain","completetaskll","btnSaveTwelveLeadrecord"));


    GifImageView gifImageView;
ActivityTwelveLeadBinding binding;
PatientModel patientModel;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       binding = DataBindingUtil.setContentView(this,R.layout.activity_twelve_lead);
        pref = this.getSharedPreferences("sunyahealth", Context.MODE_PRIVATE);
        ptno = pref.getString("PTNO", "");
        patientModel = getIntent().getParcelableExtra("patient");
//        labdb = new LabDB(getApplicationContext());
//        ecgReport=labdb.getLastEcgSign(ptno);
        mContext = getApplicationContext();
//
        hideAndSeek(buttoncollectionshide,true);

        hideAndSeek(new ArrayList<>(Arrays.asList("txttwelveleadone")),false);

   binding.btnReport.setOnClickListener(view -> {
       Log.d("rantest","viewing pdf");

       if(!pdfurl.equals(""))
       {
           Log.d("rantest","url available");
           File file = new File(pdfurl);
           Intent intent = new Intent(Intent.ACTION_VIEW);

           // set leadIndex to give temporary permission to external app to use your FileProvider
           intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);

           // generate URI, I defined authority as the application ID in the Manifest, the last param is file I want to open
           Uri photoURI = FileProvider.getUriForFile(TwelveLeadEcg.this,
                   BuildConfig.APPLICATION_ID + ".provider",
                   file);
           // I am opening a PDF file so I give it a valid MIME type
           intent.setDataAndType(photoURI, "application/pdf");

           // validate that the device can open your File!
           startActivity(intent);



   }});
   binding.btnComplete.setOnClickListener(view -> TwelveLeadEcg.this.onBackPressed());

        initViews();
        initOnClickListener();
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


    @Override
    public void onBackPressed() {

        DialogUtil.getOKCancelDialog(this, "Warning", "Do you want to complete Twelve Lead Test?", "Yes", "No", (dialogInterface, i) -> TwelveLeadEcg.super.onBackPressed(), (dialogInterface, i) -> {


        });




    }

    private void initViews() {


        txttwelveleadone = findViewById(R.id.txttwelveleadone);
        description = findViewById(R.id.tv_description);
        txttwelveleadtwo = findViewById(R.id.txttwelveleadtwo);
        txttwelvevone = findViewById(R.id.txttwelvevone);
        txttwelvevtwo = findViewById(R.id.txttwelvevtwo);
        txttwelvevthree = findViewById(R.id.txttwelvevthree);
        txttwelvevfour = findViewById(R.id.txttwelvevfour);
        txttwelvevfive = findViewById(R.id.txttwelvevfive);
        txttwelvevsix = findViewById(R.id.txttwelvevsix);
        gifImageView = findViewById(R.id.gif_holder);
        txttwelveleadoneagain=findViewById(R.id.txttwelveleadoneagain);
        txttwelveleadtwoagain=findViewById(R.id.txttwelveleadtwoagain);
        txttwelvevoneagain=findViewById(R.id.txttwelvevoneagain);
        txttwelvevtwoagain=findViewById(R.id.txttwelvevtwoagain);
        txttwelvevthreeagain=findViewById(R.id.txttwelvevthreeagain);
        txttwelvevfouragain=findViewById(R.id.txttwelvevfouragain);
        txttwelvevfiveagain=findViewById(R.id.txttwelvevfiveagain);
        txttwelvevsixagain=findViewById(R.id.txttwelvevsixagain);
        btnSaveTwelveLeadRecord = findViewById(R.id.btnSaveTwelveLeadrecord);
        completeTestll=findViewById(R.id.completeTestll);
        txttwelveleadagain=findViewById(R.id.txttwelveleadagain);






    }


    public void hideAndSeek(ArrayList<String> idsarray, boolean ishide){
        try{
            if(ishide){
                for(String selectedString:idsarray){

                    View linearLayout = findViewById(getResourceId(selectedString,"id",getPackageName()));
                    linearLayout.setVisibility(View.GONE);

                }





            }
            else {

                for(String selectedString:idsarray){

                    View linearLayout = findViewById(getResourceId(selectedString,"id",getPackageName()));
                    linearLayout.setVisibility(View.VISIBLE);

                }


            }

        }
        catch (Exception e){
            Log.d("rantest",e.getLocalizedMessage());



        }


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
    @Override
    protected void onResume() {
        buttoncollectionshide = new ArrayList<String>(Arrays.asList("txttwelveleadone","txttwelveleadoneagain","txttwelveleadtwo","txttwelveleadtwoagain","txttwelvevone","txttwelvevoneagain","txttwelvevtwo","txttwelvevtwoagain","txttwelvevthree","txttwelvevthreeagain","txttwelvevfour","txttwelvevfouragain","txttwelvevfive","txttwelvevfiveagain","txttwelvevsix","txttwelvevsixagain","txttwelveleadagain","completetaskll","btnSaveTwelveLeadrecord"));

//         This is done  dynamically to  hide buttons in respet to the listener's flag set on theirs.
//        ArrayList<String> buttoncollectionshide = new ArrayList<String>(Arrays.asList("txttwelveleadone","txttwelveleadoneagain","txttwelveleadtwo","txttwelveleadtwoagain","txttwelvevone","txttwelvevoneagain","txttwelvevtwo","txttwelvevtwoagain","txttwelvevthree","txttwelvevthreeagain","txttwelvevfour","txttwelvevfouragain","txttwelvevfive","txttwelvevfiveagain","txttwelvevsix","txttwelvevsixagain","txttwelveleadagain","completetaskll","btnSaveTwelveLeadrecord"));


//
        if(leadIndex ==0&&again){
//
            again =false;
            showDynamicimage("gif_lead1");
            showDynamicDescription("ecginfo");
            hideAndSeek(buttoncollectionshide,true);
            ArrayList<String> buttoncollectionsshow0=new ArrayList<String>(Arrays.asList("txttwelveleadone"));
            hideAndSeek(buttoncollectionsshow0,false);
        }

        else if(leadIndex ==1){
            x++;
            if(!again&&x==2){
//                 this is as lead one pressed and performed test for the fir5st time
                leadIndex =0;
                x=0;
                showDynamicimage("gif_lead2");
//                showDynamicDescription("ecginfo");
                hideAndSeek(buttoncollectionshide,true);
                description.setText(getResources().getString(R.string.Leadtwoecginfo));

                ArrayList<String> buttoncollectionsshow21=new ArrayList<String>(Arrays.asList("txttwelveleadoneagain","txttwelveleadtwo"));
                hideAndSeek(buttoncollectionsshow21,false);

            }

            else if(again&&x==2) {
                leadIndex =0;
                x=0;
                again=false;
                showDynamicimage("gif_lead2");
//                showDynamicDescription("ecginfo");
                description.setText(getResources().getString(R.string.Leadtwoecginfo));
                hideAndSeek(buttoncollectionshide,true);
                ArrayList<String> buttoncollectionsshow22=new ArrayList<String>(Arrays.asList("txttwelveleadoneagain","txttwelveleadtwo"));
                hideAndSeek(buttoncollectionsshow22,false);

            }


        }



        else if(leadIndex ==2)
        {

            x++;
            if(!again&&x==2){
                leadIndex =0;
                x=0;
                showDynamicimage("gif_leadv1");
//                showDynamicDescription("ecginfo");
                description.setText(getResources().getString(R.string.V1ecginfo));
                hideAndSeek(buttoncollectionshide,true);
                ArrayList<String> buttoncollectionsshow11=new ArrayList<String>(Arrays.asList("txttwelveleadtwoagain","txttwelvevone"));
                hideAndSeek(buttoncollectionsshow11,false);

            }

            else if(again&&x==2) {
                leadIndex =0;
                x=0;
                again=false;
                showDynamicimage("gif_leadv1");
//                showDynamicDescription("ecginfo");
                hideAndSeek(buttoncollectionshide,true);
                description.setText(getResources().getString(R.string.V1ecginfo));
                ArrayList<String> buttoncollectionsshow12=new ArrayList<String>(Arrays.asList("txttwelveleadtwoagain","txttwelvevone"));
                hideAndSeek(buttoncollectionsshow12,false);

            }




        }
        else if(leadIndex ==3)
        {

            x++;
            if(!again&&x==2){
                leadIndex =0;
                x=0;
                showDynamicimage("gif_leadv2");
//                showDynamicDescription("ecginfo");
                hideAndSeek(buttoncollectionshide,true);
                description.setText(getResources().getString(R.string.V2ecginfo));
                ArrayList<String> buttoncollectionsshow11=new ArrayList<String>(Arrays.asList("txttwelvevoneagain","txttwelvevtwo"));
                hideAndSeek(buttoncollectionsshow11,false);

            }

            else if(again&&x==2) {
                leadIndex =0;
                x=0;
                again=false;
                showDynamicimage("gif_leadv2");
//                showDynamicDescription("ecginfo");
                hideAndSeek(buttoncollectionshide,true);
                description.setText(getResources().getString(R.string.V2ecginfo));
                ArrayList<String> buttoncollectionsshow12=new ArrayList<String>(Arrays.asList("txttwelvevoneagain","txttwelvevtwo"));
                hideAndSeek(buttoncollectionsshow12,false);

            }




        }

        else if(leadIndex ==4)
        {

            x++;
            if(!again&&x==2){
                leadIndex =0;
                x=0;
                showDynamicimage("gif_leadv3");
//                showDynamicDescription("ecginfo");
                hideAndSeek(buttoncollectionshide,true);
                description.setText(getResources().getString(R.string.V3ecginfo));
                ArrayList<String> buttoncollectionsshow11=new ArrayList<String>(Arrays.asList("txttwelvevtwoagain","txttwelvevthree"));
                hideAndSeek(buttoncollectionsshow11,false);

            }

            else if(again&&x==2) {
                leadIndex =0;
                x=0;
                again=false;
                showDynamicimage("gif_leadv3");
//                showDynamicDescription("ecginfo");
                hideAndSeek(buttoncollectionshide,true);
                description.setText(getResources().getString(R.string.V3ecginfo));
                ArrayList<String> buttoncollectionsshow12=new ArrayList<String>(Arrays.asList("txttwelvevtwoagain","txttwelvevthree"));
                hideAndSeek(buttoncollectionsshow12,false);

            }




        }

        else if(leadIndex ==5)
        {

            x++;
            if(!again&&x==2){
                leadIndex =0;
                x=0;
                showDynamicimage("gif_leadv4");
//                showDynamicDescription("ecginfo");
                description.setText(getResources().getString(R.string.V4ecginfo));
                hideAndSeek(buttoncollectionshide,true);
                ArrayList<String> buttoncollectionsshow11=new ArrayList<String>(Arrays.asList("txttwelvevthreeagain","txttwelvevfour"));
                hideAndSeek(buttoncollectionsshow11,false);

            }

            else if(again&&x==2) {
                leadIndex =0;
                x=0;
                again=false;
                showDynamicimage("gif_leadv4");
//                showDynamicDescription("ecginfo");
                description.setText(getResources().getString(R.string.V4ecginfo));
                hideAndSeek(buttoncollectionshide,true);
                ArrayList<String> buttoncollectionsshow12=new ArrayList<String>(Arrays.asList("txttwelvevthreeagain","txttwelvevfour"));
                hideAndSeek(buttoncollectionsshow12,false);

            }




        }

        else if(leadIndex ==6)
        {

            x++;
            if(!again&&x==2){
                leadIndex =0;
                x=0;
                showDynamicimage("gif_leadv5");
//                showDynamicDescription("ecginfo");
                hideAndSeek(buttoncollectionshide,true);
                description.setText(getResources().getString(R.string.V5ecginfo));
                ArrayList<String> buttoncollectionsshow11=new ArrayList<String>(Arrays.asList("txttwelvevfouragain","txttwelvevfive"));
                hideAndSeek(buttoncollectionsshow11,false);

            }

            else if(again&&x==2) {
                leadIndex =0;
                x=0;
                again=false;
                showDynamicimage("gif_leadv5");
//                showDynamicDescription("ecginfo");
                description.setText(getResources().getString(R.string.V5ecginfo));
                hideAndSeek(buttoncollectionshide,true);
                ArrayList<String> buttoncollectionsshow12=new ArrayList<String>(Arrays.asList("txttwelvevfouragain","txttwelvevfive"));
                hideAndSeek(buttoncollectionsshow12,false);

            }




        }
        else if(leadIndex ==7)
        {

            x++;
            if(!again&&x==2){
                leadIndex =0;
                x=0;
                showDynamicimage("gif_leadv6");
//                showDynamicDescription("ecginfo");
                hideAndSeek(buttoncollectionshide,true);
                description.setText(getResources().getString(R.string.V6ecginfo));
                ArrayList<String> buttoncollectionsshow11=new ArrayList<String>(Arrays.asList("txttwelvevfiveagain","txttwelvevsix"));
                hideAndSeek(buttoncollectionsshow11,false);

            }

            else if(again&&x==2) {
                leadIndex =0;
                x=0;
                again=false;
                showDynamicimage("gif_leadv6");
//                showDynamicDescription("ecginfo");
                description.setText(getResources().getString(R.string.V6ecginfo));
                hideAndSeek(buttoncollectionshide,true);
                ArrayList<String> buttoncollectionsshow12=new ArrayList<String>(Arrays.asList("txttwelvevfiveagain","txttwelvevsix"));
                hideAndSeek(buttoncollectionsshow12,false);

            }




        }

        else if(leadIndex ==8)
        {

            x++;
            if(!again&&x==2){
                leadIndex =0;
                x=0;
                showDynamicimage("gif_leadv6");
//                showDynamicDescription("ecginfo");
                hideAndSeek(buttoncollectionshide,true);
                description.setText(getResources().getString(R.string.V6ecginfo));
                ArrayList<String> buttoncollectionsshow11=new ArrayList<String>(Arrays.asList("txttwelvevsixagain","btnSaveTwelveLeadrecord"));
                hideAndSeek(buttoncollectionsshow11,false);

            }

            else if(again&&x==2) {
                leadIndex =0;
                x=0;
                again=false;
                showDynamicimage("gif_leadv6");
//                showDynamicDescription("ecginfo");
                description.setText(getResources().getString(R.string.V6ecginfo));
                hideAndSeek(buttoncollectionshide,true);
                ArrayList<String> buttoncollectionsshow12=new ArrayList<String>(Arrays.asList("txttwelvevsixagain","btnSaveTwelveLeadrecord"));
                hideAndSeek(buttoncollectionsshow12,false);

            }




        }

        else if(leadIndex ==9)
        {

            x++;
            if(!again&&x==2){
                leadIndex =0;
                x=0;
                showDynamicimage("gif_leadv6");
//                showDynamicDescription("ecginfo");
                hideAndSeek(buttoncollectionshide,true);
                description.setText(getResources().getString(R.string.V6ecginfo));
                ArrayList<String> buttoncollectionsshow11=new ArrayList<String>(Arrays.asList("txttwelvevsixagain","btnSaveTwelveLeadrecord"));
                hideAndSeek(buttoncollectionsshow11,false);

            }

            else if(again&&x==2) {
                leadIndex =0;
                x=0;
                again=false;
                showDynamicimage("gif_leadv6");
//                showDynamicDescription("ecginfo");
                description.setText(getResources().getString(R.string.V6ecginfo));
                hideAndSeek(buttoncollectionshide,true);
                ArrayList<String> buttoncollectionsshow12=new ArrayList<String>(Arrays.asList("txttwelvevsixagain","btnSaveTwelveLeadrecord"));
                hideAndSeek(buttoncollectionsshow12,false);

            }




        }
        super.onResume();
    }



    private void initOnClickListener() {
        txttwelveleadone.setOnClickListener(v -> {
//            this is for lead one already tested
            leadIndex =1;
            again=false;

            getReadingForECG(1);



        });

        txttwelveleadoneagain.setOnClickListener(v ->{
            leadIndex =1;
            again=true;


            getReadingForECG(1);



        });

        txttwelveleadtwo.setOnClickListener(v ->{
            leadIndex =2;
            again=false;
            getReadingForECG(8);



        });

        txttwelveleadtwoagain.setOnClickListener(v ->{
            leadIndex =2;
            again=true;


            getReadingForECG(8);


        });

        txttwelvevone.setOnClickListener(v -> {
            leadIndex=3;
            again=false;

            getReadingForECG(2);});

        txttwelvevoneagain.setOnClickListener(v -> {
            leadIndex=3;
            again=true;

            getReadingForECG(2);

        });

        txttwelvevtwo.setOnClickListener(v ->{

            leadIndex=4;
            again=false;
            getReadingForECG(3);});

        txttwelvevtwoagain.setOnClickListener(v ->{

            leadIndex=4;
            again=true;
            getReadingForECG(3);});

        txttwelvevthree.setOnClickListener(v ->{

            leadIndex=5;
            again=false;
            getReadingForECG(4);});
        txttwelvevthreeagain.setOnClickListener(v ->{

            leadIndex=5;
            again=true;
            getReadingForECG(4);});

        txttwelvevfour.setOnClickListener(v ->{

            leadIndex=6;
            again=false;
            getReadingForECG(5);});
        txttwelvevfouragain.setOnClickListener(v ->{

            leadIndex=6;
            again=true;
            getReadingForECG(5);});
        txttwelvevfive.setOnClickListener(v ->{

            leadIndex=7;
            again=false;
            getReadingForECG(6);});

        txttwelvevfiveagain.setOnClickListener(v ->{

            leadIndex=7;
            again=true;
            getReadingForECG(6);});
        txttwelvevsix.setOnClickListener(v ->{

            leadIndex=8;
            again=false;
            getReadingForECG(7);});
        txttwelvevsixagain.setOnClickListener(v ->{

            leadIndex=8;
            again=true;
            getReadingForECG(7);});


        btnSaveTwelveLeadRecord.setOnClickListener(v ->{

            hideAndSeek(buttoncollectionshide,true);
            hideAndSeek(new ArrayList<>(Arrays.asList("txttwelveleadagain","completetaskll")),false);
            createPDF();




        });

        txttwelveleadagain.setOnClickListener(v ->{
            leadIndex =0;
            again=true;
            showDynamicimage("gif_lead1");
            showDynamicDescription("ecginfo");
            hideAndSeek(buttoncollectionshide,true);
            ArrayList<String> buttoncollectionsshow0=new ArrayList<String>(Arrays.asList("txttwelveleadone"));
            hideAndSeek(buttoncollectionsshow0,false);

        });




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


    public void createPDF() {
        InitiateEcg initiateEcg = new InitiateEcg();
        initiateEcg.saveEcgData(mContext, "test", new SaveEcgCallBack() {
            @Override
            public void onSuccess(Success success, EcgConfig ecgConfig) {
//                LabDB db = new LabDB(getApplicationContext());
//                ecgReport.setPt_no(ptno);
//                ecgReport.setHeartrate(ecgConfig.getHeartRate());
//                ecgReport.setPr((ecgConfig.getpR()));
//                ecgReport.setQt(ecgConfig.getqT());
//                ecgReport.setQtc(ecgConfig.getqTc());
//                ecgReport.setQrs(ecgConfig.getqRs());
//                ecgReport.setSdnn(ecgConfig.getSdnn());
//                ecgReport.setRmssd(ecgConfig.getRmssd());
//                ecgReport.setMrr(ecgConfig.getmRR());
//                ecgReport.setFinding(ecgConfig.getFinding());
//                int last_ecgsign_row_id = db.SaveSingleleadECGSign(ecgReport);
//                try {
//                    Thread.sleep(1000);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//                ecgReport.setRow_id(last_ecgsign_row_id);
                setMobileDataEnabled(TwelveLeadEcg.this,false);
                showProgress("Generating pdf");
                makePDF(ecgConfig);






            }

            @Override
            public void onError(Errors error) {
                hideProgress();
                setMobileDataEnabled(TwelveLeadEcg.this,true);

                Toast.makeText(mContext, error.getErrorMsg(), Toast.LENGTH_SHORT).show();
            }
        });
    }



    public void makePDF(EcgConfig ecgConfig) {

        InitiateEcg initiateEcg = new InitiateEcg();
        initiateEcg.makeEcgReport(mContext, new UserDetails(patientModel.getPtName(), patientModel.getPtAge(), patientModel.getPtSex()), true, SECRET_ID, ecgConfig, new PdfCallback() {
            @Override
            public void onPdfAvailable(EcgConfig ecgConfig) {
                Log.e("makepdfpath", ecgConfig.getFileUrl());
                String filePath = ecgConfig.getFileUrl();



                pdfurl = filePath;
                LabDB db = new LabDB(getApplicationContext());
                LabInstanceDB labInstanceDB=new LabInstanceDB(getApplicationContext());
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
                ecgReport.setEcgType("TL");

                ecgReport.setFilepath(ecgConfig.getFileUrl());
                compositeDisposable.add(db.updateEcgObserVable(ecgReport)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(ecgid -> {

                                    if (ecgid != null) {

                                        if (!ecgid.equals("")) {

                                            pref.edit().putInt("TLF", 1 ).apply();

                                            DialogUtil.getOKDialog(TwelveLeadEcg.this, "", "Report Saved Successfully", "ok");

                                        } else {


                                            DialogUtil.getOKDialog(TwelveLeadEcg.this, "", "Error While saving", "ok");
                                        }


                                    } else {



                                        DialogUtil.getOKDialog(TwelveLeadEcg.this, "", "Error While saving", "ok");
                                    }
                                },
                                throwable -> {

                                    Log.e("rantest", "Unable to get username", throwable);


                                }));

                compositeDisposable.add(labInstanceDB.updateEcgObserVable(ecgReport)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(ecgid -> {

                                    if (ecgid != null) {

                                        if (!ecgid.equals("")) {

                                            pref.edit().putInt("TLF", 1 ).apply();

                                            DialogUtil.getOKDialog(TwelveLeadEcg.this, "", "Report Saved Successfully", "ok");

                                        } else {


                                            DialogUtil.getOKDialog(TwelveLeadEcg.this, "", "Error While saving", "ok");
                                        }


                                    } else {



                                        DialogUtil.getOKDialog(TwelveLeadEcg.this, "", "Error While saving", "ok");
                                    }
                                },
                                throwable -> {

                                    Log.e("rantest", "Unable to get username", throwable);


                                }));


                Toast.makeText(mContext, "Successfully generated pdf.", Toast.LENGTH_SHORT).show();
                setMobileDataEnabled(TwelveLeadEcg.this,true);
                hideProgress();

            }

            @Override
            public void onError(Errors errors) {
                setMobileDataEnabled(TwelveLeadEcg.this,true);
                hideProgress();
                Toast.makeText(mContext, errors.getErrorMsg(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Intent intent = new Intent(TwelveLeadEcg.this, EcgOptionsActivity.class);
                startActivity(intent);
                finish();
                break;
        }

        return super.onOptionsItemSelected(item);
    }


    public void showDynamicDescription(String ecginfo){
        description.setText(getResourceId(ecginfo,"string",getPackageName()));
    }

    public void showDynamicimage(String imagesrcs){
        try{

            gifImageView.setImageResource((getResourceId(imagesrcs,"drawable",getPackageName())));}
        catch (Exception e){


            Log.d("rantest",e.getLocalizedMessage());
        }

    }





}
