package com.example.mido.databasetest;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class ListViewActivity extends AppCompatActivity {

    DatabaseHelper SQLITEHELPER;
    SQLiteDatabase SQLITEDATABASE;
    Cursor cursor;
    SQLiteListAdapter ListAdapter ;

    ArrayList<String> ID_ArrayList = new ArrayList<String>();
    ArrayList<String> NAME_ArrayList = new ArrayList<String>();
    ArrayList<String> PHONE_NUMBER_ArrayList = new ArrayList<String>();
    ArrayList<String> SUBJECT_ArrayList = new ArrayList<String>();
    ListView LISTVIEW;
    Button buuplist;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);



        LISTVIEW = (ListView) findViewById(R.id.list);

        SQLITEHELPER = new DatabaseHelper(this);

        buuplist=(Button)findViewById(R.id.buuplist);
        buuplist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ShowSQLiteDBdata() ;



            }
        });
    }

    @Override
    protected void onResume() {


        super.onResume();
    }

    private void ShowSQLiteDBdata() {

        new CountDownTimer(1200, 1000) {

            public void onTick(long millisUntilFinished) {
                findViewById(R.id.loadingPanel).setVisibility(View.VISIBLE);
            }

            public void onFinish() {

                SQLITEDATABASE = SQLITEHELPER.getWritableDatabase();

                cursor = SQLITEDATABASE.rawQuery("SELECT * FROM Student", null);

                ID_ArrayList.clear();
                NAME_ArrayList.clear();
                PHONE_NUMBER_ArrayList.clear();
                SUBJECT_ArrayList.clear();

                if (cursor.moveToFirst()) {
                    do {

                        Log.i("SR","DATALIST");


                        ID_ArrayList.add(cursor.getString(cursor.getColumnIndex(DatabaseHelper.Col_1)));

                        NAME_ArrayList.add(cursor.getString(cursor.getColumnIndex(DatabaseHelper.Col_2)));

                        PHONE_NUMBER_ArrayList.add(cursor.getString(cursor.getColumnIndex(DatabaseHelper.Col_3)));

                        SUBJECT_ArrayList.add(cursor.getString(cursor.getColumnIndex(DatabaseHelper.Col_4)));

                    } while (cursor.moveToNext());
                }

                ListAdapter = new SQLiteListAdapter(ListViewActivity.this,

                        ID_ArrayList,
                        NAME_ArrayList,
                        PHONE_NUMBER_ArrayList,
                        SUBJECT_ArrayList

                );

                LISTVIEW.setAdapter(ListAdapter);

                cursor.close();
                findViewById(R.id.loadingPanel).setVisibility(View.GONE);
             }

        }.start();




    }
}
