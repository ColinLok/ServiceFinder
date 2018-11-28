package com.example.colin.servicefinder;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.mapbox.mapboxsdk.Mapbox;
import com.mapbox.mapboxsdk.annotations.Icon;
import com.mapbox.mapboxsdk.annotations.IconFactory;
import com.mapbox.mapboxsdk.annotations.MarkerOptions;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.mapboxsdk.maps.MapView;
import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.mapbox.mapboxsdk.maps.OnMapReadyCallback;

import java.io.IOException;
import java.io.InputStream;

public class HouseDetail extends AppCompatActivity {
    private MapView mapView;
    housedatabase db;
    DatabaseHelper db2;
    Cursor cursor;
    Cursor cursor2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Mapbox.getInstance(this, "pk.eyJ1IjoiYmlpYTAiLCJhIjoiY2puejFteDl3MWUwYzN2bmR5dHZ1Zzd6diJ9.KdZCXQHsk-0b9hOIPvhtng");
        setContentView(R.layout.activity_house_detail);
        Intent intent = getIntent();
        Intent intent2 = getIntent();
        int i = intent.getIntExtra("id",0);
        int i2 = intent2.getIntExtra("id",0);
        IconFactory iconFactory = IconFactory.getInstance(HouseDetail.this);
        Icon icon = iconFactory.fromResource(R.drawable.hicon);
        try {
            db = new housedatabase(this, loadJSONFromAsset(this));
            db2 = new DatabaseHelper(this,loadJSONFromAsset(this));
        }catch(Exception e){}
        cursor = db.viewData();
        cursor2 = db2.viewData();
        cursor.moveToPosition(i);
        cursor2.moveToPosition(i2);

        double lat = Double.parseDouble(cursor.getString(4));
        double lon = Double.parseDouble(cursor.getString(3));


        mapView = (MapView) findViewById(R.id.mapView);

        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(MapboxMap mapboxMap) {
                // One way to add a marker view
                mapboxMap.addMarker(new MarkerOptions()
                        .position(new LatLng(lat,lon))
                        .title(cursor.getString(1))
                        .snippet(cursor.getString(2))
                        .icon(icon)
                );

                while(cursor2.moveToNext()){
                    double lat2 = Double.parseDouble(cursor2.getString(6));
                    double lon2 = Double.parseDouble(cursor2.getString(5));
                    mapboxMap.addMarker(new MarkerOptions()
                            .position(new LatLng(lat2,lon2))
                            .title(cursor2.getString(1))
                            .snippet(cursor2.getString(2))
                    );
                }
            }
        });

        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);

        TextView description = (TextView)findViewById(R.id.address);
        description.setText("Address: " + cursor.getString(2));

        TextView name = (TextView)findViewById(R.id.name);
        name.setText("Name: " + cursor.getString(1));
    }

    public String loadJSONFromAsset(Context context)throws Exception{
        String json = null;
        try {
            InputStream is = context.getAssets().open("RENTALHOUSING.json");
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


