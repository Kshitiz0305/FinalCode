package com.agatsa.testsdknew;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;
import android.os.Environment;
import android.util.Log;


import com.agatsa.testsdknew.Models.BloodReport;
import com.agatsa.testsdknew.Models.ECGReport;
import com.agatsa.testsdknew.Models.LongECGReport;
import com.agatsa.testsdknew.Models.PatientModel;
import com.agatsa.testsdknew.Models.UrineReport;
import com.agatsa.testsdknew.Models.VitalSign;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.Calendar;



/**
 * Created by sanjiv on 1/12/19.
 */

public class LabDB extends SQLiteOpenHelper {

    // Database Version
    private static final int DATABASE_VERSION = 3;

    // Database Name
    private static final String DATABASE_NAME = "newpathlogyonbox.db";

//     Common Column
    private static final String COLUMN_PT_NO = "ptno";
    private static final String COLUMN_ID = "id";

    // PatientTable
    private static final String TABLE_PT_DETAILS = "pt_details";
    // Columns for patient table

    private static final String COLUMN_PT_USER_ID = "user_id";
    private static final String COLUMN_PT_NAME = "ptName";
    private static final String COLUMN_PT_ADDRESS = "ptAddress";
    private static final String COLUMN_PT_CONTACTNO = "ptContactNo";
    private static final String COLUMN_PT_EMAIL = "ptEmail";
    private static final String COLUMN_PT_AGE = "ptAge";
    private static final String COLUMN_PT_DOB= "ptDob";
    private static final String COLUMN_PT_SEX = "ptSex";
    private static final String COLUMN_PT_MARITALSTATUS = "ptMaritalstatus";
    private static final String COLUMN_PT_NO_OF_BOYS = "ptNoofboys";
    private static final String COLUMN_PT_NO_OF_GIRLS = "ptNoofgirls";
    private static final String COLUMN_PT_DRUG_ALLERGIES = "ptDrugAllergies";
    private static final String COLUMN_PT_DISEASE = "ptDisease";
    private static final String COLUMN_PT_MEDICATION = "ptMedication";
    private static final String COLUMN_PT_MEDICATION_MEDICINE = "ptMedicationMedicine";
    private static final String COLUMN_PT_SMOKING = "ptSmoking";
    private static final String COLUMN_PT_ALCOHOL = "ptAlcohol";
    private static final String COLUMN_ADDEDDATE = "added_date";
    private static final String COLUMN_UPDATEDDATE = "updated_date";

    // Patient Vital Sign
    private static final String TABLE_VITAL_SIGN = "vital_sign";
    // Columns of Vital Signs

    private static final String COLUMN_WEIGHT = "weight";
    private static final String COLUMN_HEIGHT = "height";
    private static final String COLUMN_TEMP = "tempt";
    private static final String COLUMN_PULSE = "pulse";
    private static final String COLUMN_BP_S = "bp_s";
    private static final String COLUMN_BP_D = "bp_d";
    private static final String COLUMN_STO2 = "sto2";
    private static final String COLUMN_VITAL_GLUCOSE= "glucose";


    // Patient Vital Sign
    private static final String TABLE_ECG_SIGN = "ecg_sign";
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



    // Patient Blood Test Report
    private static final String TABLE_BLOOD_TEST = "blood_test";
    // Columns of Blood Test
    private static final String COLUMN_GLUCOSE = "glucose";
    private static final String COLUMN_CHLORESTROL = "chlorestrol";
    private static final String COLUMN_URIC_ACID = "uric_acid";

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

    // DATA TABLE
    private static final String TABLE_DATA = "urine_data";

    // FIELDS FOR urine_data
    private static final String COLUMN_DATA = "_data";

    public LabDB(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_PATIENT_TABLE = "CREATE TABLE " + TABLE_PT_DETAILS + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_PT_NO + " TEXT,"
                + COLUMN_PT_USER_ID + " TEXT,"
                + COLUMN_PT_NAME + " TEXT,"
                + COLUMN_PT_ADDRESS + " TEXT,"
                + COLUMN_PT_CONTACTNO + " TEXT,"
                + COLUMN_PT_EMAIL + " TEXT,"
                + COLUMN_PT_AGE + " TEXT,"
                + COLUMN_PT_DOB + " TEXT,"
                + COLUMN_PT_SEX + " TEXT,"
                + COLUMN_PT_MARITALSTATUS + " TEXT,"
                + COLUMN_PT_NO_OF_BOYS + " TEXT,"
                + COLUMN_PT_NO_OF_GIRLS + " TEXT,"
                + COLUMN_PT_DRUG_ALLERGIES + " TEXT,"
                + COLUMN_PT_DISEASE + " TEXT,"
                + COLUMN_PT_MEDICATION + " TEXT,"
                + COLUMN_PT_MEDICATION_MEDICINE + " TEXT,"
                + COLUMN_PT_SMOKING + " TEXT,"
                + COLUMN_PT_ALCOHOL + " TEXT,"
                + COLUMN_ADDEDDATE + " TEXT,"
                + COLUMN_UPDATEDDATE + " TEXT)";
        db.execSQL(CREATE_PATIENT_TABLE);
        String CREATE_VITAL_SIGN_TABLE = "CREATE TABLE " + TABLE_VITAL_SIGN + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_PT_NO + " INTEGER,"
                + COLUMN_WEIGHT + " REAL DEFAULT 0,"
                + COLUMN_HEIGHT + " REAL DEFAULT 0,"
                + COLUMN_TEMP + " REAL DEFAULT 0,"
                + COLUMN_PULSE + " REAL DEFAULT 0,"
                + COLUMN_BP_S + " REAL DEFAULT 0,"
                + COLUMN_BP_D + " REAL DEFAULT 0,"
                + COLUMN_STO2 + " REAL DEFAULT 0,"
                + COLUMN_VITAL_GLUCOSE + " REAL DEFAULT 0,"
                + COLUMN_ADDEDDATE + " TEXT,"
                + COLUMN_UPDATEDDATE + " TEXT)";
        db.execSQL(CREATE_VITAL_SIGN_TABLE);
//        String CREATE_GLUCOSE_TABLE = "CREATE TABLE " + TABLE_GLUCOSE_SIGN + "("
//                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
//                + COLUMN_PT_NO + " INTEGER,"
//                + COLUMN_GLUCOSE + " REAL DEFAULT 0,"
//                + COLUMN_ADDEDDATE + " TEXT,"
//                + COLUMN_UPDATEDDATE + " TEXT)";
//        db.execSQL(CREATE_GLUCOSE_TABLE);

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
                + COLUMN_UPDATEDDATE + " TEXT)";
        db.execSQL(CREATE_ECGSIGN_TABLE);
        String CREATE_LONG_ECG_SIGN_TABLE = "CREATE TABLE " + TABLE_LONG_ECG_SIGN + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_PT_NO + " INTEGER,"
                + COLUMN_LONG_HEARTRATE + " REAL DEFAULT 0,"
                + COLUMN_LONG_PR + " REAL DEFAULT 0,"
                + COLUMN_LONG_QT + " REAL DEFAULT 0,"
                + COLUMN_LONG_QTC + " REAL DEFAULT 0,"
                + COLUMN_LONG_QRS + " REAL DEFAULT 0,"
                + COLUMN_LONG_SDNN + " REAL DEFAULT 0,"
                + COLUMN_LONG_RMSSD + " REAL DEFAULT 0,"
                + COLUMN_LONG_MRR + " REAL DEFAULT 0,"
                + COLUMN_LONG_FINDING + " REAL DEFAULT 0,"
                + COLUMN_ADDEDDATE + " TEXT,"
                + COLUMN_UPDATEDDATE + " TEXT)";
        db.execSQL(CREATE_LONG_ECG_SIGN_TABLE);
        String CREATE_BLOOD_REPORT_TABLE = "CREATE TABLE " + TABLE_BLOOD_TEST + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_PT_NO + " INTEGER,"
                + COLUMN_GLUCOSE + " REAL DEFAULT 0,"
                + COLUMN_CHLORESTROL + " REAL DEFAULT 0,"
                + COLUMN_URIC_ACID + " REAL DEFAULT 0,"
                + COLUMN_ADDEDDATE + " TEXT,"
                + COLUMN_UPDATEDDATE + " TEXT)";
        db.execSQL(CREATE_BLOOD_REPORT_TABLE);
        String CREATE_URINE_REPORT_TABLE = "CREATE TABLE " + TABLE_URINE_TEST + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_PT_NO + " INTEGER,"
                + COLUMN_AA + " REAL DEFAULT 0,"
                + COLUMN_URINE_GLUCOSE + " REAL DEFAULT 0,"
                + COLUMN_BIL + " REAL DEFAULT 0,"
                + COLUMN_KET + " REAL DEFAULT 0,"
                + COLUMN_SG + " REAL DEFAULT 0,"
                + COLUMN_BLOOD + " REAL DEFAULT 0,"
                + COLUMN_PH + " REAL DEFAULT 0,"
                + COLUMN_PROTEIN + " REAL DEFAULT 0,"
                + COLUMN_URB + " REAL DEFAULT 0,"
                + COLUMN_NITRATE + " REAL DEFAULT 0,"
                + COLUMN_LEUK + " REAL DEFAULT 0,"
                + COLUMN_ADDEDDATE + " TEXT,"
                + COLUMN_UPDATEDDATE + " TEXT)";
        db.execSQL(CREATE_URINE_REPORT_TABLE);
        String CREATE_URINE_DATA_TABLE = "CREATE TABLE " + TABLE_DATA + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_PT_NO + " INTEGER,"
                + COLUMN_DATA + " TEXT)";
        db.execSQL(CREATE_URINE_DATA_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {






    }

    public int SavePatient(PatientModel ptdetail) {
        int result = 0;
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_PT_NO, ptdetail.getPtNo());
        values.put(COLUMN_PT_USER_ID, ptdetail.getUser_id());
        values.put(COLUMN_PT_NAME, ptdetail.getPtName());
        values.put(COLUMN_PT_ADDRESS, ptdetail.getPtAddress());
        values.put(COLUMN_PT_CONTACTNO, ptdetail.getPtContactNo());
        values.put(COLUMN_PT_EMAIL, ptdetail.getPtEmail());
        values.put(COLUMN_PT_AGE, ptdetail.getPtAge());
        values.put(COLUMN_PT_DOB, ptdetail.getPtDob());
        values.put(COLUMN_PT_SEX, ptdetail.getPtSex());
        values.put(COLUMN_PT_MARITALSTATUS, ptdetail.getPtmaritalstatus());
        values.put(COLUMN_PT_NO_OF_BOYS, ptdetail.getPtnoofboys());
       values.put(COLUMN_PT_NO_OF_GIRLS, ptdetail.getPtnoofgirls());
       values.put(COLUMN_PT_DRUG_ALLERGIES, ptdetail.getPtnoofgirls());
       values.put(COLUMN_PT_MEDICATION, ptdetail.getPtnoofgirls());
       values.put(COLUMN_PT_DRUG_ALLERGIES, ptdetail.getPtnoofgirls());
       values.put(COLUMN_PT_DISEASE, ptdetail.getPtdiseases());
       values.put(COLUMN_PT_MEDICATION, ptdetail.getPtmedication());
       values.put(COLUMN_PT_MEDICATION_MEDICINE, ptdetail.getPtmedicationmedicinename());
       values.put(COLUMN_PT_SMOKING, ptdetail.getPtsmoking());
       values.put(COLUMN_PT_ALCOHOL, ptdetail.getPtsmoking());
       Log.d("DOB",ptdetail.getPtDob());



        values.put(COLUMN_UPDATEDDATE, Calendar.getInstance().getTimeInMillis() / 1000);
        if (ptdetail.getId() != 0) {
            db.update(TABLE_PT_DETAILS, values, COLUMN_ID + "=? AND " + COLUMN_PT_NO + "=?",
                    new String[]{String.valueOf(ptdetail.getId()), ptdetail.getPtNo()});
            result = ptdetail.getId();
        } else {
            values.put(COLUMN_ADDEDDATE, Calendar.getInstance().getTimeInMillis() / 1000);
            db.insert(TABLE_PT_DETAILS, null, values);
            result = getLastID(TABLE_PT_DETAILS, db);
        }
        db.close();
        return result;
    }

    public PatientModel getPatient(String pt_no, int pt_id) {
        PatientModel patientModel = new PatientModel();
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_PT_DETAILS, new String[]{
                        COLUMN_ID,
                        COLUMN_PT_NO,
                        COLUMN_PT_NAME,
                        COLUMN_PT_ADDRESS,
                        COLUMN_PT_CONTACTNO,
                        COLUMN_PT_EMAIL,
                        COLUMN_PT_AGE,
                        COLUMN_PT_SEX
                }, COLUMN_PT_NO + "=? AND " + COLUMN_ID + "=?",
                new String[]{pt_no, String.valueOf(pt_id)}, null, null, COLUMN_ID + " DESC", String.valueOf(1));
        if (cursor.moveToFirst()) {
            patientModel.setId(cursor.getInt(0));
            patientModel.setPtNo(cursor.getString(1));
            patientModel.setPtName(cursor.getString(2));
            patientModel.setPtAddress(cursor.getString(3));
            patientModel.setPtContactNo(cursor.getString(4));
            patientModel.setPtEmail(cursor.getString(5));
            patientModel.setPtAge(cursor.getString(6));
            patientModel.setPtSex(cursor.getString(7));

        }
        cursor.close();
        db.close();
        return patientModel;
    }

    public String getJoinedTableJsonArray(String device_id) {
        try {
            String columns = "user_id, pt.ptno AS ptno, ptName, ptAddress,ptContactNo, ptEmail, ptAge, ptdob, ptSex," +
                    " weight, height, tempt, pulse, bp_s, bp_d, sto2, bt.glucose AS bt_glucose, chlorestrol, uric_acid," +
                    " aa, ut.glucose as ut_glucose, bil, ket, sg, blood, ph, pro, urb, nit, leuk";
            JSONArray jsonArray = new JSONArray();
            int i = 1;
            ArrayList<PatientModel> pm = new ArrayList<PatientModel>();
            SQLiteDatabase db = this.getReadableDatabase();
            Cursor cursor = db.rawQuery(
                    "SELECT "+ columns +" FROM " + TABLE_PT_DETAILS + " pt," +
                            TABLE_VITAL_SIGN + " vt," +
                            TABLE_BLOOD_TEST + " bt," +
                            TABLE_URINE_TEST + " ut " +
                            "WHERE pt.id = vt.ptno AND pt.id = bt.ptno" +
                            " AND pt.id = ut.ptno;", null);
            if (cursor.moveToFirst()) {
                do {
                    JSONObject patientJsonObj = new JSONObject();
                    String name[] = cursor.getString(cursor.getColumnIndex(COLUMN_PT_NAME)).split(" ");
                    patientJsonObj.put("device", device_id);
                    patientJsonObj.put("user_id", cursor.getString(cursor.getColumnIndex(COLUMN_PT_NO)));
                    patientJsonObj.put("first_name", name[0]);
                    patientJsonObj.put("last_name", name[name.length - 1]);
                    patientJsonObj.put("email", cursor.getString(cursor.getColumnIndex(COLUMN_PT_EMAIL)));
                    patientJsonObj.put("address", cursor.getString(cursor.getColumnIndex(COLUMN_PT_ADDRESS)));
                    patientJsonObj.put("contact_no", cursor.getString(cursor.getColumnIndex(COLUMN_PT_CONTACTNO)));
                    patientJsonObj.put("age", cursor.getString(cursor.getColumnIndex(COLUMN_PT_AGE)));
                    patientJsonObj.put("gender", cursor.getString(cursor.getColumnIndex(COLUMN_PT_SEX)));

                    JSONObject vitalJsonObj = new JSONObject();

                    vitalJsonObj.put("weight", cursor.getDouble(cursor.getColumnIndex(COLUMN_WEIGHT)));
                    vitalJsonObj.put("height", cursor.getDouble(cursor.getColumnIndex(COLUMN_HEIGHT)));
                    vitalJsonObj.put("temperature", cursor.getDouble(cursor.getColumnIndex(COLUMN_TEMP)));
                    vitalJsonObj.put("pulse", cursor.getDouble(cursor.getColumnIndex(COLUMN_PULSE)));
                    vitalJsonObj.put("bp_systolic", cursor.getDouble(cursor.getColumnIndex(COLUMN_BP_S)));
                    vitalJsonObj.put("bp_diastolic", cursor.getDouble(cursor.getColumnIndex(COLUMN_BP_D)));
                    vitalJsonObj.put("sto2", cursor.getDouble(cursor.getColumnIndex(COLUMN_STO2)));

                    JSONObject bloodJsonObj = new JSONObject();

                    bloodJsonObj.put("glucose", cursor.getDouble(cursor.getColumnIndex("bt_glucose")));
                    bloodJsonObj.put("cholesterol", cursor.getDouble(cursor.getColumnIndex(COLUMN_CHLORESTROL)));
                    bloodJsonObj.put("uric_acid", cursor.getDouble(cursor.getColumnIndex(COLUMN_URIC_ACID)));

                    JSONObject urineJsonObj = new JSONObject();

                    urineJsonObj.put("leukocytes", cursor.getDouble(cursor.getColumnIndex(COLUMN_LEUK)));
                    urineJsonObj.put("nitrate", cursor.getDouble(cursor.getColumnIndex(COLUMN_NITRATE)));
                    urineJsonObj.put("urobilinogen", cursor.getDouble(cursor.getColumnIndex(COLUMN_URB)));
                    urineJsonObj.put("protein", cursor.getDouble(cursor.getColumnIndex(COLUMN_PROTEIN)));
                    urineJsonObj.put("ph", cursor.getDouble(cursor.getColumnIndex(COLUMN_PH)));
                    urineJsonObj.put("blood", cursor.getDouble(cursor.getColumnIndex(COLUMN_BLOOD)));
                    urineJsonObj.put("sp_gravity", cursor.getDouble(cursor.getColumnIndex(COLUMN_SG)));
                    urineJsonObj.put("ketone", cursor.getDouble(cursor.getColumnIndex(COLUMN_KET)));
                    urineJsonObj.put("bilirubin", cursor.getDouble(cursor.getColumnIndex(COLUMN_BIL)));
                    urineJsonObj.put("glucose", cursor.getDouble(cursor.getColumnIndex("ut_glucose")));
                    urineJsonObj.put("ascorbic_acid", cursor.getDouble(cursor.getColumnIndex(COLUMN_AA)));

                    JSONObject dataObj = new JSONObject();
                    dataObj.put("id", String.valueOf(i));
                    dataObj.put("user", patientJsonObj);
                    dataObj.put("vital_sign", vitalJsonObj);
                    dataObj.put("blood_test", bloodJsonObj);
                    dataObj.put("urine_test", urineJsonObj);

                    jsonArray.put(dataObj);
                    i = i + 1;
                } while (cursor.moveToNext());
            }
            cursor.close();
            db.close();
            if (jsonArray.length() == 0) {
                return "";
            } else {
                return jsonArray.toString();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    public ArrayList<PatientModel> getAllPatientData() {
        ArrayList<PatientModel> pm = new ArrayList<PatientModel>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_PT_DETAILS, new String[]{
                COLUMN_ID,
                COLUMN_PT_NO,
                COLUMN_PT_NAME,
                COLUMN_PT_ADDRESS,
                COLUMN_PT_CONTACTNO,
                COLUMN_PT_EMAIL,
                COLUMN_PT_AGE,
                COLUMN_PT_SEX
        }, null, null, null, null, COLUMN_ID + " ASC", null);
        if (cursor.moveToFirst()) {
            do {
                PatientModel patientModel = new PatientModel();
                patientModel.setId(cursor.getInt(0));
                patientModel.setPtNo(cursor.getString(1));
                patientModel.setPtName(cursor.getString(2));
                patientModel.setPtAddress(cursor.getString(3));
                patientModel.setPtContactNo(cursor.getString(4));
                patientModel.setPtEmail(cursor.getString(5));
                patientModel.setPtAge(cursor.getString(6));
                patientModel.setPtSex(cursor.getString(7));
                pm.add(patientModel);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return pm;
    }

    public int SaveVitalSign(VitalSign vitalSign) {
        int result = 0;
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_PT_NO, vitalSign.getPt_no());
        values.put(COLUMN_WEIGHT, vitalSign.getWeight());
        values.put(COLUMN_HEIGHT, vitalSign.getHeight());
        values.put(COLUMN_TEMP, vitalSign.getTempt());
        values.put(COLUMN_PULSE, vitalSign.getPulse());
        values.put(COLUMN_STO2, vitalSign.getSto2());
        values.put(COLUMN_BP_D, vitalSign.getBpd());
        values.put(COLUMN_BP_S, vitalSign.getBps());
        values.put(COLUMN_VITAL_GLUCOSE, vitalSign.getGlucose());
        Log.d("glucose", String.valueOf(vitalSign.getGlucose()));
        values.put(COLUMN_UPDATEDDATE, Calendar.getInstance().getTimeInMillis() / 1000);
        if (vitalSign.getRow_id() != 0) {
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

//    public int SaveGlucoseSign(GlucoseModel glucoseModel) {
//        int result = 0;
//        SQLiteDatabase db = this.getWritableDatabase();
//        ContentValues values = new ContentValues();
//        values.put(COLUMN_PT_NO, glucoseModel.getPt_no());
//        values.put(COLUMN_GLUCOSE, glucoseModel.getPtGlucose());
//        values.put(COLUMN_UPDATEDDATE, Calendar.getInstance().getTimeInMillis() / 1000);
//        if (glucoseModel.getRow_id() != 0) {
//            db.update(TABLE_GLUCOSE_SIGN, values, COLUMN_ID + "=?", new String[]{String.valueOf(glucoseModel.getRow_id())});
//            result = glucoseModel.getRow_id();
//        } else {
//            values.put(COLUMN_ADDEDDATE, Calendar.getInstance().getTimeInMillis() / 1000);
//            db.insert(TABLE_GLUCOSE_SIGN, null, values);
//            result = getLastID(TABLE_GLUCOSE_SIGN, db);
//        }
//        db.close();
//        return result;
//    }


    public int SaveSingleleadECGSign(ECGReport ecgReport) {
        int result = 0;
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_PT_NO, ecgReport.getPt_no());
        values.put(COLUMN_HEARTRATE, ecgReport.getHeartrate());
        Log.d("singleleadecg", String.valueOf(ecgReport.getHeartrate()));
        values.put(COLUMN_PR, ecgReport.getPr());
        values.put(COLUMN_QT, ecgReport.getQt());
        values.put(COLUMN_QTC, ecgReport.getQtc());
        values.put(COLUMN_QRS, ecgReport.getQrs());
        values.put(COLUMN_SDNN, ecgReport.getSdnn());
        values.put(COLUMN_RMSSD, ecgReport.getRmssd());
        values.put(COLUMN_MRR, ecgReport.getMrr());
        values.put(COLUMN_FINDING, ecgReport.getFinding());
        values.put(COLUMN_UPDATEDDATE, Calendar.getInstance().getTimeInMillis() / 1000);
        if (ecgReport.getRow_id() != 0) {
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

    public int SavelongleadECGSign(LongECGReport longECGReport) {
        int result = 0;
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
        if (longECGReport.getRow_id() != 0) {
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

    public LongECGReport getlongLeadEcgSign(int pt_no) {
        LongECGReport longECGReport = new LongECGReport();
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_LONG_ECG_SIGN, new String[]{COLUMN_ID, COLUMN_LONG_HEARTRATE,
                        COLUMN_LONG_PR, COLUMN_LONG_QT, COLUMN_LONG_QTC,
                        COLUMN_LONG_QRS, COLUMN_LONG_SDNN, COLUMN_LONG_RMSSD,COLUMN_LONG_MRR,COLUMN_LONG_FINDING}, COLUMN_PT_NO + "=?",
                new String[]{String.valueOf(pt_no)}, null, null, COLUMN_ID + " DESC", String.valueOf(1));
        if (cursor.moveToFirst()) {
            longECGReport.setRow_id(Integer.parseInt(cursor.getString(0)));
            longECGReport.setHeartrate((float) Double.parseDouble(cursor.getString(1)));
            longECGReport.setPr((float) Double.parseDouble(cursor.getString(2)));
            longECGReport.setQt((float) Double.parseDouble(cursor.getString(3)));
            longECGReport.setQtc((float) Double.parseDouble(cursor.getString(4)));
            longECGReport.setQrs((float) Double.parseDouble(cursor.getString(5)));
            longECGReport.setSdnn((float) Double.parseDouble(cursor.getString(6)));
            longECGReport.setRmssd((float) Double.parseDouble(cursor.getString(7)));
            longECGReport.setMrr((float) Double.parseDouble(cursor.getString(8)));
            longECGReport.setFinding((cursor.getString(9)));
        }
        longECGReport.setPt_no(pt_no);
        cursor.close();
        db.close();
        return longECGReport;
    }


    public ECGReport getSingleLeadEcgSign(int pt_no) {
        ECGReport ecgReport = new ECGReport();
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_ECG_SIGN, new String[]{COLUMN_ID, COLUMN_HEARTRATE,
                        COLUMN_PR, COLUMN_QT, COLUMN_QTC,
                        COLUMN_QRS, COLUMN_SDNN, COLUMN_RMSSD,COLUMN_MRR,COLUMN_FINDING}, COLUMN_PT_NO + "=?",
                new String[]{String.valueOf(pt_no)}, null, null, COLUMN_ID + " DESC", String.valueOf(1));
        if (cursor.moveToFirst()) {
            ecgReport.setRow_id(Integer.parseInt(cursor.getString(0)));
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



    public VitalSign getLastVitalSign(int pt_no) {
        VitalSign vitalSign = new VitalSign();
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_VITAL_SIGN, new String[]{COLUMN_ID, COLUMN_WEIGHT,
                        COLUMN_HEIGHT, COLUMN_TEMP, COLUMN_PULSE,
                        COLUMN_BP_S, COLUMN_BP_D, COLUMN_STO2,COLUMN_VITAL_GLUCOSE}, COLUMN_PT_NO + "=?",
                new String[]{String.valueOf(pt_no)}, null, null, COLUMN_ID + " DESC", String.valueOf(1));
        if (cursor.moveToFirst()) {
            vitalSign.setRow_id(Integer.parseInt(cursor.getString(0)));
            vitalSign.setWeight((float) Double.parseDouble(cursor.getString(1)));
            vitalSign.setHeight((float) Double.parseDouble(cursor.getString(2)));
            vitalSign.setTempt((float) Double.parseDouble(cursor.getString(3)));
            vitalSign.setPulse((float) Double.parseDouble(cursor.getString(4)));
            vitalSign.setBps((float) Double.parseDouble(cursor.getString(5)));
            vitalSign.setBpd((float) Double.parseDouble(cursor.getString(6)));
            vitalSign.setSto2((float) Double.parseDouble(cursor.getString(7)));
            vitalSign.setGlucose((float) Double.parseDouble(cursor.getString(8)));
        }
        vitalSign.setPt_no(pt_no);
        cursor.close();
        db.close();
        return vitalSign;
    }

    public ArrayList<VitalSign> getAllVitalSignData() {
        ArrayList<VitalSign> vs = new ArrayList<VitalSign>();

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_VITAL_SIGN, new String[]{COLUMN_ID, COLUMN_WEIGHT,
                        COLUMN_HEIGHT, COLUMN_TEMP, COLUMN_PULSE,
                        COLUMN_BP_S, COLUMN_BP_D, COLUMN_STO2},
                null, null, null, null, COLUMN_ID + " ASC", null);
        if (cursor.moveToFirst()) {
            do {
                VitalSign vitalSign = new VitalSign();
                vitalSign.setRow_id(cursor.getInt(0));
                 vitalSign.setPt_no(cursor.getInt(1));
                vitalSign.setWeight((float) Double.parseDouble(cursor.getString(2)));
                vitalSign.setHeight((float) Double.parseDouble(cursor.getString(3)));
                vitalSign.setTempt((float) Double.parseDouble(cursor.getString(4)));
                vitalSign.setPulse((float) Double.parseDouble(cursor.getString(5)));
                vitalSign.setBps((float) Double.parseDouble(cursor.getString(6)));
                vitalSign.setBpd((float) Double.parseDouble(cursor.getString(7)));
                vitalSign.setSto2((float) Double.parseDouble(cursor.getString(8)));
                vs.add(vitalSign);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return vs;
    }

    public int SaveBloodReport(BloodReport bloodReport) {
        int result = 0;
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_PT_NO, bloodReport.getPt_no());
        values.put(COLUMN_URIC_ACID, bloodReport.getUric_acid());
        values.put(COLUMN_CHLORESTROL, bloodReport.getChlorestrol());
        values.put(COLUMN_GLUCOSE, bloodReport.getGlucose());
        values.put(COLUMN_UPDATEDDATE, Calendar.getInstance().getTimeInMillis() / 1000);
        if (bloodReport.getRow_id() != 0) {
            db.update(TABLE_BLOOD_TEST, values, COLUMN_ID + "=?", new String[]{String.valueOf(bloodReport.getRow_id())});
            result = bloodReport.getRow_id();
        } else {
            values.put(COLUMN_ADDEDDATE, Calendar.getInstance().getTimeInMillis() / 1000);
            db.insert(TABLE_BLOOD_TEST, null, values);
            result = getLastID(TABLE_BLOOD_TEST, db);
        }
        db.close();
        return result;
    }

    public BloodReport getLastBloodReport(int pt_no) {
        BloodReport bloodReport = new BloodReport();
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_BLOOD_TEST, new String[]{COLUMN_ID, COLUMN_GLUCOSE,
                        COLUMN_URIC_ACID, COLUMN_CHLORESTROL}, COLUMN_PT_NO + "=?",
                new String[]{String.valueOf(pt_no)}, null, null, COLUMN_ID + " DESC", String.valueOf(1));
        if (cursor.moveToFirst()) {
            bloodReport.setRow_id(Integer.parseInt(cursor.getString(0)));
            bloodReport.setGlucose((float) Double.parseDouble(cursor.getString(1)));
            bloodReport.setUric_acid((float) Double.parseDouble(cursor.getString(2)));
            bloodReport.setChlorestrol((float) Double.parseDouble(cursor.getString(3)));
        }
        bloodReport.setPt_no(pt_no);
        cursor.close();
        db.close();
        return bloodReport;
    }

    public ArrayList<BloodReport> getAllBloodReport() {
        ArrayList<BloodReport> br = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_BLOOD_TEST, new String[]{COLUMN_ID, COLUMN_GLUCOSE,
                        COLUMN_URIC_ACID, COLUMN_CHLORESTROL},
                null, null, null, null, COLUMN_ID + " ASC", null);
        if (cursor.moveToFirst()) {
            do {
                BloodReport bloodReport = new BloodReport();
                bloodReport.setRow_id(Integer.parseInt(cursor.getString(0)));
//            bloodReport.setPt_no(cursor.getInt(1));
                bloodReport.setGlucose((float) Double.parseDouble(cursor.getString(1)));
                bloodReport.setUric_acid((float) Double.parseDouble(cursor.getString(2)));
                bloodReport.setChlorestrol((float) Double.parseDouble(cursor.getString(3)));
                br.add(bloodReport);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return br;
    }

    public int SaveUrineReport(UrineReport urineReport) {
        int result = 0;
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
        if (urineReport.getRow_id() != 0) {
            db.update(TABLE_URINE_TEST, values, COLUMN_ID + "=?", new String[]{String.valueOf(urineReport.getRow_id())});
            result = urineReport.getRow_id();
        } else {
            values.put(COLUMN_ADDEDDATE, Calendar.getInstance().getTimeInMillis() / 1000);
            db.insert(TABLE_URINE_TEST, null, values);
            result = getLastID(TABLE_URINE_TEST, db);
        }
        db.close();
        return result;
    }

    public UrineReport getLastUrineReport(int pt_no) {
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
            urineReport.setRow_id(Integer.parseInt(cursor.getString(0)));
            urineReport.setLeuko((float) Double.parseDouble(cursor.getString(1)));
            urineReport.setNit((float) Double.parseDouble(cursor.getString(2)));
            urineReport.setUrb((float) Double.parseDouble(cursor.getString(3)));
            urineReport.setProtein((float) Double.parseDouble(cursor.getString(4)));
            urineReport.setPh((float) Double.parseDouble(cursor.getString(5)));
            urineReport.setBlood((float) Double.parseDouble(cursor.getString(6)));
            urineReport.setSg((float) Double.parseDouble(cursor.getString(7)));
            urineReport.setKet((float) Double.parseDouble(cursor.getString(8)));
            urineReport.setBili((float) Double.parseDouble(cursor.getString(9)));
            urineReport.setGlucose((float) Double.parseDouble(cursor.getString(10)));
            urineReport.setAsc((float) Double.parseDouble(cursor.getString(11)));
        }
        urineReport.setPt_no(pt_no);
        cursor.close();
        db.close();
        return urineReport;
    }

    public ArrayList<UrineReport> getAllUrineReport() {
        ArrayList<UrineReport> ur = new ArrayList<>();

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
                },
                null, null, null, null, COLUMN_ID + " ASC", null);
        if (cursor.moveToFirst()) {
            do {
                UrineReport urineReport = new UrineReport();
                urineReport.setRow_id(Integer.parseInt(cursor.getString(0)));
//            urineReport.setPt_no(Integer.parseInt(cursor.getString(1)));
                urineReport.setLeuko((float) Double.parseDouble(cursor.getString(1)));
                urineReport.setNit((float) Double.parseDouble(cursor.getString(2)));
                urineReport.setUrb((float) Double.parseDouble(cursor.getString(3)));
                urineReport.setProtein((float) Double.parseDouble(cursor.getString(4)));
                urineReport.setPh((float) Double.parseDouble(cursor.getString(5)));
                urineReport.setBlood((float) Double.parseDouble(cursor.getString(6)));
                urineReport.setSg((float) Double.parseDouble(cursor.getString(7)));
                urineReport.setKet((float) Double.parseDouble(cursor.getString(8)));
                urineReport.setBili((float) Double.parseDouble(cursor.getString(9)));
                urineReport.setGlucose((float) Double.parseDouble(cursor.getString(10)));
                urineReport.setAsc((float) Double.parseDouble(cursor.getString(11)));
                ur.add(urineReport);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return ur;
    }

    private boolean isPatientExist(int ptno, SQLiteDatabase db) {
        Cursor cursor = db.rawQuery("SELECT 1 FROM " + TABLE_PT_DETAILS
                + " WHERE " + COLUMN_ID + "=" + ptno, new String[]{});
        boolean exists = (cursor.getCount() > 0);
        cursor.close();
        db.close();
        return exists;
    }

    private int getLastID(String table, SQLiteDatabase db) {
        int last_id = 0;
        Cursor cursor = db.rawQuery("SELECT " + COLUMN_ID + " FROM " + table
                + " ORDER BY " + COLUMN_ID + " DESC LIMIT 1", new String[]{});
        boolean exists = (cursor.getCount() > 0);
        if (exists) {
            cursor.moveToFirst();
            last_id = Integer.parseInt(cursor.getString(0));
        }
        cursor.close();
        return last_id;
    }

    public int getLastID(String table) {
        int last_id = 0;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT " + COLUMN_ID + " FROM " + table
                + " ORDER BY " + COLUMN_ID + " DESC LIMIT 1", new String[]{});
        boolean exists = (cursor.getCount() > 0);
        if (exists) {
            cursor.moveToFirst();
            last_id = Integer.parseInt(cursor.getString(0));
        }
        cursor.close();
        return last_id;
    }

    public String getLastUserID(String table) {
        SQLiteDatabase db = this.getWritableDatabase();
        String last_user_id = "00000";
        Cursor cursor = db.rawQuery("SELECT " + COLUMN_PT_USER_ID + " FROM " + table
                + " ORDER BY " + COLUMN_PT_NO + " DESC LIMIT 1", new String[]{});
        boolean exists = (cursor.getCount() > 0);
        if (exists) {
            cursor.moveToFirst();
            last_user_id = cursor.getString(0);
        }
        cursor.close();
        db.close();
        if (last_user_id == null || last_user_id.equals("null")) {
            last_user_id = "00000";
        }
        return last_user_id;
    }

    public String getUserIdFromPtno(String table, int pt_id) {
        SQLiteDatabase db = this.getWritableDatabase();
        String user_id = "00000";
        Cursor cursor = db.rawQuery("SELECT " + COLUMN_PT_USER_ID + " FROM " + table
                + " WHERE " + COLUMN_ID + "=" + pt_id + " ORDER BY " + COLUMN_ID + " LIMIT 1", new String[]{});
        boolean exists = (cursor.getCount() > 0);
        if (exists) {
            cursor.moveToFirst();
            user_id = cursor.getString(0);
        }
        cursor.close();
        db.close();
        return user_id;
    }

    public void setUrineData(String data) {
        SQLiteDatabase db = this.getWritableDatabase();
        int last_id = getLastID(TABLE_DATA, db);
        ContentValues values = new ContentValues();
        values.put(COLUMN_DATA, data);
        if (last_id == 0) {
            // insert data
            db.insert(TABLE_DATA, null, values);
        } else {
            db.update(TABLE_DATA, values, COLUMN_ID + "=?", new String[]{String.valueOf(last_id)});
            // update data
        }
        db.close();
    }

    public String getUrineData() {
        String data = "";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_DATA, new String[]{});
        if (cursor.moveToFirst()) {
            data = cursor.getString(1);
        }
        cursor.close();
        db.close();
        return data;
    }

    public int getRowCount(String table_name, String where_condn) {
        SQLiteDatabase db = this.getReadableDatabase();
        long count = DatabaseUtils.longForQuery(db, "SELECT COUNT (*) FROM " + table_name + where_condn, null);
        db.close();

        return (int) count;
    }

    public int getCountForLastDetails(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        long count = DatabaseUtils.longForQuery(db, "SELECT COUNT (*) FROM " + TABLE_PT_DETAILS + " pt," +
                TABLE_VITAL_SIGN + " vt," +
                TABLE_BLOOD_TEST + " bt," +
                TABLE_URINE_TEST + " ut " +
                "WHERE pt.id="+id+" AND pt.id = vt.ptno AND pt.id = bt.ptno" +
                " AND pt.id = ut.ptno;", null);
        db.close();

        return (int) count;
    }

    public void exportDB(String fam_code_no, Context context) {
        File sd = Environment.getExternalStorageDirectory();
        File data = Environment.getDataDirectory();
        File a = new File(sd + "/SUNYA_HEALTH/");
        if (!a.exists()) {
            a.mkdirs();
        }
        FileChannel source = null;
        FileChannel destination = null;
//        String currentDBPath = "/data/com.sunyaHealth/databases/" + DATABASE_NAME;
        File currentDB = new File(context.getDatabasePath(DATABASE_NAME).getAbsolutePath());
        String backupDBPath = fam_code_no + ".db";
//        File currentDB = new File(data, currentDBPath);
        File backupDB = new File(sd + "/SUNYA_HEALTH/", backupDBPath);
        try {
            source = new FileInputStream(currentDB).getChannel();
            destination = new FileOutputStream(backupDB).getChannel();
            destination.transferFrom(source, 0, source.size());
            source.close();
            destination.close();
            //Toast.makeText(this, "DB Exported!", Toast.LENGTH_LONG).show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onOpen(SQLiteDatabase database) {
        super.onOpen(database);
        if (Build.VERSION.SDK_INT >= 28) {
            database.disableWriteAheadLogging();
        }
    }

    public void updateDbSequence(String table_name) {
        SQLiteDatabase db = this.getReadableDatabase();
        db.execSQL("UPDATE sqlite_sequence SET seq = (SELECT MAX(id) FROM " + table_name + ")" +
                " WHERE name='" + table_name + "'");
        db.close();
    }

    public void deleteAllData(String TABLE_NAME) {
        SQLiteDatabase db = this.getReadableDatabase();
        db.execSQL("Delete from " + TABLE_NAME);
        db.execSQL("Delete from sqlite_sequence where name='" + TABLE_NAME + "';");
        db.close();
    }

    public void deleteData(String TABLE_NAME, String where_condn) {
        SQLiteDatabase db = this.getReadableDatabase();
        db.execSQL("Delete from " + TABLE_NAME + " WHERE "+where_condn);
        updateDbSequence(TABLE_NAME);
        db.close();
    }

}
