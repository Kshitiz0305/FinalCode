package com.agatsa.testsdknew.ui;

import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.agatsa.testsdknew.Models.BloodPressureModel;
import com.agatsa.testsdknew.Models.BloodReport;
import com.agatsa.testsdknew.Models.ECGReport;
import com.agatsa.testsdknew.Models.GlucoseModel;
import com.agatsa.testsdknew.Models.LongECGReport;
import com.agatsa.testsdknew.Models.PatientModel;
import com.agatsa.testsdknew.Models.TwoParameterUrineModel;
import com.agatsa.testsdknew.Models.UricAcidModel;
import com.agatsa.testsdknew.Models.UrineReport;
import com.agatsa.testsdknew.Models.VitalSign;
import com.agatsa.testsdknew.R;
import com.agatsa.testsdknew.customviews.DialogUtil;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Method;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Set;
import java.util.UUID;

public class PrintReport extends AppCompatActivity {
    private static final String TAG ="" ;
    String pt_no = "";
    PatientModel patientModel;
    VitalSign vitalSign;
    UrineReport urineReport;
    ECGReport ecgReport;
    TextView txtPtNo,txtPtName,txtPtAddress,txtPtContactNo,txtEmail,txtSex;
    TextView txtAge,txtWeight,txtHeight,txtTemp,txtPulse,txtBP,txtSTO2;
    TextView txtLeuko,txtNitrate,txtURB,txtProtein,txtPH;
    TextView txtBlood,txtSG,txtKet,txtBili,txtUrineGlucose,txtASC;
    TextView txtBMI;
    ProgressDialog dialog;
    LabDB labDB;

    GlucoseModel glucoseModel;
    BloodPressureModel bloodPressureModel;
    TwoParameterUrineModel twoParameterUrineModel;
    UricAcidModel uricAcidModel;

    double latestmealdate;
    TextView heartrate,pr,qt,qtc,qrs,sdnn,rmssd,mrr,finding;
    TextView longheartrate,longpr,longqt,longqtc,longqrs,longsdnn,longrmssd,longmrr,longfinding;
    TextView chestleadecgheartrate,chestleadecgpr,chestleadecgqt,chestleadecgqtc,chestleadecgqrs,chestleadecgsdnn,chestleadecgrmssd,chestleadecgmrr,chestleadecgfinding;
    TextView limbsixleadecgheartrate,limbsixleadecgpr,limbsixleadecgqt,limbsixleadecgqtc,limbsixleadecgqrs,limbsixleadecgsdnn,limbsixleadecgrmssd,limbsixleadecgmrr,limbsixleadecgfinding;
    TextView twelveleadecgheartrate,twelveleadecgpr,twelveleadecgqt,twelveleadecgqtc,twelveleadecgqrs,twelveleadecgsdnn,twelveleadecgrmssd,twelveleadecgmrr,twelveleadecgfinding;
    TextView txtdiabetes;
    TextView txturicacid;
    TextView txtmicroalbumincreatinen;

    ArrayList<String> keys = new  ArrayList<>(Arrays.asList("VTF","DF","SLF","CSLF","LISLF","TLF","LSLF","UTF","TPTF","UATF"));

    TextView txtReport;
    Button printreport,completeprintreport;
    static String AgeSexConcat;

    CardView vitalsigncv,diabetescv,urinereportcv,fitnessecgcv,singleleadecgcv,chestleadecgcv,limbsixleadecgcv,twelveleadecgcv,twoparametercv,uricacidcv;

    // For Print
    BluetoothAdapter bluetoothAdapter;
    BluetoothSocket socket;
    BluetoothDevice bluetoothDevice;
    OutputStream outputStream;
    InputStream inputStream;
    Thread workerThread;
    byte[] readBuffer;
    int readBufferPosition;

    volatile boolean stopWorker;

    String value = "",  duid = "", device_id = "";
    SharedPreferences pref;
    boolean isPrintClicked = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_print_report);
        pref = this.getSharedPreferences("sunyahealth", Context.MODE_PRIVATE);
        pt_no =pref.getString("PTNO","");
        dialog=new ProgressDialog(this);
        patientModel = getIntent().getParcelableExtra("patient");


//        if (pt_no == null) pt_no = "";
        if (pt_no.equals("")) {
            Toast.makeText(getApplicationContext(), "No Patient Selected", Toast.LENGTH_SHORT).show();
            return;
        }


        //Patient Model
        txtPtName = findViewById(R.id.txtPatientName);
        txtAge = findViewById(R.id.txtAge);
        txtPtAddress=findViewById(R.id.txtAddress);

        // Vital Signs
        txtTemp = findViewById(R.id.txtTemp);
        txtWeight = findViewById(R.id.txtWeight);
        txtHeight = findViewById(R.id.txtHeight);
        txtPulse = findViewById(R.id.txtPulse);
        txtBP = findViewById(R.id.txtBP);
        txtSTO2 = findViewById(R.id.txtSto2);
        txtBMI = findViewById(R.id.txtBMI);

        //ECG Report

        heartrate=findViewById(R.id.heartrate);
        pr=findViewById(R.id.pr);
        qt=findViewById(R.id.qt);
        qtc=findViewById(R.id.qtc);
        qrs=findViewById(R.id.qrs);
        sdnn=findViewById(R.id.sdnn);
        rmssd=findViewById(R.id.rmssd);
        mrr=findViewById(R.id.mrr);
        finding=findViewById(R.id.finding);


        //Chest Six Lead Report

        chestleadecgheartrate=findViewById(R.id.chestleadecgheartrate);
        chestleadecgpr=findViewById(R.id.chestleadecgpr);
        chestleadecgqt=findViewById(R.id.chestleadecgqt);
        chestleadecgqtc=findViewById(R.id.chestleadecgqtc);
        chestleadecgqrs=findViewById(R.id.chestleadecgqrs);
        chestleadecgsdnn=findViewById(R.id.chestleadecgsdnn);
        chestleadecgrmssd=findViewById(R.id.chestleadecgrmssd);
        chestleadecgmrr=findViewById(R.id.chestleadecgmrr);
        chestleadecgfinding=findViewById(R.id.chestleadecgfinding);


        //Limb Six Lead Report

        limbsixleadecgheartrate=findViewById(R.id.limbsixleadecgheartrate);
        limbsixleadecgpr=findViewById(R.id.limbsixleadecgpr);
        limbsixleadecgqt=findViewById(R.id.limbsixleadecgqt);
        limbsixleadecgqtc=findViewById(R.id.limbsixleadecgqtc);
        limbsixleadecgqrs=findViewById(R.id.limbsixleadecgqrs);
        limbsixleadecgsdnn=findViewById(R.id.limbsixleadecgsdnn);
        limbsixleadecgrmssd=findViewById(R.id.limbsixleadecgrmssd);
        limbsixleadecgmrr=findViewById(R.id.limbsixleadecgmrr);
        limbsixleadecgfinding=findViewById(R.id.limbsixleadecgfinding);


        //Twelve Lead Report
        twelveleadecgheartrate=findViewById(R.id.twelveleadecgheartrate);
        twelveleadecgpr=findViewById(R.id.twelveleadecgpr);
        twelveleadecgqt=findViewById(R.id.twelveleadecgqt);
        twelveleadecgqtc=findViewById(R.id.twelveleadecgqtc);
        twelveleadecgqrs=findViewById(R.id.twelveleadecgqrs);
        twelveleadecgsdnn=findViewById(R.id.twelveleadecgsdnn);
        twelveleadecgrmssd=findViewById(R.id.twelveleadecgrmssd);
        twelveleadecgmrr=findViewById(R.id.twelveleadecgmrr);
        twelveleadecgfinding=findViewById(R.id.twelveleadecgfinding);




        //Diabetes

        txtdiabetes=findViewById(R.id.txtglucose);

        // LONG ECG Report

        longheartrate=findViewById(R.id.longheartrate);
        longpr=findViewById(R.id.longpr);
        longqt=findViewById(R.id.longqt);
        longqtc=findViewById(R.id.longqtc);
        longqrs=findViewById(R.id.longqrs);
        longsdnn=findViewById(R.id.longsdnn);
        longrmssd=findViewById(R.id.longrmssd);
        longmrr=findViewById(R.id.longmrr);
        longfinding=findViewById(R.id.longfinding);


        // Card View
        vitalsigncv=findViewById(R.id.vitalsigncv);
        diabetescv=findViewById(R.id.diabetescv);
        urinereportcv=findViewById(R.id.urinereportcv);
        fitnessecgcv=findViewById(R.id.fitnessecgcv);
        chestleadecgcv=findViewById(R.id.chestleadecgcv);
        limbsixleadecgcv=findViewById(R.id.limbsixleadecgcv);
        twelveleadecgcv=findViewById(R.id.twelveleadecgcv);
        singleleadecgcv=findViewById(R.id.singleleadecgcv);
        twoparametercv=findViewById(R.id.twoparametercv);
        uricacidcv=findViewById(R.id.uricacidcv);

        // Urine Test
        txtLeuko = findViewById(R.id.txtleukocytes);
        txtNitrate = findViewById(R.id.txtNitrite);
        txtURB = findViewById(R.id.txturobilinogen);
        txtProtein = findViewById(R.id.txtprotein);
        txtPH = findViewById(R.id.txtPH);
        txtBlood = findViewById(R.id.txtblood);
        txtSG = findViewById(R.id.txtspecificgravity);
        txtKet = findViewById(R.id.txtKetones);
        txtBili = findViewById(R.id.txtbilirubin);
        txtUrineGlucose = findViewById(R.id.txturineGlucose);
        txtASC = findViewById(R.id.txtascorbicacid);

        txtReport = findViewById(R.id.txtReport);
        printreport = findViewById(R.id.btnSendDataToPrint);

        //Two Parameter Urine Test

        txtmicroalbumincreatinen=findViewById(R.id.txtmicroalbumincreatinen);


        //Uric Acid Test

        txturicacid=findViewById(R.id.txturicacid);


        // Retrive From Database
        labDB = new LabDB(getApplicationContext());
        initViews();

        completeprintreport=findViewById(R.id.completeprintreport);



        completeprintreport.setOnClickListener(v -> {
            DialogUtil.getOKCancelDialog(this, "", "Do you want to complete the  test of " + patientModel.getPtName(), "Yes","No", (dialogInterface, i) -> {
                pref.edit().putInt("VTF",0).apply();
                pref.edit().putInt("UTF",0).apply();
                pref.edit().putInt("DF",0).apply();
                pref.edit().putInt("SLF",0).apply();
                pref.edit().putInt("CSLF",0).apply();
                pref.edit().putInt("LISLF",0).apply();
                pref.edit().putInt("TLF",0).apply();
                pref.edit().putInt("LSLF",0).apply();
                pref.edit().putInt("TPTF",0).apply();
                pref.edit().putInt("UATF",0).apply();
                navigatenext();


            });

        });





        txtPtName.setText(patientModel.getPtName());
        String address=patientModel.getPtAddress() + "," + patientModel.getPtCity();

        txtPtAddress.setText(address);
        String AgeSex = patientModel.getPtAge();
        AgeSex += " years/" + patientModel.getPtSex();
        AgeSexConcat=AgeSex;
        txtAge.setText(AgeSex);

        printreport.setOnClickListener(v -> {
            if (!isPrintClicked) {
                value = "";
                isPrintClicked = true;
                PrintThisReport();
//


            }
        });

    }

    private void navigatenext() {
        Intent i = new Intent(PrintReport.this, PatientEntryActivity.class);
        startActivity(i);

    }

    int InitPrinter(){
        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        try
        {
            if(!bluetoothAdapter.isEnabled())
            {
                Intent enableBluetooth = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                startActivityForResult(enableBluetooth, 0);
                return 0;
            }

            Set<BluetoothDevice> pairedDevices = bluetoothAdapter.getBondedDevices();

            if(pairedDevices.size() > 0)
            {
                for(BluetoothDevice device : pairedDevices)
                {
                    if(device.getName().equals("MTP-II")) //Note, you will need to change this to match the name of your device
                    {
                        bluetoothDevice = device;
                        break;
                    }
                }

                UUID uuid = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB"); //Standard SerialPortService ID
                Method m = bluetoothDevice.getClass().getMethod("createRfcommSocket", new Class[]{int.class});
                socket = (BluetoothSocket) m.invoke(bluetoothDevice, 1);
                bluetoothAdapter.cancelDiscovery();
                socket.connect();
                outputStream = socket.getOutputStream();
                inputStream = socket.getInputStream();
                beginListenForData();
            }
            else
            {
                value+="No Devices found";
                Toast.makeText(this, value, Toast.LENGTH_LONG).show();
                return 0;
            }
        }
        catch(Exception ex)
        {
            value+=ex.toString()+ "\n" +" InitPrinter \n";
            Toast.makeText(this, value, Toast.LENGTH_LONG).show();
            return 0;
        }
        return 1;
    }
    void beginListenForData(){
        try {
            final Handler handler = new Handler();

            // this is the ASCII code for a newline character
            final byte delimiter = 10;

            stopWorker = false;
            readBufferPosition = 0;
            readBuffer = new byte[1024];

            workerThread = new Thread(() -> {

                while (!Thread.currentThread().isInterrupted() && !stopWorker) {

                    try {

                        int bytesAvailable = inputStream.available();

                        if (bytesAvailable > 0) {

                            byte[] packetBytes = new byte[bytesAvailable];
                            inputStream.read(packetBytes);

                            for (int i = 0; i < bytesAvailable; i++) {

                                byte b = packetBytes[i];
                                if (b == delimiter) {

                                    byte[] encodedBytes = new byte[readBufferPosition];
                                    System.arraycopy(
                                            readBuffer, 0,
                                            encodedBytes, 0,
                                            encodedBytes.length
                                    );

                                    // specify US-ASCII encoding
                                    final String data = new String(encodedBytes, "US-ASCII");
                                    readBufferPosition = 0;

                                    // tell the user data were sent to bluetooth printer device
                                    handler.post(() -> Log.d("e", data));

                                } else {
                                    readBuffer[readBufferPosition++] = b;
                                }
                            }
                        }

                    } catch (IOException ex) {
                        stopWorker = true;
                    }

                }
            });

            workerThread.start();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void initViews(){


        for (String s : keys) {

            if (pref.getInt(s, 0) == 1) {

                switch (s) {




                    case "SLF":
//                        this is to be done in asynctask and view loading is to be done in post execution
                        ecgReport = labDB.getSingleLeadEcgSign(pt_no, "SL");
                        if (ecgReport != null) {
                            singleleadecgcv.setVisibility(View.VISIBLE);
                            pr.setText(String.valueOf(ecgReport.getPr()));
                            heartrate.setText(String.valueOf(ecgReport.getHeartrate()));
                            qt.setText(String.valueOf(ecgReport.getQt()));
                            qtc.setText(String.valueOf(ecgReport.getQtc()));
                            qrs.setText(String.valueOf(ecgReport.getQrs()));
                            sdnn.setText(String.valueOf(ecgReport.getSdnn()));
                            rmssd.setText(String.valueOf(ecgReport.getRmssd()));
                            mrr.setText(String.valueOf(ecgReport.getMrr()));
                            finding.setText(ecgReport.getFinding());
                        }
                        break;

                    case "CSLF":
//                        this is to be done in asynctask and view loading is to be done in post execution
                        ecgReport = labDB.getSingleLeadEcgSign(pt_no, "CSL");
                        if (ecgReport != null) {

                            chestleadecgcv.setVisibility(View.VISIBLE);
                            chestleadecgpr.setText(String.valueOf(ecgReport.getPr()));
                            chestleadecgheartrate.setText(String.valueOf(ecgReport.getHeartrate()));
                            chestleadecgqt.setText(String.valueOf(ecgReport.getQt()));
                            chestleadecgqtc.setText(String.valueOf(ecgReport.getQtc()));
                            chestleadecgqrs.setText(String.valueOf(ecgReport.getQrs()));
                            chestleadecgsdnn.setText(String.valueOf(ecgReport.getSdnn()));
                            chestleadecgrmssd.setText(String.valueOf(ecgReport.getRmssd()));
                            chestleadecgmrr.setText(String.valueOf(ecgReport.getMrr()));
                            chestleadecgfinding.setText(ecgReport.getFinding());
                        }
                        break;


                    case "LISLF":
//                        this is to be done in asynctask and view loading is to be done in post execution
                        ecgReport = labDB.getSingleLeadEcgSign(pt_no, "LISL");
                        if (ecgReport != null) {

                            limbsixleadecgcv.setVisibility(View.VISIBLE);
                            limbsixleadecgpr.setText(String.valueOf(ecgReport.getPr()));
                            limbsixleadecgheartrate.setText(String.valueOf(ecgReport.getHeartrate()));
                            limbsixleadecgqt.setText(String.valueOf(ecgReport.getQt()));
                            limbsixleadecgqtc.setText(String.valueOf(ecgReport.getQtc()));
                            limbsixleadecgqrs.setText(String.valueOf(ecgReport.getQrs()));
                            limbsixleadecgsdnn.setText(String.valueOf(ecgReport.getSdnn()));
                            limbsixleadecgrmssd.setText(String.valueOf(ecgReport.getRmssd()));
                            limbsixleadecgmrr.setText(String.valueOf(ecgReport.getMrr()));
                            limbsixleadecgfinding.setText(ecgReport.getFinding());
                        }
                        break;

                    case "TLF":
//                        this is to be done in asynctask and view loading is to be done in post execution
                        ecgReport = labDB.getSingleLeadEcgSign(pt_no, "TL");
                        if (ecgReport != null) {

                            twelveleadecgcv.setVisibility(View.VISIBLE);
                            twelveleadecgpr.setText(String.valueOf(ecgReport.getPr()));
                            twelveleadecgheartrate.setText(String.valueOf(ecgReport.getHeartrate()));
                            twelveleadecgqt.setText(String.valueOf(ecgReport.getQt()));
                            twelveleadecgqtc.setText(String.valueOf(ecgReport.getQtc()));
                            twelveleadecgqrs.setText(String.valueOf(ecgReport.getQrs()));
                            twelveleadecgsdnn.setText(String.valueOf(ecgReport.getSdnn()));
                            twelveleadecgrmssd.setText(String.valueOf(ecgReport.getRmssd()));
                            twelveleadecgmrr.setText(String.valueOf(ecgReport.getMrr()));
                            twelveleadecgfinding.setText(ecgReport.getFinding());
                        }
                        break;

                    case "LSLF":
//                        this is to be done in asynctask and view loading is to be done in post execution
                        ecgReport = labDB.getSingleLeadEcgSign(pt_no, "LSL");
                        if (ecgReport != null) {

                            fitnessecgcv.setVisibility(View.VISIBLE);
                            longpr.setText(String.valueOf(ecgReport.getPr()));
                            longheartrate.setText(String.valueOf(ecgReport.getHeartrate()));
                            longqt.setText(String.valueOf(ecgReport.getQt()));
                            longqtc.setText(String.valueOf(ecgReport.getQtc()));
                            longqrs.setText(String.valueOf(ecgReport.getQrs()));
                            longsdnn.setText(String.valueOf(ecgReport.getSdnn()));
                            longrmssd.setText(String.valueOf(ecgReport.getRmssd()));
                            longmrr.setText(String.valueOf(ecgReport.getMrr()));
                            longfinding.setText(ecgReport.getFinding());
                        }
                        break;


                    case "VTF":
//                        this is to be done in asynctask and view loading is to be done in post executionv
                        vitalSign = labDB.getLastVitalSign(pt_no);
                        bloodPressureModel = labDB.getbloodpressuresign(pt_no);
                        if (vitalSign != null) {

                            vitalsigncv.setVisibility(View.VISIBLE);
                            txtWeight.setText(vitalSign.getWeight() + " Kg");
                            txtHeight.setText(vitalSign.getHeight() + " Inches");
                            txtTemp.setText(String.valueOf(vitalSign.getTempt()));
                            txtPulse.setText(String.valueOf(vitalSign.getPulse()));
                            txtBMI.setText(String.valueOf(vitalSign.getBmi()));
                            txtBP.setText(String.valueOf(bloodPressureModel.getSystolicdiastolic()));
                            txtSTO2.setText(String.valueOf(vitalSign.getSto2()));
                        }
                        break;

                    case "UTF":
//                        this is to be done in asynctask and view loading is to be done in post execution
                        urineReport = labDB.getLastUrineReport(pt_no);
                        if (urineReport != null) {

                            urinereportcv.setVisibility(View.VISIBLE);
                            txtLeuko.setText((urineReport.getLeuko()));
                            txtNitrate.setText((urineReport.getNit()));
                            txtURB.setText((urineReport.getUrb()));
                            txtProtein.setText((urineReport.getProtein()));
                            txtPH.setText((urineReport.getPh()));
                            txtBlood.setText((urineReport.getBlood()));
                            txtSG.setText((urineReport.getSg()));
                            txtKet.setText((urineReport.getKet()));
                            txtBili.setText((urineReport.getBili()));
                            txtUrineGlucose.setText((urineReport.getGlucose()));
                            txtASC.setText((urineReport.getAsc()));
                        }
                        break;

                    case "DF":
//
                        glucoseModel = labDB.getDiabetesSign(pt_no);
                        if (glucoseModel != null) {

                            diabetescv.setVisibility(View.VISIBLE);
//
                            String value=(glucoseModel.getPtGlucose()+"mg/dL");
                            String diabetestype=glucoseModel.getPttesttype();
                            String totalvalue=value+" ("+diabetestype+" )";
                            txtdiabetes.setText(totalvalue);


                        }
                        break;

                    case "TPTF" :
                        twoParameterUrineModel=labDB.gettwoparameterurinedata(pt_no);
                        if(twoParameterUrineModel!=null){
                            twoparametercv.setVisibility(View.VISIBLE);
                            txtmicroalbumincreatinen.setText(twoParameterUrineModel.getMicrocreat()+"mg/L");

                        }
                        break;



                    case "UATF" :
                        uricAcidModel=labDB.geturicaciddata(pt_no);
                        if(uricAcidModel!=null){
                            uricacidcv.setVisibility(View.VISIBLE);
                            txturicacid.setText(uricAcidModel.getAcid_level()+"mg/L");
                        }

                        break;






                }
            }

        }



    }




    void PrintThisReport(){


        try{
            byte[] format = {29, 33, 35 }; // manipulate your font size in the second parameter
            byte[] center =  { 0x1b, 'a', 0x01 }; // center alignmen
            byte[] left=new byte[]{0x1B, 'a',0x00};
            byte[] textSize = new byte[]{0x1B,0x21,0x00}; // 2- bold with medium text
            // Start Printer
            int received_val = InitPrinter();
            if (received_val == 0) {
                isPrintClicked = false;
                return;
            }
            outputStream.write(center);
            outputStream.write(format);
            String reporttoprint= "";
            reporttoprint = getString(R.string.app_name).toUpperCase();
            reporttoprint +="\n";
            outputStream.write(textSize);
            outputStream.write(reporttoprint.getBytes());
            outputStream.write(left);
            outputStream.write("Lalitpur Municipality Ward 3 \n\n".getBytes());
            outputStream.write("---------------------------\n".getBytes());
            outputStream.write("Patient Details\n".getBytes());
            outputStream.write("---------------------------\n".getBytes());
            String ptLine ="Patient Name :";
//            ptLine += String.valueOf(patientModel.getPtNo());
//            ptLine += ", ";
            ptLine += patientModel.getPtName();
            ptLine += "\n";
            outputStream.write(ptLine.getBytes());
            outputStream.write(("Address : " + patientModel.getPtAddress()  + "," + patientModel.getPtCity()+ "\n").getBytes());
            outputStream.write(("Age/Sex : " + AgeSexConcat  + "\n").getBytes());
            outputStream.write("\n".getBytes());


            for (String s : keys){

                if(pref.getInt(s,0)==1){

                    switch (s){
//                        "SLF","CSLF","LISLF","TLF","LSLF"
                        case "VTF":
//                        this is to be done in asynctask and view loading is to be done in post execution
//                            vitalSign = db.getLastVitalSign(pt_no);
                            outputStream.write("---------------------------\n".getBytes());
                            outputStream.write("Vital Sign\n".getBytes());
                            outputStream.write("-----------------------------\n".getBytes());
                            outputStream.write(("Weight :" +txtWeight.getText() + "\n").getBytes());
                            outputStream.write(("Height :" + txtHeight.getText() +"\n").getBytes());
                            outputStream.write(("BMI:" + txtBMI.getText() + "\n").getBytes());
                            outputStream.write(("Temp :" + txtTemp.getText() + "\n") .getBytes());
                            outputStream.write(("Pulse :" + txtPulse.getText() + "\n") .getBytes());
                            outputStream.write(("STO2 :" +txtSTO2.getText() + "\n") .getBytes());
                            outputStream.write(("BP :" + txtBP.getText() + "\n").getBytes());


                            break;


                        case "DF":
//

                            outputStream.write("-----------------------------\n".getBytes());
                            outputStream.write("Diabetes Test Sign\n".getBytes());
                            outputStream.write("-----------------------------\n".getBytes());
                            outputStream.write(("Glucose Level : " +txtdiabetes.getText() + "\n\n").getBytes());




                            break;
                        case "SLF":
//                        this is to be done in asynctask and view loading is to be done in post execution
//                            ecgReport=    db.getSingleLeadEcgSign(pt_no,"SL");

                            outputStream.write("-----------------------------\n".getBytes());
                            outputStream.write("Single Lead Ecg Sign\n".getBytes());
                            outputStream.write("-----------------------------\n".getBytes());
                            outputStream.write(("Pr :" + pr.getText() + "\n").getBytes());
                            outputStream.write(("Heartrate :" + heartrate.getText() + "\n").getBytes());
                            outputStream.write(("Qt:" + qt.getText() + "\n").getBytes());
                            outputStream.write(("Qtc :" + qtc.getText() + "\n") .getBytes());
                            outputStream.write(("Qrs :" + qrs.getText() + "\n") .getBytes());
                            outputStream.write(("Sdnn :" +sdnn.getText() + "\n") .getBytes());
                            outputStream.write(("Rmssd :" + rmssd.getText() + "\n").getBytes());
                            outputStream.write(("Mrr :" + mrr.getText() + "\n").getBytes());
                            outputStream.write(("Finding :" + finding.getText() + "\n").getBytes());



                            break;

                        case "CSLF":
//                        this is to be done in asynctask and view loading is to be done in post execution
//                            ecgReport=    db.getSingleLeadEcgSign(pt_no);

                            outputStream.write("-----------------------------\n".getBytes());
                            outputStream.write("Chest Six Lead Ecg Sign\n".getBytes());
                            outputStream.write("-----------------------------\n".getBytes());
                            outputStream.write(("Pr :" +chestleadecgpr.getText() + "\n").getBytes());
                            outputStream.write(("Heartrate :" + chestleadecgheartrate.getText() + "\n").getBytes());
                            outputStream.write(("Qt:" + chestleadecgqt.getText() + "\n").getBytes());
                            outputStream.write(("Qtc :" + chestleadecgqtc.getText() + "\n") .getBytes());
                            outputStream.write(("Qrs :" + chestleadecgqrs.getText() + "\n") .getBytes());
                            outputStream.write(("Sdnn :" +chestleadecgsdnn.getText() + "\n") .getBytes());
                            outputStream.write(("Rmssd :" + chestleadecgrmssd.getText() + "\n").getBytes());
                            outputStream.write(("Mrr :" + chestleadecgmrr.getText() + "\n").getBytes());
                            outputStream.write(("Finding :" + chestleadecgfinding.getText() + "\n").getBytes());



                            break;


                        case "LISLF":
//                        this is to be done in asynctask and view loading is to be done in post execution
//                            ecgReport=    db.getSingleLeadEcgSign(pt_no);
                            outputStream.write("-----------------------------\n".getBytes());
                            outputStream.write("Limb Six Lead Ecg Sign\n".getBytes());
                            outputStream.write("-----------------------------\n".getBytes());
                            outputStream.write(("Pr :" + limbsixleadecgpr.getText() + "\n").getBytes());
                            outputStream.write(("Heartrate :" + limbsixleadecgheartrate.getText() + "\n").getBytes());
                            outputStream.write(("Qt:" + limbsixleadecgqt.getText() + "\n").getBytes());
                            outputStream.write(("Qtc :" + limbsixleadecgqtc.getText() + "\n") .getBytes());
                            outputStream.write(("Qrs :" + limbsixleadecgqrs.getText() + "\n") .getBytes());
                            outputStream.write(("Sdnn :" +limbsixleadecgsdnn.getText() + "\n") .getBytes());
                            outputStream.write(("Rmssd :" + limbsixleadecgrmssd.getText() + "\n").getBytes());
                            outputStream.write(("Mrr :" +limbsixleadecgmrr.getText() + "\n").getBytes());
                            outputStream.write(("Finding :" + limbsixleadecgfinding.getText() + "\n").getBytes());


                            break;

                        case "TLF":
//                        this is to be done in asynctask and view loading is to be done in post execution
//                            ecgReport=    db.getSingleLeadEcgSign(pt_no);


                            outputStream.write("-----------------------------\n".getBytes());
                            outputStream.write("Twelve Lead Ecg Sign\n".getBytes());
                            outputStream.write("-----------------------------\n".getBytes());
                            outputStream.write(("Pr :" + twelveleadecgpr.getText() + "\n").getBytes());
                            outputStream.write(("Heartrate :" +twelveleadecgheartrate.getText() + "\n").getBytes());
                            outputStream.write(("Qt:" + twelveleadecgqt.getText() + "\n").getBytes());
                            outputStream.write(("Qtc :" + twelveleadecgqtc.getText() + "\n") .getBytes());
                            outputStream.write(("Qrs :" + twelveleadecgqrs.getText() + "\n") .getBytes());
                            outputStream.write(("Sdnn :" +twelveleadecgsdnn.getText() + "\n") .getBytes());
                            outputStream.write(("Rmssd :" +twelveleadecgrmssd.getText() + "\n").getBytes());
                            outputStream.write(("Mrr :" + twelveleadecgmrr.getText() + "\n").getBytes());
                            outputStream.write(("Finding :" + twelveleadecgfinding.getText() + "\n").getBytes());



                            break;

                        case "LSLF":
//                        this is to be done in asynctask and view loading is to be done in post execution
//                            ecgReport=    db.getSingleLeadEcgSign(pt_no);


                            outputStream.write("-----------------------------\n".getBytes());
                            outputStream.write("Long Lead Ecg Sign\n".getBytes());
                            outputStream.write("-----------------------------\n".getBytes());
                            outputStream.write(("Pr :" + longpr.getText() + "\n").getBytes());
                            outputStream.write(("Heartrate :" + longheartrate.getText() + "\n").getBytes());
                            outputStream.write(("Qt:" + longqt.getText() + "\n").getBytes());
                            outputStream.write(("Qtc :" + longqtc.getText() + "\n") .getBytes());
                            outputStream.write(("Qrs :" + longqrs.getText() + "\n") .getBytes());
                            outputStream.write(("Sdnn :" +longsdnn.getText() + "\n") .getBytes());
                            outputStream.write(("Rmssd :" + longrmssd.getText() + "\n").getBytes());
                            outputStream.write(("Mrr :" + longmrr.getText() + "\n").getBytes());
                            outputStream.write(("Finding :" + longfinding.getText() + "\n").getBytes());


                            break;





                        case "UTF":
//                        this is to be done in asynctask and view loading is to be done in post execution


                            outputStream.write("-----------------------------\n".getBytes());
                            outputStream.write("Urine Test Sign\n".getBytes());
                            outputStream.write("-----------------------------\n".getBytes());
                            outputStream.write(("Leukocytes :" +txtLeuko.getText() + "\n").getBytes());
                            outputStream.write(("Nitrite :" + txtNitrate.getText() + "\n").getBytes());
                            outputStream.write(("Urobilinogen:" + txtURB.getText()+ "\n").getBytes());
                            outputStream.write(("Protein :" + txtProtein.getText() + "\n") .getBytes());
                            outputStream.write(("pH :" + txtPH.getText() + "\n") .getBytes());
                            outputStream.write(("Blood :" + txtBlood.getText() + "\n") .getBytes());
                            outputStream.write(("Specific Gravity :" + txtSG.getText() + "\n").getBytes());
                            outputStream.write(("Ketones :" + txtKet.getText() + "\n").getBytes());
                            outputStream.write(("Bilirubin :" + txtBili.getText() + "\n").getBytes());
                            outputStream.write(("Glucose :" + txtUrineGlucose.getText() + "\n").getBytes());
                            outputStream.write(("Ascorbic acid :" + txtASC.getText() + "\n").getBytes());



                            break;

                        case "TPTF":
                            outputStream.write(("Microalbumin/Creatinine :" +txtmicroalbumincreatinen.getText() + "\n").getBytes());

                         break;

                        case "UATF":
                            outputStream.write(("Uric Acid Level :" +txturicacid.getText() + "\n").getBytes());


                    }

                }
            }

            outputStream.write("\n".getBytes());
            outputStream.write(("Printed Date " + Calendar.getInstance().getTime()).getBytes());
            outputStream.write("\n\n\n".getBytes());
            // End Of Report
            outputStream.close();
            socket.close();

        }catch (Exception e){
            value+=e.toString()+ "\n" +"Excep Print \n";
            Toast.makeText(this, value, Toast.LENGTH_LONG).show();
        }
//        pref.edit().putInt("VTF",0).apply();
//        pref.edit().putInt("UTF",0).apply();
//        pref.edit().putInt("DF",0).apply();
//        pref.edit().putInt("SLF",0).apply();
//        pref.edit().putInt("CSLF",0).apply();
//        pref.edit().putInt("LISLF",0).apply();
//        pref.edit().putInt("TLF",0).apply();
//        pref.edit().putInt("LSLF",0).apply();
        isPrintClicked = false;



    }

    @Override
    protected void onResume() {
        super.onResume();
        isPrintClicked = false;
    }

    @Override
    public void onBackPressed() {
        PrintReport.super.onBackPressed();
    }














}
