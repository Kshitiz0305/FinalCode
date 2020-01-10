package com.agatsa.testsdknew;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
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
import com.ontbee.legacyforks.cn.pedant.SweetAlert.SweetAlertDialog;

public class TwelveLeadEcg extends AppCompatActivity {

    private static final String CLIENT_ID = "5a3b4c16b4a56b000132f5d5b4580266565440bda51dcb4122d39844";
    private static final String SECRET_ID = "5a3b4c16b4a56b000132f5d5746be305d56c49e49cc88b12ccb05d71";
    private Button twelvebtnSavechestreport,twelvebtnViewpdf ;

    LinearLayout txttwelveleadone, txttwelveleadtwo,txttwelvevone,txttwelvevtwo,txttwelvevthree,txttwelvevfour,txttwelvevfive,txttwelvevsix;
    private Context mContext;
    int ptno = 0;
    SharedPreferences pref;
    SweetAlertDialog pDialog;
    Toolbar toolbar;





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


        initViews();
        initOnClickListener();
    }


    private void initViews() {

        txttwelveleadone = findViewById(R.id.txttwelveleadone);
        txttwelveleadtwo = findViewById(R.id.txttwelveleadtwo);
        txttwelvevone = findViewById(R.id.txttwelvevone);
        txttwelvevtwo = findViewById(R.id.txttwelvevtwo);
        txttwelvevthree = findViewById(R.id.txttwelvevthree);
        txttwelvevfour = findViewById(R.id.txttwelvevfour);
        txttwelvevfive = findViewById(R.id.txttwelvevfive);
        txttwelvevsix = findViewById(R.id.txttwelvevsix);
        twelvebtnSavechestreport = findViewById(R.id.twelvebtnSavechestreport);
        twelvebtnViewpdf = findViewById(R.id.twelvebtnViewpdf);






    }


    private void initOnClickListener() {

        txttwelvevone.setOnClickListener(v -> getReadingForECG(2));

        txttwelvevtwo.setOnClickListener(v -> getReadingForECG(3));

        txttwelvevthree.setOnClickListener(v -> getReadingForECG(4));

        txttwelvevfour.setOnClickListener(v -> getReadingForECG(5));

        txttwelvevfive.setOnClickListener(v -> getReadingForECG(6));

        txttwelvevsix.setOnClickListener(v -> getReadingForECG(7));
        txttwelveleadone.setOnClickListener(v -> getReadingForECG(1));
        txttwelveleadtwo.setOnClickListener(v -> getReadingForECG(8));

        twelvebtnSavechestreport.setOnClickListener(v -> createPDF());

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





}
