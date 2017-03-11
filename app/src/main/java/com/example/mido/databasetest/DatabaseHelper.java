package com.example.mido.databasetest;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by mido on 11/03/17.
 */

public class DatabaseHelper extends SQLiteOpenHelper {

    public static  final String DATABASE_NAME="Student.db";
    public static  final String TABLE_NAME="Student.table";
    public static  final String Col_1="ID";
    public static  final String Col_2="NAME";
    public static  final String Col_3="SUBERNAME";
    public static  final String Col_4="MARK";


    public DatabaseHelper(Context context ) {
        super(context, DATABASE_NAME, null, 1);
        SQLiteDatabase db =this.getWritableDatabase();

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table" + TABLE_NAME+ "(ID INTEGER PRIMARY KEY AUTOINCRMENT, NAME TEXT ,SUBERNAME TEXT ,MARK INTEGER)" );

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS"+TABLE_NAME);
        onCreate(db);

    }
}