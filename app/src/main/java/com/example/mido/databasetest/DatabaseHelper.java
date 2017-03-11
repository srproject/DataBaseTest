package com.example.mido.databasetest;


import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by mido on 11/03/17.
 */

public class DatabaseHelper extends SQLiteOpenHelper {

    public static  final String DATABASE_NAME="Student.db";
    public static  final String TABLE_NAME="Student";
    public static  final String Col_1="ID";
    public static  final String Col_2="NAME";
    public static  final String Col_3="SUBERNAME";
    public static  final String Col_4="MARK";


    public DatabaseHelper(Context context ) {
        super(context, DATABASE_NAME, null, 1);


    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table Student (ID INTEGER PRIMARY KEY AUTOINCREMENT, NAME TEXT ,SUBERNAME TEXT ,MARK INTEGER)" );

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS"+TABLE_NAME);
        onCreate(db);

    }
    public boolean insertData (String name,String suberName,String mark) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(Col_2, name);
        contentValues.put(Col_3, suberName);
        contentValues.put(Col_4, mark);


        long result = db.insert(TABLE_NAME, null, contentValues);
        if (result == -1) {
            return false;
        }
        else {
            return true;
        }
    }
}