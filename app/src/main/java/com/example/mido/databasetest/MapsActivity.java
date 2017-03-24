package com.example.mido.databasetest;

import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.location.Location;
import android.os.CountDownTimer;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

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
    ArrayList<Double> lat0 = new ArrayList<Double>();
    ArrayList<Double> Lng0 = new ArrayList<Double>();


    ArrayList<Integer> location_name = new ArrayList<Integer>();
    List<Integer> location_id = new ArrayList<Integer>();
    LatLng newLatLng;

    private Circle mCircle;
    private Marker mMarker;

    DatabaseHelper myDB;



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

        myDB = new DatabaseHelper(this);



        try {
            cursor = SQLITEDATABASE.rawQuery("SELECT * FROM Student", null);
            if (cursor != null) {
                if (cursor.moveToFirst()) {
                    do {
                        name1 = cursor.getString(cursor.getColumnIndex("ID"));
                        lat1 = cursor.getString(cursor.getColumnIndex("LAT"));
                        lng1 = cursor.getString(cursor.getColumnIndex("LON"));
                        newLatLng = new LatLng(Double.parseDouble(lat1), Double.parseDouble(lng1));
                        latLngs.add(newLatLng);
                        lat0.add(Double.parseDouble(lat1));
                        Lng0.add(Double.parseDouble(lng1));

                        location_name.add(Integer.parseInt(name1));
                        location_id.add(Integer.parseInt(name1));

                        lat = Double.parseDouble(lat1);
                        lng = Double.parseDouble(lng1);


                        Log.i("SR", "SR");

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


        //--------------------------------

        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        googleMap.setMyLocationEnabled(true);




        try {
            //test outside
            double mLatitude =  30.044;
            double mLongitude = 31.235;




            //test inside
            //double mLatitude = 37.7795516;
            //double mLongitude = -122.39292;




            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(mLatitude, mLongitude), 15));


            MarkerOptions options = new MarkerOptions();


            // Setting the position of the marker


            options.position(new LatLng(mLatitude, mLongitude));


            //googleMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map)).getMap();


            LatLng latLng = new LatLng(mLatitude, mLongitude);
            drawMarkerWithCircle(latLng);




            googleMap.setOnMyLocationChangeListener(new GoogleMap.OnMyLocationChangeListener() {
                @Override
                public void onMyLocationChange(Location location) {
                    float[] distance = new float[2];


                        /*
                        Location.distanceBetween( mMarker.getPosition().latitude, mMarker.getPosition().longitude,
                                mCircle.getCenter().latitude, mCircle.getCenter().longitude, distance);
                                */
                    int i=0;

                    Iterator<Double> iterator00 = lat0.iterator();
                    Iterator<Double> iterator0 = Lng0.iterator();
                    Iterator<LatLng> iterator = latLngs.iterator();
                    Iterator<Integer> iterator2 = location_name.iterator();
                    for(int k=0; k<location_id.size(); k++) {
                        String id=iterator2.next().toString();


                            Location.distanceBetween(iterator00.next(), iterator0.next(),
                                    mCircle.getCenter().latitude, mCircle.getCenter().longitude, distance);


                            if (distance[0] > mCircle.getRadius()) {


                                Toast.makeText(getBaseContext(),String.valueOf(location_id.get(k))+ " Outside, distance from center: " + distance[0] + " radius: " + mCircle.getRadius(), Toast.LENGTH_SHORT).show();
                            } else {

                                try {

                                    myDB.deletData(String.valueOf(location_id.get(k)));
                                   Toast.makeText(getBaseContext(),String.valueOf(location_id.get(k))+ "Delete and Inside, distance from center: " + distance[0] + " radius: " + mCircle.getRadius(), Toast.LENGTH_SHORT).show();

                                }catch (NoSuchElementException e){

                                    myDB.deletData(String.valueOf(iterator2.next()));


                                    Toast.makeText(getBaseContext(), e.toString() , Toast.LENGTH_LONG).show();


                                }


                            }


                            i++;


                        }
                    }






            });







        } catch (Exception e) {
            e.printStackTrace();
        }






        //--------------------------------




        Iterator<LatLng> iterator = latLngs.iterator();
        Iterator<Integer> iterator2 = location_name.iterator();

        while (iterator.hasNext()) {

            while (iterator.hasNext()) {




                mMap.addMarker(new MarkerOptions().position(iterator.next()).snippet("SR SR SR  SR SR SR  \n ").title(String.valueOf(iterator2.next())));


            }




        }




        LatLng sydney = new LatLng(lat, lng);


    //    mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
      //  CameraUpdate zoom=CameraUpdateFactory.zoomTo(16);
        //mMap.animateCamera(zoom);
 







    }

    private void drawMarkerWithCircle(LatLng position){
        double radiusInMeters = 500.0;
        int strokeColor = 0xffff0000; //red outline
        int shadeColor = 0x44ff0000; //opaque red fill


        CircleOptions circleOptions = new CircleOptions().center(position).radius(radiusInMeters).fillColor(shadeColor).strokeColor(strokeColor).strokeWidth(8);
        mCircle = mMap.addCircle(circleOptions);


        MarkerOptions markerOptions = new MarkerOptions().position(position);
        mMarker = mMap.addMarker(markerOptions);
    }


}

