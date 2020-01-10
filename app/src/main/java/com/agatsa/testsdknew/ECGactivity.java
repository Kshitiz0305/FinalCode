package com.agatsa.testsdknew;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.agatsa.sanketlife.callbacks.ResponseCallback;
import com.agatsa.sanketlife.development.Errors;
import com.agatsa.sanketlife.development.InitiateEcg;
import com.agatsa.sanketlife.development.Success;

public class ECGactivity extends AppCompatActivity {
    Button l1,v1,v2,v3,v4,v5,v6,l2,pdf,smallPdf;
    InitiateEcg initiateEcg;
    String ecgType;
//    EcgConfigInternal ecgConfigInternalSaved;
    Context mContext;
    final String authkey= "5a3b4c16b4a56b000132f5d5a8d6b8982b144251a6af587f99f05555";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getApplicationContext();
        ecgType = getIntent().getStringExtra("ecgType");

        setContentView(R.layout.activity_ecgactivity);
        l1 = (Button) findViewById(R.id.l1);
        v1 = (Button) findViewById(R.id.v1);
        v2 = (Button) findViewById(R.id.v2);
        v3 = (Button) findViewById(R.id.v3);
        v4 = (Button) findViewById(R.id.v4);
        v5 = (Button) findViewById(R.id.v5);
        v6 = (Button) findViewById(R.id.v6);
        l2 = (Button) findViewById(R.id.l2);
        pdf = (Button) findViewById(R.id.pdf);
        smallPdf = (Button) findViewById(R.id.pdfs);

      /*  if(ecgType.equalsIgnoreCase("single")){
            v1.setVisibility(View.GONE);
            v2.setVisibility(View.GONE);
            v3.setVisibility(View.GONE);
            v4.setVisibility(View.GONE);
            v5.setVisibility(View.GONE);
            v6.setVisibility(View.GONE);
            l2.setVisibility(View.GONE);
        }else if(ecgType.equalsIgnoreCase("limb")){
            v1.setVisibility(View.GONE);
            v2.setVisibility(View.GONE);
            v3.setVisibility(View.GONE);
            v4.setVisibility(View.GONE);
            v5.setVisibility(View.GONE);
            v6.setVisibility(View.GONE);
        }else if(ecgType.equalsIgnoreCase("chest")){
            l2.setVisibility(View.GONE);
            l1.setVisibility(View.GONE);

        }*/
        //final String authkey= "5a3b4c16b4a56b000132f5d584b3f07939994bfabf6c1898b2299663";

        //final String authkey="";
        initiateEcg = new InitiateEcg();
        l1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /* initiateEcg.takeEcg(ECGactivity.this, authkey,1, new ResponseCallback() {

                    @Override
                    public void onError(Errors errors) {
                        Log.e("errors",errors.getErrorMsg());

                    }

                    @Override
                    public void onSuccess(Success success) {

                    }
                });*/
                initiateEcg.takeLongEcg(ECGactivity.this, authkey, 1, 35, new ResponseCallback() {
                    //         initiateEcg.takeEcg(ECGactivity.this,authkey ,1,new ResponseCallback() {
                    @Override
                    public void onError(Errors errors) {
                        Log.e("errors", errors.getErrorMsg());

                    }

                    @Override
                    public void onSuccess(Success success) {
                        Log.e("errors", success.getSuccessMsg());

                    }
                });
            }
        });
        v1.setOnClickListener(view -> {
            initiateEcg.takeEcg(ECGactivity.this, authkey, 2, new ResponseCallback() {

                @Override
                public void onError(Errors errors) {
                    Log.e("errors", errors.getErrorMsg());

                }

                @Override
                public void onSuccess(Success success) {

                }
            });
            /*initiateEcg.takeLongLead(ECGactivity.this,authkey ,2, 10,new ResponseCallback() {

                @Override
                public void onError(Errors errors) {
                    Log.e("errors",errors.getErrorMsg());

                }

                @Override
                public void onSuccess(Success success) {
                    Log.e("errors",success.getSuccessMsg());

                }
            });*/
        });
        v2.setOnClickListener(view -> {
            initiateEcg.takeEcg(ECGactivity.this, authkey, 3, new ResponseCallback() {

                @Override
                public void onError(Errors errors) {
                    Log.e("errors", errors.getErrorMsg());

                }

                @Override
                public void onSuccess(Success success) {

                }
            });
            /*initiateEcg.takeLongLead(ECGactivity.this,authkey ,3, 10,new ResponseCallback() {

                @Override
                public void onError(Errors errors) {
                    Log.e("errors",errors.getErrorMsg());

                }

                @Override
                public void onSuccess(Success success) {
                    Log.e("errors",success.getSuccessMsg());

                }
            });*/
        });
        v3.setOnClickListener(view -> {
            initiateEcg.takeEcg(ECGactivity.this, authkey, 4, new ResponseCallback() {

                @Override
                public void onError(Errors errors) {
                    Log.e("errors", errors.getErrorMsg());

                }

                @Override
                public void onSuccess(Success success) {

                }
            });
           /* initiateEcg.takeLongLead(ECGactivity.this,authkey ,4, 10,new ResponseCallback() {

                @Override
                public void onError(Errors errors) {
                    Log.e("errors",errors.getErrorMsg());

                }

                @Override
                public void onSuccess(Success success) {
                    Log.e("errors",success.getSuccessMsg());

                }
            });*/
        });
        v4.setOnClickListener(view -> {
            initiateEcg.takeEcg(ECGactivity.this, authkey, 5, new ResponseCallback() {

                @Override
                public void onError(Errors errors) {
                    Log.e("errors", errors.getErrorMsg());

                }

                @Override
                public void onSuccess(Success success) {

                }
            });
            /*initiateEcg.takeLongLead(ECGactivity.this,authkey ,5, 10,new ResponseCallback() {

                @Override
                public void onError(Errors errors) {
                    Log.e("errors",errors.getErrorMsg());

                }

                @Override
                public void onSuccess(Success success) {
                    Log.e("errors",success.getSuccessMsg());

                }
            });*/
        });
        v5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                initiateEcg.takeEcg(ECGactivity.this, authkey, 6, new ResponseCallback() {

                    @Override
                    public void onError(Errors errors) {
                        Log.e("errors", errors.getErrorMsg());

                    }

                    @Override
                    public void onSuccess(Success success) {

                    }
                });
                /*initiateEcg.takeLongLead(ECGactivity.this,authkey ,6, 10,new ResponseCallback() {

                    @Override
                    public void onError(Errors errors) {
                        Log.e("errors",errors.getErrorMsg());

                    }

                    @Override
                    public void onSuccess(Success success) {
                        Log.e("errors",success.getSuccessMsg());

                    }
                });*/
            }
        });
        v6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                initiateEcg.takeEcg(ECGactivity.this, authkey, 7, new ResponseCallback() {

                    @Override
                    public void onError(Errors errors) {
                        Log.e("errors", errors.getErrorMsg());

                    }

                    @Override
                    public void onSuccess(Success success) {

                    }
                });
               /* initiateEcg.takeLongLead(ECGactivity.this,authkey ,7, 10,new ResponseCallback() {

                    @Override
                    public void onError(Errors errors) {
                        Log.e("errors",errors.getErrorMsg());

                    }

                    @Override
                    public void onSuccess(Success success) {
                        Log.e("errors",success.getSuccessMsg());

                    }
                });*/
            }
        });
        l2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                initiateEcg.takeEcg(ECGactivity.this, authkey, 8, new ResponseCallback() {

                    @Override
                    public void onError(Errors errors) {
                        Log.e("errors", errors.getErrorMsg());

                    }

                    @Override
                    public void onSuccess(Success success) {

                    }
                });
                /*initiateEcg.takeLongLead(ECGactivity.this,authkey ,8, 10,new ResponseCallback() {

                    @Override
                    public void onError(Errors errors) {
                        Log.e("errors",errors.getErrorMsg());

                    }

                    @Override
                    public void onSuccess(Success success) {
                        Log.e("errors",success.getSuccessMsg());

                    }
                });*/
            }
        });
        /*pdf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UserDetails userDetails=new UserDetails("abc","45","Male");
                initiateEcg.makeLongEcgReport(ECGactivity.this, userDetails, true, authkey, new LongPdfCallBack() {
                    @Override
                    public void onPdfAvailable(LongEcgConfigInternal ecgConfig) {
                        Log.e("path", ecgConfig.getFileUrl());
            //            Log.e("Hr", String.valueOf(ecgConfig.getHeartRate()));
                        Intent intent = new Intent(ECGactivity.this, PdfViewActivity.class);
                        intent.putExtra("fileUrl", ecgConfig.getFileUrl());
                        startActivity(intent);
                        // config.getHeartRate();
                        // config.getFinding();                    }
                    }
                    @Override
                    public void onError(Errors errors) {
                        Log.e("path", errors.getErrorMsg());
                    }
                });


               *//* initiateEcg.makeEcgReport(ECGactivity.this, userDetails,true, authkey,new PdfCallback() {


                    @Override
                    public void onPdfAvailable(String path, EcgConfigInternal config) {
                        Log.e("path",path);
                        Intent intent = new Intent(ECGactivity.this, PdfViewActivity.class);
                        intent.putExtra("fileUrl", path);
                        config.getHeartRate();
                        config.getFinding();


                        startActivity(intent);
                    }

                    @Override
                    public void onError(Errors errors) {
                        Log.e(""+errors.getErrorCode(),errors.getErrorMsg());

                    }
                });
*//*


            }
        });
        smallPdf.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                initiateEcg.saveEcgData(mContext, "346", new SaveEcgCallBack() {
                    @Override
                    public void onSuccess(Success success, EcgConfigInternal ecgConfigInternal) {
                        Toast.makeText(mContext,success.getSuccessMsg(),Toast.LENGTH_SHORT).show();
//                        ecgConfigInternalSaved = ecgConfigInternal;
                        createPDF(ecgConfigInternal);
                    }

                    @Override
                    public void onError(Errors error) {
                        Toast.makeText(mContext,"Problem Saving Data",Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    public void createPDF(EcgConfigInternal ecgConfigInternal){
        initiateEcg.makeEcgReport(mContext, new UserDetails("Vikas", "24", "Male"), true, authkey, ecgConfigInternal, new PdfCallback() {
            @Override
            public void onPdfAvailable(EcgConfigInternal ecgConfigInternal) {
                Log.e("path", ecgConfigInternal.getFileUrl());
                Intent intent = new Intent(ECGactivity.this, PdfViewActivity.class);
                intent.putExtra("fileUrl", ecgConfigInternal.getFileUrl());
                startActivity(intent);
            }

            @Override
            public void onError(Errors errors) {
                Toast.makeText(mContext, errors.getErrorMsg(), Toast.LENGTH_SHORT).show();
            }
        });
    }*/
    }
}
