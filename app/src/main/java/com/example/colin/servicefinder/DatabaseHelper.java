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

public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "ServiceDatabase";
    public static final String TABLE_NAME = "JusticeServices";
    public static final int DATABASE_VERSION = 1;
    public static final String[] DATABASE_COLUMNS = {"id", "Name", "Description", "Category", "Hours", "X"
            , "Y", "PostalCode", "Phone", "Email", "Website"};
    JSONObject obj;
    JSONObject data;
    JSONArray features;
    JSONObject data3;
    InputStream is;






    public DatabaseHelper(Context context, String json){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        try {

            data = new JSONObject(json);
            features = data.getJSONArray("features");









        }catch(Exception e){
            Log.d("nibba","Contructor Error");
        }
    }


    @Override
    public void onCreate(SQLiteDatabase db){

        String sql = "CREATE TABLE " +
                TABLE_NAME+ " (" +
            "`id` INTEGER PRIMARY KEY AUTOINCREMENT," +
            "`Name` TEXT," +
            "`Description` TEXT," +
            "`Category` TEXT," +
            "`Hours` TEXT," +
            "`X` TEXT," +
            "`Y` TEXT," +
            "`PostalCode` TEXT," +
            "`Phone` TEXT," +
            "`Email` TEXT," +
            "`Website` TEXT" +
            ");";

        db.execSQL(sql);
        try {


            for(int i = 0; i < features.length();i++) {
                data3 = features.getJSONObject(i);
                obj = data3.getJSONObject("properties");



                String nameTemp;
                String descTemp;
                String catTemp;
                String hoursTemp;
                String locTemp;
                String pcTemp;
                String phoneTemp;
                String emailTemp;
                String webTemp;
                String xTemp;
                String yTemp;
                nameTemp = obj.getString("Name");
                descTemp = obj.getString("Description");
                catTemp = obj.getString("Category");
                hoursTemp = obj.getString("Hours");
                locTemp = obj.getString("Location");
                pcTemp = obj.getString("PC");
                phoneTemp = obj.getString("Phone");
                emailTemp = obj.getString("Email");
                webTemp = obj.getString("Website");
                xTemp = obj.getString("X");
                yTemp = obj.getString("Y");

                String str = "INSERT INTO JusticeServices(Name,Description,Category,Hours,X,Y,PostalCode,Phone,Email,Website)"
                        + "VALUES("
                        + "\"" + nameTemp + "\", "
                        + "\"" + descTemp + "\", "
                        + "\"" + catTemp + "\", "
                        + "\"" + hoursTemp + "\", "
                        + "\"" + xTemp + "\", "
                        + "\"" + yTemp + "\", "
                        + "\"" + pcTemp + "\", "
                        + "\"" + phoneTemp + "\", "
                        + "\"" + emailTemp + "\", "
                        + "\"" + webTemp + "\")";

                db.execSQL(str);

            }







        }catch(Exception e){

        }



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
