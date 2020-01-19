package com.agatsa.testsdknew.ui;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.MailTo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import com.agatsa.testsdknew.Models.PatientModel;
import com.agatsa.testsdknew.Models.UrineReport;
import com.agatsa.testsdknew.R;
import com.agatsa.testsdknew.customviews.DialogUtil;

import org.opencv.android.OpenCVLoader;
import org.opencv.android.Utils;
import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.MatOfInt;
import org.opencv.core.MatOfPoint;
import org.opencv.core.MatOfPoint2f;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.RotatedRect;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;


import static org.opencv.imgproc.Imgproc.CHAIN_APPROX_SIMPLE;
import static org.opencv.imgproc.Imgproc.RETR_EXTERNAL;


public  class CapturedImageActivity extends AppCompatActivity {
    String ptno = " ";
    SharedPreferences pref;
    PatientModel patientModel=new PatientModel();
    private AssetManager assetManager;
    LabDB labDB;
    UrineReport urineReport=new UrineReport();
    private ProgressDialog dialog;
    HashMap<Integer, String> test_names = new HashMap<Integer, String>(){
        {
            put(0, "Leukocytes");
            put(1, "Nitrite");
            put(2, "Urobilinogen");
            put(3, "Protein");
            put(4, "pH");
            put(5, "Blood");
            put(6, "Specific Gravity");
            put(7, "Ketones");
            put(8, "Bilirubin");
            put(9, "Glucose");
            put(10, "Ascorbic acid");
        }
    };

    HashMap<Integer, HashMap<String, String>> test_values = new HashMap<Integer, HashMap<String, String>>(){
        {
            put(0, new HashMap<String, String>(){
                {
                    put("0.png", "Negative");
                    put("1.png", "Trace");
                    put("2.png", "+70 WBC/μL");
                    put("3.png", "++125 WBC/μL");
                    put("4.png", "+++500 WBC/μL");
                }
            });
            put(1, new HashMap<String, String>(){
                {
                    put("0.png", "Negative");
                    put("1.png", "Trace");
                    put("2.png", "Positive");
                }
            });
            put(2, new HashMap<String, String>(){
                {
                    put("0.png", "0.1 (normal) mg/dl");
                    put("1.png", "1(16) (normal) mg/dl");
                    put("2.png", "2(33) mg/dl");
                    put("3.png", "4(66) mg/dl");
                    put("4.png", "8(131) mg/dl");
                }
            });
            put(3, new HashMap<String, String>(){
                {
                    put("0.png", "Negative");
                    put("1.png", "Trace");
                    put("2.png", "+30(0.3) mg/dl(g/L)");
                    put("3.png", "++100(1.0) mg/dl(g/L)");
                    put("4.png", "+++300(3.0) mg/dl(g/L)");
                    put("5.png", "++++1000(10) mg/dl(g/L)");
                }
            });
            put(4, new HashMap<String, String>(){
                {
                    put("0.png", "5");
                    put("1.png", "6");
                    put("2.png", "6.5");
                    put("3.png", "7");
                    put("4.png", "7.5");
                    put("5.png", "8");
                    put("6.png", "8.5");
                }
            });
            put(5, new HashMap<String, String>(){
                {
                    put("0.png", "Negative");
                    put("1.png", "Hemolysis Trace");
                    put("2.png", "+25 RBC/μL");
                    put("3.png", "++80 RBC/μL");
                    put("4.png", "+++200 RBC/μL");
                    put("5.png", "Non Hemolysis+10 RBC/μL");
                    put("6.png", "++80 RBC/μL");
                }
            });
            put(6, new HashMap<String, String>(){
                {
                    put("0.png", "1.000");
                    put("1.png", "1.005");
                    put("2.png", "1.010");
                    put("3.png", "1.015");
                    put("4.png", "1.020");
                    put("5.png", "1.025");
                    put("6.png", "1.030");
                }
            });
            put(7, new HashMap<String, String>(){
                {
                    put("0.png", "Negative");
                    put("1.png", "±5(0.5) mg/dl(mmol/L)");
                    put("2.png", "+15(1.5) mg/dl(mmol/L)");
                    put("3.png", "++40(3.9) mg/dl(mmol/L)");
                    put("4.png", "+++80(8) mg/dl(mmol/L)");
                    put("5.png", "++++160(16) mg/dl(mmol/L)");
                }
            });
            put(8, new HashMap<String, String>(){
                {
                    put("0.png", "Negative");
                    put("1.png", "+");
                    put("2.png", "++");
                    put("3.png", "+++");
                }
            });
            put(9, new HashMap<String, String>(){
                {
                    put("0.png", "Negative");
                    put("1.png", "±100(5.5) mg/dl(mmol/L)");
                    put("2.png", "+250(14) mg/dl(mmol/L)");
                    put("3.png", "++500(28) mg/dl(mmol/L)");
                    put("4.png", "+++1000(55) mg/dl(mmol/L)");
                    put("5.png", "++++2000(111) mg/dl(mmol/L)");
                }
            });
            put(10, new HashMap<String, String>(){
                {
                    put("0.png", "Negative");
                    put("1.png", "+20(1.2) mg/dl(mmol/L)");
                    put("2.png", "++40(2.4) mg/dl(mmol/L)");
                }
            });
        }
    };

    static final int REQUEST_IMAGE_CAPTURE = 1;
    private final static int REQUEST_CODE_ASK_PERMISSIONS = 1;
    private ImageView mImageView;
    String[] report;
    Button report_list, process,saveurinetest;
    TextView description;
    private Activity mActivity;
    private boolean detected;
    Bitmap currentpict;
    static final int REQUEST_TAKE_PHOTO = 1;
    String currentPhotoPath;
    private static final String[] REQUIRED_SDK_PERMISSIONS = new String[]{
            Manifest.permission.CAMERA,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };
    private String[] reportVal;
    private String leukocytes;
    private String nitrite;
    private String urobilinogen;
    private String protein;
    private String ph;
    private String blood;
    private String specific_gravity;
    private String ketones;
    private String bilirubin;
    private String glucose;
    private String ascorbic_acid;

    static {
        System.loadLibrary("opencv_java3");
    }

    public static Bitmap rotateImage(Bitmap source, float angle) {
        Matrix matrix = new Matrix();
        matrix.postRotate(angle);
        return Bitmap.createBitmap(source, 0, 0, source.getWidth(), source.getHeight(),
                matrix, true);
    }

    public static void restartActivity(Activity activity) {
        if (Build.VERSION.SDK_INT >= 11) {
            activity.recreate();
        } else {
            activity.finish();
            activity.startActivity(activity.getIntent());
        }
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_captured_image);
        pref = this.getSharedPreferences("sunyahealth", Context.MODE_PRIVATE);
        dialog= new ProgressDialog(this);
        ptno = pref.getString("PTNO", "");
        patientModel = getIntent().getParcelableExtra("patient");
        checkPermissions();
        detected = false;
        mActivity = CapturedImageActivity.this;
        mImageView = findViewById(R.id.camera_photo);
        description = findViewById(R.id.description);
        process = findViewById(R.id.done);
        report_list = findViewById(R.id.btnReport);
        labDB=new LabDB(getApplicationContext());
        saveurinetest = findViewById(R.id.saveurinetest);
        assetManager = getAssets();


        if (!OpenCVLoader.initDebug()) {
            Toast.makeText(this, "OpenCV not Loaded", Toast.LENGTH_LONG).show();
            description.setText("OpenCV was not loaded, Please try again.");
//            new AlertDialog.Builder(CapturedImageActivity.this)
//                    .setTitle("Error: OpenCV Initialization")
//                    .setMessage("OpenCV was not loaded, Please try again.")
//                    .setCancelable(false)
//                    .setPositiveButton("Okay", (dialog, which) -> restartActivity(mActivity)).show();
        } else {
            description.setText("OpenCV Loaded.");
            Toast.makeText(this, "OpenCV Loaded", Toast.LENGTH_LONG).show();
            startCamera();
        }

        process.setOnClickListener(v -> {mImageView.setImageResource(0); startCamera();});

        report_list.setOnClickListener(v -> showDialog(CapturedImageActivity.this, report));

        saveurinetest.setOnClickListener(view -> {
            new SaveData().execute();


        });

    }

    public boolean save_report(String[] data){
        return false;
    }

    public void process(){
        if (detected){
            detected = false;
        }
        Mat mat = new Mat();
        Utils.bitmapToMat(currentpict, mat);
//        Utils.bitmapToMat(bitmap, mat);
//        Core.rotate(mat, mat, Core.ROTATE_90_COUNTERCLOCKWISE);
//        Bitmap testBitmap = Bitmap.createBitmap(mat.cols(), mat.rows(), Bitmap.Config.ARGB_8888);
//        Utils.matToBitmap(mat, testBitmap);
        Mat quad = new Mat();
        try {
            quad = board_detect(mat);

            int x = (int) (quad.width()*0.20);
            int y = (int) (quad.height()*0.20);
            int width = (int) (quad.width()-(2*(quad.width()*0.20)));
            int height = (int) (quad.height()-(2*(quad.height()*0.20)));
            quad = quad.submat(new Rect(x, y, width, height));
            Core.rotate(quad, quad, Core.ROTATE_90_CLOCKWISE);
            Bitmap temp_quad = Bitmap.createBitmap(quad.cols(), quad.rows(), Bitmap.Config.ARGB_8888);
            Utils.matToBitmap(quad, temp_quad);
            report = processBoard(quad);
            if (report != null) {
                report = clearify(report);

                detected = true;
                description.setText("Please check the detected strip, if found wrong detection, please restart the process by clicking the \"Open Camera\" button again.");
                Toast.makeText(this, "Processing was completed.", Toast.LENGTH_SHORT);
            }else {
                detected = false;
            }
        } catch (Exception e) {
            new AlertDialog.Builder(CapturedImageActivity.this)
                    .setTitle("Error")
                    .setMessage("Please take picture again!")
                    .setCancelable(false)
                    .setPositiveButton("Open Camera", (dialog, which) -> {
                        mImageView.setImageResource(0);
                        dialog.dismiss();
                        startCamera();}).show();
        }

    }

    public void startCamera(){
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        takePictureIntent.putExtra("android.intent.extras.FLASH_MODE_ON", 1);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
//                new AlertDialog.Builder(CapturedImageActivity.this)
//                        .setTitle("Error: Image File")
//                        .setMessage("Something went wrong while creating the image file. Please restart.")
//                        .setCancelable(false)
//                        .setPositiveButton("Okay", (dialog, which) -> {
//                            dialog.dismiss();
//                            restartActivity(mActivity);
//                        }).show();
            }
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(CapturedImageActivity.this,
                        "com.agatsa.testsdknew" + ".provider",
                        photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[],
                                           @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CODE_ASK_PERMISSIONS:
                for (int index = permissions.length - 1; index >= 0; --index) {
                    if (grantResults[index] != PackageManager.PERMISSION_GRANTED) {
                        // exit the app if one permission is not granted
                        Toast.makeText(this, "Required permission '" + permissions[index]
                                + "' not granted, exiting", Toast.LENGTH_LONG).show();
                        finish();
                        return;
                    }
                }
                break;
        }
    }

    public String[] processBoard(Mat board){
        Mat temp_board = new Mat();
        board.copyTo(temp_board);
        List<Scalar> standard_values = new ArrayList<>();
        standard_values.add(new Scalar(45, 0, 183, 0));
        standard_values.add(new Scalar(83, 134, 0, 0));
        standard_values.add(new Scalar(157, 53, 44, 0));
        List<Scalar> pixel_values = new ArrayList<>();
        pixel_values = ref_process(temp_board);
        List<Scalar> deviation = new ArrayList<>();
        for (int i = 0; i < 3; i++){
            double[] vals = new double[3];
            for (int j = 0; j < 3; j++){
                vals[j] = standard_values.get(i).val[j] - pixel_values.get(i).val[j];
            }
            deviation.add(new Scalar(vals));
        }
        String[] result = get_strip(temp_board, deviation);
        return result;

    }

    private String[] get_strip(Mat img, List<Scalar> deviation) {
        Bitmap bitmaptest = Bitmap.createBitmap(img.cols(), img.rows(), Bitmap.Config.RGB_565);
        Utils.matToBitmap(img, bitmaptest);
        Mat very_orig = new Mat();
        img.copyTo(very_orig);
        int img_h = img.height();
        int img_w = img.width();
        System.out.println(img.size());
        Mat orig = img.submat(new Rect(0, 0, (int) (img.width()*0.5), img.height()));
        int point = (int)( 0.72*orig.width());
        Mat roi = orig.submat(new Rect(point-5, 0, 10, (int) (orig.height()*0.85)));
        Mat roi1 = very_orig.submat(new Rect(point-20, 0, 40, (int) (orig.height()*0.85)));
        System.out.println(roi.size());
        Mat roi_p1 = adjust_gamma(roi, 0.1);
        Mat blurred = new Mat();
        Imgproc.medianBlur(roi_p1, blurred, 3);
        int roi_h = blurred.height();
        int roi_w = blurred.width();
        Mat hsv = new Mat();
        Imgproc.cvtColor(blurred, hsv, Imgproc.COLOR_BGR2HSV);
        List<Mat> Hsv = new ArrayList<>();
        Core.split(hsv, Hsv);
        Mat hue = Hsv.get(0);
        Mat sat = Hsv.get(1);
        Mat value = Hsv.get(2);
        Mat vertical = new Mat();
        Imgproc.threshold(sat, vertical, 0, 255, Imgproc.THRESH_BINARY+Imgproc.THRESH_OTSU);
        Mat edges = new Mat();
        Imgproc.Canny(vertical, edges, 100, 200);
        Mat padded = new Mat();
        Core.copyMakeBorder(edges, padded, 0, 0, 10, 10, Core.BORDER_CONSTANT, new Scalar(0, 0, 0));
        List<MatOfPoint> contours = new ArrayList<>();
        Imgproc.findContours(padded, contours, new Mat(), RETR_EXTERNAL, CHAIN_APPROX_SIMPLE);
        int max_y = 0;
        for (MatOfPoint cnt: contours){
            Rect rect = Imgproc.boundingRect(cnt);
            if (max_y < rect.y){
                max_y = rect.y;
            }
        }
//        MediaStore.Images.Media.insertImage(getContentResolver(), bitmap, "strip", "strip image");
        Mat strip = roi1.submat(new Rect(0, max_y-(int) (0.76*roi.height()), roi1.width(), (int) (0.76*roi.height())));
        Mat strip_blurred = roi1.submat(new Rect(0, max_y-(int) (0.76*roi.height()), roi1.width(), (int) (0.76*roi.height())));
        Mat kernel = Mat.ones(new Size(5,5), CvType.CV_32F);
        Core.divide(kernel, new Scalar(25), kernel);
        System.out.println(kernel);
        Imgproc.filter2D(strip_blurred, strip_blurred, -1, kernel);
        double[] ratio = {0.04, 0.13, 0.23, 0.32, 0.41, 0.50, 0.60, 0.69, 0.78, 0.880, 0.970};
        int x_point = (int) (strip.width()*0.5);
        int radius = (int) (strip.width()/6);
        String[] reported = new String[11];
        for (int i = 0; i < 11; i++){
            int y_point = Math.round((int)(ratio[i]*strip.height()));
            String res = calc(strip_blurred.submat(new Rect(x_point-radius, y_point-radius, radius+radius, radius+radius)), String.valueOf(i+1), deviation);
            reported[i] = res;
            Imgproc.rectangle(strip, new Point(x_point-radius, y_point-radius), new Point(x_point+radius, y_point+radius), new Scalar(255, 0, 255), 3);
        }
        Bitmap bitmap12 = Bitmap.createBitmap(strip.cols(), strip.rows(), Bitmap.Config.ARGB_8888);
        Utils.matToBitmap(strip, bitmap12);
//        MediaStore.Images.Media.insertImage(getContentResolver(), bitmap12, "roi1", "strip image");
        mImageView.setImageBitmap(bitmap12);
        return reported;
    }

    public double calculate_distance(Scalar tst, Scalar ref){
        double value = 0;
        for(int i = 0; i < 4; i++){
            value += Math.pow(tst.val[i]-ref.val[i], 2);
        }
        return value;
    }

    public String calc(Mat test_patch, String index_no, List<Scalar> deviation){
        for(Scalar dev: deviation){
            for (int i = 0; i < 4; i++){
                dev.val[i] = Math.floor(dev.val[i]/test_patch.width());
            }
        }
        Scalar avg_deviation = new Scalar(0, 0, 0);
        for (int i = 0; i < 3; i++){
            for (int j = 0; j < 3; j++){
                avg_deviation.val[i] = Math.floor((avg_deviation.val[i] + deviation.get(j).val[i])/3);
            }
        }
//        Imgcodecs.imwrite("stepwiseout/patch.jpg", test_patch);
        Imgproc.cvtColor(test_patch, test_patch, Imgproc.COLOR_BGR2RGB);
        Imgproc.cvtColor(test_patch, test_patch, Imgproc.COLOR_RGB2Lab);
        Scalar avg_color_test = Core.mean(test_patch);
        for (int i = 0; i < 3; i++){
            avg_color_test.val[i] += avg_deviation.val[i];
        }
        HashMap<String, Double> score = new HashMap<>();
        try {
            Mat temp = new Mat();
//            File[] files = new File( "pics/dus11/" + index_no).listFiles();
            String[] files = assetManager.list("pics/dus11/" + index_no);

            for (String file: files){
                InputStream is = assetManager.open("pics/dus11/" + index_no + "/" + file);
                Bitmap  bitmap = BitmapFactory.decodeStream(is);
                Utils.bitmapToMat(bitmap, temp);
                Mat ref_image = temp;
                Imgproc.resize(ref_image, ref_image, test_patch.size());
                Imgproc.cvtColor(ref_image, ref_image, Imgproc.COLOR_BGR2RGB);
                Imgproc.cvtColor(ref_image, ref_image, Imgproc.COLOR_RGB2Lab);
                Scalar avg_color_ref = Core.mean(ref_image);
                double mf = calculate_distance(avg_color_test, avg_color_ref);
                score.put(file, mf);
            }
        } catch (IOException e) {
            Toast.makeText(this, "Error Loading dus11 files", Toast.LENGTH_LONG).show();
        }

        Map<String, Double> lab_score = sortByValue(score);
//        for (Map.Entry<String, Double> en : lab_score.entrySet()) {
//            System.out.println(en.getKey() + " = " +
//                    " = " + en.getValue());
//
//        }
        Object myKey = lab_score.keySet().toArray()[0];
//        System.out.println(Double.toString(lab_score.get(myKey)));
        return myKey.toString();
    }

    public List<Scalar> ref_process(Mat ref){
        int ref_h = ref.height(), ref_w = ref.width();
        ref = ref.submat(new Rect(0+(int)ref_w/2, 0, (int)ref_w/2, ref_h));
        ref_h = ref.height();
        ref_w = ref.width();
        Mat blurred = new Mat();
        Imgproc.medianBlur(ref, blurred, 3);
        double gamma = 0.1;
        Mat eddy = new Mat(); blurred.copyTo(eddy);
        Mat adjusted = adjust_gamma(eddy, gamma);
        Mat hsv = new Mat();
        Imgproc.cvtColor(adjusted, hsv, Imgproc.COLOR_BGR2HSV);
        List<Mat> Hsv = new ArrayList<>();
        Core.split(hsv, Hsv);
        Mat hue = Hsv.get(0);
        Mat sat = Hsv.get(1);
        Mat value = Hsv.get(2);
        Mat vertical = new Mat();
        Imgproc.threshold(value, vertical, 0, 255, Imgproc.THRESH_BINARY + Imgproc.THRESH_OTSU);
        Mat kernel = Mat.ones(new Size(5,5), CvType.CV_8UC1);
        Imgproc.erode(vertical, vertical, kernel, new Point(-1, -1), 2);
        Imgproc.dilate(vertical, vertical, kernel, new Point(-1, -1), 2);
        List<MatOfPoint> contours = new ArrayList<>();
        Imgproc.findContours(vertical, contours, new Mat(), Imgproc.RETR_EXTERNAL, Imgproc.CHAIN_APPROX_SIMPLE);
        MatOfPoint max_contour = new MatOfPoint();
        double maxArea = 0;
        Iterator<MatOfPoint> iterator = contours.iterator();
        while (iterator.hasNext()){
            MatOfPoint contour = iterator.next();
            double area = Imgproc.contourArea(contour);
            if(area > maxArea){
                maxArea = area;
                max_contour = contour;
            }
        }
        Rect rect = Imgproc.boundingRect(max_contour);
        Mat refimg = blurred.submat(new Rect(rect.x, rect.y, rect.width, rect.height));
        hsv.release();
        Imgproc.cvtColor(refimg, hsv, Imgproc.COLOR_BGR2HSV);
        Hsv.clear();
        Core.split(hsv, Hsv);
        hue = Hsv.get(0);
        sat = Hsv.get(1);
        value = Hsv.get(2);
        vertical = new Mat();
        Imgproc.threshold(sat, vertical, 0, 255, Imgproc.THRESH_BINARY + Imgproc.THRESH_OTSU);
        Imgproc.erode(vertical, vertical, kernel, new Point(-1, -1), 2);
        Imgproc.dilate(vertical, vertical, kernel, new Point(-1, -1), 2);
        contours = new ArrayList<>();
        Imgproc.findContours(vertical, contours, new Mat(), Imgproc.RETR_EXTERNAL, Imgproc.CHAIN_APPROX_SIMPLE);
        Collections.sort(contours, Collections.<MatOfPoint>reverseOrder(new Comparator<MatOfPoint>() {
            @Override
            public int compare(MatOfPoint o1, MatOfPoint o2) {
                if (Imgproc.boundingRect(o1).area() > Imgproc.boundingRect(o2).area()) {
                    return 1;
                } else if (Imgproc.boundingRect(o1).area() == Imgproc.boundingRect(o2).area()) {
                    return 0;
                }else
                    return -1;
            }
        }));
        contours = contours.subList(0, 3);
        List<Rect> bounding_boxes = new ArrayList<>();
        for (MatOfPoint cnt : contours){
            bounding_boxes.add(Imgproc.boundingRect(cnt));
        }
        Collections.sort(contours, new Comparator<MatOfPoint>() {
            @Override
            public int compare(MatOfPoint o1, MatOfPoint o2) {
                return Double.compare(Imgproc.boundingRect(o1).tl().y, Imgproc.boundingRect(o2).tl().y);
            }
        });
        List<Scalar> values = new ArrayList<>();
        for (int i = 0; i < 3; i++){
            Rect r = Imgproc.boundingRect(contours.get(i));
            int center_x = (int) ((2*r.x + r.width)/2);
            int center_y = (int) ((2*r.y + r.height)/2);
            int rad = 10;
            Mat patch = refimg.submat(new Rect(center_x-rad, center_y-rad, rad, rad));
            Scalar avg_color_test = Core.mean(patch);
            values.add(avg_color_test);
        }
        return values;
    }

    private Mat adjust_gamma(Mat image, double gamma) {
        Mat lut = new Mat(1, 256, CvType.CV_8UC1);
        lut.setTo(new Scalar(0));
        for (int i = 0; i < 256; i++) {
            lut.put(0, i, Math.pow(i/255.0, 1.0/gamma) * 255.0);
        }
        Core.LUT(image, lut, image);
        return image;
    }




    public String[] clearify(String[] report){
        leukocytes = test_values.get(0).get(report[0]);
        Log.d("leuko",leukocytes);
        nitrite = test_values.get(1).get(report[1]);
        urobilinogen = test_values.get(2).get(report[2]);
        protein = test_values.get(3).get(report[3]);
        ph = test_values.get(4).get(report[4]);
        blood = test_values.get(5).get(report[5]);
        specific_gravity = test_values.get(6).get(report[6]);
        ketones = test_values.get(7).get(report[7]);
        bilirubin = test_values.get(8).get(report[8]);
        glucose = test_values.get(9).get(report[9]);
        ascorbic_acid = test_values.get(10).get(report[10]);



        reportVal = new String[11];
        for (int i = 0; i < 11; i++){
            reportVal[i] = test_values.get(i).get(report[i]);
            report[i] = test_names.get(i) + ":  " + test_values.get(i).get(report[i]);
        }
        System.out.println("Completed making report.");
        return report;
    }

    public void showDialog(CapturedImageActivity activity, final String[] report){

        final Dialog dialog = new Dialog(activity, android.R.style.Theme_Black_NoTitleBar_Fullscreen);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.report_listview);

        Button btndialogclose = (Button) dialog.findViewById(R.id.btndialogclose);
//        Button saveReport = (Button) dialog.findViewById(R.id.save_report);
        btndialogclose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

//        saveReport.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                save_report(report);
//            }
//        });

        ListView listView = (ListView) dialog.findViewById(R.id.listview);
        ArrayAdapter arrayAdapter = new ArrayAdapter(this,R.layout.list_item, R.id.tv, report);
        listView.setAdapter(arrayAdapter);
        dialog.show();
    }

    public static HashMap<String, Double> sortByValue(HashMap<String, Double> score) {
        List<Map.Entry<String, Double> > list =
                new LinkedList<Map.Entry<String, Double> >(score.entrySet());
        Collections.sort(list, new Comparator<Map.Entry<String, Double> >() {
            public int compare(Map.Entry<String, Double> o1,
                               Map.Entry<String, Double> o2)
            {
                return (o1.getValue()).compareTo(o2.getValue());
            }
        });
        HashMap<String, Double> temp = new LinkedHashMap<String, Double>();
        for (Map.Entry<String, Double> aa : list) {
            temp.put(aa.getKey(), aa.getValue());
        }
        return temp;
    }

    protected void checkPermissions() {
        final List<String> missingPermissions = new ArrayList<String>();
        // check all required dynamic permissions
        for (final String permission : REQUIRED_SDK_PERMISSIONS) {
            final int result = ContextCompat.checkSelfPermission(this, permission);
            if (result != PackageManager.PERMISSION_GRANTED) {
                missingPermissions.add(permission);
            }
        }
        if (!missingPermissions.isEmpty()) {
            // request all missing permissions
            final String[] permissions = missingPermissions
                    .toArray(new String[missingPermissions.size()]);
            ActivityCompat.requestPermissions(this, permissions, REQUEST_CODE_ASK_PERMISSIONS);
        } else {
            final int[] grantResults = new int[REQUIRED_SDK_PERMISSIONS.length];
            Arrays.fill(grantResults, PackageManager.PERMISSION_GRANTED);
            onRequestPermissionsResult(REQUEST_CODE_ASK_PERMISSIONS, REQUIRED_SDK_PERMISSIONS,
                    grantResults);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            try {
                setPic();
                process();
            } catch (Exception e){
//                new AlertDialog.Builder(CapturedImageActivity.this)
//                        .setTitle("Error: Image")
//                        .setMessage("Something went wrong while loading the captured image. Please capture photo again.")
//                        .setCancelable(false)
//                        .setPositiveButton("Open Camera", new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialog, int which) {
//                                dialog.dismiss();
//                                startCamera();
//                            }
//                        }).show();
            }
        }
    }

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        currentPhotoPath = image.getAbsolutePath();
        return image;
    }

    private static double getAngle(Point pt1, Point pt2, Point pt0)
    {
        double dx1 = pt1.x - pt0.x;
        double dy1 = pt1.y - pt0.y;
        double dx2 = pt2.x - pt0.x;
        double dy2 = pt2.y - pt0.y;
        return (dx1*dx2 + dy1*dy2)/Math.sqrt((dx1*dx1 + dy1*dy1)*(dx2*dx2 + dy2*dy2) + 1e-10);
    }

    public Mat board_detect(Mat img){
        Mat orig = new Mat();
        img.copyTo(orig);

        // start processing

        Imgproc.cvtColor(img, img, Imgproc.COLOR_BGR2GRAY);
        Imgproc.GaussianBlur(img, img, new Size(3,3), 2, 2);
        Imgproc.Canny(img, img, 20, 60, 3, false);
        Imgproc.dilate(img, img, new Mat(), new Point(-1,-1), 3, 1, new Scalar(1));
        List<MatOfPoint> contours = new ArrayList<>();
        Mat hierarchy = new Mat();
        Imgproc.findContours(img, contours, hierarchy, RETR_EXTERNAL, CHAIN_APPROX_SIMPLE);
        List<MatOfPoint> squares = new ArrayList<>();
        List<MatOfPoint> hulls = new ArrayList<>();
        MatOfInt hull = new MatOfInt();
        MatOfPoint2f approx = new MatOfPoint2f();
        approx.convertTo(approx, CvType.CV_32F);
        for (MatOfPoint contour: contours) {
            Imgproc.convexHull(contour, hull);

            Point[] contourPoints = contour.toArray();
            int[] indices = hull.toArray();
            List<Point> newPoints = new ArrayList<>();
            for (int index : indices) {
                newPoints.add(contourPoints[index]);
            }
            MatOfPoint2f contourHull = new MatOfPoint2f();
            contourHull.fromList(newPoints);

            Imgproc.approxPolyDP(contourHull, approx, Imgproc.arcLength(contourHull, true)*0.02, true);

            MatOfPoint approxf1 = new MatOfPoint();
            approx.convertTo(approxf1, CvType.CV_32S);
            if (approx.rows() == 4 && Math.abs(Imgproc.contourArea(approx)) > 40000 &&
                    Imgproc.isContourConvex(approxf1)) {
                double maxCosine = 0;
                for (int j = 2; j < 5; j++) {
                    double cosine = Math.abs(getAngle(approxf1.toArray()[j%4], approxf1.toArray()[j-2], approxf1.toArray()[j-1]));
                    maxCosine = Math.max(maxCosine, cosine);
                }
                if (maxCosine < 0.3) {
                    MatOfPoint tmp = new MatOfPoint();
                    contourHull.convertTo(tmp, CvType.CV_32S);
                    squares.add(approxf1);
                    hulls.add(tmp);
                }
            }
        }
        int index = findLargestSquare(squares);
        MatOfPoint largest_square = squares.get(index);

        if (largest_square.rows() == 0 || largest_square.cols() == 0)
            return orig;

        MatOfPoint contourHull = hulls.get(index);
        MatOfPoint2f tmp = new MatOfPoint2f();
        contourHull.convertTo(tmp, CvType.CV_32F);
        Imgproc.approxPolyDP(tmp, approx, 3, true);
        List<Point> newPointList = new ArrayList<>();
        double maxL = Imgproc.arcLength(approx, true) * 0.02;
        for (Point p : approx.toArray()) {
            if (!(getSpacePointToPoint(p, largest_square.toList().get(0)) > maxL &&
                    getSpacePointToPoint(p, largest_square.toList().get(1)) > maxL &&
                    getSpacePointToPoint(p, largest_square.toList().get(2)) > maxL &&
                    getSpacePointToPoint(p, largest_square.toList().get(3)) > maxL)) {
                newPointList.add(p);
            }
        }

        List<double[]> lines = new ArrayList<>();
        for (int i = 0; i < newPointList.size(); i++) {
            Point p1 = newPointList.get(i);
            Point p2 = newPointList.get((i+1) % newPointList.size());
            if (getSpacePointToPoint(p1, p2) > 2 * maxL) {
                lines.add(new double[]{p1.x, p1.y, p2.x, p2.y});
            }
        }

        List<Point> corners = new ArrayList<>();
        for (int i = 0; i < lines.size(); i++) {
            Point corner = computeIntersect(lines.get(i),lines.get((i+1) % lines.size()));
            corners.add(corner);
        }
        corners = sortCorners(corners);

        Point p0 = corners.get(0);
        Point p1 = corners.get(1);
        Point p2 = corners.get(2);
        Point p3 = corners.get(3);
        double space0 = getSpacePointToPoint(p0, p1);
        double space1 = getSpacePointToPoint(p1, p2);
        double space2 = getSpacePointToPoint(p2, p3);
        double space3 = getSpacePointToPoint(p3, p0);

        double imgWidth = space1 > space3 ? space1 : space3;
        double imgHeight = space0 > space2 ? space0 : space2;

        if (imgWidth < imgHeight) {
            double temp = imgWidth;
            imgWidth = imgHeight;
            imgHeight = temp;
            Point tempPoint = p0.clone();
            p0 = p1.clone();
            p1 = p2.clone();
            p2 = p3.clone();
            p3 = tempPoint.clone();
        }

        Mat quad = Mat.zeros((int)imgHeight * 2, (int)imgWidth * 2, CvType.CV_8UC3);

        MatOfPoint2f cornerMat = new MatOfPoint2f(p0, p1, p2, p3);
        MatOfPoint2f quadMat = new MatOfPoint2f(new Point(imgWidth*0.4, imgHeight*1.6),
                new Point(imgWidth*0.4, imgHeight*0.4),
                new Point(imgWidth*1.6, imgHeight*0.4),
                new Point(imgWidth*1.6, imgHeight*1.6));

        Mat transmtx = Imgproc.getPerspectiveTransform(cornerMat, quadMat);
        Imgproc.warpPerspective(orig, quad, transmtx, quad.size());
//        Bitmap bitmap = Bitmap.createBitmap(quad.width(), quad.height(), Bitmap.Config.RGB_565);
//
//        Utils.matToBitmap(quad, bitmap);
        return quad;
    }

    private List<Point> sortCorners(List<Point> corners) {
//        if (corners.size() == 0) return;
        Point p1 = corners.get(0);
        int index = 0;
        for (int i = 1; i < corners.size(); i++) {
            Point point = corners.get(i);
            if (p1.x > point.x) {
                p1 = point;
                index = i;
            }
        }

        corners.set(index, corners.get(0));
        corners.set(0, p1);

        Point lp = corners.get(0);
        for (int i = 1; i < corners.size(); i++) {
            for (int j = i + 1; j < corners.size(); j++) {
                Point point1 = corners.get(i);
                Point point2 = corners.get(j);
                if ((point1.y-lp.y*1.0)/(point1.x-lp.x)>(point2.y-lp.y*1.0)/(point2.x-lp.x)) {
                    Point temp = point1.clone();
                    corners.set(i, corners.get(j));
                    corners.set(j, temp);
                }
            }
        }
        return corners;
    }

    private static double getSpacePointToPoint(Point p1, Point p2) {
        double a = p1.x - p2.x;
        double b = p1.y - p2.y;
        return Math.sqrt(a * a + b * b);
    }

    private static Point computeIntersect(double[] a, double[] b) {
        if (a.length != 4 || b.length != 4)
            throw new ClassFormatError();
        double x1 = a[0], y1 = a[1], x2 = a[2], y2 = a[3], x3 = b[0], y3 = b[1], x4 = b[2], y4 = b[3];
        double d = ((x1 - x2) * (y3 - y4)) - ((y1 - y2) * (x3 - x4));
        if (d != 0) {
            Point pt = new Point();
            pt.x = ((x1 * y2 - y1 * x2) * (x3 - x4) - (x1 - x2) * (x3 * y4 - y3 * x4)) / d;
            pt.y = ((x1 * y2 - y1 * x2) * (y3 - y4) - (y1 - y2) * (x3 * y4 - y3 * x4)) / d;
            return pt;
        }
        else
            return new Point(-1, -1);
    }

    private static int findLargestSquare(List<MatOfPoint> squares) {
        if (squares.size() == 0)
            return -1;
        int max_width = 0;
        int max_height = 0;
        int max_square_idx = 0;
        int currentIndex = 0;
        for (MatOfPoint square : squares) {
            Rect rectangle = Imgproc.boundingRect(square);
            if (rectangle.width >= max_width && rectangle.height >= max_height) {
                max_width = rectangle.width;
                max_height = rectangle.height;
                max_square_idx = currentIndex;
            }
            currentIndex++;
        }
        return max_square_idx;
    }

    private void setPic() {
        // Get the dimensions of the View
//        int targetW = mImageView.getWidth();
//        int targetH = mImageView.getHeight();

        // Get the dimensions of the bitmap
        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        bmOptions.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(currentPhotoPath, bmOptions);
//        int photoW = bmOptions.outWidth;
//        int photoH = bmOptions.outHeight;

        // Determine how much to scale down the image
//        int scaleFactor = Math.min(photoW/targetW, photoH/targetH);

        // Decode the image file into a Bitmap sized to fill the View
        bmOptions.inJustDecodeBounds = false;
//        bmOptions.inSampleSize = scaleFactor;
        bmOptions.inPurgeable = true;

        Bitmap bitmap = BitmapFactory.decodeFile(currentPhotoPath, bmOptions);

        ExifInterface ei = null;
        Bitmap rotatedBitmap = null;
        try {
            ei = new ExifInterface(currentPhotoPath);
            int orientation = ei.getAttributeInt(ExifInterface.TAG_ORIENTATION,
                    ExifInterface.ORIENTATION_UNDEFINED);

            switch (orientation) {

                case ExifInterface.ORIENTATION_ROTATE_90:
                    rotatedBitmap = rotateImage(bitmap, 90);
                    break;

                case ExifInterface.ORIENTATION_ROTATE_180:
                    rotatedBitmap = rotateImage(bitmap, 180);
                    break;

                case ExifInterface.ORIENTATION_ROTATE_270:
                    rotatedBitmap = rotateImage(bitmap, 270);
                    break;

                case ExifInterface.ORIENTATION_NORMAL:
                default:
                    rotatedBitmap = bitmap;
            }

        } catch (IOException e) {
            e.printStackTrace();
            rotatedBitmap = bitmap;
        }

        currentpict = rotatedBitmap;
//        mImageView.setImageBitmap(rotatedBitmap);
    }

    @SuppressLint("StaticFieldLeak")
    private class SaveData extends AsyncTask<String, Void, Integer> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog.setMessage("Saving Data");
            dialog.show();
        }

        @Override
        protected Integer doInBackground(String... strings) {
//
            LabDB db = new LabDB(getApplicationContext());
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
                return 3;
            }
            // Save Vital Sign
            urineReport.setPt_no(ptno);
            Log.d("pt_no",ptno);
            urineReport.setLeuko(leukocytes);
            urineReport.setNit(nitrite);
            urineReport.setUrb(urobilinogen);
            urineReport.setProtein(protein);
            urineReport.setPh(ph);
            urineReport.setBlood(blood);
            urineReport.setSg(specific_gravity);
            urineReport.setKet(ketones);
            urineReport.setBili(bilirubin);
            urineReport.setGlucose(glucose);
            urineReport.setAsc(ascorbic_acid);


            String last_vitalsign_row_id = db.SaveUrineReport(urineReport);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
                return 3;
            }
            urineReport.setRow_id(last_vitalsign_row_id);
            return 1;
        }

        @Override
        protected void onPostExecute(Integer s) {
            super.onPostExecute(s);
            if (s == 2) {
                if (dialog.isShowing())
                    dialog.dismiss();
                Toast.makeText(getApplicationContext(), "Already saved " , Toast.LENGTH_LONG).show();

            } else if (s == 3) {
                if (dialog.isShowing())
                    dialog.dismiss();
                Toast.makeText(getApplicationContext(), "Exception catched ", Toast.LENGTH_LONG).show();

            } else {
                if (dialog.isShowing())
                    dialog.dismiss();
                CapturedImageActivity.super.onBackPressed();
                Toast.makeText(getApplicationContext(), "Patient Saved " , Toast.LENGTH_LONG).show();

            }
        }
    }

    @Override
    public void onBackPressed() {

//       here back is handled in async postexecute to avoid memory leak  this activity is already killed in back


        DialogUtil.getOKCancelDialog(this, "", "Do you want to exit by  saving the  urine test of " + patientModel.getPtName(), "Yes","No", (dialogInterface, i) -> {
            new SaveData().execute();
//            CapturedImageActivity.super.onBackPressed();

        });





    }
















}

