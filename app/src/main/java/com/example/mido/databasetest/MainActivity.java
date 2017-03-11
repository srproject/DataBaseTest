package com.example.mido.databasetest;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

public class MainActivity extends AppCompatActivity {
  DatabaseHelper myDB;
    EditText editName, editSuberName, editMark;
    Button button,button2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myDB = new DatabaseHelper(this);

        copyDatabase(getApplicationContext(), "Student.db");

        editName = (EditText) findViewById(R.id.editName);
        editMark = (EditText) findViewById(R.id.editMark);
        editSuberName = (EditText) findViewById(R.id.editSuberName);
        button = (Button) findViewById(R.id.button);
        button2 = (Button) findViewById(R.id.button2);


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean inserted = myDB.insertData(editName.getText().toString(), editSuberName.getText().toString(), editMark.getText().toString());
                if (inserted == true) {
                    Toast.makeText(getApplicationContext(), "حبيبي تسلم ", Toast.LENGTH_LONG).show();

                    copyDatabase(getApplicationContext(), "Student.db");

                } else {
                    Toast.makeText(getApplicationContext(), " مش تسلم ", Toast.LENGTH_LONG).show();
                }

            }
        });

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cursor res = myDB.getAllData();
                if (res.getCount() == 0) {
                    //show massage
                    showMessage("ERROR","Nothing Found");
                    return;
                }
                StringBuffer buffer = new StringBuffer();
                while (res.moveToNext()) {
                    buffer.append("Id :" + res.getString(0) + "\n");
                    buffer.append("Name :" + res.getString(1) + "\n");
                    buffer.append("SuberName :" + res.getString(2) + "\n");
                    buffer.append("Mark :" + res.getString(3) + "\n");

                }
                //show massage
                showMessage("Data",buffer.toString());
            }
        });

    }

    public  void showMessage(String title, String massege) {

        AlertDialog.Builder builder= new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(massege);
        builder.show();

    }



    public static void copyDatabase(Context c, String DATABASE_NAME) {
        String databasePath = c.getDatabasePath(DATABASE_NAME).getPath();
        File f = new File(databasePath);
        OutputStream myOutput = null;
        InputStream myInput = null;
        Log.d("testing", " testing db path " + databasePath);
        Log.d("testing", " testing db exist " + f.exists());

        if (f.exists()) {
            try {

                File directory = new File("/mnt/sdcard/DB_SR");
                if (!directory.exists())
                    directory.mkdir();

                myOutput = new FileOutputStream(directory.getAbsolutePath()
                        + "/" + DATABASE_NAME);
                myInput = new FileInputStream(databasePath);

                byte[] buffer = new byte[1024];
                int length;
                while ((length = myInput.read(buffer)) > 0) {
                    myOutput.write(buffer, 0, length);
                }

                myOutput.flush();
            } catch (Exception e) {
            } finally {
                try {
                    if (myOutput != null) {
                        myOutput.close();
                        myOutput = null;
                    }
                    if (myInput != null) {
                        myInput.close();
                        myInput = null;
                    }
                } catch (Exception e) {
                }
            }
        }
    }
}
