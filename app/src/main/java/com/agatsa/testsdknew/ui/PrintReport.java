package com.agatsa.testsdknew.ui;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


import androidx.appcompat.app.AppCompatActivity;

import com.agatsa.testsdknew.Models.BloodReport;
import com.agatsa.testsdknew.Models.ECGReport;
import com.agatsa.testsdknew.Models.GlucoseModel;
import com.agatsa.testsdknew.Models.LongECGReport;
import com.agatsa.testsdknew.Models.PatientModel;
import com.agatsa.testsdknew.Models.UrineReport;
import com.agatsa.testsdknew.Models.VitalSign;
import com.agatsa.testsdknew.R;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Set;
import java.util.UUID;

public class PrintReport extends AppCompatActivity {
    String pt_no = "";
    PatientModel patientModel=new PatientModel();
    VitalSign vitalSign;
    BloodReport bloodReport;
    UrineReport urineReport;
    LongECGReport longECGReport;
    ECGReport ecgReport;
    TextView txtPtNo,txtPtName,txtPtAddress,txtPtContactNo,txtEmail,txtSex;
    TextView txtAge,txtWeight,txtHeight,txtTemp,txtPulse,txtBP,txtSTO2;
    TextView txtChlorestrol,txtBloodGlucose,txtUricAcid;
    TextView txtLeuko,txtNitrate,txtURB,txtProtein,txtPH;
    TextView txtBlood,txtSG,txtKet,txtBili,txtUrineGlucose,txtASC;
    TextView txtBMI;
    TextView txtBGNormalRange,txtTCNormalRange,txtUANormalRange;
    LabDB db;

    GlucoseModel glucoseModel;


    TextView heartrate,pr,qt,qtc,qrs,sdnn,rmssd,mrr,finding,txtglucose;
    TextView longheartrate,longpr,longqt,longqtc,longqrs,longsdnn,longrmssd,longmrr,longfinding;
    TextView txtdiabetes;

    ArrayList<String> keys = new  ArrayList<>(Arrays.asList("SLF","CSLF","LISLF","TLF","LSLF"));

    TextView txtReport;
    Button printreport;

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
        patientModel = getIntent().getParcelableExtra("patient");
//        if (pt_no == null) pt_no = "";
        if (pt_no.equals("")) {
            Toast.makeText(getApplicationContext(), "No Patient Selected", Toast.LENGTH_SHORT).show();
            return;
        }
        // Patient Details
//        txtPtNo = findViewById(R.id.txtPatientNo);
        txtPtName = findViewById(R.id.txtPatientName);
        txtAge = findViewById(R.id.txtAge);
        txtPtAddress=findViewById(R.id.txtAddress);
//        txtmaritalstatus=findViewById(R.id.txtmaritalstatus);
//        txtSex = findViewById(R.id.txtSex);
        // Vital Signs
        txtTemp = findViewById(R.id.txtTemp);
        txtWeight = findViewById(R.id.txtWeight);
        txtHeight = findViewById(R.id.txtHeight);
        txtPulse = findViewById(R.id.txtPulse);
        txtBP = findViewById(R.id.txtBP);
        txtSTO2 = findViewById(R.id.txtSto2);
        txtBMI = findViewById(R.id.txtBMI);
//        txtglucose=findViewById(R.id.txtglucose);

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




        // Blood Test
        txtChlorestrol = findViewById(R.id.txtCholesterol);
        txtBloodGlucose = findViewById(R.id.txtBloodGlucose);
        txtUricAcid = findViewById(R.id.txtUricAcid);
        txtTCNormalRange = findViewById(R.id.txtTCNormalRange);
        txtBGNormalRange = findViewById(R.id.txtBGNormalRange);
        txtUANormalRange = findViewById(R.id.txtUANormalRange);
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


        // Retrive From Database
         db = new LabDB(getApplicationContext());
        initViews();

        // ***************
//        patientModel = db.getPatient(pt_no);
        //System.out.println("Patient Sex " + patientModel.getPtSex());
//         vitalSign = db.getLastVitalSign(pt_no);

//        ecgReport=db.getSingleLeadEcgSign(pt_no);
//        bloodReport = db.getLastBloodReport(pt_no);
//        longECGReport=db.getlongLeadEcgSign(pt_no);
//        //*****************
//       urineReport = db.getLastUrineReport(pt_no);

       glucoseModel=db.getDiabetesSign(pt_no);

        // Set Text
        // Patient Details
//        txtPtName.setText(patientModel.getPtName());
//        Log.d("address",patientModel.getPtAddress());
//        Log.d("name",patientModel.getPtName());
//
//        String address=patientModel.getPtAddress();
//
//        txtPtAddress.setText(address);
//        String AgeSex = patientModel.getPtAge();
//        AgeSex += " years/" + patientModel.getPtSex();
//        txtAge.setText(AgeSex);
////        txtmaritalstatus.setText(patientModel.getPtmaritalstatus());
////        txtSex.setText(patientModel.getPtSex());
//        // Fillup Vital Sign
//        txtTemp.setText(String.valueOf(vitalSign.getTempt()));
//        txtWeight.setText(vitalSign.getWeight() + " Kg");
//        txtHeight.setText(vitalSign.getHeight() + " feet");
//        txtPulse.setText( String.valueOf(vitalSign.getPulse()));
//        txtSTO2.setText(String.valueOf(vitalSign.getSto2()));
//
//        // For BMI
//        double heightinmeter = (Double.parseDouble(vitalSign.getHeight())* 0.0254);
//        double m2 = heightinmeter * heightinmeter;
//        double weight =Double.parseDouble(vitalSign.getWeight()) ;
//        double bmi = weight / m2;
//        String BMI = String.format("%.2f", bmi);
//        if (bmi < 18.5) {
//            BMI += "(Under Weight Range)";
//        } else if (bmi < 24.9) {
//            BMI += "(Healthy Weight Range)";
//        } else if (bmi < 29.9) {
//            BMI += "(Over Weight Range)";
//        } else if (bmi < 39.9) {
//            BMI += "(Obese Range)";
//        }
//        txtBMI.setText(BMI);
//        String bp = String.valueOf(vitalSign.getBps());
//        bp = bp + "/";
//        bp = bp + String.valueOf(vitalSign.getBpd());
//        txtBP.setText(bp);
//        txtSTO2.setText(String.valueOf(vitalSign.getSto2()));
//
//
//        pr.setText(String.valueOf(ecgReport.getPr()));
//        heartrate.setText(String.valueOf(ecgReport.getHeartrate()));
//        qt.setText(String.valueOf(ecgReport.getQt()));
//        qtc.setText(String.valueOf(ecgReport.getQtc()));
//        qrs.setText(String.valueOf(ecgReport.getQrs()));
//        sdnn.setText(String.valueOf(ecgReport.getSdnn()));
//        rmssd.setText(String.valueOf(ecgReport.getRmssd()));
//        mrr.setText(String.valueOf(ecgReport.getMrr()));
//        finding.setText(ecgReport.getFinding());

        // Fillup Long ECG Report
//        longpr.setText(String.valueOf(longECGReport.getPr()));
//        longheartrate.setText(String.valueOf(longECGReport.getHeartrate()));
//        longqt.setText(String.valueOf(longECGReport.getQt()));
//        longqtc.setText(String.valueOf(longECGReport.getQtc()));
//        longqrs.setText(String.valueOf(longECGReport.getQrs()));
//        longsdnn.setText(String.valueOf(longECGReport.getSdnn()));
//        longrmssd.setText(String.valueOf(longECGReport.getRmssd()));
//        longmrr.setText(String.valueOf(longECGReport.getMrr()));
//        longfinding.setText(longECGReport.getFinding());




        // Fillup Blood Report
//        txtBloodGlucose.setText(String.valueOf(bloodReport.getGlucose()) + "mg/dl");
//        if(bloodReport.getGlucose()>160){
//            txtBloodGlucose.setText(txtBloodGlucose.getText() + " HIGH");
//        }
//        String BGNormalRange = "80-160mg/dl";
//        txtBGNormalRange.setText(BGNormalRange.toString());
//        String UANR;
//        if (patientModel.getPtSex().equals("Female")) {
//            UANR = "2.4-6.0mg/dl";
//        } else {
//            UANR = "3.4-7.0mg/dl";
//        }
//        txtUANormalRange.setText(UANR.toString());
//        txtTCNormalRange.setText("100-129mg/dl");
//        txtUricAcid.setText(String.valueOf(bloodReport.getUric_acid()));
//        txtChlorestrol.setText(String.valueOf(bloodReport.getChlorestrol()));
//
        // Fillup Urine Report
//        txtLeuko.setText((urineReport.getLeuko()));
//        txtNitrate.setText((urineReport.getNit()));
//        txtURB.setText((urineReport.getUrb()));
//        txtProtein.setText((urineReport.getProtein()));
//        txtPH.setText((urineReport.getPh()));
//        txtBlood.setText((urineReport.getBlood()));
//        txtSG.setText((urineReport.getSg()));
//        txtKet.setText((urineReport.getKet()));
//        txtBili.setText((urineReport.getBili()));
//        txtUrineGlucose.setText((urineReport.getGlucose()));
//        txtASC.setText((urineReport.getAsc()));


         //   Diabetes report

        txtdiabetes.setText(glucoseModel.getPtGlucose());

//        if(urineReport.getLeuko()<1){
//            txtLeuko.setText("Nil");
//        }else if(urineReport.getLeuko()<16){
//            txtLeuko.setText("Trace");
//        }else if(urineReport.getLeuko()<71){
//            txtLeuko.setText("Small ");
//        }else if(urineReport.getLeuko()<126){
//            txtLeuko.setText("Modrate");
//        }else if(urineReport.getLeuko()<501){
//            txtLeuko.setText("Large");
//        }
//        String Report = "Your Report Summary : \n";
//        int toreport = 0;
//        if (urineReport.getLeuko() > 16) {
//            toreport++;
//            Report += txtLeuko.getText() +  " Leukocyte Detected in urine \n";
//        }

//        txtNitrate.setText(String.valueOf(urineReport.getNit()));
////        if(urineReport.getNit()<1){
////            txtNitrate.setText("Negative");
////        }else if (urineReport.getNit() < 2) {
////            toreport++;
////            Report += "Small amount of Nitrates detected in urine \n";
////        }else if(urineReport.getNit()>2){
////            toreport++;
////            Report += "Nitrates detected in urine \n";
////        }
////        if(urineReport.getUrb()<2){
////            txtURB.setText("Normal");
////        }else {
//            txtURB.setText(String.valueOf(urineReport.getUrb()));
////        }
////        if (urineReport.getUrb() > 1) {
////            Report += "Urobilirubin is Higher than Normal Range \n";
////        }
////        if(urineReport.getProtein()<1){
////            txtProtein.setText("Negative");
////        }else if(urineReport.getProtein()<16){
////            txtProtein.setText("Trace");
////        } else {
//            txtProtein.setText(String.valueOf(urineReport.getProtein()));
//        }
//        if (urineReport.getProtein() > 150) {
//            Report += "Protien is Higher than Normal Range \n";
//        }
//        txtPH.setText(String.valueOf(urineReport.getPh()));
//        if (urineReport.getPh() > 8) {
//            Report += "Your urine is more acidic than Normal Range \n";
//        }
//
//        if(urineReport.getBlood()<1){
//            txtBlood.setText("Nil");
//        }else if(urineReport.getBili()<2){
//            txtBlood.setText("Few Dark Flecks");
//        }else if(urineReport.getBlood()<3){
//            txtBlood.setText("Many Dark Flecks");
//        }else if(urineReport.getBlood()<4){
//            txtBlood.setText("Trace");
//        }else if(urineReport.getBlood()<5){
//            txtBlood.setText("Small+");
//        }else if(urineReport.getBlood()>=5){
//            txtBlood.setText("Large+++");
//        }
//        else {
//            txtBlood.setText(String.valueOf(urineReport.getBlood()));
//        }
//        if (urineReport.getBlood() > 4) {
//            Report += "Blood seen on urine \n";
//        }
//
//        txtSG.setText(String.valueOf(urineReport.getSg()));
//        if (urineReport.getSg() > 1.025) {
//            Report += "Specific Gravity is more than Normal Range \n";
//        }
//        if(urineReport.getKet()<1){
//            txtKet.setText("Negative");
//        }else if(urineReport.getKet()<6){
//            txtKet.setText("Trace");
//        }else if(urineReport.getKet()<16){
//            txtKet.setText("Small (15)");
//        }else if(urineReport.getKet()<41){
//            txtKet.setText("Moderate (40)");
//        }
//        else {
//            txtKet.setText(String.valueOf(urineReport.getKet()));
//        }
//        if (urineReport.getKet() > 15) {
//            Report += "Ketones are present on your urine \n";
//        }
//
//        if(urineReport.getBili()<1){
//            txtBili.setText("Negative");
//        }else {
//            txtBili.setText(String.valueOf(urineReport.getBili()));
//        }
//        if(urineReport.getBili()>1){
//            Report += "Bilirubin detected on your urine \n";
//        }
//        if(urineReport.getGlucose()==0.00){
//            txtUrineGlucose.setText("Nil");
//        }else {
//            txtUrineGlucose.setText(String.valueOf(urineReport.getGlucose()));
//        }
//        if (urineReport.getGlucose() > 0) {
//            Report += "Glucose is present on your urine \n";
//        }
//        if(urineReport.getAsc()==0.00){
//            txtASC.setText("Nil");
//        }else {
//            txtASC.setText(String.valueOf(urineReport.getAsc()));
//        }
//        txtReport.setText(Report);
        printreport.setOnClickListener(v -> {
            if (!isPrintClicked) {
                value = "";
                isPrintClicked = true;
                PrintThisReport();
                navigatenext();
            }
        });

    }

    private void navigatenext() {
        Intent i = new Intent(PrintReport.this, PatientActivity.class);
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

            workerThread = new Thread(new Runnable() {
                public void run() {

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
                                        handler.post(new Runnable() {
                                            public void run() {
                                                Log.d("e", data);
                                            }
                                        });

                                    } else {
                                        readBuffer[readBufferPosition++] = b;
                                    }
                                }
                            }

                        } catch (IOException ex) {
                            stopWorker = true;
                        }

                    }
                }
            });

            workerThread.start();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void initViews(){


        for (String s : keys){

           if(pref.getInt(s,0)==1){

               switch (s){


//
//                   "SLF","CSLF","LISLF","TLF","LSLF"
                   case "SLF":
//                        this is to be done in asynctask and view loading is to be done in post execution
                       ecgReport=    db.getSingleLeadEcgSign(pt_no);
                       if(ecgReport!=null) {


                           heartrate.setText(String.valueOf(ecgReport.getHeartrate()));



                       }
                       }
                       break;



// slf ko view milaune




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
            outputStream.write("Patient Detail\n".getBytes());
            outputStream.write("---------------------------\n".getBytes());
            String ptLine ="Patient Name :";
            ptLine += String.valueOf(patientModel.getPtNo());
            ptLine += ", ";
            ptLine += patientModel.getPtName();
            ptLine += "\n";
            outputStream.write(ptLine.getBytes());
            outputStream.write(("Address : " + patientModel.getPtAddress() + "\n").getBytes());
            outputStream.write(("Age/Sex : " + txtAge.getText() + "\n").getBytes());
            outputStream.write("\n".getBytes());

            outputStream.write("---------------------------\n".getBytes());
            outputStream.write("Vital Sign\n".getBytes());
            outputStream.write("-----------------------------\n".getBytes());
            outputStream.write(("Weight :" + vitalSign.getWeight() + "\n").getBytes());
            outputStream.write(("Height :" + vitalSign.getHeight() + "\n").getBytes());
            outputStream.write(("BMI:" + txtBMI.getText() + "\n").getBytes());
            outputStream.write(("Temp :" + txtTemp.getText() + " | Pulse :" + txtPulse.getText() + " | STO2 : " + txtSTO2.getText() +  "\n").getBytes());
            outputStream.write(("BP :" + txtBP.getText() + "\n").getBytes());
            outputStream.write(("GLUCOSE :" + txtglucose.getText() + "\n").getBytes());
            outputStream.write("\n".getBytes());
            outputStream.write("---------------------------\n".getBytes());
            outputStream.write("Single Lead Ecg Report\n".getBytes());
            outputStream.write("------------------------------\n".getBytes());
            outputStream.write(("HEARTRATE :" + ecgReport.getHeartrate() + "\n").getBytes());
            outputStream.write(("PR :" + ecgReport.getPr() + "\n").getBytes());
            outputStream.write(("QT :" + ecgReport.getQt() + "\n").getBytes());
            outputStream.write(("QTC:" + ecgReport.getQtc() + "\n").getBytes());
            outputStream.write(("QRS :" + ecgReport.getQrs() + "\n").getBytes());
            outputStream.write(("SDNN :" + ecgReport.getSdnn() + "\n").getBytes());
            outputStream.write(("RMSSD :" + ecgReport.getRmssd() + "\n").getBytes());
            outputStream.write(("MRR :" + ecgReport.getMrr() + "\n").getBytes());
            outputStream.write(("FINDING :" + ecgReport.getFinding() + "\n").getBytes());
            outputStream.write("------------------------------\n".getBytes());
            outputStream.write("\n".getBytes());
            outputStream.write(" Fitness Ecg Report\n".getBytes());
            outputStream.write("------------------------------\n".getBytes());
            outputStream.write(("HEARTRATE :" + longECGReport.getHeartrate() + "\n").getBytes());
            outputStream.write(("PR :" + longECGReport.getPr() + "\n").getBytes());
            outputStream.write(("QT :" + longECGReport.getQt() + "\n").getBytes());
            outputStream.write(("QTC:" + longECGReport.getQtc() + "\n").getBytes());
            outputStream.write(("QRS :" + longECGReport.getQrs() + "\n").getBytes());
            outputStream.write(("SDNN :" + longECGReport.getSdnn() + "\n").getBytes());
            outputStream.write(("RMSSD :" + longECGReport.getRmssd() + "\n").getBytes());
            outputStream.write(("MRR :" + longECGReport.getMrr() + "\n").getBytes());
            outputStream.write(("FINDING :" + longECGReport.getFinding() + "\n").getBytes());
            outputStream.write("------------------------------\n".getBytes());
//            outputStream.write(("Glucose :" + txtBloodGlucose.getText() + "\n").getBytes());
//            outputStream.write("\n".getBytes());
//            outputStream.write("Urine Report\n".getBytes());
//            outputStream.write("------------\n".getBytes());

//            outputStream.write(("Leukocyte : " + txtLeuko.getText() + "\n").getBytes());
//            outputStream.write(("Nitrate : " + txtNitrate.getText() + "\n").getBytes());
//            outputStream.write(("Urobilinogen : " + txtURB.getText() + "\n").getBytes());
//            outputStream.write(("Protein : " + txtProtein.getText() + "\n").getBytes());
//            outputStream.write(("pH : " + txtPH.getText() + "\n").getBytes());
//            outputStream.write(("Blood : " + txtBlood.getText() + "\n").getBytes());
//            outputStream.write(("Sp. Gravity : " + txtSG.getText() + "\n").getBytes());
//            outputStream.write(("Ketone : " + txtKet.getText() + "\n").getBytes());
//            outputStream.write(("Bilirubin : " + txtBili.getText() + "\n").getBytes());
//            outputStream.write(("Glucose : " + txtUrineGlucose.getText() + "\n").getBytes());
//            outputStream.write(("Ascorbic Acid : " + txtASC.getText() + "\n").getBytes());

//            String ReportSummary = txtReport.getText().toString();
//            ReportSummary = ReportSummary.replace("\n","\n");
//            outputStream.write(txtReport.getText().toString().getBytes());
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
        isPrintClicked = false;
    }

    @Override
    protected void onResume() {
        super.onResume();
        isPrintClicked = false;
    }
}
