package com.example.colin.servicefinder;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import org.json.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

public class housedatabase extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "HouseDatabase";
    public static final String TABLE_NAME = "tbl_house";
    public static final int DATABASE_VERSION = 1;
    public static final String[] DATABASE_COLUMNS = {"BldgID", "Name", "Address", "X"
            , "Y"};
    JSONObject geometryObject;
    JSONObject data;
    JSONArray houses;
    JSONObject houseObject;
    JSONArray coordinates;
    JSONArray coordinates2;
    JSONArray coordinates3;
    InputStream is;






    public housedatabase(Context context, String json){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        try {

            data = new JSONObject(json);
            houses = data.getJSONArray("houses");

        }catch(Exception e){
            Log.d("nibba","Contructor Error");
        }
    }


    @Override
    public void onCreate(SQLiteDatabase db){



        String sql = "CREATE TABLE " +
                TABLE_NAME+ " (" +
                "`BldgID` INTEGER PRIMARY KEY AUTOINCREMENT," +
                "`Name` TEXT," +
                "`Address` TEXT," +
                "`X` TEXT," +
                "`Y` TEXT" +
                ");";

        db.execSQL(sql);

        try {
            String name;
            String x;
            String y;
            String address;
            for(int i = 0; i < houses.length(); ++i) {
                houseObject = houses.getJSONObject(i);
                geometryObject = houseObject.getJSONObject("json_geometry");
                coordinates = geometryObject.getJSONArray("coordinates");
                coordinates2 = coordinates.getJSONArray(0);
                coordinates3 = coordinates2.getJSONArray(0);
                 x = Double.toString(coordinates3.getDouble(0));
                 y = Double.toString(coordinates3.getDouble(1));
                 name = houseObject.getString("NgbrNam") + " " + Integer.toString(houseObject.getInt("BLDG_ID"));
                 address = houseObject.getString("STRNUM") + " "
                         + houseObject.getString("STRNAM") + " " + houseObject.getString("BLDGNAM");

                String str = "INSERT INTO tbl_house(Name,Address,X,Y)"
                        + "VALUES("
                        + "\"" + address + "\", "
                        + "\"" + name + "\", "
                        + "\"" + x + "\", "
                        + "\"" + y + "\")";

                db.execSQL(str);

            }


        }catch(Exception e){}
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVer, int newVer) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);

    }

    public Cursor viewData(){
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "Select * from "+TABLE_NAME;
        Cursor cursor = db.rawQuery(query, null);
        return cursor;
    }

    public Cursor getData(int id){
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "Select * from "+TABLE_NAME +"Where ID Like " + Integer.toString(id);
        Cursor cursor = db.rawQuery(query, null);
        return cursor;
    }
}
