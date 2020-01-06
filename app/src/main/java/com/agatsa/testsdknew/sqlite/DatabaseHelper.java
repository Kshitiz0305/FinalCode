//package com.agatsa.testsdknew.sqlite;
//
//import android.content.Context;
//import android.database.sqlite.SQLiteDatabase;
//import android.database.sqlite.SQLiteOpenHelper;
//
//import com.getbase.android.schema.Schemas;
//import com.getbase.android.schema.Schemas.TableDefinitionOperation;
//import com.google.common.collect.ImmutableList;
//
//
//public class DatabaseHelper extends SQLiteOpenHelper {
//
//    Context mContext;
//    public static DatabaseHelper instance = null;
//
//    final static String DB_NAME = "Sunyahealth.db";
//    final static int DB_VERSION = 4;
//
//    public static DatabaseHelper getInstance(Context context) {
//        if (instance == null) {
//            instance = new DatabaseHelper(context);
//        }
//        return instance;
//    }
//
//    public DatabaseHelper(Context context) {
//        super(context, DB_NAME, null, DB_VERSION);
//        this.mContext = context;
//    }
//
//    @SuppressWarnings("deprecation")
//    public final Schemas SunyaHealhSchema = Schemas.Builder
//            .currentSchema(200,
//                    /*
//                    Create Table Patient
//                     */
//                    new Schemas.TableDefinition(Database.TAB_PT_DETAILS, ImmutableList.<TableDefinitionOperation>builder().add(
//                            new Schemas.AddColumn(Database.COLUMN_PT_USER_ID, "INTEGER PRIMARY KEY AUTOINCREMENT"),
//                            new Schemas.AddColumn(Database.COLUMN_PT_NAME, "VARCHAR(50)  "),
//                            new Schemas.AddColumn(Database.COLUMN_PT_ADDRESS, "VARCHAR(100) "),
//                            new Schemas.AddColumn(Database.COLUMN_PT_CONTACTNO, "VARCHAR(100) NULL"),
//                            new Schemas.AddColumn(Database.COLUMN_PT_EMAIL, "VARCHAR(100)  NULL"),
//                            new Schemas.AddColumn(Database.COLUMN_PT_AGE, "VARCHAR(50) NULL"),
//                            new Schemas.AddColumn(Database.COLUMN_PT_SEX, "VARCHAR(50) NULL"),
//                            new Schemas.AddColumn(Database.COLUMN_PT_MARITALSTATUS, "VARCHAR(50) "),
//                            new Schemas.AddColumn(Database.COLUMN_ADDEDDATE, "VARCHAR(50)  "),
//                            new Schemas.AddColumn(Database.COLUMN_UPDATEDDATE, "VARCHAR(50) NULL")
//
//                    ).build()),
//
//
//
//
//                    new Schemas.TableDefinition(Database.TABLE_VITAL_SIGN, ImmutableList.<TableDefinitionOperation>builder().add(
//                            new Schemas.AddColumn(Database.COLUMN_WEIGHT, "VARCHAR(50)  NULL"),
//                            new Schemas.AddColumn(Database.COLUMN_HEIGHT, "VARCHAR(50) "),
//                            new Schemas.AddColumn(Database.COLUMN_TEMP, "VARCHAR(50) "),
//                            new Schemas.AddColumn(Database.COLUMN_PULSE, "VARCHAR(100) "),
//                            new Schemas.AddColumn(Database.COLUMN_BP_S, "VARCHAR(100) NULL"),
//                            new Schemas.AddColumn(Database.COLUMN_BP_D, "VARCHAR(100)  NULL"),
//                            new Schemas.AddColumn(Database.COLUMN_STO2, "VARCHAR(50) NULL")
//
//                    ).build()),
////
//                    new Schemas.TableDefinition(Database.TABLE_ECG_SIGN, ImmutableList.<TableDefinitionOperation>builder().add(
//                            new Schemas.AddColumn(Database.COLUMN_HEARTRATE, "INTEGER NULL"),
//                            new Schemas.AddColumn(Database.COLUMN_PR, "VARCHAR(50)  NULL"),
//                            new Schemas.AddColumn(Database.COLUMN_QT, "VARCHAR(50) NULL"),
//                            new Schemas.AddColumn(Database.COLUMN_QTC, "VARCHAR(50) NULL"),
//                            new Schemas.AddColumn(Database.COLUMN_QRS, "VARCHAR(100) NULL"),
//                            new Schemas.AddColumn(Database.COLUMN_SDNN, "VARCHAR(100) NULL"),
//                            new Schemas.AddColumn(Database.COLUMN_RMSSD, "VARCHAR(100)  NULL"),
//                            new Schemas.AddColumn(Database.COLUMN_MRR, "VARCHAR(100)  NULL"),
//                            new Schemas.AddColumn(Database.COLUMN_FINDING, "VARCHAR(100)  NULL")
//
//
//                    ).build())
//
//            ).build();
//
//
//    @Override
//    public void onCreate(SQLiteDatabase db) {
//        Schemas.Schema currentSchema = SunyaHealhSchema.getCurrentSchema();
//        for (String table : currentSchema.getTables()) {
//            db.execSQL(currentSchema.getCreateTableStatement(table));
//        }
//
//    }
//
//    public void onOpen(SQLiteDatabase db) {
//        super.onOpen(db);
//    }
//
//    @Override
//    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
//        SunyaHealhSchema.upgrade(oldVersion, mContext, db);
//    }
//
//    @Override
//    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
//        super.onDowngrade(db, oldVersion, newVersion);
//        db.beginTransaction();
//        try {
////            db.execSQL("DROP TABLE IF EXISTS " + Database.TAB_QUESTION_ANSWER);
//
//
//            db.setTransactionSuccessful();
//
//        } finally {
//            db.endTransaction();
//            onCreate(db);
//        }
//
//    }
//
//
//    @Override
//    public void onConfigure(SQLiteDatabase db) {
//        super.onConfigure(db);
//    }
//
//    @Override
//    public synchronized void close() {
//        super.close();
//    }
//
//}
//
