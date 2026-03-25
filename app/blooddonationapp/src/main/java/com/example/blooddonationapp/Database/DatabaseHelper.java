package com.example.blooddonationapp.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.blooddonationapp.Model.Person;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "blood_donation.db";
    private static final int DATABASE_VERSION = 5; // ✅ Increased version

    private static final String TABLE_NAME = "donors";
    private static final String COL_ID = "id";
    private static final String COL_NAME = "name";
    private static final String COL_BLOOD = "blood_group";
    private static final String COL_PHONE = "phone";
    private static final String COL_AREA = "Area";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        // ✅ Donor Table
        String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + "("
                + COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COL_NAME + " TEXT,"
                + COL_BLOOD + " TEXT,"
                + COL_PHONE + " TEXT,"
                + COL_AREA + " TEXT)";
        db.execSQL(CREATE_TABLE);

        // ✅ User Table
        String CREATE_USER_TABLE = "CREATE TABLE user_table ("
                + "id INTEGER PRIMARY KEY AUTOINCREMENT,"
                + "username TEXT,"
                + "password TEXT)";
        db.execSQL(CREATE_USER_TABLE);

        // ✅ NEW: Requests Table (FIX FOR CRASH)
        String CREATE_REQUEST_TABLE = "CREATE TABLE requests ("
                + "id INTEGER PRIMARY KEY AUTOINCREMENT,"
                + "name TEXT,"
                + "blood_group TEXT,"
                + "phone TEXT,"
                + "hospital TEXT,"
                + "location TEXT)";
        db.execSQL(CREATE_REQUEST_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        // ✅ Drop all tables safely
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS user_table");
        db.execSQL("DROP TABLE IF EXISTS requests");
        onCreate(db);
    }

    // ================== DONOR ==================

    public void addDonor(Person person) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_NAME, person.getName());
        values.put(COL_BLOOD, person.getBloodGroup());
        values.put(COL_PHONE, person.getPhone());
        values.put(COL_AREA, person.getArea());

        db.insert(TABLE_NAME, null, values);
        db.close();
    }

    public List<Person> getAllDonors() {
        List<Person> list = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME, null);

        if (cursor.moveToFirst()) {
            do {
                Person person = new Person(
                        cursor.getInt(0),
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getString(3),
                        cursor.getString(4)
                );
                list.add(person);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return list;
    }

    public int updateDonor(Person person) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_NAME, person.getName());
        values.put(COL_BLOOD, person.getBloodGroup());
        values.put(COL_PHONE, person.getPhone());
        values.put(COL_AREA, person.getArea());

        return db.update(TABLE_NAME, values, COL_ID + "=?",
                new String[]{String.valueOf(person.getId())});
    }

    public void deleteDonor(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, COL_ID + "=?",
                new String[]{String.valueOf(id)});
        db.close();
    }

    public int getBloodGroupCount(String bloodGroup){
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(
                "SELECT * FROM donors WHERE blood_group = ?",
                new String[]{bloodGroup}
        );

        int count = cursor.getCount();
        cursor.close();
        return count;
    }

    public int getTotalDonors(){
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(
                "SELECT * FROM donors",
                null
        );

        int count = cursor.getCount();
        cursor.close();
        return count;
    }

    // ================== REQUEST ==================

    // ✅ Add Blood Request
    public boolean addRequest(String name, String blood, String phone, String hospital, String location){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("name", name);
        values.put("blood_group", blood);
        values.put("phone", phone);
        values.put("hospital", hospital);
        values.put("location", location);

        long result = db.insert("requests", null, values);
        return result != -1;
    }

    // ✅ Total Requests (FIXED)
    public int getTotalRequests(){
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT COUNT(*) FROM requests", null);

        int count = 0;
        if(cursor.moveToFirst()){
            count = cursor.getInt(0);
        }

        cursor.close();
        return count;
    }

    // ================== USER ==================

    public boolean updatePassword(String username, String newPassword){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values= new ContentValues();
        values.put("password", newPassword);

        int result = db.update("user_table", values,"username=?",new String[]{username});

        return result>0;
    }

    public boolean registerUser(String username, String password){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values=new ContentValues();
        values.put("username", username);
        values.put("password", password);

        long result=db.insert("user_table", null, values);
        return result != -1;
    }

    public boolean checkUser(String username, String password){

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(
                "SELECT * FROM user_table WHERE username=? AND password=?",
                new String[]{username, password}
        );

        boolean exists = cursor.getCount() > 0;
        cursor.close();

        return exists;
    }
    public List<Person> getAllRequests() {
        List<Person> list = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM requests", null);

        if (cursor.moveToFirst()) {
            do {
                Person person = new Person(
                        cursor.getInt(0),
                        cursor.getString(1), // name
                        cursor.getString(2), // blood
                        cursor.getString(3), // phone
                        cursor.getString(4), // hospital
                        cursor.getString(5)  // location
                );
                list.add(person);
            } while (cursor.moveToNext());
        }

        cursor.close();
        return list;
    }
    // ✅ Request Blood Group Count
    public int getRequestBloodGroupCount(String bloodGroup){

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(
                "SELECT * FROM requests WHERE blood_group = ?",
                new String[]{bloodGroup}
        );

        int count = cursor.getCount();
        cursor.close();

        return count;
    }
}