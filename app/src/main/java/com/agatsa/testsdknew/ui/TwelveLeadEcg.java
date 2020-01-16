package com.agatsa.testsdknew.ui;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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

import com.agatsa.sanketlife.callbacks.PdfCallback;
import com.agatsa.sanketlife.callbacks.ResponseCallback;
import com.agatsa.sanketlife.callbacks.SaveEcgCallBack;
import com.agatsa.sanketlife.development.EcgConfig;
import com.agatsa.sanketlife.development.Errors;
import com.agatsa.sanketlife.development.InitiateEcg;
import com.agatsa.sanketlife.development.Success;
import com.agatsa.sanketlife.development.UserDetails;
import com.agatsa.sanketlife.models.EcgTypes;
import com.agatsa.testsdknew.R;
import com.ontbee.legacyforks.cn.pedant.SweetAlert.SweetAlertDialog;

import java.util.ArrayList;
import java.util.Arrays;

import pl.droidsonroids.gif.GifImageView;

public class TwelveLeadEcg extends AppCompatActivity {

    private static final String CLIENT_ID = "5a3b4c16b4a56b000132f5d5b4580266565440bda51dcb4122d39844";
    private static final String SECRET_ID = "5a3b4c16b4a56b000132f5d5746be305d56c49e49cc88b12ccb05d71";
    private Button twelvebtnViewpdf,btnSaveTwelveLeadRecord;

    LinearLayout txttwelveleadone, txttwelveleadtwo,txttwelvevone,txttwelvevtwo,txttwelvevthree,txttwelvevfour,txttwelvevfive,txttwelvevsix,completeTestll;
    LinearLayout txttwelveleadoneagain, txttwelveleadtwoagain,txttwelvevoneagain,txttwelvevtwoagain,txttwelvevthreeagain,txttwelvevfouragain,txttwelvevfiveagain,txttwelvevsixagain,txttwelveleadagain;
    private Context mContext;
    int ptno = 0;
    SharedPreferences pref;
    SweetAlertDialog pDialog;
    Toolbar toolbar;
    public   static  int leadIndex = 0,x=0;
    public   static  boolean again =false;
    TextView description;
    ArrayList<String> buttoncollectionshide = new ArrayList<String>(Arrays.asList("txttwelveleadone","txttwelveleadoneagain","txttwelveleadtwo","txttwelveleadtwoagain","txttwelvevone","txttwelvevoneagain","txttwelvevtwo","txttwelvevtwoagain","txttwelvevthree","txttwelvevthreeagain","txttwelvevfour","txttwelvevfouragain","txttwelvevfive","txttwelvevfiveagain","txttwelvevsix","txttwelvevsixagain","txttwelveleadagain","completetaskll","btnSaveTwelveLeadrecord"));


    GifImageView gifImageView;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_twelve_lead);
        pref = this.getSharedPreferences("sunyahealth", Context.MODE_PRIVATE);
        ptno = pref.getInt("pt_id", 0);
//        labdb = new LabDB(getApplicationContext());
//        ecgReport=labdb.getLastEcgSign(ptno);
        mContext = getApplicationContext();
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Twelve Lead");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        hideAndSeek(buttoncollectionshide,true);
        hideAndSeek(new ArrayList<>(Arrays.asList("txttwelveleadone")),false);


        initViews();
        initOnClickListener();
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
        txttwelvevfiveagain=findViewById(R.id.txttwelvevfouragain);
        txttwelvevsixagain=findViewById(R.id.txttwelvevfouragain);
        btnSaveTwelveLeadRecord = findViewById(R.id.btnSaveTwelveLeadrecord);
        twelvebtnViewpdf = findViewById(R.id.btnviewreport);
        completeTestll=findViewById(R.id.completeTestll);
        txttwelveleadagain=findViewById(R.id.txttwelveleadagain);






    }
    public void hideAndSeek(ArrayList<String> idsarray, boolean ishide){
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
                showDynamicDescription("ecginfo");
                hideAndSeek(buttoncollectionshide,true);
                ArrayList<String> buttoncollectionsshow21=new ArrayList<String>(Arrays.asList("txttwelveleadoneagain","txttwelveleadtwo"));
                hideAndSeek(buttoncollectionsshow21,false);

            }

            else if(again&&x==2) {
                leadIndex =0;
                x=0;
                again=false;
                showDynamicimage("gif_lead2");
                showDynamicDescription("ecginfo");
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
                showDynamicimage("gif_lead2");
                showDynamicDescription("ecginfo");
                hideAndSeek(buttoncollectionshide,true);
                ArrayList<String> buttoncollectionsshow11=new ArrayList<String>(Arrays.asList("txttwelveleadtwoagain","txttwelvevone"));
                hideAndSeek(buttoncollectionsshow11,false);

            }

            else if(again&&x==2) {
                leadIndex =0;
                x=0;
                again=false;
                showDynamicimage("gif_lead2");
                showDynamicDescription("ecginfo");
                hideAndSeek(buttoncollectionshide,true);
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
                showDynamicimage("gif_lead2");
                showDynamicDescription("ecginfo");
                hideAndSeek(buttoncollectionshide,true);
                ArrayList<String> buttoncollectionsshow11=new ArrayList<String>(Arrays.asList("txttwelvevoneagain","txttwelvevtwo"));
                hideAndSeek(buttoncollectionsshow11,false);

            }

            else if(again&&x==2) {
                leadIndex =0;
                x=0;
                again=false;
                showDynamicimage("gif_lead2");
                showDynamicDescription("ecginfo");
                hideAndSeek(buttoncollectionshide,true);
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
                showDynamicimage("gif_lead2");
                showDynamicDescription("ecginfo");
                hideAndSeek(buttoncollectionshide,true);
                ArrayList<String> buttoncollectionsshow11=new ArrayList<String>(Arrays.asList("txttwelvevtwoagain","txttwelvevthree"));
                hideAndSeek(buttoncollectionsshow11,false);

            }

            else if(again&&x==2) {
                leadIndex =0;
                x=0;
                again=false;
                showDynamicimage("gif_lead2");
                showDynamicDescription("ecginfo");
                hideAndSeek(buttoncollectionshide,true);
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
                showDynamicimage("gif_lead2");
                showDynamicDescription("ecginfo");
                hideAndSeek(buttoncollectionshide,true);
                ArrayList<String> buttoncollectionsshow11=new ArrayList<String>(Arrays.asList("txttwelvevthreeagain","txttwelvevfour"));
                hideAndSeek(buttoncollectionsshow11,false);

            }

            else if(again&&x==2) {
                leadIndex =0;
                x=0;
                again=false;
                showDynamicimage("gif_lead2");
                showDynamicDescription("ecginfo");
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
                showDynamicimage("gif_lead2");
                showDynamicDescription("ecginfo");
                hideAndSeek(buttoncollectionshide,true);
                ArrayList<String> buttoncollectionsshow11=new ArrayList<String>(Arrays.asList("txttwelvevfouragain","txttwelvevfive"));
                hideAndSeek(buttoncollectionsshow11,false);

            }

            else if(again&&x==2) {
                leadIndex =0;
                x=0;
                again=false;
                showDynamicimage("gif_lead2");
                showDynamicDescription("ecginfo");
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
                showDynamicimage("gif_lead2");
                showDynamicDescription("ecginfo");
                hideAndSeek(buttoncollectionshide,true);
                ArrayList<String> buttoncollectionsshow11=new ArrayList<String>(Arrays.asList("txttwelvevfiveagain","txttwelvevsix"));
                hideAndSeek(buttoncollectionsshow11,false);

            }

            else if(again&&x==2) {
                leadIndex =0;
                x=0;
                again=false;
                showDynamicimage("gif_lead2");
                showDynamicDescription("ecginfo");
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
                showDynamicimage("gif_lead2");
                showDynamicDescription("ecginfo");
                hideAndSeek(buttoncollectionshide,true);
                ArrayList<String> buttoncollectionsshow11=new ArrayList<String>(Arrays.asList("txttwelvevsixagain","btnSaveTwelveLeadrecord"));
                hideAndSeek(buttoncollectionsshow11,false);

            }

            else if(again&&x==2) {
                leadIndex =0;
                x=0;
                again=false;
                showDynamicimage("gif_lead2");
                showDynamicDescription("ecginfo");
                hideAndSeek(buttoncollectionshide,true);
                ArrayList<String> buttoncollectionsshow12=new ArrayList<String>(Arrays.asList("txttwelvevsixagain","btnSaveTwelveLeadrecord"));
                hideAndSeek(buttoncollectionsshow12,false);

            }




        }

        else if(leadIndex ==8)
        {

            x++;
            if(!again&&x==2){
                leadIndex =0;
                x=0;
                showDynamicimage("gif_lead2");
                showDynamicDescription("ecginfo");
                hideAndSeek(buttoncollectionshide,true);
                ArrayList<String> buttoncollectionsshow11=new ArrayList<String>(Arrays.asList("txttwelvevsixagain","btnSaveTwelveLeadrecord"));
                hideAndSeek(buttoncollectionsshow11,false);

            }

            else if(again&&x==2) {
                leadIndex =0;
                x=0;
                again=false;
                showDynamicimage("gif_lead2");
                showDynamicDescription("ecginfo");
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

        });

        twelvebtnViewpdf.setOnClickListener(view -> {
            Intent intent = new Intent(getApplicationContext(), HistoryActivity.class);
            intent.putExtra("type", EcgTypes.NORMAL_ECG);
            startActivity(intent);
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
                Intent intent = new Intent(TwelveLeadEcg.this, PdfViewActivity.class);
                intent.putExtra("fileUrl", filePath);
                startActivity(intent);

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
                Intent intent = new Intent(TwelveLeadEcg.this, EcgOptionsActivity.class);
                startActivity(intent);
                finish();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(TwelveLeadEcg.this, EcgOptionsActivity.class);
        startActivity(intent);

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
