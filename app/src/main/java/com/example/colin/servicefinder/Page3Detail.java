package com.example.colin.servicefinder;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.mapbox.mapboxsdk.Mapbox;
import com.mapbox.mapboxsdk.annotations.MarkerOptions;
import com.mapbox.mapboxsdk.camera.CameraPosition;
import com.mapbox.mapboxsdk.camera.CameraUpdateFactory;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.mapboxsdk.maps.MapView;
import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.mapbox.mapboxsdk.maps.OnMapReadyCallback;

import java.io.IOException;
import java.io.InputStream;

public class Page3Detail extends AppCompatActivity {
    private MapView mapView;
    DatabaseHelper db;
    Cursor cursor;

    public String loadJSONFromAsset(Context context)throws Exception{
        String json = null;
        try {
            InputStream is = context.getAssets().open("GOVERNMENT_AND_JUSTICE_SERVICES.json");

            int size = is.available();

            byte[] buffer = new byte[size];

            is.read(buffer);

            is.close();

            json = new String(buffer, "UTF-8");


        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Mapbox.getInstance(this, "pk.eyJ1IjoiYmlpYTAiLCJhIjoiY2puejFteDl3MWUwYzN2bmR5dHZ1Zzd6diJ9.KdZCXQHsk-0b9hOIPvhtng");
        setContentView(R.layout.activity_page3_detail);
        Intent intent = getIntent();
        int i = intent.getIntExtra("id",0);
        try {
            db = new DatabaseHelper(this, loadJSONFromAsset(this));
        }catch(Exception e){}
        cursor = db.viewData();
        cursor.moveToPosition(i);
        double lat = Double.parseDouble(cursor.getString(6));
        double lon = Double.parseDouble(cursor.getString(5));

        mapView = (MapView) findViewById(R.id.mapView);

        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(MapboxMap mapboxMap) {
                // One way to add a marker view
                CameraPosition position = new CameraPosition.Builder()
                        .target(new LatLng(lat,lon)) // Sets the new camera position
                        .zoom(13) // Sets the zoom to level 13
                        .tilt(20) // Set the camera tilt to 20 degrees
                        .build(); // Builds the CameraPosition object from the builder
                mapboxMap.animateCamera(CameraUpdateFactory
                        .newCameraPosition(position), 500);
                mapboxMap.addMarker(new MarkerOptions()
                        .position(new LatLng(lat,lon))
                        .title(cursor.getString(1))
                        .snippet(cursor.getString(2))
                );
            }
        });

        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);

        TextView description = (TextView)findViewById(R.id.address);
        description.setText("Hours: " + cursor.getString(4));

        TextView name = (TextView)findViewById(R.id.name);
        name.setText(cursor.getString(1));

        TextView phone= (TextView)findViewById(R.id.phone);
        phone.setText("Phone# " + cursor.getString(8));


    }
    @Override
    public void onStart() {
        super.onStart();
        mapView.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
        mapView.onStop();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }

}
