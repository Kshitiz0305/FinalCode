package com.agatsa.testsdknew.ui;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;
import android.util.Log;
import com.agatsa.testsdknew.Models.BloodPressureModel;
import com.agatsa.testsdknew.Models.ECGReport;
import com.agatsa.testsdknew.Models.GlucoseModel;
import com.agatsa.testsdknew.Models.LongECGReport;
import com.agatsa.testsdknew.Models.PatientModel;
import com.agatsa.testsdknew.Models.TwoParameterUrineModel;
import com.agatsa.testsdknew.Models.UricAcidModel;
import com.agatsa.testsdknew.Models.UrineReport;
import com.agatsa.testsdknew.Models.VitalSign;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import io.reactivex.Flowable;


/**
 * Created by  kshitiz .
 */

public class LabDB extends SQLiteOpenHelper {


    // Database Version
    private static final int DATABASE_VERSION = 3;

    // Database Name
    private static final String DATABASE_NAME = "Test1.db";

    //     Common Column
    private static final String COLUMN_PT_NO = "ptNo";
    private static final String UUID = "ptUUID";
    private static final String COLUMN_ID = "id";

    // PatientTable
    private static final String TABLE_PT_DETAILS = "pt_details";
    // Columns for patient table

    //    private static final String COLUMN_PT_USER_ID = "userId";
    private static final String COLUMN_PT_NAME = "ptName";
    private static final String COLUMN_PT_ADDRESS = "ptAddress";
    private static final String COLUMN_PT_CONTACTNO = "ptContactNo";
    private static final String COLUMN_PT_EMAIL = "ptEmail";
    private static final String COLUMN_PT_AGE = "ptAge";
    private static final String COLUMN_PT_DOB= "ptDob";
    private static final String COLUMN_PT_SEX = "ptSex";
    private static final String COLUMN_PT_CITY = "ptCity";
    private static final String COLUMN_PT_MARITALSTATUS = "ptMaritalstatus";
    private static final String COLUMN_PT_NO_OF_BOYS = "ptNoOfBoys";
    private static final String COLUMN_PT_NO_OF_GIRLS = "ptNoOfGirls";
    private static final String COLUMN_PT_DRUG_ALLERGIES = "ptDrugAllergies";
    private static final String COLUMN_PT_DISEASE = "ptDisease";
    private static final String COLUMN_PT_MEDICATION_MEDICINE = "ptMedicationMedicine";
    private static final String COLUMN_PT_SMOKING = "ptSmoking";
    private static final String COLUMN_PT_ALCOHOL = "ptAlcohol";
    private static final String COLUMN_ADDEDDATE = "ptDateOfRegistration";
    private static final String COLUMN_UPDATEDDATE = "ptUpdated_date";

    // Patient Vital Sign
    private static final String TABLE_VITAL_SIGN = "vital_sign";

    // Columns of Vital Signs

    private static final String COLUMN_WEIGHT = "weight";
    private static final String COLUMN_HEIGHT = "height";
    private static final String COLUMN_TEMP = "tempt";
    private static final String COLUMN_PULSE = "pulse";
    private static final String COLUMN_STO2 = "sto2";
    private static final String COLUMN_BMI = "bmi";


//    private static final String COLUMN_VITAL_GLUCOSE= "glucose";


    // Patient Vital Sign
    private static final String TABLE_ECG_SIGN = "ptEcg";
    // Columns of  SINGLE LEAD ECG Signs

    private static final String COLUMN_HEARTRATE = "heartrate";
    private static final String COLUMN_PR = "pr";
    private static final String COLUMN_QT = "qt";
    private static final String COLUMN_QTC = "qtc";
    private static final String COLUMN_QRS = "qrs";
    private static final String COLUMN_SDNN = "sdnn";
    private static final String COLUMN_RMSSD = "rmssd";
    private static final String COLUMN_MRR = "mrr";
    private static final String COLUMN_FINDING = "finding";
    private static final String COLUMN_FILEPATH = "ptPath";

//    private static final String TABLE_GLUCOSE_SIGN = "glucose_sign";
//    // Columns of  SINGLE LEAD ECG Signs




    // Columns of  LONG LEAD ECG Signs
    private static final String TABLE_LONG_ECG_SIGN = "long_ecg_sign";

    private static final String COLUMN_LONG_HEARTRATE = "longheartrate";
    private static final String COLUMN_LONG_PR = "longpr";
    private static final String COLUMN_LONG_QT = "longqt";
    private static final String COLUMN_LONG_QTC = "longqtc";
    private static final String COLUMN_LONG_QRS = "longqrs";
    private static final String COLUMN_LONG_SDNN = "longsdnn";
    private static final String COLUMN_LONG_RMSSD = "longrmssd";
    private static final String COLUMN_LONG_MRR = "longmrr";
    private static final String COLUMN_LONG_FINDING = "longfinding";

    // Patient Diabetes Test
    private static final String TABLE_DIABETES = "diabetes_test";
    // Columns of Diabetes Test
    private static final String COLUMN_DIABETES = "diabetes";
    private static final String COLUMN_TEST_TIME = "timetest";
    private static final String COLUMN_TEST_TYPE = "testtype";
    private static final String COLUMN_LATEST_MEAL_TIME = "latestmealtime";
    private static final String COLUMN_LATEST_MEAL_TYPE = "latestmealtype";




    private static final String TABLE_BLOOD_PRESSURE = "blood_pressure_test";
    // Columns of Diabetes Test
    private static final String COLUMN_SYSTOLIC_AND_DIASTOLIC = "systolic_diastolic";
    private static final String COLUMN_SYSTOLIC = "systolic";
    private static final String COLUMN_DIASTOLIC = "diastolic";


    private static final String TABLE_TWO_PARAMETER_URINE_TEST = "two_parameter_urine_test";
    // Columns of Two Parameter Urine Test
    private static final String COLUMN_MICROALBUMINE_CREATININE = "microalbumine_creatinine";
    private static final String COLUMN_TWO_PARAMETER_AVERAGE_COLOR_TEST = "two_parameter_average_color_test";
    private static final String COLUMN_TWO_PARAMETER_STRIP_PHOTO_URI = "two_parameter_strip_photo_uri";


    private static final String TABLE_URIC_ACID = "uric_acid";
    // Columns of Uric Acid Test
    private static final String COLUMN_URINE_ACID_LEVEL = "acid_level";
    private static final String COLUMN_URIC_ACID_AVERAGE_COLOR_TEST = "uric_acid_average_color_test";
    private static final String COLUMN_URIC_ACID_STRIP_PHOTO_URI = "uric_acid_strip_photo_uri";






    // Patient Urine Test Report
    private static final String TABLE_URINE_TEST = "urine_test";

    // Columns of Urine
    private static final String COLUMN_AA = "aa";
    private static final String COLUMN_URINE_GLUCOSE = "glucose";
    private static final String COLUMN_BIL = "bil";
    private static final String COLUMN_KET = "ket";
    private static final String COLUMN_SG = "sg";
    private static final String COLUMN_BLOOD = "blood";
    private static final String COLUMN_PH = "ph";
    private static final String COLUMN_PROTEIN = "pro";
    private static final String COLUMN_URB = "urb";
    private static final String COLUMN_NITRATE = "nit";
    private static final String COLUMN_LEUK = "leuk";
    private static final String CULUMN_Latitude = "ptLatitude";
    private static final String CULUMN_Longitude = "ptLongitude";
    private static final String COLUMN_AVERAGE_COLOR_TEST = "average_colo_test";
    private static final String COLUMN_STRIP_PHOTO_URI = "strip_photo_uri";

    private static final String  COLUMN_ECGTYPE   = "ptEcgType";
//     "SL", "CSL", "LSL", "TL", "FT"




    public LabDB(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }



    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_PATIENT_TABLE = "CREATE TABLE " + TABLE_PT_DETAILS + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_PT_NO + " TEXT ,"
                + UUID + " TEXT  DEFAULT 'nil', "
                + COLUMN_PT_NAME + " TEXT,"
                + COLUMN_PT_ADDRESS + " TEXT,"
                + COLUMN_PT_CONTACTNO + " TEXT,"
                + COLUMN_PT_EMAIL + " TEXT,"
                + COLUMN_PT_AGE + " TEXT,"
                + COLUMN_PT_DOB + " TEXT,"
                + COLUMN_PT_SEX + " TEXT,"
                + COLUMN_PT_CITY + " TEXT,"
                + COLUMN_PT_MARITALSTATUS + " TEXT,"
                + COLUMN_PT_NO_OF_BOYS + " TEXT,"
                + COLUMN_PT_NO_OF_GIRLS + " TEXT,"
                + COLUMN_PT_DRUG_ALLERGIES + " TEXT,"
                + COLUMN_PT_DISEASE + " TEXT,"
                + COLUMN_PT_MEDICATION_MEDICINE + " TEXT,"
                + COLUMN_PT_SMOKING + " TEXT,"
                + COLUMN_PT_ALCOHOL + " TEXT,"
                + COLUMN_ADDEDDATE + " TEXT,"
                + CULUMN_Longitude + " DOUBLE DEFAULT 0,"
                + CULUMN_Latitude + " DOUBLE DEFAULT 0)";


        db.execSQL(CREATE_PATIENT_TABLE);
        String CREATE_VITAL_SIGN_TABLE = "CREATE TABLE " + TABLE_VITAL_SIGN + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_PT_NO + " INTEGER,"
                + COLUMN_WEIGHT + " REAL DEFAULT 0,"
                + COLUMN_HEIGHT + " REAL DEFAULT 0,"
                + COLUMN_TEMP + " REAL DEFAULT 0,"
                + COLUMN_PULSE + " REAL DEFAULT 0,"
                + COLUMN_STO2 + " REAL DEFAULT 0,"
                + COLUMN_BMI + " REAL DEFAULT 0,"
                + COLUMN_ADDEDDATE + " TEXT,"
                + COLUMN_UPDATEDDATE + " TEXT)";
        db.execSQL(CREATE_VITAL_SIGN_TABLE);
        String CREATE_DIABETES_TABLE = "CREATE TABLE " + TABLE_DIABETES + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_PT_NO + " INTEGER,"
                + COLUMN_DIABETES + " REAL DEFAULT 0,"
                + COLUMN_TEST_TIME + " REAL DEFAULT 0,"
                + COLUMN_TEST_TYPE + " REAL DEFAULT 0,"
                + COLUMN_LATEST_MEAL_TIME + " REAL DEFAULT 0,"
                + COLUMN_LATEST_MEAL_TYPE + " REAL DEFAULT 0,"
                + COLUMN_ADDEDDATE + " TEXT,"
                + COLUMN_UPDATEDDATE + " TEXT)";
        db.execSQL(CREATE_DIABETES_TABLE);

        String CREATE_TWO_PARAMETER_TABLE = "CREATE TABLE " + TABLE_TWO_PARAMETER_URINE_TEST + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_PT_NO + " INTEGER,"
                + COLUMN_MICROALBUMINE_CREATININE + " REAL DEFAULT 0,"
                + COLUMN_TWO_PARAMETER_AVERAGE_COLOR_TEST + " TEXT,"
                + COLUMN_TWO_PARAMETER_STRIP_PHOTO_URI+ " TEXT,"
                + COLUMN_ADDEDDATE + " TEXT,"
                + COLUMN_UPDATEDDATE + " TEXT)";
        db.execSQL(CREATE_TWO_PARAMETER_TABLE);

        String CREATE_URIC_ACID_TABLE = "CREATE TABLE " + TABLE_URIC_ACID + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_PT_NO + " INTEGER,"
                + COLUMN_URINE_ACID_LEVEL + " REAL DEFAULT 0,"
                + COLUMN_URIC_ACID_AVERAGE_COLOR_TEST + " TEXT,"
                + COLUMN_URIC_ACID_STRIP_PHOTO_URI+ " TEXT,"
                + COLUMN_ADDEDDATE + " TEXT,"
                + COLUMN_UPDATEDDATE + " TEXT)";
        db.execSQL(CREATE_URIC_ACID_TABLE);

        String CREATE_BLOOD_PRESSURE_TABLE = "CREATE TABLE " + TABLE_BLOOD_PRESSURE + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_PT_NO + " INTEGER,"
                + COLUMN_SYSTOLIC_AND_DIASTOLIC + " REAL DEFAULT 0,"
                + COLUMN_SYSTOLIC + " REAL DEFAULT 0,"
                + COLUMN_DIASTOLIC + " REAL DEFAULT 0,"
                + COLUMN_ADDEDDATE + " TEXT,"
                + COLUMN_UPDATEDDATE + " TEXT)";
        db.execSQL(CREATE_BLOOD_PRESSURE_TABLE);

        String CREATE_ECGSIGN_TABLE = "CREATE TABLE " + TABLE_ECG_SIGN + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_PT_NO + " INTEGER,"
                + COLUMN_HEARTRATE + " REAL DEFAULT 0,"
                + COLUMN_PR + " REAL DEFAULT 0,"
                + COLUMN_QT + " REAL DEFAULT 0,"
                + COLUMN_QTC + " REAL DEFAULT 0,"
                + COLUMN_QRS + " REAL DEFAULT 0,"
                + COLUMN_SDNN + " REAL DEFAULT 0,"
                + COLUMN_RMSSD + " REAL DEFAULT 0,"
                + COLUMN_MRR + " REAL DEFAULT 0,"
                + COLUMN_FINDING + " REAL DEFAULT 0,"
                + COLUMN_ADDEDDATE + " TEXT,"
                + COLUMN_ECGTYPE + " TEXT,"
                + COLUMN_FILEPATH + " TEXT DEFAULT 'notFound' ,"
                + COLUMN_UPDATEDDATE + " TEXT)";
        db.execSQL(CREATE_ECGSIGN_TABLE);

//        String CREATE_BLOOD_REPORT_TABLE = "CREATE TABLE " + TABLE_BLOOD_TEST + "("
//                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
//                + COLUMN_PT_NO + " INTEGER,"
//                + COLUMN_GLUCOSE + " REAL DEFAULT 0,"
//                + COLUMN_CHLORESTROL + " REAL DEFAULT 0,"
//                + COLUMN_URIC_ACID + " REAL DEFAULT 0,"
//                + COLUMN_ADDEDDATE + " TEXT,"
//                + COLUMN_UPDATEDDATE + " TEXT)";
//        db.execSQL(CREATE_BLOOD_REPORT_TABLE);
        String CREATE_URINE_REPORT_TABLE = "CREATE TABLE " + TABLE_URINE_TEST + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_PT_NO + " INTEGER,"
                + COLUMN_LEUK + " REAL DEFAULT 0,"
                + COLUMN_NITRATE + " REAL DEFAULT 0,"
                + COLUMN_URB + " REAL DEFAULT 0,"
                + COLUMN_PROTEIN + " REAL DEFAULT 0,"
                + COLUMN_PH + " REAL DEFAULT 0,"
                + COLUMN_BLOOD + " REAL DEFAULT 0,"
                + COLUMN_SG + " REAL DEFAULT 0,"
                + COLUMN_KET + " REAL DEFAULT 0,"
                + COLUMN_BIL + " REAL DEFAULT 0,"
                + COLUMN_URINE_GLUCOSE + " REAL DEFAULT 0,"
                + COLUMN_AA + " REAL DEFAULT 0,"
                + COLUMN_ADDEDDATE + " TEXT,"
                + COLUMN_UPDATEDDATE + " TEXT,"
                + COLUMN_AVERAGE_COLOR_TEST + " TEXT,"
                + COLUMN_STRIP_PHOTO_URI + " TEXT)";
        db.execSQL(CREATE_URINE_REPORT_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }

    public String SavePatient(PatientModel ptdetail) {
        String result = "";
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(COLUMN_PT_NO, ptdetail.getId());
        values.put(COLUMN_PT_NAME, ptdetail.getPtName());
        values.put(COLUMN_PT_ADDRESS, ptdetail.getPtAddress());

        values.put(COLUMN_PT_CONTACTNO, ptdetail.getPtContactNo());
        values.put(COLUMN_PT_EMAIL, ptdetail.getPtEmail());
        values.put(COLUMN_PT_AGE, ptdetail.getPtAge());
        values.put(COLUMN_PT_DOB, ptdetail.getPtDob());
        values.put(COLUMN_PT_SEX, ptdetail.getPtSex());
        values.put(COLUMN_PT_CITY, ptdetail.getPtCity());
        values.put(COLUMN_PT_MARITALSTATUS, ptdetail.getPtmaritalstatus());
        values.put(COLUMN_PT_NO_OF_BOYS, ptdetail.getPtnoofboys());
        values.put(COLUMN_PT_NO_OF_GIRLS, ptdetail.getPtnoofgirls());
        values.put(COLUMN_PT_DRUG_ALLERGIES, ptdetail.getPtdrugallergies());
//
        values.put(COLUMN_PT_DISEASE, ptdetail.getPtdiseases());
        values.put(COLUMN_PT_MEDICATION_MEDICINE, ptdetail.getPtmedicationmedicinename());
        values.put(COLUMN_PT_SMOKING, ptdetail.getPtsmoking());
        values.put(COLUMN_PT_ALCOHOL, ptdetail.getPtalcohol());

        Log.d("DOB",ptdetail.getPtDob());





//        This is trying to update previous line
//        if (!ptdetail.getId().equals("")) {
//
//            Log.d("rantest",ptdetail.getId());
//            db.update(TABLE_PT_DETAILS, values,  COLUMN_PT_NO + "=?",
//                    new String[]{(ptdetail.getId())});
//            result = ptdetail.getId();
//        } else {

//         Remaiing to pop message incase of failure in insertion
//     This is trying to insert new data
        Log.d("rantest","pateint is  blank");
        values.put(COLUMN_ADDEDDATE, Calendar.getInstance().getTimeInMillis() / 1000);
        db.insert(TABLE_PT_DETAILS, null, values);
        result = getLastID(TABLE_PT_DETAILS, db);
//        }
        db.close();
        return result;
    }
    public Flowable<List<PatientModel>> getPatientObservableByName(String name) {
        name+="%";


        List<PatientModel> patientModelList= new ArrayList<>();
        Flowable<List<PatientModel>> patientModelFlowable= Flowable.just(patientModelList);
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from " + TABLE_PT_DETAILS+" where ptName  like?", new String[]{name});
        while (cursor.moveToNext()) {
//            this model is to be rechecked
            PatientModel patientModel = new PatientModel();
            patientModel.setId(cursor.getString(0));
            patientModel.setPtNo(cursor.getString(1));
            patientModel.setmUuid(cursor.getString(2));
            patientModel.setPtName(cursor.getString(3));
            patientModel.setPtAddress(cursor.getString(4));
            patientModel.setPtContactNo(cursor.getString(5));
            patientModel.setPtEmail(cursor.getString(6));
            patientModel.setPtAge(cursor.getString(7));
            patientModel.setPtDob(cursor.getString(8));
            patientModel.setPtSex(cursor.getString(9));
            patientModel.setPtCity(cursor.getString(10));
            patientModelList.add(patientModel);


        }

        cursor.close();
        db.close();
        patientModelFlowable= Flowable.just(patientModelList);
        return patientModelFlowable;
    }
    public Flowable<List<PatientModel>> getPatientObservableId(String Id) {


        List<PatientModel> patientModelList= new ArrayList<>();
        Flowable<List<PatientModel>> patientModelFlowable= Flowable.just(patientModelList);
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from " + TABLE_PT_DETAILS+" where ptNo  =?", new String[]{Id});
        while (cursor.moveToNext()) {
            PatientModel patientModel = new PatientModel();
            patientModel.setRow_id(cursor.getString(0));
            patientModel.setId(cursor.getString(1));
            patientModel.setPtNo(cursor.getString(2));
            patientModel.setPtName(cursor.getString(3));
            patientModel.setPtAddress(cursor.getString(4));
            patientModel.setPtContactNo(cursor.getString(5));
            patientModel.setPtEmail(cursor.getString(6));
            patientModel.setPtAge(cursor.getString(7));
            patientModelList.add(patientModel);


        }

        cursor.close();
        db.close();
        patientModelFlowable= Flowable.just(patientModelList);
        return patientModelFlowable;
    }
    //    public String getJoinedTableJsonArray(String pt_no) {
//        try {
//            String columns = "user_id, pt.ptno AS ptno, ptName, ptAddress,ptContactNo, ptEmail, ptAge, ptdob, ptSex," +
//                    " weight, height, tempt, pulse, bp_s, bp_d, sto2, bt.glucose AS bt_glucose, chlorestrol, uric_acid," +
//                    " aa, ut.glucose as ut_glucose, bil, ket, sg, blood, ph, pro, urb, nit, leuk";
//            JSONArray jsonArray = new JSONArray();
//            int i = 1;
//            SQLiteDatabase db = this.getReadableDatabase();
//            Cursor cursor = db.rawQuery(
//                    "SELECT "+ columns +" FROM " + TABLE_PT_DETAILS + " pt," +
//                            TABLE_VITAL_SIGN + " vt," +
//                            TABLE_BLOOD_TEST + " bt," +
//                            TABLE_URINE_TEST + " ut " +
//                            "WHERE pt.id = vt.ptno AND pt.id = bt.ptno" +
//                            " AND pt.id = ut.ptno;", null);
//            if (cursor.moveToFirst()) {
//                do {
//                    JSONObject patientJsonObj = new JSONObject();
//                    String name[] = cursor.getString(cursor.getColumnIndex(COLUMN_PT_NAME)).split(" ");
//                    patientJsonObj.put("device", pt_no);
//                    patientJsonObj.put("user_id", cursor.getString(cursor.getColumnIndex(COLUMN_PT_NO)));
//                    patientJsonObj.put("first_name", name[0]);
//                    patientJsonObj.put("last_name", name[name.length - 1]);
//                    patientJsonObj.put("email", cursor.getString(cursor.getColumnIndex(COLUMN_PT_EMAIL)));
//                    patientJsonObj.put("address", cursor.getString(cursor.getColumnIndex(COLUMN_PT_ADDRESS)));
//                    patientJsonObj.put("contact_no", cursor.getString(cursor.getColumnIndex(COLUMN_PT_CONTACTNO)));
//                    patientJsonObj.put("age", cursor.getString(cursor.getColumnIndex(COLUMN_PT_AGE)));
//                    patientJsonObj.put("gender", cursor.getString(cursor.getColumnIndex(COLUMN_PT_SEX)));
//
//                    JSONObject vitalJsonObj = new JSONObject();
//
//                    vitalJsonObj.put("weight", cursor.getDouble(cursor.getColumnIndex(COLUMN_WEIGHT)));
//                    vitalJsonObj.put("height", cursor.getDouble(cursor.getColumnIndex(COLUMN_HEIGHT)));
//                    vitalJsonObj.put("temperature", cursor.getDouble(cursor.getColumnIndex(COLUMN_TEMP)));
//                    vitalJsonObj.put("pulse", cursor.getDouble(cursor.getColumnIndex(COLUMN_PULSE)));
//                    vitalJsonObj.put("bp_systolic", cursor.getDouble(cursor.getColumnIndex(COLUMN_BP_S)));
//                    vitalJsonObj.put("bp_diastolic", cursor.getDouble(cursor.getColumnIndex(COLUMN_BP_D)));
//                    vitalJsonObj.put("sto2", cursor.getDouble(cursor.getColumnIndex(COLUMN_STO2)));
//
//                    JSONObject bloodJsonObj = new JSONObject();
//
//                    bloodJsonObj.put("glucose", cursor.getDouble(cursor.getColumnIndex("bt_glucose")));
//                    bloodJsonObj.put("cholesterol", cursor.getDouble(cursor.getColumnIndex(COLUMN_CHLORESTROL)));
//                    bloodJsonObj.put("uric_acid", cursor.getDouble(cursor.getColumnIndex(COLUMN_URIC_ACID)));
//
//                    JSONObject urineJsonObj = new JSONObject();
//
//                    urineJsonObj.put("leukocytes", cursor.getDouble(cursor.getColumnIndex(COLUMN_LEUK)));
//                    urineJsonObj.put("nitrate", cursor.getDouble(cursor.getColumnIndex(COLUMN_NITRATE)));
//                    urineJsonObj.put("urobilinogen", cursor.getDouble(cursor.getColumnIndex(COLUMN_URB)));
//                    urineJsonObj.put("protein", cursor.getDouble(cursor.getColumnIndex(COLUMN_PROTEIN)));
//                    urineJsonObj.put("ph", cursor.getDouble(cursor.getColumnIndex(COLUMN_PH)));
//                    urineJsonObj.put("blood", cursor.getDouble(cursor.getColumnIndex(COLUMN_BLOOD)));
//                    urineJsonObj.put("sp_gravity", cursor.getDouble(cursor.getColumnIndex(COLUMN_SG)));
//                    urineJsonObj.put("ketone", cursor.getDouble(cursor.getColumnIndex(COLUMN_KET)));
//                    urineJsonObj.put("bilirubin", cursor.getDouble(cursor.getColumnIndex(COLUMN_BIL)));
//                    urineJsonObj.put("glucose", cursor.getDouble(cursor.getColumnIndex("ut_glucose")));
//                    urineJsonObj.put("ascorbic_acid", cursor.getDouble(cursor.getColumnIndex(COLUMN_AA)));
//
//                    JSONObject dataObj = new JSONObject();
//                    dataObj.put("id", String.valueOf(i));
//                    dataObj.put("user", patientJsonObj);
//                    dataObj.put("vital_sign", vitalJsonObj);
//                    dataObj.put("blood_test", bloodJsonObj);
//                    dataObj.put("urine_test", urineJsonObj);
//
//                    jsonArray.put(dataObj);
//                    i = i + 1;
//                } while (cursor.moveToNext());
//            }
//            cursor.close();
//            db.close();
//            if (jsonArray.length() == 0) {
//                return "";
//            } else {
//                return jsonArray.toString();
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//            return "";
//        }
//    }
    public String SaveVitalSign(VitalSign vitalSign) {
        String result = "";
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_PT_NO, vitalSign.getPt_no());
        values.put(COLUMN_WEIGHT, vitalSign.getWeight());
        values.put(COLUMN_HEIGHT, vitalSign.getHeight());
        values.put(COLUMN_TEMP, vitalSign.getTempt());
        values.put(COLUMN_PULSE, vitalSign.getPulse());
        values.put(COLUMN_STO2, vitalSign.getSto2());
        values.put(COLUMN_BMI, vitalSign.getBmi());
        values.put(COLUMN_UPDATEDDATE, Calendar.getInstance().getTimeInMillis() / 1000);
        if (!vitalSign.getRow_id() .equals("")) {
            db.update(TABLE_VITAL_SIGN, values, COLUMN_ID + "=?", new String[]{String.valueOf(vitalSign.getRow_id())});
            result = vitalSign.getRow_id();
        } else {
            values.put(COLUMN_ADDEDDATE, Calendar.getInstance().getTimeInMillis() / 1000);
            db.insert(TABLE_VITAL_SIGN, null, values);
            result = getLastID(TABLE_VITAL_SIGN, db);
        }
        db.close();
        return result;
    }

    public String SaveTwoParameterUrinetest(TwoParameterUrineModel twoParameterUrineModel) {
        String result = "";
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_PT_NO, twoParameterUrineModel.getPt_no());
        values.put(COLUMN_MICROALBUMINE_CREATININE, twoParameterUrineModel.getMicrocreat());
        values.put(COLUMN_TWO_PARAMETER_AVERAGE_COLOR_TEST, twoParameterUrineModel.getAveragecolortest());
        values.put(COLUMN_TWO_PARAMETER_STRIP_PHOTO_URI, twoParameterUrineModel.getPhotouri());
        values.put(COLUMN_UPDATEDDATE, Calendar.getInstance().getTimeInMillis() / 1000);
        if (!twoParameterUrineModel.getRow_id() .equals("")) {
            db.update(TABLE_TWO_PARAMETER_URINE_TEST, values, COLUMN_ID + "=?", new String[]{String.valueOf(twoParameterUrineModel.getRow_id())});
            result = twoParameterUrineModel.getRow_id();
        } else {
            values.put(COLUMN_ADDEDDATE, Calendar.getInstance().getTimeInMillis() / 1000);
            db.insert(TABLE_TWO_PARAMETER_URINE_TEST, null, values);
            result = getLastID(TABLE_TWO_PARAMETER_URINE_TEST, db);
        }
        db.close();
        return result;
    }

    public String SaveUricAcid(UricAcidModel uricAcidModel) {
        String result = "";
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_PT_NO, uricAcidModel.getPt_no());
        values.put(COLUMN_URINE_ACID_LEVEL, uricAcidModel.getAcid_level());
        values.put(COLUMN_URIC_ACID_AVERAGE_COLOR_TEST, uricAcidModel.getAverage_color());
        values.put(COLUMN_URIC_ACID_STRIP_PHOTO_URI, uricAcidModel.getPhoto_uri());
        values.put(COLUMN_UPDATEDDATE, Calendar.getInstance().getTimeInMillis() / 1000);
        if (!uricAcidModel.getRow_id() .equals("")) {
            db.update(TABLE_URIC_ACID, values, COLUMN_ID + "=?", new String[]{String.valueOf(uricAcidModel.getRow_id())});
            result = uricAcidModel.getRow_id();
        } else {
            values.put(COLUMN_ADDEDDATE, Calendar.getInstance().getTimeInMillis() / 1000);
            db.insert(TABLE_URIC_ACID, null, values);
            result = getLastID(TABLE_URIC_ACID, db);
        }
        db.close();
        return result;
    }

    public String SaveGlucoseSign(GlucoseModel glucoseModel) {
        String result = "";
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_PT_NO, glucoseModel.getPt_no());
        values.put(COLUMN_DIABETES, glucoseModel.getPtGlucose());
        values.put(COLUMN_TEST_TIME, glucoseModel.getPttimetaken());
        values.put(COLUMN_TEST_TYPE, glucoseModel.getPttesttype());
        values.put(COLUMN_LATEST_MEAL_TIME, glucoseModel.getPtlatestmealtime());
        values.put(COLUMN_LATEST_MEAL_TYPE, glucoseModel.getPtmealtype());
        values.put(COLUMN_UPDATEDDATE, Calendar.getInstance().getTimeInMillis() / 1000);

        if (!glucoseModel.getRow_id() .equals("")) {
            db.update(TABLE_DIABETES, values, COLUMN_ID + "=?", new String[]{String.valueOf(glucoseModel.getRow_id())});
            result = glucoseModel.getRow_id();
        } else {
            values.put(COLUMN_ADDEDDATE, Calendar.getInstance().getTimeInMillis());
            db.insert(TABLE_DIABETES, null, values);
            result = getLastID(TABLE_DIABETES, db);
        }
        db.close();
        return result;

    }
    public String SaveBloodpressureSign(BloodPressureModel bloodPressureModel) {
        String result = "";
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_PT_NO, bloodPressureModel.getPt_no());
        values.put(COLUMN_SYSTOLIC_AND_DIASTOLIC, bloodPressureModel.getSystolicdiastolic());
        values.put(COLUMN_SYSTOLIC, bloodPressureModel.getSystolic());
        values.put(COLUMN_DIASTOLIC, bloodPressureModel.getDiastolic());
        values.put(COLUMN_UPDATEDDATE, Calendar.getInstance().getTimeInMillis() / 1000);

        if (!bloodPressureModel.getRow_id() .equals("")) {
            db.update(TABLE_BLOOD_PRESSURE, values, COLUMN_ID + "=?", new String[]{String.valueOf(bloodPressureModel.getRow_id())});
            result = bloodPressureModel.getRow_id();
        } else {
            values.put(COLUMN_ADDEDDATE, Calendar.getInstance().getTimeInMillis() / 1000);
            db.insert(TABLE_BLOOD_PRESSURE, null, values);
            result = getLastID(TABLE_BLOOD_PRESSURE, db);
        }
        db.close();
        return result;

    }
    public Flowable<String> updateEcgObserVable(ECGReport ecgReport) {

        Flowable<String> idFlowable;


        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_PT_NO, ecgReport.getPt_no());
        values.put(COLUMN_HEARTRATE, ecgReport.getHeartrate());

        values.put(COLUMN_PR, ecgReport.getPr());
        values.put(COLUMN_QT, ecgReport.getQt());
        values.put(COLUMN_QTC, ecgReport.getQtc());
        values.put(COLUMN_QRS, ecgReport.getQrs());
        values.put(COLUMN_SDNN, ecgReport.getSdnn());
        values.put(COLUMN_RMSSD, ecgReport.getRmssd());
        values.put(COLUMN_MRR, ecgReport.getMrr());
        values.put(COLUMN_FINDING, ecgReport.getFinding());
        values.put(COLUMN_FILEPATH, ecgReport.getFilepath());
        values.put(COLUMN_UPDATEDDATE, Calendar.getInstance().getTimeInMillis() / 1000);
        values.put(COLUMN_ECGTYPE, ecgReport.getEcgType());
        if (!ecgReport.getRow_id().equals("")) {
            db.update(TABLE_ECG_SIGN, values, COLUMN_ID + "=?", new String[]{String.valueOf(ecgReport.getRow_id())});
            idFlowable =Flowable.just( ecgReport.getRow_id());
        } else {
            values.put(COLUMN_ADDEDDATE, Calendar.getInstance().getTimeInMillis() / 1000);
            db.insert(TABLE_ECG_SIGN, null, values);
            idFlowable =Flowable.just( getLastID(TABLE_ECG_SIGN,db));
        }
        db.close();
        return idFlowable;
    }
    public String SaveSingleleadECGSign(ECGReport ecgReport) {
        String result = "";
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_PT_NO, ecgReport.getPt_no());
        values.put(COLUMN_HEARTRATE, ecgReport.getHeartrate());

        values.put(COLUMN_PR, ecgReport.getPr());
        values.put(COLUMN_QT, ecgReport.getQt());
        values.put(COLUMN_QTC, ecgReport.getQtc());
        values.put(COLUMN_QRS, ecgReport.getQrs());
        values.put(COLUMN_SDNN, ecgReport.getSdnn());
        values.put(COLUMN_RMSSD, ecgReport.getRmssd());
        values.put(COLUMN_MRR, ecgReport.getMrr());
        values.put(COLUMN_FINDING, ecgReport.getFinding());
        values.put(COLUMN_UPDATEDDATE, Calendar.getInstance().getTimeInMillis() / 1000);
        values.put(COLUMN_ECGTYPE, ecgReport.getEcgType());
        if (!ecgReport.getRow_id().equals("")) {
            db.update(TABLE_ECG_SIGN, values, COLUMN_ID + "=?", new String[]{String.valueOf(ecgReport.getRow_id())});
            result = ecgReport.getRow_id();
        } else {
            values.put(COLUMN_ADDEDDATE, Calendar.getInstance().getTimeInMillis() / 1000);
            db.insert(TABLE_ECG_SIGN, null, values);
            result = getLastID(TABLE_ECG_SIGN, db);
        }
        db.close();
        return result;
    }
    public String SavelongleadECGSign(LongECGReport longECGReport) {
        String result = "";
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_PT_NO, longECGReport.getPt_no());
        values.put(COLUMN_LONG_HEARTRATE, longECGReport.getHeartrate());
        Log.d("longleadecg", String.valueOf(longECGReport.getHeartrate()));
        values.put(COLUMN_LONG_PR, longECGReport.getPr());
        values.put(COLUMN_LONG_QT, longECGReport.getQt());
        values.put(COLUMN_LONG_QTC, longECGReport.getQtc());
        values.put(COLUMN_LONG_QRS, longECGReport.getQrs());
        values.put(COLUMN_LONG_SDNN, longECGReport.getSdnn());
        values.put(COLUMN_LONG_RMSSD, longECGReport.getRmssd());
        values.put(COLUMN_LONG_MRR, longECGReport.getMrr());
        values.put(COLUMN_LONG_FINDING, longECGReport.getFinding());
        values.put(COLUMN_UPDATEDDATE, Calendar.getInstance().getTimeInMillis() / 1000);
        if (!longECGReport.getRow_id() .equals("")) {
            db.update(TABLE_LONG_ECG_SIGN, values, COLUMN_ID + "=?", new String[]{String.valueOf(longECGReport.getRow_id())});
            result = longECGReport.getRow_id();
        } else {
            values.put(COLUMN_ADDEDDATE, Calendar.getInstance().getTimeInMillis() / 1000);
            db.insert(TABLE_LONG_ECG_SIGN, null, values);
            result = getLastID(TABLE_LONG_ECG_SIGN, db);
        }
        db.close();
        return result;
    }
    public ECGReport getSingleLeadEcgSign(String pt_no,String ecgtype) {
        ECGReport ecgReport = new ECGReport();
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor=db.rawQuery("select "+COLUMN_ID+","+ COLUMN_HEARTRATE+","+COLUMN_PR+","+ COLUMN_QT+","+ COLUMN_QTC+","+COLUMN_QRS+","+ COLUMN_SDNN+","+ COLUMN_RMSSD+","+COLUMN_MRR+","+COLUMN_FINDING+","+COLUMN_ECGTYPE+" from "+ TABLE_ECG_SIGN +" where "+ COLUMN_ECGTYPE +"="+"\""+ecgtype+"\""+ " ORDER BY "+COLUMN_ID+" DESC LIMIT 1",null);

//        Cursor cursor = db.query(TABLE_ECG_SIGN, new String[]{COLUMN_ID, COLUMN_HEARTRATE,
//                        COLUMN_PR, COLUMN_QT, COLUMN_QTC,
//                        COLUMN_QRS, COLUMN_SDNN, COLUMN_RMSSD,COLUMN_MRR,COLUMN_FINDING,COLUMN_ECGTYPE}, COLUMN_PT_NO + "=?",
//                new String[]{String.valueOf(pt_no)}, null, null, COLUMN_ID + " DESC", String.valueOf(1));
        if (cursor.moveToFirst()) {
            ecgReport.setRow_id((cursor.getString(0)));
            ecgReport.setHeartrate((float) Double.parseDouble(cursor.getString(1)));
            ecgReport.setPr((float) Double.parseDouble(cursor.getString(2)));
            ecgReport.setQt((float) Double.parseDouble(cursor.getString(3)));
            ecgReport.setQtc((float) Double.parseDouble(cursor.getString(4)));
            ecgReport.setQrs((float) Double.parseDouble(cursor.getString(5)));
            ecgReport.setSdnn((float) Double.parseDouble(cursor.getString(6)));
            ecgReport.setRmssd((float) Double.parseDouble(cursor.getString(7)));
            ecgReport.setMrr((float) Double.parseDouble(cursor.getString(8)));
            ecgReport.setFinding((cursor.getString(9)));
        }
        ecgReport.setPt_no(pt_no);
        cursor.close();
        db.close();
        return ecgReport;
    }
    public VitalSign getLastVitalSign(String pt_no) {
        VitalSign vitalSign = new VitalSign();
        SQLiteDatabase db = this.getReadableDatabase();
//
        Cursor cursor = db.query(TABLE_VITAL_SIGN, new String[]{COLUMN_ID, COLUMN_WEIGHT,
                        COLUMN_HEIGHT, COLUMN_TEMP, COLUMN_PULSE, COLUMN_STO2,COLUMN_BMI}, COLUMN_PT_NO + "=?",
                new String[]{String.valueOf(pt_no)}, null, null, COLUMN_ID + " DESC", String.valueOf(1));
        if (cursor.moveToFirst()) {
            vitalSign.setRow_id((cursor.getString(0)));
            vitalSign.setWeight((cursor.getString(1)));
            vitalSign.setHeight((cursor.getString(2)));
            vitalSign.setTempt((cursor.getString(3)));
            vitalSign.setPulse((cursor.getString(4)));
            vitalSign.setSto2(cursor.getString(5));
            vitalSign.setBmi((cursor.getString(6)));

        }
        vitalSign.setPt_no(pt_no);
        cursor.close();
        db.close();
        return vitalSign;
    }
    public GlucoseModel getDiabetesSign(String pt_no) {
        GlucoseModel glucoseModel = new GlucoseModel();
        SQLiteDatabase db = this.getReadableDatabase();
//
        Cursor cursor = db.query(TABLE_DIABETES, new String[]{COLUMN_ID,
                        COLUMN_DIABETES,COLUMN_TEST_TIME,COLUMN_TEST_TYPE,COLUMN_LATEST_MEAL_TIME,COLUMN_LATEST_MEAL_TYPE
                        ,COLUMN_ADDEDDATE,COLUMN_UPDATEDDATE}, COLUMN_PT_NO + "=?",
                new String[]{String.valueOf(pt_no)}, null, null, COLUMN_ID + " DESC", String.valueOf(1));
        if (cursor.moveToFirst()) {
            glucoseModel.setRow_id(cursor.getString(0));
            glucoseModel.setPtGlucose(cursor.getString(1));
            glucoseModel.setPttimetaken((cursor.getString(2)));
            glucoseModel.setPttesttype((cursor.getString(3)));
            glucoseModel.setPtlatestmealtime((cursor.getString(4)));
            glucoseModel.setPtmealtype((cursor.getString(5)));
            glucoseModel.setAddeddate((cursor.getString(6)));
            glucoseModel.setUpdateddate((cursor.getString(7)));


        }
        glucoseModel.setPt_no(pt_no);
        cursor.close();
        db.close();
        return glucoseModel;
    }

    public PatientModel getPatientmodel(String pt_no) {
        PatientModel patientModel = new PatientModel();
        SQLiteDatabase db = this.getReadableDatabase();
//
        Cursor cursor = db.query(TABLE_PT_DETAILS, new String[]{COLUMN_ID,COLUMN_PT_NO,UUID,
                        COLUMN_PT_NAME,COLUMN_PT_ADDRESS,COLUMN_PT_CONTACTNO,COLUMN_PT_EMAIL,COLUMN_PT_AGE,
                        COLUMN_PT_DOB,COLUMN_PT_SEX,COLUMN_PT_CITY,COLUMN_PT_MARITALSTATUS,
                        COLUMN_PT_NO_OF_BOYS,COLUMN_PT_NO_OF_GIRLS,COLUMN_PT_DRUG_ALLERGIES,COLUMN_PT_DISEASE,COLUMN_PT_MEDICATION_MEDICINE,
                        COLUMN_PT_SMOKING,COLUMN_PT_ALCOHOL}, COLUMN_PT_NO + "=?",
                new String[]{String.valueOf(pt_no)}, null, null, COLUMN_ID + " DESC", String.valueOf(1));
        if (cursor.moveToFirst()) {
            patientModel.setId(cursor.getString(0));
            patientModel.setPtNo(cursor.getString(1));
            patientModel.setmUuid(cursor.getString(2));
            patientModel.setPtName(cursor.getString(3));
            patientModel.setPtAddress(cursor.getString(4));
            patientModel.setPtContactNo(cursor.getString(5));
            patientModel.setPtEmail(cursor.getString(6));
            patientModel.setPtAge(cursor.getString(7));
            patientModel.setPtDob(cursor.getString(8));
            patientModel.setPtSex(cursor.getString(9));
            patientModel.setPtCity(cursor.getString(10));
            patientModel.setPtmaritalstatus(cursor.getString(11));
            patientModel.setPtnoofboys(cursor.getString(12));
            patientModel.setPtnoofgirls(cursor.getString(13));
            patientModel.setPtdrugallergies(cursor.getString(14));
            patientModel.setPtdiseases(cursor.getString(15));
            patientModel.setPtmedicationmedicinename(cursor.getString(16));
            patientModel.setPtsmoking(cursor.getString(17));
            patientModel.setPtalcohol(cursor.getString(18));



        }
        patientModel.setPtNo(pt_no);
        cursor.close();
        db.close();
        return patientModel;
    }

    public ECGReport getlastecg(String pt_no) {
        ECGReport ecgReport = new ECGReport();
        SQLiteDatabase db = this.getReadableDatabase();
//
        Cursor cursor = db.query(TABLE_ECG_SIGN, new String[]{COLUMN_ID,COLUMN_HEARTRATE,
                        COLUMN_PR,COLUMN_QT,COLUMN_QTC,COLUMN_QRS,COLUMN_SDNN,
                        COLUMN_RMSSD,COLUMN_MRR,COLUMN_FINDING,
                }, COLUMN_PT_NO + "=?",
                new String[]{String.valueOf(pt_no)}, null, null, COLUMN_ID + " DESC", String.valueOf(1));
        if (cursor.moveToFirst()) {
            ecgReport.setRow_id(cursor.getString(0));
            ecgReport.setHeartrate(Double.parseDouble(cursor.getString(1)));
            ecgReport.setPr(Double.parseDouble(cursor.getString(2)));
            ecgReport.setQt(Double.parseDouble(cursor.getString(3)));
            ecgReport.setQtc(Double.parseDouble(cursor.getString(4)));
            ecgReport.setQrs(Double.parseDouble(cursor.getString(7)));
            ecgReport.setSdnn(Double.parseDouble(cursor.getString(8)));
            ecgReport.setRmssd(Double.parseDouble(cursor.getString(9)));
            ecgReport.setMrr(Double.parseDouble(cursor.getString(10)));
//            ecgReport.setFinding("0");





        }
        ecgReport.setPt_no(pt_no);
        cursor.close();
        db.close();
        return ecgReport;
    }

    public TwoParameterUrineModel gettwoparameterurinedata(String pt_no) {
        TwoParameterUrineModel twoParameterUrineModel = new TwoParameterUrineModel();
        SQLiteDatabase db = this.getReadableDatabase();
//
        Cursor cursor = db.query(TABLE_TWO_PARAMETER_URINE_TEST, new String[]{COLUMN_ID,
                        COLUMN_MICROALBUMINE_CREATININE}, COLUMN_PT_NO + "=?",
                new String[]{String.valueOf(pt_no)}, null, null, COLUMN_ID + " DESC", String.valueOf(1));
        if (cursor.moveToFirst()) {
            twoParameterUrineModel.setRow_id(cursor.getString(0));
            twoParameterUrineModel.setMicrocreat((cursor.getString(1)));


        }
        twoParameterUrineModel.setPt_no(pt_no);
        cursor.close();
        db.close();
        return twoParameterUrineModel;
    }

    public UricAcidModel geturicaciddata(String pt_no) {
        UricAcidModel uricAcidModel = new UricAcidModel();
        SQLiteDatabase db = this.getReadableDatabase();
//
        Cursor cursor = db.query(TABLE_URIC_ACID, new String[]{COLUMN_ID,
                        COLUMN_URINE_ACID_LEVEL}, COLUMN_PT_NO + "=?",
                new String[]{String.valueOf(pt_no)}, null, null, COLUMN_ID + " DESC", String.valueOf(1));
        if (cursor.moveToFirst()) {
            uricAcidModel.setRow_id(cursor.getString(0));
            uricAcidModel.setAcid_level((cursor.getString(1)));

        }
        uricAcidModel.setPt_no(pt_no);
        cursor.close();
        db.close();
        return uricAcidModel;
    }
    public BloodPressureModel getbloodpressuresign(String pt_no) {
        BloodPressureModel bloodPressureModel = new BloodPressureModel();
        SQLiteDatabase db = this.getReadableDatabase();
//
        Cursor cursor = db.query(TABLE_BLOOD_PRESSURE, new String[]{COLUMN_ID,
                        COLUMN_SYSTOLIC_AND_DIASTOLIC,COLUMN_SYSTOLIC,COLUMN_DIASTOLIC}, COLUMN_PT_NO + "=?",
                new String[]{String.valueOf(pt_no)}, null, null, COLUMN_ID + " DESC", String.valueOf(1));
        if (cursor.moveToFirst()) {
            bloodPressureModel.setRow_id(cursor.getString(0));
            bloodPressureModel.setSystolicdiastolic(cursor.getString(1));
            bloodPressureModel.setSystolic(cursor.getString(2));
            bloodPressureModel.setDiastolic(cursor.getString(3));


        }
        bloodPressureModel.setPt_no(pt_no);
        cursor.close();
        db.close();
        return bloodPressureModel;
    }
    public String SaveUrineReport(UrineReport urineReport) {
        String result = "";
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_PT_NO, urineReport.getPt_no());
        values.put(COLUMN_LEUK, urineReport.getLeuko());
        values.put(COLUMN_NITRATE, urineReport.getNit());
        values.put(COLUMN_URB, urineReport.getUrb());
        values.put(COLUMN_PROTEIN, urineReport.getProtein());
        values.put(COLUMN_PH, urineReport.getPh());
        values.put(COLUMN_BLOOD, urineReport.getBlood());
        values.put(COLUMN_SG, urineReport.getSg());
        values.put(COLUMN_KET, urineReport.getKet());
        values.put(COLUMN_BIL, urineReport.getBili());
        values.put(COLUMN_URINE_GLUCOSE, urineReport.getGlucose());
        values.put(COLUMN_AA, urineReport.getAsc());
        values.put(COLUMN_UPDATEDDATE, Calendar.getInstance().getTimeInMillis() / 1000);
        values.put(COLUMN_AVERAGE_COLOR_TEST, urineReport.getAvgcolortest());
        values.put(COLUMN_STRIP_PHOTO_URI, urineReport.getstripPhotoPathUri());
        db.insert(TABLE_URINE_TEST, null, values);
        result = getLastID(TABLE_URINE_TEST, db);
//        if (urineReport.getRow_id().equals("")) {
//            db.update(TABLE_URINE_TEST, values, COLUMN_ID + "=?", new String[]{String.valueOf(urineReport.getRow_id())});
//            result = urineReport.getRow_id();
//        } else {
//            values.put(COLUMN_ADDEDDATE, Calendar.getInstance().getTimeInMillis() / 1000);
//            db.insert(TABLE_URINE_TEST, null, values);
//            result = getLastID(TABLE_URINE_TEST, db);
//        }
        db.close();
        return result;
    }

    public UrineReport getLastUrineReport(String pt_no) {
        UrineReport urineReport = new UrineReport();
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_URINE_TEST, new String[]{
                        COLUMN_ID,
                        COLUMN_LEUK,
                        COLUMN_NITRATE,
                        COLUMN_URB,
                        COLUMN_PROTEIN,
                        COLUMN_PH,
                        COLUMN_BLOOD,
                        COLUMN_SG,
                        COLUMN_KET,
                        COLUMN_BIL,
                        COLUMN_URINE_GLUCOSE,
                        COLUMN_AA
                }, COLUMN_PT_NO + "=?",
                new String[]{String.valueOf(pt_no)}, null, null, COLUMN_ID + " DESC", String.valueOf(1));
        if (cursor.moveToFirst()) {
            urineReport.setRow_id((cursor.getString(0)));
            urineReport.setLeuko((cursor.getString(1)));
            urineReport.setNit((cursor.getString(2)));
            urineReport.setUrb((cursor.getString(3)));
            urineReport.setProtein((cursor.getString(4)));
            urineReport.setPh((cursor.getString(5)));
            urineReport.setBlood((cursor.getString(6)));
            urineReport.setSg((cursor.getString(7)));
            urineReport.setKet((cursor.getString(8)));
            urineReport.setBili((cursor.getString(9)));
            urineReport.setGlucose((cursor.getString(10)));
            urineReport.setAsc((cursor.getString(11)));
        }
        urineReport.setPt_no(pt_no);
        cursor.close();
        db.close();
        return urineReport;
    }
    private String getLastID(String table, SQLiteDatabase db) {
        String last_id = "";
        Cursor cursor = db.rawQuery("SELECT " + COLUMN_PT_NO + " FROM " + table
                + " ORDER BY " + COLUMN_PT_NO + " DESC LIMIT 1", new String[]{});
        boolean exists = (cursor.getCount() > 0);
        if (exists) {
            cursor.moveToFirst();
            last_id = (cursor.getString(0));
        }
        cursor.close();
        return last_id;
    }
    public int getRowCount(String table_name, String where_condn) {
        SQLiteDatabase db = this.getReadableDatabase();
        long count = DatabaseUtils.longForQuery(db, "SELECT COUNT (*) FROM " + table_name + where_condn, null);
        db.close();

        return (int) count;
    }




    @Override
    public void onOpen(SQLiteDatabase database) {
        super.onOpen(database);
        if (Build.VERSION.SDK_INT >= 28) {
            database.disableWriteAheadLogging();
        }
    }




}
