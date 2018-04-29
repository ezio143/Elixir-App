package com.example.dell.elixir.SQL_db;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * Created by Ezio on 3/7/2018.
 */

public class DataProvider extends ContentProvider {

    private final static int PH = 100;
    private final static int PH_ID = 101;
    private final static int TEMP = 200;
    private final static int TEMP_ID = 201;
    private final static int TURB = 300;
    private final static int TURB_ID = 301;
    private final static int MAPS = 400;
    private final static int MAPS_ID = 401;


    private SQLiteDatabase db;
    private DB_helper dbHelper;


    private static final UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    @Override
    public boolean onCreate() {
        dbHelper = new DB_helper(getContext());
        return false;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionargs, @Nullable String s1) {
        Cursor cursor = null;
        db = dbHelper.getReadableDatabase();

        int match = sUriMatcher.match(uri);
        switch (match) {

            case PH:
                cursor = db.query(SContract.PH_Entry.TABLE_NAME, projection, selection, selectionargs, null, null, null);
                break;
            case PH_ID:
                selection = SContract.PH_Entry._ID + "=?";
                selectionargs = new String[]{String.valueOf(ContentUris.parseId(uri))};
                cursor = db.query(SContract.PH_Entry.TABLE_NAME, projection, selection, selectionargs, null, null, null);
                break;
            case TEMP:
                cursor = db.query(SContract.TEMP_Entry.TABLE_NAME, projection, selection, selectionargs, null, null, null);
                break;
            case TEMP_ID:
                selection = SContract.TEMP_Entry._ID + "=?";
                selectionargs = new String[]{String.valueOf(ContentUris.parseId(uri))};
                cursor = db.query(SContract.TEMP_Entry.TABLE_NAME, projection, selection, selectionargs, null, null, null);
                break;

            case TURB:
                cursor = db.query(SContract.TURB_Entry.TABLE_NAME,projection,selection,selectionargs,null,null,null);
                break;
            case TURB_ID:
                selection = SContract.TURB_Entry._ID + "=?";
                selectionargs = new String[]{String.valueOf(ContentUris.parseId(uri))};
                cursor = db.query(SContract.TURB_Entry.TABLE_NAME, projection, selection, selectionargs, null, null, null);
                break;
            case MAPS:cursor = db.query(SContract.MAPS_Entry.TABLE_NAME,projection,selection,selectionargs,null,null,null);
            break;

            default:
                throw new IllegalArgumentException("cannot query unknown URI" + uri);
        }
        return cursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        int match = sUriMatcher.match(uri);
        switch (match) {
            case PH:
                return insert_values(uri, values, SContract.PH_Entry.TABLE_NAME);
            case TEMP:
                return insert_values(uri,values, SContract.TEMP_Entry.TABLE_NAME);
            case TURB:
                return insert_values(uri,values,SContract.TURB_Entry.TABLE_NAME);
            case MAPS:
                return insert_values(uri,values,SContract.MAPS_Entry.TABLE_NAME);
            default:
                throw new IllegalArgumentException("insertion cannot be performed for " + uri);
        }
    }

    private Uri insert_values(Uri uri,ContentValues values,String tablename){
        db = dbHelper.getWritableDatabase();


        long newrowid = db.insert(tablename, null, values);
        if (newrowid == -1) {
            //Toast.makeText(getContext(), "error in inserting values", Toast.LENGTH_SHORT).show();

        }
        else{
           // Toast.makeText(getContext(), " add successful", Toast.LENGTH_SHORT).show();
        }

        //return the new uri with the id appeded at the end
        return ContentUris.withAppendedId(uri, newrowid);


    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String s, @Nullable String[] strings) {
        int match = sUriMatcher.match(uri);
        db = dbHelper.getWritableDatabase();
        switch (match){
            case TEMP: return db.delete(SContract.TEMP_Entry.TABLE_NAME,s,strings);
            case PH : return db.delete(SContract.PH_Entry.TABLE_NAME,s,strings);
            case TURB: return db.delete(SContract.TURB_Entry.TABLE_NAME,s,strings);
            case MAPS: return db.delete(SContract.MAPS_Entry.TABLE_NAME,s,strings);
            default: return -1;
        }

    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues contentValues, @Nullable String s, @Nullable String[] strings) {
        return 0;
    }

    static{
        sUriMatcher.addURI(SContract.CONTENT_AUTHORITY, SContract.PATH_PH, PH);
        sUriMatcher.addURI(SContract.CONTENT_AUTHORITY, SContract.PATH_PH+"/#", PH_ID);

        sUriMatcher.addURI(SContract.CONTENT_AUTHORITY, SContract.PATH_TEMP, TEMP);
        sUriMatcher.addURI(SContract.CONTENT_AUTHORITY, SContract.PATH_TEMP+"/#", TEMP_ID);

        sUriMatcher.addURI(SContract.CONTENT_AUTHORITY, SContract.PATH_TURB, TURB);
        sUriMatcher.addURI(SContract.CONTENT_AUTHORITY, SContract.PATH_TURB+"/#", TURB_ID);

        sUriMatcher.addURI(SContract.CONTENT_AUTHORITY,SContract.PATH_MAPS,MAPS);
        sUriMatcher.addURI(SContract.CONTENT_AUTHORITY,SContract.PATH_MAPS+"/#",MAPS_ID);


    }
}
