//package com.agatsa.testsdknew;
//
//import android.bluetooth.BluetoothAdapter;
//import android.bluetooth.BluetoothDevice;
//import android.bluetooth.BluetoothSocket;
//import android.content.Intent;
//import android.os.Bundle;
//import android.os.Handler;
//import android.util.Log;
//import android.view.View;
//import android.widget.AdapterView;
//import android.widget.Button;
//import android.widget.EditText;
//import android.widget.Spinner;
//import android.widget.Toast;
//
//import androidx.annotation.Nullable;
////import androidx.appcompat.app.AppCompatActivity;
//
//
//import java.io.IOException;
//import java.io.InputStream;
//import java.io.OutputStream;
//import java.lang.reflect.Method;
//import java.util.Calendar;
//import java.util.Set;
//import java.util.UUID;
//
//public class PrintReportActivity extends AppCompatActivity {
//
//    EditText name;
//    EditText address;
//    EditText age;
//    EditText height;
//    EditText weight;
//    EditText bmi;
//    EditText temp;
//    EditText pulse;
//    EditText sto;
//    EditText pr;
//    EditText qrs;
//    EditText qt;
//    EditText qtc;
//    EditText sdnn;
//    EditText mrr;
//    EditText rmssd;
//
//    Spinner sex;
//    String sexvalue;
//
//    Button submitbtn;
//    BluetoothAdapter bluetoothAdapter;
//    BluetoothSocket socket;
//    BluetoothDevice bluetoothDevice;
//    OutputStream outputStream;
//    InputStream inputStream;
//    Thread workerThread;
//    byte[] readBuffer;
//    int readBufferPosition;
//    volatile boolean stopWorker;
//    String value = "";
//    boolean isPrintClicked = false;
//    int prvalue;
//    int qrsvalue;
//    int qtvalue;
//    int qtcvalue;
//    double sdnnnvalue;
//    double mrrvalue;
//    double rmssdvalue;
//
//    @Override
//    protected void onCreate(@Nullable Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_print_report);
//        name=findViewById(R.id.name);
//        address=findViewById(R.id.address);
//        age=findViewById(R.id.age);
//        sex=findViewById(R.id.spinnersex);
//        height=findViewById(R.id.height);
//        weight=findViewById(R.id.weight);
//        bmi=findViewById(R.id.bmi);
//        temp=findViewById(R.id.temprature);
//        pulse=findViewById(R.id.pulse);
//        sto=findViewById(R.id.sto);
//        submitbtn=findViewById(R.id.submitbtn);
//
//        sex.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
//                sexvalue=sex.getSelectedItem().toString();
//
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> adapterView) {
//
//            }
//        });
//
//        submitbtn.setOnClickListener(view -> {
//            if (!isPrintClicked) {
//                value = "";
//                isPrintClicked = true;
//                PrintThisReport();
//            }
//
//
//        });
//
//
//
//
//    }
//
//    int InitPrinter(){
//        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
//        try
//        {
//            if(!bluetoothAdapter.isEnabled())
//            {
//                Intent enableBluetooth = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
//                startActivityForResult(enableBluetooth, 0);
//                return 0;
//            }
//
//            Set<BluetoothDevice> pairedDevices = bluetoothAdapter.getBondedDevices();
//
//            if(pairedDevices.size() > 0)
//            {
//                for(BluetoothDevice device : pairedDevices)
//                {
//                    if(device.getName().equals("MTP-II")) //Note, you will need to change this to match the name of your device
//                    {
//                        bluetoothDevice = device;
//                        break;
//                    }
//                }
//
//                UUID uuid = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB"); //Standard SerialPortService ID
//                Method m = bluetoothDevice.getClass().getMethod("createRfcommSocket", new Class[]{int.class});
//                socket = (BluetoothSocket) m.invoke(bluetoothDevice, 1);
//                bluetoothAdapter.cancelDiscovery();
//                socket.connect();
//                outputStream = socket.getOutputStream();
//                inputStream = socket.getInputStream();
//                beginListenForData();
//            }
//            else
//            {
//                value+="No Devices found";
//                Toast.makeText(this, value, Toast.LENGTH_LONG).show();
//                return 0;
//            }
//        }
//        catch(Exception ex)
//        {
//            value+=ex.toString()+ "\n" +" InitPrinter \n";
//            Toast.makeText(this, value, Toast.LENGTH_LONG).show();
//            return 0;
//        }
//        return 1;
//    }
//    void beginListenForData(){
//        try {
//            final Handler handler = new Handler();
//
//            // this is the ASCII code for a newline character
//            final byte delimiter = 10;
//
//            stopWorker = false;
//            readBufferPosition = 0;
//            readBuffer = new byte[1024];
//
//            workerThread = new Thread(new Runnable() {
//                public void run() {
//
//                    while (!Thread.currentThread().isInterrupted() && !stopWorker) {
//
//                        try {
//
//                            int bytesAvailable = inputStream.available();
//
//                            if (bytesAvailable > 0) {
//
//                                byte[] packetBytes = new byte[bytesAvailable];
//                                inputStream.read(packetBytes);
//
//                                for (int i = 0; i < bytesAvailable; i++) {
//
//                                    byte b = packetBytes[i];
//                                    if (b == delimiter) {
//
//                                        byte[] encodedBytes = new byte[readBufferPosition];
//                                        System.arraycopy(
//                                                readBuffer, 0,
//                                                encodedBytes, 0,
//                                                encodedBytes.length
//                                        );
//
//                                        // specify US-ASCII encoding
//                                        final String data = new String(encodedBytes, "US-ASCII");
//                                        readBufferPosition = 0;
//
//                                        // tell the user data were sent to bluetooth printer device
//                                        handler.post(new Runnable() {
//                                            public void run() {
//                                                Log.d("e", data);
//                                            }
//                                        });
//
//                                    } else {
//                                        readBuffer[readBufferPosition++] = b;
//                                    }
//                                }
//                            }
//
//                        } catch (IOException ex) {
//                            stopWorker = true;
//                        }
//
//                    }
//                }
//            });
//
//            workerThread.start();
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    void PrintThisReport(){
//        try{
//            byte[] format = {29, 33, 35 }; // manipulate your font size in the second parameter
//            byte[] center =  { 0x1b, 'a', 0x01 }; // center alignmen
//            byte[] left=new byte[]{0x1B, 'a',0x00};
//            byte[] textSize = new byte[]{0x1B,0x21,0x00}; // 2- bold with medium text
//            // Start Printer
//            int received_val = InitPrinter();
//            if (received_val == 0) {
//                isPrintClicked = false;
//                return;
//            }
//            outputStream.write(center);
//            outputStream.write(format);
//            String reporttoprint= "";
//            reporttoprint = getString(R.string.company_name).toUpperCase();
//            reporttoprint +="\n";
//            outputStream.write(textSize);
//            outputStream.write(reporttoprint.getBytes());
//            outputStream.write(left);
//            outputStream.write("Patient Detail\n".getBytes());
//            outputStream.write("--------------\n".getBytes());
//            String ptLine ="";
//            ptLine += name.getText();
//            ptLine += "\n";
//            outputStream.write(ptLine.getBytes());
//            outputStream.write(("Address : " + address.getText() + "\n").getBytes());
//            outputStream.write(("Age/Sex : " + age.getText()+sexvalue + "\n").getBytes());
//            outputStream.write("\n".getBytes());
//            outputStream.write("Vital Sign\n".getBytes());
//            outputStream.write("----------\n".getBytes());
//            outputStream.write(("Weight :" + weight.getText() + "\n").getBytes());
//            outputStream.write(("Height :" + height.getText()+ "\n").getBytes());
//            outputStream.write(("BMI:" + bmi.getText()+ "\n").getBytes());
//            outputStream.write(("Temp :" + temp.getText() + " | Pulse :" + pulse.getText() + " | STO2 : " + sto.getText() +  "\n").getBytes());
////            outputStream.write(("BP :" + txtBP.getText() + "\n").getBytes());
//            outputStream.write("\n".getBytes());
//            outputStream.write("ECG Report\n".getBytes());
//            outputStream.write("------------\n".getBytes());
//            outputStream.write(("PR :" + pr.getText() + "\n").getBytes());
//            outputStream.write(("QRS :" + qrs.getText() + "\n").getBytes());
//            outputStream.write(("QT :" + qt.getText() + "\n").getBytes());
//            outputStream.write(("QTC :" + qtc.getText() + "\n").getBytes());
//            outputStream.write(("SDNN :" + sdnn.getText() + "\n").getBytes());
//            outputStream.write(("MRR :" + mrr.getText() + "\n").getBytes());
//            outputStream.write(("RMSSD :" + rmssd.getText() + "\n").getBytes());
//            outputStream.write("\n".getBytes());
//
////            outputStream.write("\n".getBytes());
////            String ReportSummary = txtReport.getText().toString();
////            ReportSummary = ReportSummary.replace("\n","\n");
////            outputStream.write(txtReport.getText().toString().getBytes());
//            outputStream.write("\n".getBytes());
//            outputStream.write(("Printed Date " + Calendar.getInstance().getTime()).getBytes());
//            outputStream.write("\n\n\n".getBytes());
//            // End Of Report
//            outputStream.close();
//            socket.close();
//        }catch (Exception e){
//            value+=e.toString()+ "\n" +"Excep Print \n";
//            Toast.makeText(this, value, Toast.LENGTH_LONG).show();
//        }
//        isPrintClicked = false;
//    }
//
//    @Override
//    protected void onResume() {
//        super.onResume();
//        isPrintClicked = false;
//    }
//
//
//
//}
