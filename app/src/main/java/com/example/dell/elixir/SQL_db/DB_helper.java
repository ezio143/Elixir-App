package com.example.dell.elixir.SQL_db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Ezio on 3/7/2018.
 */

public class DB_helper extends SQLiteOpenHelper {


    private static final String DB_NAME = "student.db";
    private static final int DB_VERSION = 1;
    private  String SQL_CREATE_PH_TABLE,SQL_CREATE_TEMP_TABLE,SQL_CREATE_TURB_TABLE,SQL_CREATE_MAPS_TABLE;



    public DB_helper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);

        SQL_CREATE_PH_TABLE = "CREATE TABLE "+SContract.PH_Entry.TABLE_NAME+" (" +
                SContract.PH_Entry._ID+" INTEGER PRIMARY KEY AUTOINCREMENT," +
                SContract.PH_Entry.COLUMN_PH+" NUMERIC," +
                SContract.PH_Entry.Date_Time+" TEXT );";


        SQL_CREATE_TEMP_TABLE = "CREATE TABLE "+SContract.TEMP_Entry.TABLE_NAME+" (" +
                SContract.TEMP_Entry._ID+" INTEGER PRIMARY KEY AUTOINCREMENT," +
                SContract.TEMP_Entry.COLUMN_TEMP+" NUMERIC," +
                SContract.TEMP_Entry.Date_Time+" TEXT );";

        SQL_CREATE_TURB_TABLE = "CREATE TABLE "+SContract.TURB_Entry.TABLE_NAME+" (" +
                SContract.TURB_Entry._ID+" INTEGER PRIMARY KEY AUTOINCREMENT," +
                SContract.TURB_Entry.COLUMN_TURB+" NUMERIC," +
                SContract.TURB_Entry.Date_Time+" TEXT );";

        SQL_CREATE_MAPS_TABLE = "CREATE TABLE "+SContract.MAPS_Entry.TABLE_NAME+" (" +
                SContract.MAPS_Entry._ID+" INTEGER PRIMARY KEY AUTOINCREMENT," +
                SContract.MAPS_Entry.COLUMN_LAT+" NUMERIC," +
                SContract.MAPS_Entry.COLUMN_LONG+" NUMERIC," +
                SContract.MAPS_Entry.Date_Time+" TEXT );";

    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(SQL_CREATE_PH_TABLE);
        db.execSQL(SQL_CREATE_TEMP_TABLE);
        db.execSQL(SQL_CREATE_TURB_TABLE);
        db.execSQL(SQL_CREATE_MAPS_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {

        db.execSQL("DROP TABLE IF EXISTS "+ SContract.PH_Entry.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS "+ SContract.TURB_Entry.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS "+ SContract.MAPS_Entry.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS "+ SContract.TEMP_Entry.TABLE_NAME);
        onCreate(db);

    }
}
