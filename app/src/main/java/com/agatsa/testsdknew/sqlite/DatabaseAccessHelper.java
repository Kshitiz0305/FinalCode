//package com.agatsa.testsdknew.sqlite;
//
//import android.content.ContentValues;
//import android.content.Context;
//import android.database.Cursor;
//import android.database.DatabaseUtils;
//import android.database.sqlite.SQLiteDatabase;
//
//import com.agatsa.testsdknew.Models.PatientModel;
//
//import java.util.Calendar;
//
//import static com.agatsa.testsdknew.sqlite.Database.COLUMN_ADDEDDATE;
//import static com.agatsa.testsdknew.sqlite.Database.COLUMN_ID;
//import static com.agatsa.testsdknew.sqlite.Database.COLUMN_PT_ADDRESS;
//import static com.agatsa.testsdknew.sqlite.Database.COLUMN_PT_AGE;
//import static com.agatsa.testsdknew.sqlite.Database.COLUMN_PT_CONTACTNO;
//import static com.agatsa.testsdknew.sqlite.Database.COLUMN_PT_EMAIL;
//import static com.agatsa.testsdknew.sqlite.Database.COLUMN_PT_NAME;
//import static com.agatsa.testsdknew.sqlite.Database.COLUMN_PT_NO;
//import static com.agatsa.testsdknew.sqlite.Database.COLUMN_PT_SEX;
//import static com.agatsa.testsdknew.sqlite.Database.TAB_PT_DETAILS;
//
//
//
//public class DatabaseAccessHelper {
//
//    SQLiteDatabase db;
//    DatabaseHelper databaseAccessHelper;
//    Context mContext;
//
//    public DatabaseAccessHelper(Context context) {
//        this.mContext = context;
//        if (databaseAccessHelper == null) {
//            databaseAccessHelper = DatabaseHelper.getInstance(context);
//        }
//    }
//
//    public int SavePatient(PatientModel ptdetail) {
//        int result = 0;
//        db = databaseAccessHelper.getWritableDatabase();
//        ContentValues values = new ContentValues();
//        values.put(Database.COLUMN_PT_NO, ptdetail.getPtNo());
//        values.put(Database.COLUMN_PT_USER_ID, ptdetail.getUser_id());
//        values.put(Database.COLUMN_PT_NAME, ptdetail.getPtName());
//        values.put(Database.COLUMN_PT_ADDRESS, ptdetail.getPtAddress());
//        values.put(Database.COLUMN_PT_CONTACTNO, ptdetail.getPtContactNo());
//        values.put(Database.COLUMN_PT_EMAIL, ptdetail.getPtEmail());
//        values.put(Database.COLUMN_PT_AGE, ptdetail.getPtAge());
//        values.put(Database.COLUMN_PT_SEX, ptdetail.getPtSex());
//        values.put(Database.COLUMN_PT_MARITALSTATUS, ptdetail.getPtmaritalstatus());
//        values.put(Database.COLUMN_UPDATEDDATE, Calendar.getInstance().getTimeInMillis() / 1000);
//        if (ptdetail.getId() != 0) {
//            db.update(Database.TAB_PT_DETAILS, values, COLUMN_ID + "=? AND " + COLUMN_PT_NO + "=?",
//                    new String[]{String.valueOf(ptdetail.getId()), ptdetail.getPtNo()});
//            result = ptdetail.getId();
//        } else {
//            values.put(Database.COLUMN_ADDEDDATE, Calendar.getInstance().getTimeInMillis() / 1000);
//            db.insert(Database.TAB_PT_DETAILS, null, values);
//            result = getLastID(Database.TAB_PT_DETAILS, db);
//        }
//        db.close();
//        return result;
//    }
//
//    public PatientModel getPatient(String pt_no, int pt_id) {
//        PatientModel patientModel = new PatientModel();
//        db = databaseAccessHelper.getReadableDatabase();
//        Cursor cursor = db.query(TAB_PT_DETAILS, new String[]{
//                        COLUMN_ID,
//                        COLUMN_PT_NO,
//                        COLUMN_PT_NAME,
//                        COLUMN_PT_ADDRESS,
//                        COLUMN_PT_CONTACTNO,
//                        COLUMN_PT_EMAIL,
//                        COLUMN_PT_AGE,
//                        COLUMN_PT_SEX
//                }, COLUMN_PT_NO + "=? AND " + COLUMN_ID + "=?",
//                new String[]{pt_no, String.valueOf(pt_id)}, null, null, COLUMN_ID + " DESC", String.valueOf(1));
//        if (cursor.moveToFirst()) {
//            patientModel.setId(cursor.getInt(0));
//            patientModel.setPtNo(cursor.getString(1));
//            patientModel.setPtName(cursor.getString(2));
//            patientModel.setPtAddress(cursor.getString(3));
//            patientModel.setPtContactNo(cursor.getString(4));
//            patientModel.setPtEmail(cursor.getString(5));
//            patientModel.setPtAge(cursor.getString(6));
//            patientModel.setPtSex(cursor.getString(7));
//            patientModel.setPtmaritalstatus(cursor.getString(8));
//
//        }
//        cursor.close();
//        db.close();
//        return patientModel;
//    }
//
//
//    private int getLastID(String table, SQLiteDatabase db) {
//        int last_id = 0;
//        Cursor cursor = db.rawQuery("SELECT " + COLUMN_ID + " FROM " + table
//                + " ORDER BY " + COLUMN_ID + " DESC LIMIT 1", new String[]{});
//        boolean exists = (cursor.getCount() > 0);
//        if (exists) {
//            cursor.moveToFirst();
//            last_id = Integer.parseInt(cursor.getString(0));
//        }
//        cursor.close();
//        return last_id;
//    }
//    public int getRowCount(String table_name, String where_condn) {
//        db = databaseAccessHelper.getReadableDatabase();
//        long count = DatabaseUtils.longForQuery(db, "SELECT COUNT (*) FROM " + table_name + where_condn, null);
//        db.close();
//
//        return (int) count;
//    }
//
//
//
//
//
//
//
//}
