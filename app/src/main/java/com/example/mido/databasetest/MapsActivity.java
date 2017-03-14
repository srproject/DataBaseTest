package com.example.mido.databasetest;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.CountDownTimer;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.Iterator;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

 
 
     double lng;
    double lat;

    String name1;
    String lat1;
    String lng1;

    DatabaseHelper SQLITEHELPER;
    SQLiteDatabase SQLITEDATABASE;
    Cursor cursor;

    ArrayList<LatLng> latLngs = new ArrayList<LatLng>();
    ArrayList<String> location_name = new ArrayList<String>();
    LatLng newLatLng;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

              SQLITEHELPER = new DatabaseHelper(this);

    SQLITEDATABASE = SQLITEHELPER.getReadableDatabase();


 

        try {
            cursor = SQLITEDATABASE.rawQuery("SELECT * FROM Student", null);
            if (cursor != null) {
                if (cursor.moveToFirst()) {
                    do {
                         name1 = cursor.getString(cursor.getColumnIndex("NAME"));
                        lat1 = cursor.getString(cursor.getColumnIndex("LAT"));
                        lng1 = cursor.getString(cursor.getColumnIndex("LON"));
                        newLatLng = new LatLng(Double.parseDouble(lat1), Double.parseDouble(lng1));
                        latLngs.add(newLatLng);
                        location_name.add(name1);
                        lat = Double.parseDouble(lat1);
                        lng=Double.parseDouble(lng1);



                        Log.i("SR","SR");

                    } while (cursor.moveToNext());
                }
            }
        } catch (Exception e) {
        } finally {
            cursor.close();
        }
    }





    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
 




        Iterator<LatLng> iterator = latLngs.iterator();
        Iterator<String> iterator2 = location_name.iterator();
        while (iterator.hasNext()) {

            while (iterator.hasNext()) {




                mMap.addMarker(new MarkerOptions().position(iterator.next()).snippet("SR SR SR  SR SR SR  \n ").title(iterator2.next()));


            }




        }




        LatLng sydney = new LatLng(lat, lng);


        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
        CameraUpdate zoom=CameraUpdateFactory.zoomTo(16);
        mMap.animateCamera(zoom);
 







    }
}

