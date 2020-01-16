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
import com.agatsa.testsdknew.Models.ECGReport;
import com.agatsa.testsdknew.R;
import com.ontbee.legacyforks.cn.pedant.SweetAlert.SweetAlertDialog;

import java.util.ArrayList;
import java.util.Arrays;

import pl.droidsonroids.gif.GifImageView;

public class LimbSixLead extends AppCompatActivity {
    private static final String CLIENT_ID = "5a3b4c16b4a56b000132f5d5b4580266565440bda51dcb4122d39844";
    private static final String SECRET_ID = "5a3b4c16b4a56b000132f5d5746be305d56c49e49cc88b12ccb05d71";
    private Button btnSavelimbreport,btnlimbHistory;

    LinearLayout txtlimbleadone,savell,completetaskll,
            txtlimboneagain,txtlimbleadtwo,txtlimbtwoagain,txtlimbagain;
    private Context mContext;
    String ptno = "";
    SharedPreferences pref;
    ECGReport ecgReport=new ECGReport();
    SweetAlertDialog pDialog;
    Toolbar toolbar;
    TextView description;

    public   static  int leadIndex = 0,x=0;
    public   static  boolean again =false;

    GifImageView gifImageView;
    ArrayList<String> buttoncollectionshide = new ArrayList<String>(Arrays.asList("txtlimbleadone","txtlimboneagain","txtlimbleadtwo","txtlimbtwoagain","txtlimbagain","ll_savereport","ll_report","ll_complete"));
    ArrayList<String> buttoncollectionsshow=new ArrayList<String>(Arrays.asList("txtlimbleadone","txtlimboneagain"));



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_limb_lead);
        pref = this.getSharedPreferences("sunyahealth", Context.MODE_PRIVATE);
        ptno = pref.getString("PTNO", "");
//        labdb = new LabDB(getApplicationContext());
//        ecgReport=labdb.getLastEcgSign(ptno);
        mContext = getApplicationContext();
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Limb Six Lead");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        hideAndSeek(buttoncollectionshide,true);
        ArrayList<String> buttoncollectionsshowstart=new ArrayList<String>(Arrays.asList("txtlimbleadone"));
        hideAndSeek(buttoncollectionsshowstart,false);





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
        btnSavelimbreport = findViewById(R.id.btnSavelimbreport);
        txtlimbleadone = findViewById(R.id.txtlimbleadone);
        savell=findViewById(R.id.savell);
        txtlimbleadtwo = findViewById(R.id.txtlimbleadtwo);

        completetaskll=findViewById(R.id.completetaskll);
        txtlimboneagain=findViewById(R.id.txtlimboneagain);
        txtlimbtwoagain=findViewById(R.id.txtlimbtwoagain);
        txtlimbagain=findViewById(R.id.txtlimbagain);
        gifImageView=findViewById(R.id.gif_holder);
        description=findViewById(R.id.tv_description);






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

    private void initOnClickListener() {

        txtlimboneagain.setOnClickListener(v ->{
leadIndex =1;
again=true;


            getReadingForECG(1);



        });

       txtlimbtwoagain.setOnClickListener(v ->{
           leadIndex =2;
           again=true;


           getReadingForECG(8);


       });
       txtlimbagain.setOnClickListener(v ->{
            leadIndex =0;
            again=true;


        });


        txtlimbleadone.setOnClickListener(v -> {
//            this is for lead one already tested
            leadIndex =1;
            again=false;

            getReadingForECG(1);



        });
        txtlimbleadtwo.setOnClickListener(v ->{
            leadIndex =2;
            again=false;
            getReadingForECG(8);



        });

     btnSavelimbreport.setOnClickListener(v -> {
//         savell.setVisibility(View.GONE);
//         completetaskll.setVisibility(View.VISIBLE);
//         ","txtlimbagain","ll_savereport","ll_report","ll_complete")
         hideAndSeek(buttoncollectionshide,true);
         ArrayList <String> limbreport = new ArrayList<>(Arrays.asList("txtlimbagain","ll_report","ll_complete"));
         hideAndSeek(limbreport,false);
         createPDF();

     });

//        btnlimbHistory.setOnClickListener(view -> {
//            Intent intent = new Intent(getApplicationContext(), HistoryActivity.class);
//            intent.putExtra("type", EcgTypes.NORMAL_ECG);
//            startActivity(intent);
//        });


        pDialog = new SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE);
        pDialog.getProgressHelper().setBarColor(ContextCompat.getColor(getApplicationContext(), R.color.colorAccent));
        pDialog.setTitleText("Registering");
        pDialog.setCancelable(false);
    }


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
                Log.d("Reading failure:", errors.getErrorMsg());
            }
        });

    }


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
                Intent intent = new Intent(LimbSixLead.this, PdfViewActivity.class);
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
    protected void onResume() {
  buttoncollectionshide = new ArrayList<String>(Arrays.asList("txtlimbleadone","txtlimboneagain","txtlimbleadtwo","txtlimbtwoagain","txtlimbagain","ll_savereport","ll_report","ll_complete"));
//
        if(leadIndex ==0&&again){
//
            again =false;
            showDynamicimage("gif_lead1");
            showDynamicDescription("ecginfo");
            hideAndSeek(buttoncollectionshide,true);
            ArrayList<String> buttoncollectionsshow0=new ArrayList<String>(Arrays.asList("txtlimbleadone"));
            hideAndSeek(buttoncollectionsshow0,false);
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
                ArrayList<String> buttoncollectionsshow11=new ArrayList<String>(Arrays.asList("txtlimbtwoagain","ll_savereport"));
                hideAndSeek(buttoncollectionsshow11,false);

            }

            else if(again&&x==2) {
                leadIndex =0;
                x=0;
                again=false;
                showDynamicimage("gif_lead2");
                showDynamicDescription("ecginfo");
                hideAndSeek(buttoncollectionshide,true);
                ArrayList<String> buttoncollectionsshow12=new ArrayList<String>(Arrays.asList("txtlimbtwoagain","ll_savereport"));
                hideAndSeek(buttoncollectionsshow12,false);

            }



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
                ArrayList<String> buttoncollectionsshow21=new ArrayList<String>(Arrays.asList("txtlimboneagain","txtlimbleadtwo"));
                hideAndSeek(buttoncollectionsshow21,false);

            }

            else if(again&&x==2) {
                leadIndex =0;
                x=0;
                again=false;
                showDynamicimage("gif_lead2");
                showDynamicDescription("ecginfo");
                hideAndSeek(buttoncollectionshide,true);
                ArrayList<String> buttoncollectionsshow22=new ArrayList<String>(Arrays.asList("txtlimboneagain","txtlimbleadtwo"));
                hideAndSeek(buttoncollectionsshow22,false);

            }


        }
        super.onResume();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Intent intent = new Intent(LimbSixLead.this, EcgOptionsActivity.class);
                startActivity(intent);
                finish();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(LimbSixLead.this, EcgOptionsActivity.class);
        startActivity(intent);

    }


//    public String getJsonStringfromFile(String jsonFileName, String resources, String packagename) {
//
//        InputStream is = getResources().openRawResource(getResourceId(jsonFileName,resources,packagename));
//        Writer writer = new StringWriter();
//        char[] buffer = new char[1024];
//        try {
//            Reader reader;
//            reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
//            int n;
//            while ((n = reader.read(buffer)) != -1) {
//                writer.write(buffer, 0, n);
//            }
//
//            is.close();
//        }
//        catch (Exception e){
//
//
//
//        }
//
//
//        finally {
//
//        }
//
//        String jsonString = writer.toString();
//        return  jsonString;
//    }
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
    protected void onDestroy() {
        Log.d("destroy","seek n destroy");
        super.onDestroy();

    }
}
