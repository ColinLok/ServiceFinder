package com.example.colin.servicefinder;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "ServiceDatabase";
    public static final String TABLE_NAME = "JusticeServices";
    public static final int DATABASE_VERSION = 1;
    public static final String[] DATABASE_COLUMNS = {"id", "Name", "Description", "Category", "Hours", "X"
            , "Y", "PostalCode", "Phone", "Email", "Website"};

    public DatabaseHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {

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
        db.execSQL("INSERT INTO JusticeServices(Name,Description,Category,Hours,X,Y,PostalCode,Phone,Email,Website)" +
                    "VALUES(\"ACORN Canada\", \"thing\",\"Government and Justice Services\", \"By appointment only: Fridays, 1:30 pm - 6:00 pm, Saturdays, 8:00 pm - 5:00 pm\"" +
                    ",\"-122.909636869742\", \"49.2034967437016\", \"V3M 1E5\", \"604-522-8707\", \"bcacorn@acorncanada.org\", \"https://www.nwpl.ca/database/files/library/Community_Volunteer_Tax_Program___March_2018(1).pdf\" )");
        db.execSQL("INSERT INTO JusticeServices(Name,Description,Category,Hours,X,Y,PostalCode,Phone,Email,Website)" +
                "VALUES(\"BC Provincial Government - Ministry of Child and Family Development - Aboriginal Circle 6 Tri-Cities Family Services\", \"thing\",\"Government and Justice Services\", \"Monday, Tuesday, Wednesday and Friday, 8:00 am - 5:15 pm, Thursday, 8:00 am - 7:00 pm\"" +
                ",\"-122.910794429807\", \"49.2011897037881\", \"V3M 1B4\", \"604-660-8636\", \"bcacorn@acorncanada.org\", \"https://www2.gov.bc.ca/gov/content/family-social-supports/data-monitoring-quality-assurance/find-services-for-children-teens-families/sda-new-westminister-aboriginal-circle-6?keyword=new&keyword=westminster&keyword=bc\" )");

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
