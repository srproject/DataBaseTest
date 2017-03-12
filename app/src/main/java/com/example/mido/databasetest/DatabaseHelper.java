package com.example.mido.databasetest;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.text.TextUtils;
import android.widget.Toast;

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

    public Boolean CheckEditTextEmpty ;

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

    public void delete (String id ) {
        SQLiteDatabase db = this.getWritableDatabase();
        String DeleteQuery = "DELETE FROM " +TABLE_NAME+" WHERE id=" + id + ";";

        db.execSQL(DeleteQuery);

    }

    public void deleteall () {
        SQLiteDatabase db = this.getWritableDatabase();
        String DeleteQuery = "DELETE FROM " +TABLE_NAME;


        db.execSQL(DeleteQuery);

    }

    public void update(String name,String suberName,String mark,String id){




        String UpdateRecordQuery = "UPDATE "+TABLE_NAME+" SET NAME='" + name + "', SUBERNAME='" + suberName + "', MARK='" + mark + "' WHERE ID=" + id + ";";
        String GetSQliteQuery = "SELECT * FROM "+TABLE_NAME ;

        CheckEditTextIsEmptyOrNot( name,suberName, mark);

        if (CheckEditTextEmpty == false) {
            SQLiteDatabase db = this.getWritableDatabase();
            Cursor cursor;

            db.execSQL(UpdateRecordQuery);

            cursor = db.rawQuery(GetSQliteQuery, null);



        }
     }

    public void CheckEditTextIsEmptyOrNot(String Name,String PhoneNumber, String subject ){



        if(TextUtils.isEmpty(Name) || TextUtils.isEmpty(PhoneNumber) || TextUtils.isEmpty(subject)){

            CheckEditTextEmpty = true ;

        }
        else {
            CheckEditTextEmpty = false ;
        }
    }

}
