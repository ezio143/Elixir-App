package com.example.dell.elixir.SQL_db;

import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by Ezio on 3/7/2018.
 */

public final class SContract {


    private SContract(){}

    public static final String CONTENT_AUTHORITY = "com.example.dell.elixir.SQL_db";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://"+CONTENT_AUTHORITY);
    public static final String PATH_PH = "ph";
    public static final String PATH_TEMP = "temp";
    public static final String PATH_TURB = "turb";
    public static final String PATH_MAPS = "maps";



    public static final class PH_Entry implements BaseColumns {

        public static final String TABLE_NAME = "ph";
        public static final String _ID = BaseColumns._ID;
        public static String COLUMN_PH = "ph_value";
        public static String Date_Time = "date_time";

        public static final Uri CONTENT_URI = Uri.withAppendedPath(BASE_CONTENT_URI,PATH_PH);

    }


    public static final class TEMP_Entry implements BaseColumns {

        public static final String TABLE_NAME = "temps";
        public static final String _ID = BaseColumns._ID;
        public static String COLUMN_TEMP = "temp_value";
        public static String Date_Time = "date_time";

        public static final Uri CONTENT_URI = Uri.withAppendedPath(BASE_CONTENT_URI,PATH_TEMP);

    }


    public static final class TURB_Entry implements BaseColumns {

        public static final String TABLE_NAME = "turb";
        public static final String _ID = BaseColumns._ID;
        public static String COLUMN_TURB = "turb_value";
        public static String Date_Time = "date_time";


        public static final Uri CONTENT_URI = Uri.withAppendedPath(BASE_CONTENT_URI,PATH_TURB);

    }


    public static final class MAPS_Entry implements BaseColumns {

        public static final String TABLE_NAME = "maps";
        public static final String _ID = BaseColumns._ID;
        public static String COLUMN_LONG = "longitude";
        public static String COLUMN_LAT = "latitude";
        public static String Date_Time = "date_time";

        public static final Uri CONTENT_URI = Uri.withAppendedPath(BASE_CONTENT_URI,PATH_MAPS);

    }


}
