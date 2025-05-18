package com.example.vbps;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "BusPassDB";
    private static final int DATABASE_VERSION = 2; // Updated version to 2 for schema changes

    // Table and Columns
    private static final String TABLE_PASS = "pass";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_NAME = "name";
    private static final String COLUMN_FATHER_NAME = "father_name";
    private static final String COLUMN_GENDER = "gender";
    private static final String COLUMN_DOB = "dob";
    private static final String COLUMN_WHATSAPP = "whatsapp";
    private static final String COLUMN_INSTITUTION = "institution";
    private static final String COLUMN_ADMISSION = "admission";
    private static final String COLUMN_AADHAR = "aadhar";
    private static final String COLUMN_BUS_PASS_NUMBER = "bus_pass_number"; // New column for Bus Pass Number

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + TABLE_PASS + " (" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_NAME + " TEXT, " +
                COLUMN_FATHER_NAME + " TEXT, " +
                COLUMN_GENDER + " TEXT, " +
                COLUMN_DOB + " TEXT, " +
                COLUMN_WHATSAPP + " TEXT, " +
                COLUMN_INSTITUTION + " TEXT, " +
                COLUMN_ADMISSION + " TEXT, " +
                COLUMN_AADHAR + " TEXT, " +
                COLUMN_BUS_PASS_NUMBER + " TEXT)"; // Added the new column
        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion < 2) {
            // Add the new column if upgrading from version 1 to 2
            db.execSQL("ALTER TABLE " + TABLE_PASS + " ADD COLUMN " + COLUMN_BUS_PASS_NUMBER + " TEXT");
        }
    }

    public long insertPass(String name, String fatherName, String gender, String dob, String whatsapp, String institution, String admission, String aadhar) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, name);
        values.put(COLUMN_FATHER_NAME, fatherName);
        values.put(COLUMN_GENDER, gender);
        values.put(COLUMN_DOB, dob);
        values.put(COLUMN_WHATSAPP, whatsapp);
        values.put(COLUMN_INSTITUTION, institution);
        values.put(COLUMN_ADMISSION, admission);
        values.put(COLUMN_AADHAR, aadhar);
        long id = db.insert(TABLE_PASS, null, values);
        db.close();
        return id;
    }

    // New method to update Bus Pass Number
    public void updatePassNumber(long passId, String busPassNumber) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_BUS_PASS_NUMBER, busPassNumber);

        db.update(TABLE_PASS, values, COLUMN_ID + " = ?", new String[]{String.valueOf(passId)});
        db.close();
    }

    // Updated getPassByNumber Method: Query based on Bus Pass Number
    public Cursor getPassByNumber(String busPassNumber) {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.query(
                TABLE_PASS,
                null,
                COLUMN_BUS_PASS_NUMBER + " = ?", // Query based on bus_pass_number
                new String[]{busPassNumber},
                null,
                null,
                null
        );
    }
}
