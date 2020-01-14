package com.agatsa.testsdknew.ui;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
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
import com.agatsa.testsdknew.Models.LongECGReport;
import com.agatsa.testsdknew.R;
import com.ontbee.legacyforks.cn.pedant.SweetAlert.SweetAlertDialog;

public class NewMainActivity extends AppCompatActivity {

    private static final String CLIENT_ID = "5a3b4c16b4a56b000132f5d5b4580266565440bda51dcb4122d39844";
    private static final String SECRET_ID = "5a3b4c16b4a56b000132f5d5746be305d56c49e49cc88b12ccb05d71";
    private Button btnCreateLongPDF12Lead,btnHistory,btnCreateLongPDF;

//    btnRegisterDevice, btnTakeSingleLeadECG, btnCreatePDF, btnCreateLongPDF , btnLongECG
//            ,  btnLeadTwo, btnV1, btnV2,btnV3,btnV4,btnV5,btnV6 ,btnHistoryStress

    TextView LeadOne,btnLongECG, btnLeadTwo;
    private Context mContext;
    private EditText editTextTime;
    String ptno = "";
    SharedPreferences pref;

    ECGReport ecgReport=new ECGReport();
    SweetAlertDialog pDialog;

    Toolbar toolbar;
    String heartratevalue,longecgheartvalue;
    LongECGReport longECGReport=new LongECGReport();







    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_main);
        pref = this.getSharedPreferences("sunyahealth", Context.MODE_PRIVATE);
        ptno = pref.getString("PTNO", "");
//        labdb = new LabDB(getApplicationContext());
//        ecgReport=labdb.getLastEcgSign(ptno);
        mContext = getApplicationContext();
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("ECG");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        initViews();
        initOnClickListener();
    }


    private void initViews() {
//        btnRegisterDevice = findViewById(R.id.btnRegisterDevice);
        btnCreateLongPDF = findViewById(R.id.btnCreateLongPDF);
        btnLongECG = findViewById(R.id.btnLongECG);
        LeadOne = findViewById(R.id.LeadOne);
        btnLeadTwo = findViewById(R.id.btnLeadTwo);
//
//        btnV1 = findViewById(R.id.btnV1);
//        btnV2 = findViewById(R.id.btnV2);
//        btnV3 = findViewById(R.id.btnV3);
//        btnV4 = findViewById(R.id.btnV4);
//        btnV5 = findViewById(R.id.btnV5);
//        btnV6 = findViewById(R.id.btnV6);
        btnCreateLongPDF12Lead = findViewById(R.id.btnCreateLongPDF12Lead);
        btnHistory = findViewById(R.id.btnHistory);
//        btnHistoryStress = findViewById(R.id.btnHistoryStress);
        editTextTime = findViewById(R.id.editTextTime);




    }

    private void initOnClickListener() {
//        btnRegisterDevice.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                registerDevice();
//            }
//        });

        btnCreateLongPDF.setOnClickListener(v -> {
            saveLongEcg();

        });

        btnLongECG.setOnClickListener(v -> {
            hideSoftKeyboard();
            new Handler().postDelayed(() -> getlongReadingForECG(1, 60), 2000);
        });

        LeadOne.setOnClickListener(v -> getReadingForECG(1));

//        patientdetail.setOnClickListener(view -> {
//            Intent i = new Intent(NewMainActivity.this, PrintReportActivity.class);
//            i.putExtra("type", EcgTypes.NORMAL_ECG);
//            startActivity(i);
//
//        });

        btnLeadTwo.setOnClickListener(v -> getReadingForECG(8));
//
//        btnV1.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                getReadingForECG(2);
//            }
//        });
//
//        btnV2.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                getReadingForECG(3);
//            }
//        });
//
//        btnV3.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                getReadingForECG(4);
//            }
//        });
//
//        btnV4.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                getReadingForECG(5);
//            }
//        });
//
//        btnV5.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                getReadingForECG(6);
//            }
//        });
//
//        btnV6.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                getReadingForECG(7);
//            }
//        });

        btnCreateLongPDF12Lead.setOnClickListener(v -> createPDF());

        btnHistory.setOnClickListener(view -> {
            Intent intent = new Intent(getApplicationContext(), HistoryActivity.class);
            intent.putExtra("type", EcgTypes.NORMAL_ECG);
            startActivity(intent);
        });


//        btnHistoryStress.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(getApplicationContext(), HistoryActivity.class);
//                intent.putExtra("type", EcgTypes.LONG_ECG);
//                startActivity(intent);
//            }
//        });

        pDialog = new SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE);
        pDialog.getProgressHelper().setBarColor(ContextCompat.getColor(getApplicationContext(), R.color.colorAccent));
        pDialog.setTitleText("Registering");
        pDialog.setCancelable(false);
    }

//    public void registerDevice() {
//        pDialog.show();
//        InitiateEcg initiateEcg = new InitiateEcg();
//        initiateEcg.registerDevice(mContext, CLIENT_ID, new RegisterDeviceResponse() {
//            @Override
//            public void onSuccess(String s) {
//                Log.e("On Success: ", s);
//                Toast.makeText(mContext, getString(R.string.msg_device_registration_successfull) + " " + s,
//                        Toast.LENGTH_LONG).show();
//                pDialog.dismiss();
//            }
//
//            @Override
//            public void onError(Errors errors) {
//                Log.e("On Error:", errors.getErrorMsg());
//                Toast.makeText(mContext, errors.getErrorMsg(), Toast.LENGTH_LONG).show();
//                pDialog.dismiss();
//            }
//        });
//    }

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
                Intent intent = new Intent(NewMainActivity.this, PdfViewActivity.class);
                intent.putExtra("fileUrl", filePath);
                startActivity(intent);

            }

            @Override
            public void onError(Errors errors) {
                Toast.makeText(mContext, errors.getErrorMsg(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void saveLongEcg() {
        InitiateEcg initiateEcg = new InitiateEcg();
        initiateEcg.saveLongEcgData(mContext, "test", new SaveLongEcgCallBack() {
            @Override
            public void onSuccess(Success success, LongEcgConfig longEcgConfig) {
                heartratevalue=String.valueOf(longEcgConfig.getHeartRate());
                LabDB db = new LabDB(getApplicationContext());
                longECGReport.setPt_no(ptno);
                longECGReport.setHeartrate(longEcgConfig.getHeartRate());
                longECGReport.setPr((longEcgConfig.getpR()));
                longECGReport.setQt(longEcgConfig.getqT());
                longECGReport.setQtc(longEcgConfig.getqTc());
                longECGReport.setQrs(longEcgConfig.getqRs());
                longECGReport.setSdnn(longEcgConfig.getSdnn());
                longECGReport.setRmssd(longEcgConfig.getRmssd());
                longECGReport.setMrr(longEcgConfig.getmRR());
                longECGReport.setFinding(longEcgConfig.getFinding());
                String last_long_ecgsign_row_id = db.SavelongleadECGSign(longECGReport);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                ecgReport.setRow_id(last_long_ecgsign_row_id);
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
                Intent intent = new Intent(NewMainActivity.this, PdfViewActivity.class);
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

    /**
     * Function used by @Drinn in order to generate the ECG
     */
    private void generateLongECGReportAndSave() {
        pDialog.setTitleText("Generating Report");
        pDialog.show();
        final InitiateEcg initiateEcg = new InitiateEcg();
        initiateEcg.saveLongEcgData(NewMainActivity.this, "test", new SaveLongEcgCallBack() {
            @Override
            public void onSuccess(Success sucess, LongEcgConfig longEcgConfig) {
                Log.e("Generate Report fn", "onSuccess: " + "saved data");
                UserDetails userDetails = new UserDetails("Vikas", "24", "Male");

                initiateEcg.makeLongEcgReport(NewMainActivity.this, userDetails, false, SECRET_ID, longEcgConfig, new LongPdfCallBack() {
                    @Override
                    public void onPdfAvailable(LongEcgConfig longEcgConfig) {
                        Log.e("Generate Report fn", "on PDF Availanble ");
                        pDialog.dismiss();
                        Log.e("path", longEcgConfig.getFileUrl());
                        String filePath = longEcgConfig.getFileUrl();
                        Intent intent = new Intent(NewMainActivity.this, PdfViewActivity.class);
                        intent.putExtra("fileUrl", filePath);
                        startActivity(intent);
                    }

                    @Override
                    public void onError(Errors errors) {
                        Log.e("Generate Report fn", "on Error of PDF " + errors.getErrorMsg());
                        pDialog.dismiss();
                        String errorMsg = errors.getErrorMsg();
                        if (errorMsg.contains("Quota exceeded")) {
                            Toast.makeText(NewMainActivity.this, errorMsg, Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(NewMainActivity.this, errorMsg, Toast.LENGTH_SHORT).show();

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
                Toast.makeText(NewMainActivity.this, errorMsg, Toast.LENGTH_SHORT).show();

            }
        });
    }



}
