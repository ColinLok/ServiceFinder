package com.example.colin.servicefinder;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;


import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;



public class Houselist extends Activity{

    housedatabase db;
    ArrayList<String> listItem;
    ArrayAdapter adapter;
    ListView list_items;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_houselist);
        listItem = new ArrayList<>();
        list_items = (ListView)findViewById(R.id.house_listview);
        try {
            db = new housedatabase(this, loadJSONFromAsset(this));
        }catch(Exception e){}


        viewData();


        list_items.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                Intent intent = new Intent(Houselist.this, HouseDetail.class);
                intent.putExtra("id",i);
                startActivity(intent);

            }
        });


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

    public void viewData(){
        Cursor cursor = db.viewData();
        if(cursor.getCount() == 0){
            Toast.makeText(this, "No data to show", Toast.LENGTH_SHORT).show();

        } else {
            while(cursor.moveToNext()){
                listItem.add(cursor.getString(1));
            }
            adapter = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,listItem);
            list_items.setAdapter(adapter);
        }
    }

}


