package com.example.mido.databasetest;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by mido on 11/03/17.
 */

public class DatabaseHelper extends SQLiteOpenHelper {

    public static  final String DATABASE_NAME="Student0.db";
    public static final String TABLE_NAME="Student";
    public static  final String Col_1="ID";
    public static  final String Col_2="NAME";
    public static  final String Col_3="LON";
    public static  final String Col_4="LAT";
    public static final String TABLE_NAME2="event";
    public static  final String event_id="event_id";
    public static  final String event_type="event_type";
    public static  final String event_det="event_det";
    public static  final String event_photo="event_photo";


    public DatabaseHelper(Context context ) {
        super(context, DATABASE_NAME, null, 1);

       // context.deleteDatabase(DATABASE_NAME);



    }

    @Override
    public void onCreate(SQLiteDatabase db) {


        db.execSQL("create table event (event_id INTEGER PRIMARY KEY AUTOINCREMENT, event_type VARCHAR ,event_det VARCHAR ) ;" );

       db.execSQL("create table Student (ID INTEGER PRIMARY KEY AUTOINCREMENT, NAME VARCHAR ,LON VARCHAR ,LAT VARCHAR) ;  " );

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS"+TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS event");
        onCreate(db);

    }
    /* function to add the data in database */
    public boolean insertData (String name,String lon,String lat) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();

        contentValues.put(Col_2, name);
        contentValues.put(Col_3, lon);
        contentValues.put(Col_4, lat);




        long result = db.insert(TABLE_NAME, null, contentValues);
        if (result == -1) {
            return false;
        }
        else {
            return true;
        }
    }

    /* function to add the data in database */
    public boolean insertDataforeventtest (String ievent_type,String ievent_det) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("event_type", ievent_type);
        contentValues.put("event_det", ievent_det);
        //contentValues.put(event_photo, ievent_photo);





        long result = db.insert("event", null, contentValues);
        if (result == -1) {
            return false;
        }
        else {
            return true;
        }
    }
/* function to show the data */

    public Cursor getAllData() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res= db.rawQuery("select * from " +TABLE_NAME,null);
        return  res;

    }

    //function to show the data */

    public Cursor getAllDataforeventtest() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res= db.rawQuery("select * from event ",null);
        return  res;

    }

    /* function to updata data */

    public boolean updataData(String name,String lon,String lat,String id){
        SQLiteDatabase db = this.getWritableDatabase();


        String UpdateRecordQuery = "UPDATE "+TABLE_NAME+" SET NAME='" + name + "', LON='" + lon + "', LAT='" + lat + "' WHERE ID=" + id + ";";



        db.execSQL(UpdateRecordQuery);

         return true;
    }
    /* function to delet Data data */

    public boolean deletData(String id){
        SQLiteDatabase db = this.getWritableDatabase();


        String UpdateRecordQuery = "DELETE FROM "+TABLE_NAME+" WHERE ID = "+id+";";



        db.execSQL(UpdateRecordQuery);

        return true;
    }
    /* function to delet all Data data */

    public boolean deletallData( ){
        SQLiteDatabase db = this.getWritableDatabase();


        String UpdateRecordQuery = "DELETE FROM "+TABLE_NAME;


        db.execSQL(UpdateRecordQuery);

        return true;
    }
    public boolean deletallDataevent( ){
        SQLiteDatabase db = this.getWritableDatabase();


        String UpdateRecordQuery = "DELETE FROM "+TABLE_NAME2;


        db.execSQL(UpdateRecordQuery);

        return true;
    }


}