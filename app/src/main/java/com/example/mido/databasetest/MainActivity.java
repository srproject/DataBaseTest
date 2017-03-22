package com.example.mido.databasetest;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.mido.databasetest.event.ListViewEventActivity;
import com.example.mido.databasetest.stu.ListViewActivity;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

public class MainActivity extends AppCompatActivity {
  DatabaseHelper myDB;
    EditText editName, editSuberName, editMark,editid;
    Button buttonadd,buttonup,buttondele,buttondall,buttonshow,buttonshowevent,buttonshowmap;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myDB = new DatabaseHelper(this);


        copyDatabase(getApplicationContext(),"Student0.db");

        editName = (EditText)findViewById(R.id.editName);
        editMark = (EditText)findViewById(R.id.editMark);
        editSuberName = (EditText)findViewById(R.id.editSuberName);
        editid = (EditText)findViewById(R.id.editid);


        buttonadd = (Button)findViewById(R.id.buttonadd);
        buttonup = (Button)findViewById(R.id.buttonup);
        buttondele = (Button)findViewById(R.id.buttondele);
        buttondall = (Button)findViewById(R.id.buttondall);
        buttonshow= (Button)findViewById(R.id.buttonshow);
        buttonshowevent= (Button)findViewById(R.id.buttonshowevent);
        buttonshowmap= (Button)findViewById(R.id.buttonshowmap);



        buttonadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean inserted =myDB.insertData(editName.getText().toString(),editSuberName.getText().toString(),editMark.getText().toString());
                if( inserted == true){
                    Toast.makeText(getApplicationContext(),"حبيبي تسلم ",Toast.LENGTH_SHORT).show();

                    copyDatabase(getApplicationContext(),"Student.db");

                }
                else{                    Toast.makeText(getApplicationContext()," مش تسلم ",Toast.LENGTH_SHORT).show();
                    copyDatabase(getApplicationContext(),"Student.db");

                }

            }
        });


        buttonadd.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                boolean inserted =myDB.insertDataforeventtest(editName.getText().toString(),editSuberName.getText().toString());
                if( inserted == true){
                    Toast.makeText(getApplicationContext(),"حبيبي تسلم event",Toast.LENGTH_SHORT).show();

                    copyDatabase(getApplicationContext(),"Student.db");

                }
                else{                    Toast.makeText(getApplicationContext()," مش تسلم event",Toast.LENGTH_SHORT).show();
                }
return false;

            }
        });


        buttondele.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {
                 boolean delete =myDB.deletData( editid.getText().toString());
                if( delete == true) {
                    Toast.makeText(getApplicationContext(), editid.getText().toString()+" is Deleted", Toast.LENGTH_SHORT).show();

                    copyDatabase(getApplicationContext(), "Student.db");
                }else {
                    Toast.makeText(getApplicationContext(), editid.getText().toString()+" not Deleted", Toast.LENGTH_SHORT).show();



                }

                }catch (Exception e){

                    Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_SHORT).show();



                }

            }
        });

        buttondall.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {

                try {
                    boolean delete =myDB.deletallData( );
                    if( delete == true) {

                        copyDatabase(getApplicationContext(), "Student.db");
                    }else {
                        Toast.makeText(getApplicationContext(),  " not Deleted", Toast.LENGTH_SHORT).show();



                    }

                    boolean deleteevent =myDB.deletallDataevent( );
                    if( delete == true) {
                        Toast.makeText(getApplicationContext(), "all data is Deleted", Toast.LENGTH_SHORT).show();

                        copyDatabase(getApplicationContext(), "Student.db");
                    }else {
                        Toast.makeText(getApplicationContext(),  " not Deleted", Toast.LENGTH_SHORT).show();



                    }

                }catch (Exception e){

                    Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_SHORT).show();



                }

                return false;
            }
        });


        buttonup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {
                   boolean updated =myDB.updataData(editName.getText().toString(), editSuberName.getText().toString(), editMark.getText().toString(), editid.getText().toString());

                    if( updated == true) {
                        if (editName.getText().toString().trim().length() > 0
                                && editSuberName.getText().toString().trim().length() > 0
                                || editMark.getText().toString().trim().length() > 0
                                || editid.getText().toString().trim().length() > 0) {

                            Toast.makeText(getApplicationContext(), "Done", Toast.LENGTH_SHORT).show();


                            copyDatabase(getApplicationContext(), "Student.db");
                        } else {

                            Toast.makeText(getApplicationContext(), "Enter ID and other data", Toast.LENGTH_SHORT).show();


                        }
                    }else {

                        Toast.makeText(getApplicationContext(), " data not update", Toast.LENGTH_SHORT).show();


                    }

                }catch (Exception e){

                    Toast.makeText(getApplicationContext(), "error", Toast.LENGTH_SHORT).show();



                }

            }
        });





        buttonshow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this,ListViewActivity.class);
                startActivity(intent);

            }
        });



        buttonshowmap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 Intent intent=new Intent(MainActivity.this,MapsActivity.class);
                startActivity(intent);

            }
        });


        buttonshowevent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(MainActivity.this,ListViewEventActivity.class);
                startActivity(intent);

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
