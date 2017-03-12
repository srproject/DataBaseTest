package com.example.mido.databasetest;

import android.content.Context;
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
    EditText editName, editSuberName, editMark,editid;
    Button button,button2,button3,button4;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myDB = new DatabaseHelper(this);

        copyDatabase(getApplicationContext(),"Student.db");

        editName = (EditText)findViewById(R.id.editName);

        editMark = (EditText)findViewById(R.id.editMark);


        editid = (EditText)findViewById(R.id.editid);


        editSuberName = (EditText)findViewById(R.id.editSuberName);
        button = (Button)findViewById(R.id.button);
        button2 = (Button)findViewById(R.id.button2);
        button3 = (Button)findViewById(R.id.button3);
        button4 = (Button)findViewById(R.id.button4);



        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean inserted =myDB.insertData(editName.getText().toString(),editSuberName.getText().toString(),editMark.getText().toString());
                if( inserted == true){
                    Toast.makeText(getApplicationContext(),"حبيبي تسلم ",Toast.LENGTH_SHORT).show();

                    copyDatabase(getApplicationContext(),"Student.db");

                }
                else{                    Toast.makeText(getApplicationContext()," مش تسلم ",Toast.LENGTH_SHORT).show();
                }

            }
        });



        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {
                    myDB.delete(editid.getText().toString());

                    Toast.makeText(getApplicationContext(), "Done", Toast.LENGTH_SHORT).show();

                    copyDatabase(getApplicationContext(), "Student.db");

                }catch (Exception e){

                    Toast.makeText(getApplicationContext(), "Enter ID First", Toast.LENGTH_SHORT).show();



                }

            }
        });
        button3.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {

                try {
                myDB.deleteall();


                copyDatabase(getApplicationContext(),"Student.db");

                Toast.makeText(getApplicationContext(),"Deleted ALL",Toast.LENGTH_SHORT).show();


                }catch (Exception e){

                    Toast.makeText(getApplicationContext(), "Erorr  --  "+e.toString(), Toast.LENGTH_SHORT).show();



                }

                return false;
            }
        });

        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {
                    if (editName.getText().toString().trim().length() > 0
                            && editSuberName.getText().toString().trim().length() > 0
                            &&  editMark.getText().toString().trim().length() > 0
                            &&  editid.getText().toString().trim().length() > 0) {
                        myDB.update(editName.getText().toString(), editSuberName.getText().toString(), editMark.getText().toString(), editid.getText().toString());

                        Toast.makeText(getApplicationContext(), "Done", Toast.LENGTH_SHORT).show();

                        copyDatabase(getApplicationContext(), "Student.db");
                    }else {

                        Toast.makeText(getApplicationContext(), "Enter ID and other data", Toast.LENGTH_SHORT).show();



                    }

                }catch (Exception e){

                    Toast.makeText(getApplicationContext(), "Enter ID First", Toast.LENGTH_SHORT).show();



                }

            }
        });








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
