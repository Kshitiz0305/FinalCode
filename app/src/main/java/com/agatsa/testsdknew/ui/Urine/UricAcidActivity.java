package com.agatsa.testsdknew.ui.Urine;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
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
import androidx.databinding.DataBindingUtil;

import com.agatsa.testsdknew.Models.PatientModel;
import com.agatsa.testsdknew.Models.UricAcidModel;
import com.agatsa.testsdknew.R;
import com.agatsa.testsdknew.customviews.DialogUtil;
import com.agatsa.testsdknew.databinding.ActivityUricAcidTestBinding;
import com.agatsa.testsdknew.ui.LabDB;

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
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Array;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import static com.google.common.primitives.Doubles.max;
import static com.google.common.primitives.Doubles.min;
import static org.opencv.imgproc.Imgproc.CHAIN_APPROX_SIMPLE;
import static org.opencv.imgproc.Imgproc.RETR_EXTERNAL;

/**
 * <h1>Urinalysis of Uric Acid</h1>
 * The UricAcidActivity implements OpenCV sdk to perform image processing on image of urine strip of Uric Acid.
 * @author Anuj Karn
 * @version 1.0
 * @since 2019-12-15*/

public class UricAcidActivity extends AppCompatActivity {
    ActivityUricAcidTestBinding binding;


    String ptNo = " ";
    SharedPreferences pref;
    PatientModel patientModel = new PatientModel();
    private AssetManager assetManager;
    LabDB labDB;
    boolean startDetect;
    UricAcidModel uricAcidModel = new UricAcidModel();
    private ProgressDialog dialog;
    HashMap<Integer, String> testPatchNames = new HashMap<Integer, String>(){
        {
            put(0, "Uric Acid");
        }
    };

    HashMap<Integer, HashMap<String, String>> testPatchValues = new HashMap<Integer, HashMap<String, String>>(){
        {
            put(0, new HashMap<String, String>(){
                {
                    put("0.png", "100");
                    put("1.png", "300");
                    put("2.png", "700");
                    put("3.png", "1100");
                    put("4.png", "1500");
                }
            });
        }
    };

    static final int REQUEST_IMAGE_CAPTURE = 1;
    private final static int REQUEST_CODE_ASK_PERMISSIONS = 1;
    private ImageView mImageView;
    String[] report;
    TextView description;
    private Activity mActivity;
    private boolean isDetected;
    Bitmap currentImage;
    private String stripPhotoPathUri;
    private String patientAveragePatchTest;
    static final int REQUEST_TAKE_PHOTO = 1;
    String currentPhotoPath;
    private static final String[] REQUIRED_SDK_PERMISSIONS = new String[]{
            Manifest.permission.CAMERA,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };
    private String[] reportVal;
    private String uricAcidLevel;
    ImageView help;

    static {
        System.loadLibrary("opencv_java3");
    }



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= DataBindingUtil.setContentView(this, R.layout.activity_uric_acid_test);
        pref = this.getSharedPreferences("sunyahealth", Context.MODE_PRIVATE);
        ptNo = pref.getString("PTNO", "");
        patientModel = getIntent().getParcelableExtra("patient");
        dialog= new ProgressDialog(this);
        checkPermissions();
        patientAveragePatchTest = "";
        isDetected = false;
        startDetect = false;
        mActivity = UricAcidActivity.this;
        mImageView = binding.uricAcidCameraPhoto;
        description = findViewById(R.id.description);
//        process = findViewById(R.id.done);
        help = findViewById(R.id.help);
//        report_list = findViewById(R.id.btnReport);
        labDB=new LabDB(getApplicationContext());
//        saveurinetest = findViewById(R.id.saveurinetest);
        assetManager = getAssets();

        threshold = 100;

        bwIMG = new Mat();
        dsIMG = new Mat();
        hsvIMG = new Mat();
        lrrIMG = new Mat();
        urrIMG = new Mat();
        usIMG = new Mat();
        cIMG = new Mat();
        hovIMG = new Mat();
        approxCurve = new MatOfPoint2f();

        if (!OpenCVLoader.initDebug()) {
            Toast.makeText(this, "OpenCV not Loaded", Toast.LENGTH_LONG).show();
            description.setText("OpenCV was not loaded, Please try again.");
//            new AlertDialog.Builder(TwoParameterActivity.this)
//                    .setTitle("Error: OpenCV Initialization")
//                    .setMessage("OpenCV was not loaded, Please try again.")
//                    .setCancelable(false)
//                    .setPositiveButton("Okay", (dialog, which) -> restartActivity(mActivity)).show();
        } else {
//            description.setText("OpenCV Loaded.");
//            Toast.makeText(this, "OpenCV Loaded", Toast.LENGTH_LONG).show();
            startCamera();
        }

        binding.uricAcidOpenCamera.setOnClickListener(v -> {
            mImageView.setImageResource(0);
            startCamera(); report = null;
//        new String[11];
        });

        binding.uricAcidViewReport.setOnClickListener(v -> {
            if (report==null) {
                Toast.makeText(this, "Please click picture.", Toast.LENGTH_LONG).show();
            } else {
                showDialog(UricAcidActivity.this, report);
            }
        });

        binding.uricAcidSaveReport.setOnClickListener(view -> {
            SaveImage(final_strip, new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().getTime()));
            if(reportVal==null){
                Toast.makeText(mActivity, "Please Click Picture To View Report", Toast.LENGTH_SHORT).show();

            }else{
                new UricAcidActivity.SaveData().execute();

            }
        });

    }

    /**
     * This method saves isDetected patch strip image inside sunyahealth directory for a temporary period of time.
     * @param finalBitmap This parameter is the bitmap image to be saved.
     * */
    private void SaveImage(Bitmap finalBitmap) {

        String root = Environment.getExternalStorageDirectory().toString();
        File myDir = new File(root + "/sunyahealth/urinestrip/uricacid/");
        if (!myDir.exists()) {
            myDir.mkdirs();
        }
        String fname = patientModel.getPtName() +  ".jpg";
        File file = new File (myDir, fname);
        if (file.exists ())
            file.delete ();
        try {
            FileOutputStream out = new FileOutputStream(file);
            finalBitmap.compress(Bitmap.CompressFormat.JPEG, 90, out);
            out.flush();
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * This method saves the final permanent isDetected patch strip image inside sunyahealth directory.
     * @param finalBitmap This bitmap is saved.
     * @param dateTime This parameter is used to create image file name.*/
    private void SaveImage(Bitmap finalBitmap, String dateTime) {

        String root = Environment.getExternalStorageDirectory().toString();
        File myDir = new File(root + "/sunyahealth/urinestrip/uricacid/");
        if (!myDir.exists()) {
            myDir.mkdirs();
        }
        String fname = patientModel.getPtName() + dateTime +  ".jpg";
        File file = new File (myDir, fname);
        stripPhotoPathUri = file.getAbsolutePath();
        File filetemp = new File (myDir, patientModel.getPtName() + ".jpg");
        if (filetemp.exists())
            filetemp.delete();

        try {
            FileOutputStream out = new FileOutputStream(file);
            finalBitmap.compress(Bitmap.CompressFormat.JPEG, 90, out);
            out.flush();
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * This method processes the camera captured image.
     * */
    public void process(){
        if (isDetected){
            isDetected = false;
        }
        Mat mat = new Mat();
        Utils.bitmapToMat(currentImage, mat);
        Mat quad;
        try {
            quad = board_detect(mat);
            int x = (int) (quad.width()*0.20);
            int y = (int) (quad.height()*0.20);
            int width = (int) (quad.width()-(2*(quad.width()*0.20)));
            int height = (int) (quad.height()-(2*(quad.height()*0.20)));
            quad = quad.submat(new Rect(x, y, width, height));
            Core.rotate(quad, quad, Core.ROTATE_90_CLOCKWISE);
            report = processBoard(quad);
            if (report != null) {
                report = clearify(report);
                isDetected = true;
                description.setText("Please check the isDetected strip, if found wrong detection, please restart the process by clicking the \"Open Camera\" button again.");
                Toast.makeText(this, "Processing was completed.", Toast.LENGTH_SHORT);
            }else {
                isDetected = false;
            }
        } catch (Exception e) {

            new AlertDialog.Builder(UricAcidActivity.this)
                    .setTitle("Error")
                    .setMessage("Please take picture again!")
                    .setCancelable(false)
                    .setPositiveButton("Open Camera", (dialog, which) -> {
//                        mImageView.setImageResource(0);
                        dialog.dismiss();
                        startCamera();}).show();
        }
        startDetect = false;
    }

    /**
     * This method starts device default camera activity.*/
    public void startCamera(){
        startDetect = false;

        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        takePictureIntent.putExtra("android.intent.extras.FLASH_MODE_ON", 1);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                Log.d("Camera Start", "Failed to start camera activity.");
            }
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(UricAcidActivity.this,
                        "com.agatsa.testsdknew" + ".provider",
                        photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);
            }
        }
    }

    /**
     * This method is executed after user interaction with permission dialog.
     * @param requestCode This parameter is a reqest code.
     * @param permissions This parameter is list of all the permissions asked to the user.
     * @param grantResults This parameter is the result of permission queries.*/
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

    /**
     * This method processes the isDetected urine board.
     * @param board This parameter is the isDetected urine board from the image.
     * @return String[] This returns the isDetected strip patches report.*/
    public String[] processBoard(Mat board){
        Mat temp_board = new Mat();
        board.copyTo(temp_board);
        List<Scalar> standard_values = new ArrayList<>();
//        standard_values.add(new Scalar(45, 0, 183, 0));
//        standard_values.add(new Scalar(83, 134, 0, 0));
//        standard_values.add(new Scalar(157, 53, 44, 0));
        standard_values.add(new Scalar(0, 0, 255, 0));
        standard_values.add(new Scalar(0, 255, 0, 0));
        standard_values.add(new Scalar(255, 0, 0, 0));
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

    static int YMAX = 0;
    //image holder
    Mat bwIMG, hsvIMG, lrrIMG, urrIMG, dsIMG, usIMG, cIMG, hovIMG;
    MatOfPoint2f approxCurve;

    int threshold;
    private Mat findRectangle(Mat image) {
        Mat gray = new Mat();
        Mat dst = new Mat();
        Imgproc.cvtColor(image, gray, Imgproc.COLOR_RGB2GRAY);
        Imgproc.cvtColor(image, dst, Imgproc.COLOR_RGB2RGBA);


        Imgproc.pyrDown(gray, dsIMG, new Size(gray.cols() / 2, gray.rows() / 2));
        Imgproc.pyrUp(dsIMG, usIMG, gray.size());

        Imgproc.Canny(usIMG, bwIMG, 0, threshold);

        Imgproc.dilate(bwIMG, bwIMG, new Mat(), new Point(-1, 1), 1);

        List<MatOfPoint> contours = new ArrayList<MatOfPoint>();

        cIMG = bwIMG.clone();

        Imgproc.findContours(cIMG, contours, hovIMG, Imgproc.RETR_EXTERNAL, Imgproc.CHAIN_APPROX_SIMPLE);


        for (MatOfPoint cnt : contours) {

            MatOfPoint2f curve = new MatOfPoint2f(cnt.toArray());

            Imgproc.approxPolyDP(curve, approxCurve, 0.02 * Imgproc.arcLength(curve, true), true);

            int numberVertices = (int) approxCurve.total();

            double contourArea = Imgproc.contourArea(cnt);

            if (Math.abs(contourArea) < 100) {
                continue;
            }

            //Rectangle isDetected
            if (numberVertices >= 4 && numberVertices <= 6) {

                List<Double> cos = new ArrayList<>();

                for (int j = 2; j < numberVertices + 1; j++) {
                    cos.add(angle(approxCurve.toArray()[j % numberVertices], approxCurve.toArray()[j - 2], approxCurve.toArray()[j - 1]));
                }

                Collections.sort(cos);

                double mincos = cos.get(0);
                double maxcos = cos.get(cos.size() - 1);

                if (numberVertices == 4 && mincos >= -0.1 && maxcos <= 0.3) {
                    setLabel(dst, "X", cnt);
                }

            }


        }

        return dst;

    }
    private static double angle(Point pt1, Point pt2, Point pt0) {
        double dx1 = pt1.x - pt0.x;
        double dy1 = pt1.y - pt0.y;
        double dx2 = pt2.x - pt0.x;
        double dy2 = pt2.y - pt0.y;
        return (dx1 * dx2 + dy1 * dy2) / Math.sqrt((dx1 * dx1 + dy1 * dy1) * (dx2 * dx2 + dy2 * dy2) + 1e-10);
    }

    private void setLabel(Mat im, String label, MatOfPoint contour) {
        int fontface = Core.FONT_HERSHEY_SIMPLEX;
        double scale = 3;//0.4;
        int thickness = 3;//1;
        int[] baseline = new int[1];
        Size text = Imgproc.getTextSize(label, fontface, scale, thickness, baseline);
        Rect r = Imgproc.boundingRect(contour);
        Point pt = new Point(r.x + ((r.width - text.width) / 2),r.y + ((r.height + text.height) / 2));
        Imgproc.putText(im, label, pt, fontface, scale, new Scalar(255, 0, 0), thickness);
    }

    /**
     * This method detects strip from the urine board and analyzes the patches.
     * @param img This parameter is the image of isDetected urine board.
     * @param deviation This parameter os the List of Scalar values that contains the value for color deviation in camera captured image.
     * @return String[] This returns the result of urine strip after analysis.*/
    private String[] get_strip(Mat img, List<Scalar> deviation) {
        Mat very_orig = new Mat();
        img.copyTo(very_orig);
        Mat orig = img.submat(new Rect(0, 0, (int) (img.width()*0.5), img.height()));
        int point = (int)( 0.72*orig.width());
        Mat roi = orig.submat(new Rect(point-5, 0, 10, (int) (orig.height()*0.5)));
        Mat roi1 = very_orig.submat(new Rect(point-20, 0, 40, (int) (orig.height()*0.5)));
        System.out.println(roi.size());
        Mat roi_p1 = adjust_gamma(roi, 0.2);
        Mat blurred = new Mat();
        Imgproc.medianBlur(roi_p1, blurred, 3);
        List<Mat> BGR = new ArrayList<>();
        Core.split(blurred, BGR);
        Mat B = BGR.get(0);
        Mat G = BGR.get(1);
        Mat R = BGR.get(2);
        Mat vertical = new Mat();
        Imgproc.threshold(B, vertical, 0, 255, Imgproc.THRESH_BINARY+Imgproc.THRESH_OTSU);
        Mat edges = new Mat();
        Imgproc.Canny(vertical, edges, 100, 200);
        Mat padded = Mat.zeros(edges.rows(), edges.cols()+20, CvType.CV_8U);
        Mat bSubmat = padded.submat(0, padded.rows(), 10, edges.cols()+10);
        edges.copyTo(bSubmat);
        List<MatOfPoint> contours = new ArrayList<>();
        Imgproc.findContours(padded, contours, new Mat(), RETR_EXTERNAL, CHAIN_APPROX_SIMPLE);
        int max_y = 10000;
        for (MatOfPoint cnt: contours){
            Rect rect = Imgproc.boundingRect(cnt);
            if (max_y > rect.y){
                max_y = rect.y;
            }
        }
        int temp_row_end = (int) (0.087*roi1.rows());
        Mat strip = roi1.submat(max_y, max_y + temp_row_end , 0, roi1.cols());
        Bitmap haha = Bitmap.createBitmap(strip.cols(), strip.rows(), Bitmap.Config.ARGB_8888);
        Utils.matToBitmap(strip, haha);
        Mat strip_blurred = roi1.submat(max_y , max_y + temp_row_end , 0, roi1.cols());
        Mat kernel = Mat.ones(new Size(5,5), CvType.CV_32F);
        Core.divide(kernel, new Scalar(25), kernel);
        System.out.println(kernel);
        Imgproc.filter2D(strip_blurred, strip_blurred, -1, kernel);
        double[] ratio = {0.65};
        int x_point = (int) (strip.width()*0.5);
        int radius = (int) (strip.width()/4);
        String[] reported = new String[1];
        for (int i = 0; i < 1; i++){
            int y_point = Math.round((int)(ratio[i]*strip.height()));
            String res = calc(strip_blurred.submat(new Rect(x_point-radius, y_point-radius, radius+radius, radius+radius)), String.valueOf(i+1), deviation);
            reported[i] = res;
            Imgproc.rectangle(strip, new Point(x_point-radius, y_point-radius), new Point(x_point+radius, y_point+radius), new Scalar(255, 0, 255), 1);
        }
        Bitmap bitmap12 = Bitmap.createBitmap(strip.cols(), strip.rows(), Bitmap.Config.ARGB_8888);
        final_strip = Bitmap.createBitmap(strip.cols(), strip.rows(), Bitmap.Config.ARGB_8888);
        Utils.matToBitmap(strip, bitmap12);
        Utils.matToBitmap(strip, final_strip);
        mImageView.setImageBitmap(bitmap12);
        SaveImage(bitmap12);
        return reported;
    }

    Bitmap final_strip;

    /**
     * This method calculates distance between two Scalar values(Colors).
     * @param tst This is the first Scalar value.
     * @param ref This is the second Scalar value.*/
    public double calculate_distance(Scalar tst, Scalar ref){
        double value = 0;
        for(int i = 0; i < 4; i++){
            value += Math.pow(tst.val[i]-ref.val[i], 2);
        }
        return value;
    }

    public double colourDistance(int red1,int green1, int blue1, int red2, int green2, int blue2)
    {
        double rmean = ( red1 + red2 )/2;
        int r = red1 - red2;
        int g = green1 - green2;
        int b = blue1 - blue2;
        double weightR = 2 + rmean/256;
        double weightG = 4.0;
        double weightB = 2 + (255-rmean)/256;
        return Math.sqrt(weightR*r*r + weightG*g*g + weightB*b*b);
    }

    /**
     * This method converts color from RGB to LAB color space.
     * @param inputColor This parameter is the color in RGB format.
     * @return double[] This returns the color in the LAB color space.*/
    public double[] rgb2lab (double[] inputColor ){
        int num = 0;
        Scalar RGB = new Scalar(0, 0, 0);
        for (double value: inputColor){
            value = value / 255;
            if (value > 0.04045){
                value = Math.pow((( value + 0.055) / 1.055), 2.4);
            }else {
                value = value / 12.92;
            }
            RGB.val[num] = value * 100;
            num = num + 1;
        }
        Scalar XYZ = new Scalar(0, 0, 0);
        double X = RGB.val[0] * 0.4124 + RGB.val[1] * 0.3576 + RGB.val[2] * 0.1805;
        double Y = RGB.val[0] * 0.2126 + RGB.val[1] * 0.7152 + RGB.val[2] * 0.0722;
        double Z = RGB.val[0] * 0.0193 + RGB.val[1] * 0.1192 + RGB.val[2] * 0.9505;
        XYZ.val[0] = X;
        XYZ.val[1] = Y;
        XYZ.val[2] = Z;
        XYZ.val[0] = XYZ.val[0] / 95.047;         // ref_X =  95.047   Observer= 2°, Illuminant= D65
        XYZ.val[1] = XYZ.val[1] / 100.0;          // ref_Y = 100.000
        XYZ.val[2] = XYZ.val[2] / 108.883;        // ref_Z = 108.883
        for (int i = 0; i < 3; i++){
            double value = XYZ.val[i];
            if (value > 0.008856){
                value = Math.pow(value, 0.3333333333333333);
            }
            else {
                value = ( 7.787 * value ) + ( 16 / 116 );
            }
            XYZ.val[i] = value;
        }
//        Scalar Lab = new Scalar(0, 0, 0);
        double[] lab = new double[3];
        lab[0] = (116 * XYZ.val[1]) - 16;
        lab[1] = 500 * (XYZ.val[0] - XYZ.val[1]);
        lab[2] = 200 * (XYZ.val[1] - XYZ.val[2]);
        return lab;
    }

    /**
     * This method determines yellow color in the given color of RGB color space.
     * @param RGB This parameter is the color in RGB color space
     * @return boolean This returns the result of determination.*/
    public boolean determine_yellow(double[] RGB) {
        double r = RGB[0] / 255;
        double g = RGB[1] / 255;
        double b = RGB[2] / 255;
        double[] rgb = new double[] {r, g, b};
        double _max = max(rgb);
        double _min = min(rgb);
        double diff = _max - _min;
        if(diff == 0.0){
            diff = 1.0;
        }
        double h = 0.0;
        if (_max == r){
            h = (g - b) / diff;
        }
        if (_max == g){
            h = 2.0 + (b - r) / diff;
        }
        if (_max == b){
            h = 4.0 + (r - g) / diff;
        }
        h *= 60;

        if (h < 0){
            h += 360;
        }
        System.out.println(h);
        return  h > 45 && h < 68;
    }

    /**
     * This method determines green color in the given color of RGB color space.
     * @param RGB This parameter is the color in RGB color space
     * @return boolean This returns the result of determination.*/
    public boolean determine_green(double[] RGB) {
        double r = RGB[0] / 255;
        double g = RGB[1] / 255;
        double b = RGB[2] / 255;
        double[] rgb = new double[] {r, g, b};
        double _max = max(rgb);
        double _min = min(rgb);
        double diff = _max - _min;
        if(diff == 0.0){
            diff = 1.0;
        }
        double h = 0.0;
        if (_max == r){
            h = (g - b) / diff;
        }
        if (_max == g){
            h = 2.0 + (b - r) / diff;
        }
        if (_max == b){
            h = 4.0 + (r - g) / diff;
        }
        h *= 60;

        if (h < 0){
            h += 360;
        }
        return h > 61 && h < 148;
    }

    private static int[] getRGBArr(int pixel) {
        int alpha = (pixel >> 24) & 0xff;
        int red = (pixel >> 16) & 0xff;
        int green = (pixel >> 8) & 0xff;
        int blue = (pixel) & 0xff;

        return new int[]{red, green, blue};
    }

    /**
     * This method matches isDetected urine strip patches with the list of different color patches stored in the assets folder.
     * @param test_patch This parameter is the image of isDetected strip patch.
     * @param index_no This parameter represents the folder name inside assets folder.
     * @param deviation This parameter is the deviation in RGB color in camera captured image.
     * @return String This returns the matched patch file name.*/
    public String calc(Mat test_patch, String index_no, List<Scalar> deviation){
        Mat temp_rgb_patch = new Mat();
        Imgproc.cvtColor(test_patch, test_patch, Imgproc.COLOR_RGBA2RGB);
        test_patch.copyTo(temp_rgb_patch);
        Scalar avg_color_test = Core.mean(test_patch);
        HashMap<String, Integer> color_seq = new HashMap<String, Integer>(){
            {
                put("r", 0);
                put("g", 0);
                put("b", 0);
            }
        };
        int max_pos = 0;

        for (int i = 0; i < 3; i++) {
            max_pos = avg_color_test.val[i] > avg_color_test.val[max_pos] ? i : max_pos;
        }

        int min_pos = 0;

        for (int i = 0; i < 3; i++) {
            min_pos = avg_color_test.val[i] < avg_color_test.val[min_pos] ? i : min_pos;
        }

        List<Integer> set1 = new ArrayList<>();
        set1.add(min_pos);
        set1.add(max_pos);
        List<Integer> set2 = new ArrayList<>();
        set2.add(0);
        set2.add(1);
        set2.add(2);
        set2.removeAll(set1);
        int mid_pos = set2.get(0);

        if (max_pos == 0)
        {
            color_seq.put("r", 2);
        }
        if (max_pos == 1)
        {
            color_seq.put("g", 2);
        }
        if (max_pos == 2)
        {
            color_seq.put("b", 2);
        }

        if (mid_pos == 0)
        {
            color_seq.put("r", 1);
        }
        if (mid_pos == 1)
        {
            color_seq.put("g", 1);
        }
        if (mid_pos == 2)
        {
            color_seq.put("b", 1);
        }

        // RGB to LAB
        for(int i = 0; i < test_patch.rows(); i++){
            for(int j = 0; j < test_patch.cols(); j++){
                double[] temp1 = rgb2lab(test_patch.get(i, j));
                temp1[0] += 15;
                test_patch.put(i, j, temp1);
            }
        }
        Scalar abc = Core.mean(test_patch);
        HashMap<String, Double> score = new HashMap<>();
        try {
            Mat temp = new Mat();
            String[] files = assetManager.list("pics/ua/" + index_no);
            Arrays.sort(files);
            for (String file: files){
                InputStream is = assetManager.open("pics/ua/" + index_no + "/" + file);
                Bitmap  bitmap = BitmapFactory.decodeStream(is);
                Utils.bitmapToMat(bitmap, temp);
                Mat ref_image = temp;
                Imgproc.resize(ref_image, ref_image, test_patch.size());
                Imgproc.cvtColor(ref_image, ref_image, Imgproc.COLOR_RGBA2RGB);
                Scalar avg_color_ref = Core.mean(ref_image);

                HashMap<String, Integer> ref_color_seq = new HashMap<String, Integer>(){
                    {
                        put("r", 0);
                        put("g", 0);
                        put("b", 0);
                    }
                };

                max_pos = 0;
                for (int i = 0; i < 3; i++) {
                    max_pos = avg_color_ref.val[i] > avg_color_ref.val[max_pos] ? i : max_pos;
                }

                min_pos = 0;
                for (int i = 0; i < 3; i++) {
                    min_pos = avg_color_ref.val[i] < avg_color_ref.val[min_pos] ? i : min_pos;
                }
                set1 = new ArrayList<>();
                set1.add(min_pos);
                set1.add(max_pos);
                set2 = new ArrayList<>();
                set2.add(0);
                set2.add(1);
                set2.add(2);
                set2.removeAll(set1);
                mid_pos = set2.get(0);

                if (max_pos == 0)
                {
                    ref_color_seq.put("r", 2);
                }
                if (max_pos == 1)
                {
                    ref_color_seq.put("g", 2);
                }
                if (max_pos == 2)
                {
                    ref_color_seq.put("b", 2);
                }

                if (mid_pos == 0)
                {
                    ref_color_seq.put("r", 1);
                }
                if (mid_pos == 1)
                {
                    ref_color_seq.put("g", 1);
                }
                if (mid_pos == 2)
                {
                    ref_color_seq.put("b", 1);
                }

                if (ref_color_seq.equals(color_seq)) {
                    // RGB to LAB
                    for(int i = 0; i < ref_image.rows(); i++){
                        for(int j = 0; j < ref_image.cols(); j++){
                            double[] temp1 = rgb2lab(ref_image.get(i, j));
                            ref_image.put(i, j, temp1);
                        }
                    }
                    Scalar avg_color_ref_lab = Core.mean(ref_image);
                    Scalar avg_color_test_lab;
                    avg_color_test_lab = abc;
                    double mf = calculate_distance(avg_color_test_lab, avg_color_ref_lab);
                    score.put(file, mf);
                }
            }

        } catch (IOException e) {
            Toast.makeText(this, "Error Loading dus11 files", Toast.LENGTH_LONG).show();
        }
        Map<String, Double> lab_score = sortByValue(score);

        if (lab_score.isEmpty()){
            try {
                Mat temp = new Mat();
                String[] files = assetManager.list("pics/ua/" + index_no);
                for (String file: files){
                    InputStream is = assetManager.open("pics/ua/" + index_no + "/" + file);
                    Bitmap  bitmap = BitmapFactory.decodeStream(is);
                    Utils.bitmapToMat(bitmap, temp);
                    Mat ref_image = temp;
                    Imgproc.resize(ref_image, ref_image, test_patch.size());
                    Imgproc.cvtColor(ref_image, ref_image, Imgproc.COLOR_RGBA2RGB);
                    // RGB to LAB
                    for(int i = 0; i < ref_image.rows(); i++){
                        for(int j = 0; j < ref_image.cols(); j++){
                            double[] temp1 = rgb2lab(ref_image.get(i, j));
                            ref_image.put(i, j, temp1);
                        }
                    }
                    Scalar avg_color_ref = Core.mean(ref_image);
                    double mf = calculate_distance(abc, avg_color_ref);
                    score.put(file, mf);
                }
            } catch (IOException e) {
                Toast.makeText(this, "Error Loading dus11 files", Toast.LENGTH_LONG).show();
            }
            lab_score = sortByValue(score);
        }

        Object myKey = lab_score.keySet().toArray()[0];
        String key = myKey.toString();
        patientAveragePatchTest += avg_color_test.toString();
        return  key;
    }

    /**
     * This method detects and processes RGB reference board.
     * @param ref This parameter is the image of isDetected urine board.
     * @return List<Scalar> This returns the isDetected RGB value from the RGB reference board.*/
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

    /**
     * This method adjusts gamma value in the given image.
     * @param image This parameter is the image in which gamma is to be adjusted.
     * @param gamma This parameter is the value of gamma.
     * @return Mat This returns the image with adjusted gamma.*/
    private Mat adjust_gamma(Mat image, double gamma) {
        Mat lut = new Mat(1, 256, CvType.CV_8UC1);
        lut.setTo(new Scalar(0));
        for (int i = 0; i < 256; i++) {
            lut.put(0, i, Math.pow(i/255.0, 1.0/gamma) * 255.0);
        }
        Core.LUT(image, lut, image);
        return image;
    }

    /**
     * This method formats and prepares the isDetected strip result.
     * @param report This parameter is the result of isDetected urine strip patches.
     * @return String[] This returns the formatted report.*/
    public String[] clearify(String[] report){
        uricAcidLevel = testPatchValues.get(0).get(report[0]);

        reportVal = new String[1];
        for (int i = 0; i < 1; i++){
            reportVal[i] = testPatchValues.get(i).get(report[i]);
//            report[i] = testPatchNames.get(i) + ":  " + testPatchValues.get(i).get(report[i]);
            String temp = testPatchValues.get(i).get(report[i]);
            if (temp.equals("700") || temp.equals("1100") || temp.equals("1500")){
                report[i] = "Abnormal";
            } else {
                report[i] = "Normal";
            }
//            report[i] = testPatchNames.get(i) + ":  " + (testPatchValues.get(i).get(report[i]).equals("100") || testPatchValues.get(i).get(report[i]).equals("300")? "Normal": "Abnormal");
        }
        System.out.println("Completed making report.");
        return report;
    }

    /**
     * This method shows full screen dialog containing urine report.
     * @param activity This parameter represents the activity in which the dialog is to be displayed.
     * @param report This parameter represents the formatted urine report.
     * */
    public void showDialog(UricAcidActivity activity, final String[] report){

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

        ListView listView = (ListView) dialog.findViewById(R.id.listview);
        ArrayAdapter arrayAdapter = new ArrayAdapter(this,R.layout.list_item, R.id.tv, report);
        listView.setAdapter(arrayAdapter);
        dialog.show();
    }

    /**
     * This method sorts HashMap by value.
     * @param score This parameter is the HashMap which needs to be sorted buy value.
     * @return HashMap<String, Double> This returns the sorted HashMap.*/
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

    /**
     * Checks permission for camera and storage
     */
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

    /**
     * This method is executed after image is captured.
     * @param requestCode This parameter is the request code for the activity.
     * @param resultCode This parameter is the result code for the result of the activity.
     * @param data This parameter represents the data returned from the activity.*/
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            try {
                startDetect = true;
                setPic();
                process();
            } catch (Exception e){
                Log.d("Camera Capture", "Failed to capture and save image.");
            }
        }
    }

    /**
     * This method creates the image file for the camera captured image.
     * @return File This returns the created image file  name.
     * */
    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.US).format(new Date());
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

    /**
     * This method calculated the angle of three spade points.
     * @param pt0 This parameter is the first point.
     * @param pt1 This parameter is the second point.
     * @param  pt2 This parameter is the third point.
     * @return double This returns the calculated angle.*/
    private static double getAngle(Point pt1, Point pt2, Point pt0)
    {
        double dx1 = pt1.x - pt0.x;
        double dy1 = pt1.y - pt0.y;
        double dx2 = pt2.x - pt0.x;
        double dy2 = pt2.y - pt0.y;
        return (dx1*dx2 + dy1*dy2)/Math.sqrt((dx1*dx1 + dy1*dy1)*(dx2*dx2 + dy2*dy2) + 1e-10);
    }

    /**
     * This method detects urine board from the given image.
     * @param img This parameter is the camera captured image.
     * @return Mat This returns the isDetected urine board image.*/
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
        return quad;
    }

    /**
     * This method sorts list of points according to x and y co ordinates.
     * @param corners This parameter is the list of corners(Points)
     * @return List<Point> This returns the sorted List of Point(Corner)*/
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

    /**
     * This method finds intersection between two lines.
     * @param a This parameter is a first line.
     * @param b This parameter is a second line.
     * @return Point This returns point of intersection*/
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

    /**
     * This method finds largest square from the list of MatOfPoint.
     * @param squares This parameter is a list of MatOfPoint.
     * @return int This returns the index of largest square.*/
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

    /**
     * This method is adjusts image dimension and orientation.*/
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

        currentImage = rotatedBitmap;
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
            uricAcidModel.setPt_no(ptNo);
            Log.d("pt_no",ptNo);
            uricAcidModel.setAcid_level(uricAcidLevel);
            uricAcidModel.setAverage_color(patientAveragePatchTest);
            uricAcidModel.setPhoto_uri(stripPhotoPathUri);

            String last_uric_acid_row_id = db.SaveUricAcid(uricAcidModel);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
                return 3;
            }
            uricAcidModel.setRow_id(last_uric_acid_row_id);
            return 1;
        }

        @Override
        protected void onPostExecute(Integer s) {
            super.onPostExecute(s);
            if (s == 2) {
                if (dialog.isShowing())
                    dialog.dismiss();
                Toast.makeText(getApplicationContext(), "Data Already Saved " , Toast.LENGTH_LONG).show();

            } else if (s == 3) {
                if (dialog.isShowing())
                    dialog.dismiss();
                Toast.makeText(getApplicationContext(), "Exception Caught ", Toast.LENGTH_LONG).show();

            } else {
                if (dialog.isShowing())
                    dialog.dismiss();
                pref.edit().putInt("UTF",1).apply();
                Log.d("vitalTestFlag",String.valueOf(pref.getInt("UTF",0)));
                UricAcidActivity.super.onBackPressed();
                Toast.makeText(getApplicationContext(), "Uric Acid Test Saved " , Toast.LENGTH_LONG).show();

            }
        }
    }

    public static Bitmap rotateImage(Bitmap source, float angle) {
        Matrix matrix = new Matrix();
        matrix.postRotate(angle);
        return Bitmap.createBitmap(source, 0, 0, source.getWidth(), source.getHeight(),
                matrix, true);
    }

    /**
     * This method is executed when back button is pressed.*/
    @Override
    public void onBackPressed() {
        // here back is handled in async postexecute to avoid memory leak  this activity is already killed in back
        DialogUtil.getOKCancelDialog(this, "", "Do you want to discard the  uric acid  test of " + patientModel.getPtName()+"?", "Yes","No", (dialogInterface, i) -> {
            UricAcidActivity.super.onBackPressed();
        });
    }
}
