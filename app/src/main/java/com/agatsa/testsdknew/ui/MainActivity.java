package com.agatsa.testsdknew.ui;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.agatsa.sanketlife.callbacks.CreateReviewCallback;
import com.agatsa.sanketlife.callbacks.LongPdfCallBack;
import com.agatsa.sanketlife.callbacks.PdfCallback;
import com.agatsa.sanketlife.callbacks.RegisterDeviceResponse;
import com.agatsa.sanketlife.callbacks.ReportStatusCallback;
import com.agatsa.sanketlife.callbacks.ResponseCallback;
import com.agatsa.sanketlife.callbacks.SaveEcgCallBack;
import com.agatsa.sanketlife.callbacks.SaveLongEcgCallBack;
import com.agatsa.sanketlife.development.EcgConfig;
import com.agatsa.sanketlife.development.Errors;
import com.agatsa.sanketlife.development.InitiateEcg;
import com.agatsa.sanketlife.development.InitiateReview;
import com.agatsa.sanketlife.development.LongEcgConfig;
import com.agatsa.sanketlife.development.ReviewedReport;
import com.agatsa.sanketlife.development.Success;
import com.agatsa.sanketlife.development.UserDetails;
import com.agatsa.sanketlife.models.EcgTypes;
import com.agatsa.testsdknew.R;
import com.ontbee.legacyforks.cn.pedant.SweetAlert.SweetAlertDialog;

import org.json.JSONObject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;

public class MainActivity extends AppCompatActivity {

    private static final String CLIENT_ID = "5a3b4c16b4a56b000132f5d5b4580266565440bda51dcb4122d39844";
    private static final String SECRET_ID = "5a3b4c16b4a56b000132f5d5746be305d56c49e49cc88b12ccb05d71";
    private static final int TIME_MAX = 15;
    String filePath;
    SweetAlertDialog pDialog;
    UserDetails userDetails;
    Socket socket;
    private Button btnRegisterDevice, btnTakeSingleLeadECG, btnCreatePDF, btnCreateLongPDF, btnLongECG,
            btnLeadOne, btnLeadTwo, btnV1, btnV2, btnV3, btnV4, btnV5, btnV6, btnCreateLongPDF12Lead,
            btnHistory, btnHistoryStress, btnInitiateReview, btnTake12Lead;
    private Context mContext;
    private int leadCount = 1;
    private EditText editTextTime;
    int REQUEST_CODE_ASK_PERMISSIONS=101;
    private final String[] REQUIRED_PERMISSIONS = new String[]{"android.permission.ACCESS_COARSE_LOCATION",
            "android.permission.WRITE_EXTERNAL_STORAGE","android.permission.ACCESS_FINE_LOCATION"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityCompat.requestPermissions(this, REQUIRED_PERMISSIONS, REQUEST_CODE_ASK_PERMISSIONS);
        setContentView(R.layout.activity_main_new_ui);
        mContext = getApplicationContext();
        userDetails = new UserDetails("Vikas", "24", "Male");


        getLocation();
        initViews();
        initOnClickListener();
    }

    private void initViews() {
        btnRegisterDevice = findViewById(R.id.btnRegisterDevice);
        btnCreateLongPDF = findViewById(R.id.btnCreateLongPDF);
        btnInitiateReview = findViewById(R.id.btnInitiateReview);
        btnLongECG = findViewById(R.id.btnLongECG);
        btnLeadOne = findViewById(R.id.btnLeadOne);
        btnLeadTwo = findViewById(R.id.btnLeadTwo);

        btnV1 = findViewById(R.id.btnV1);
        btnV2 = findViewById(R.id.btnV2);
        btnV3 = findViewById(R.id.btnV3);
        btnV4 = findViewById(R.id.btnV4);
        btnV5 = findViewById(R.id.btnV5);
        btnV6 = findViewById(R.id.btnV6);
        btnCreateLongPDF12Lead = findViewById(R.id.btnCreateLongPDF12Lead);
        btnTake12Lead = findViewById(R.id.btnTake12Lead);
        btnHistory = findViewById(R.id.btnHistory);
        btnHistoryStress = findViewById(R.id.btnHistoryStress);
        editTextTime = findViewById(R.id.editTextTime);
    }

    private void initOnClickListener() {
        btnRegisterDevice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerDevice();
            }
        });

        btnCreateLongPDF.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveLongEcg();
//                generateLongECGReportAndSave();
            }
        });

        btnLongECG.setOnClickListener(v -> {
            hideSoftKeyboard();
            if (!(editTextTime.getText().toString().trim().isEmpty())) {
                try {
                    final int count = Integer.parseInt(editTextTime.getText().toString());
                    if (count < 10) {
                        editTextTime.setError("Long ECG Time should be greater than 10");
                    } else if (count > 60) {
                        editTextTime.setError("Long ECG Time should be less than 60");
                    } else {
                        editTextTime.setError(null);
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                getlongReadingForECG(1, count);
                            }
                        }, 2000);

                    }
                } catch (Exception e) {
                    editTextTime.setError("Please enter a number only");
                }
            } else {
                editTextTime.setError("Long ECG Time should not be empty");
            }
        });

        btnLeadOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getReadingForECG(1);
            }
        });

        btnLeadTwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getReadingForECG(8);
            }
        });

        btnV1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getReadingForECG(2);
            }
        });

        btnV2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getReadingForECG(3);
            }
        });

        btnV3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getReadingForECG(4);
            }
        });

        btnV4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getReadingForECG(5);
            }
        });

        btnV5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getReadingForECG(6);
            }
        });

        btnV6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getReadingForECG(7);
            }
        });

        btnCreateLongPDF12Lead.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createPDF();
            }
        });

        btnTake12Lead.setOnClickListener(view -> {
            InitiateEcg initiateEcg = new InitiateEcg();
            initiateEcg.takeEcgWithSwitchSy(getApplicationContext(), SECRET_ID, new ResponseCallback() {
                @Override
                public void onError(Errors errors) {
                    Log.e("Reading Failure:", errors.getErrorMsg());
                }

                @Override
                public void onSuccess(Success success) {
                    Log.e("Reading Success:", success.getSuccessMsg());
                    Toast.makeText(mContext, success.getSuccessMsg(), Toast.LENGTH_SHORT).show();
                }
            });
        });

        btnHistory.setOnClickListener(view -> {
            Intent intent = new Intent(getApplicationContext(), HistoryActivity.class);
            intent.putExtra("type", EcgTypes.NORMAL_ECG);
            startActivity(intent);
        });

        btnHistoryStress.setOnClickListener(view -> {
            Intent intent = new Intent(getApplicationContext(), HistoryActivity.class);
            intent.putExtra("type", EcgTypes.LONG_ECG);
            startActivity(intent);
        });

        btnInitiateReview.setOnClickListener(view -> {
            InitiateReview initiateReview = new InitiateReview();
            initiateReview.sendForDoctorReview(mContext, SECRET_ID, userDetails, filePath, "",false,  new CreateReviewCallback() {
                @Override
                public void onProgress(float progress) {
                    // Just in case you need for progressing
                    Log.e("progress", String.valueOf(progress));
                }

                @Override
                public void onError(Errors errors) {
                    Log.e("onError", errors.getErrorMsg());
                    Log.e("Error Code", String.valueOf(errors.getErrorCode()));
                }

                @Override
                public void onCompleted(Success success, int reportID) {
                    Log.e("onCompleted: ReportID ", String.valueOf(reportID));

                    // create socket connection

                    try {
                        socket = IO.socket("http://socket.agatsa.com");
//                            socket = IO.socket("http://192.168.0.35:9090");
                        socket.on(Socket.EVENT_CONNECT, new Emitter.Listener() {

                            @Override
                            public void call(Object... args) {
                                try {
                                    JSONObject jsonObject = new JSONObject();
                                    jsonObject.put("uniqueId", SECRET_ID);

                                    socket.emit("join", jsonObject);
                                } catch (Exception e) {

                                }
                            }

                        }).on("new_msg", args -> {
                            Log.e(" reportID json", String.valueOf(args));
                            int message = 245259;
                            try {
                                JSONObject jsonObject = new JSONObject(args[0].toString());
                                message = jsonObject.getInt("msg");
                            } catch (Exception e) {

                            }
                            // check if report id exists in your db, show it
                            InitiateReview initiateReview1 = new InitiateReview();
                            initiateReview1.getReviewReport(mContext, SECRET_ID, message, new ReportStatusCallback() {
                                @Override
                                public void onReportReviewed(Success success1, ReviewedReport reviewedReport) {
                                    Log.e(" Successfull review ", success1.getSuccessMsg());
                                    Log.e(" reviewReport ", reviewedReport.getWaveform());
                                }

                                @Override
                                public void onError(Errors errors) {
                                    Log.e("error ", errors.getErrorMsg());
                                }
                            });
                        }).on(Socket.EVENT_DISCONNECT, args -> {

                        });

                        socket.connect();
                    } catch (Exception e) {

                    }
                }

                @Override
                public void onCancelled(Errors errors) {
                    Log.e("onCancelled: ", errors.getErrorMsg());
                }
            });
        });

        pDialog = new SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE);
        pDialog.getProgressHelper().setBarColor(ContextCompat.getColor(getApplicationContext(), R.color.colorAccent));
        pDialog.setTitleText("Registering");
        pDialog.setCancelable(false);
    }

    public void registerDevice() {
        pDialog.show();
        InitiateEcg initiateEcg = new InitiateEcg();
        initiateEcg.registerDevice(mContext, CLIENT_ID, new RegisterDeviceResponse() {
            @Override
            public void onSuccess(String s) {
                Log.e("On Success: ", s);
                Toast.makeText(mContext, getString(R.string.msg_device_registration_successfull) + " " + s,
                        Toast.LENGTH_LONG).show();
                pDialog.dismiss();
            }

            @Override
            public void onError(Errors errors) {
                Log.e("On Error:", errors.getErrorMsg());
                Toast.makeText(mContext, errors.getErrorMsg(), Toast.LENGTH_LONG).show();
                pDialog.dismiss();
            }
        });
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
                new Handler().postDelayed(() -> generateLongECGReportAndSave(), 2000);
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
                Toast.makeText(mContext, success.getSuccessMsg(), Toast.LENGTH_SHORT).show();
                makePDF(ecgConfig);
            }

            @Override
            public void onError(Errors error) {
                Toast.makeText(mContext, error.getErrorMsg(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void makePDF(EcgConfig ecgConfig) {
        InitiateEcg initiateEcg = new InitiateEcg();
        initiateEcg.makeEcgReport(mContext, userDetails, true, SECRET_ID, ecgConfig, new PdfCallback() {
            @Override
            public void onPdfAvailable(EcgConfig ecgConfig) {
                Log.e("path", ecgConfig.getFileUrl());
                filePath = ecgConfig.getFileUrl();
                Intent intent = new Intent(MainActivity.this, PdfViewActivity.class);
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
        final InitiateEcg initiateEcg = new InitiateEcg();
        initiateEcg.makeLongEcgReport(mContext, userDetails, true, SECRET_ID, longEcgConfig, new LongPdfCallBack() {
            @Override
            public void onPdfAvailable(LongEcgConfig longEcgConfig) {
                Log.e("path", longEcgConfig.getFileUrl());
                filePath = longEcgConfig.getFileUrl();
                Intent intent = new Intent(MainActivity.this, PdfViewActivity.class);
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
        initiateEcg.saveLongEcgData(MainActivity.this, "test", new SaveLongEcgCallBack() {
            @Override
            public void onSuccess(Success sucess, LongEcgConfig longEcgConfig) {
                Log.e("Generate Report fn", "onSuccess: " + "saved data");
                UserDetails userDetails = new UserDetails("", "", "");

                initiateEcg.makeLongEcgReport(MainActivity.this, userDetails, false, SECRET_ID, longEcgConfig, new LongPdfCallBack() {
                    @Override
                    public void onPdfAvailable(LongEcgConfig longEcgConfig) {
                        Log.e("Generate Report fn", "on PDF Availanble ");
                        pDialog.dismiss();
                        String ecgResult = longEcgConfig.getFinding();
//                        ecgSanketTestComplete(longEcgConfig.getFileUrl(), ecgResult);
                        if (longEcgConfig.isSynced()) {
                            Toast.makeText(MainActivity.this, "Synced", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(MainActivity.this, "Not Synced", Toast.LENGTH_SHORT).show();
                        }
                        Log.e("path", longEcgConfig.getFileUrl());
                        filePath = longEcgConfig.getFileUrl();
                        Intent intent = new Intent(MainActivity.this, PdfViewActivity.class);
                        intent.putExtra("fileUrl", filePath);
                        startActivity(intent);
                    }

                    @Override
                    public void onError(Errors errors) {
                        Log.e("Generate Report fn", "on Error of PDF " + errors.getErrorMsg());
                        pDialog.dismiss();
                        String errorMsg = errors.getErrorMsg();
                        if (errorMsg.contains("Quota exceeded")) {
                            Toast.makeText(MainActivity.this, errorMsg, Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(MainActivity.this, errorMsg, Toast.LENGTH_SHORT).show();

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
                Toast.makeText(MainActivity.this, errorMsg, Toast.LENGTH_SHORT).show();

            }
        });
    }

    public void getLocation() {
        LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    Activity#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for Activity#requestPermissions for more details.
            return;
        }
        Location myLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        if (myLocation == null) {
            if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    Activity#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for Activity#requestPermissions for more details.
                return;
            }
            myLocation = locationManager.getLastKnownLocation(LocationManager.PASSIVE_PROVIDER);

        }







    }
}
